package binary.wz.netty.middle001.server;

import binary.wz.netty.middle001.server.common.CommonServerHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author binarywz
 * @date 2022/4/2 23:52
 * @description:
 */
public class ServerHandler extends CommonServerHandler {
    @Override
    protected void sendData(ChannelHandlerContext ctx) {
        sentFlag = true;
        ctx.writeAndFlush( "111111111122222222223333333333\r\n", getChannelProgressivePromise(ctx,
                ignore -> {
                    if (ctx.channel().isWritable() && !sentFlag) sendData(ctx);
                }));
    }
}
