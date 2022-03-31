package binary.wz.netty.usage004;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @author binarywz
 * @date 2022/3/31 22:12
 * @description:
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 基于换行符号解码
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 在管道中添加我们自己的接收数据实现方法
        socketChannel.pipeline().addLast(new ServerHandler());
    }
}
