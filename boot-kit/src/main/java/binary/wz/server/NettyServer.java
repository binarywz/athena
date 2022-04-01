package binary.wz.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @author binarywz
 * @date 2022/4/1 23:15
 * @description:
 */
@Component("nettyServer")
public class NettyServer {
    private Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Value("${netty.host}")
    private String host;
    @Value("${netty.port}")
    private int port;

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    @PostConstruct
    public void init() {
        ChannelFuture channelFuture = this.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> this.destroy()));
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }

    private ChannelFuture start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childHandler(new ServerChannelInitializer());
        ChannelFuture future = null;
        try {
            future = serverBootstrap.bind(buildAddress()).syncUninterruptibly();
            channel = future.channel();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (future != null && future.isSuccess()) {
                logger.info("netty server start success...");
            } else {
                logger.error("netty server start failed...");
            }
        }
        return future;
    }

    private InetSocketAddress buildAddress() {
        return new InetSocketAddress(host, port);
    }

    public void destroy() {
        if (null == channel) return;
        channel.close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return channel;
    }

}
