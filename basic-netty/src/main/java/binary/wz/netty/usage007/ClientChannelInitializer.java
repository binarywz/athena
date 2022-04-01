package binary.wz.netty.usage007;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author binarywz
 * @date 2022/4/1 21:13
 * @description:
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        System.out.println("连接报告开始");
        System.out.println("连接到服务端，channelId: " + channel.id());
        System.out.println("连接报告完毕");
    }
}
