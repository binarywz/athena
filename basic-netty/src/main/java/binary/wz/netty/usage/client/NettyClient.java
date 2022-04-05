package binary.wz.netty.usage.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author binarywz
 * @date 2022/4/1 21:24
 * @description:
 */
public class NettyClient {

    public static void main(String[] args) {
        new NettyClient().connect("localhost", 7397);
    }

    private void connect(String address, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.AUTO_READ, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .handler(new ClientChannelInitializer());
        try {
            // 此处为方便调试写成这样
            ChannelFuture future = bootstrap.connect(address, port);
            Channel channel = future.sync().channel();
            System.out.println("netty client start...");
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
