package binary.wz.netty.usage002;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author binarywz
 * @date 2022/3/31 21:14
 * @description:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理消息
        ByteBuf buf = (ByteBuf) msg;
        byte[] content = new byte[buf.readableBytes()];
        buf.readBytes(content);
        System.out.println(new Date() + " 接收到消息: ");
        System.out.println(new String(content, Charset.forName("GBK")));
    }
}
