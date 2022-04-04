package binary.wz.netty.usage.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author binarywz
 * @date 2022/4/1 21:25
 * @description:
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 客户端与服务端建立连接成功
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("连接报告开始");
        System.out.println("连接到服务端，channelId: " + channel.id());
        System.out.println("连接报告IP: " + channel.localAddress().getHostString());
        System.out.println("连接报告Port: " + channel.localAddress().getPort());
        System.out.println("连接报告完毕");
    }

    /**
     * 客户端主动断开与服务端的连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().localAddress().toString() + "断开连接...");
    }

    /**
     * 处理消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理消息
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()) + " 接收到消息: " + msg);
        String content = new Date() + " " + ctx.channel().localAddress().toString() + " " + msg + "\r\n";
        ctx.writeAndFlush(content);
    }

    /**
     * 捕获异常，发生异常进行一些处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println("异常信息: \r\n" + cause.getMessage());
    }
}
