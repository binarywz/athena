package binary.wz.netty.usage001;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author binarywz
 * @date 2022/3/30 0:00
 * @description:
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("连接报告开始");
        System.out.println("连接报告信息: 有一客户端连接到本服务端");
        System.out.println("连接报告IP: " + socketChannel.localAddress().getHostString());
        System.out.println("连接报告Port: " + socketChannel.localAddress().getPort());
        System.out.println("连接报告完毕");
    }
}
