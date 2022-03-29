package binary.wz.netty.usage001;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author binarywz
 * @date 2022/3/29 23:59
 * @description:
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().start(7397);
    }

    private void start(int port) {
        /**
         * NioEventLoopGroup extends MultithreadEventLoopGroup
         * Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) // 非阻塞模式
                .option(ChannelOption.SO_BACKLOG, 128)
                .childHandler(new ServerChannelInitializer());
        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("netty server start...");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
