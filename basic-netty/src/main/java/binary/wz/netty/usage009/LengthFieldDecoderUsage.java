package binary.wz.netty.usage009;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author binarywz
 * @date 2022/4/1 22:08
 * @description:
 */
public class LengthFieldDecoderUsage {

    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                /**
                 * int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip
                 * maxFrameLength 最大帧长度
                 * lengthFieldOffset 长度字段偏移量
                 * lengthFieldLength 长度字段字节数
                 * lengthAdjustment 长度字段为基准，内容数据偏移量
                 * initialBytesToStrip 去除字节数(从头开始算)，initialBytesToStrip=lengthFieldLength+lengthAdjustment
                 */
                new LengthFieldBasedFrameDecoder(
                        1024, 0, 4, 1,5),
                new LoggingHandler(LogLevel.DEBUG)
        );

        // 4 个字节的内容长度，实际内容
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        send(buffer, "Hello, world");
        send(buffer, "Hi!");
        channel.writeInbound(buffer);
    }

    private static void send(ByteBuf buffer, String content) {
        byte[] bytes = content.getBytes(); // 实际内容
        int length = bytes.length; // 实际内容长度
        buffer.writeInt(length);
        buffer.writeByte(1);
        buffer.writeBytes(bytes);
    }
}
