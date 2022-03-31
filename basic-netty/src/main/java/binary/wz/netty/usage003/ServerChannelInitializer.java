package binary.wz.netty.usage003;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;

/**
 * @author binarywz
 * @date 2022/3/31 21:31
 * @description:
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        /**
         * 解码器:
         * 1.基于换行符号解码器
         * 2.字符串解码器
         */
        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        socketChannel.pipeline().addLast(new ServerHandler());
    }
}
