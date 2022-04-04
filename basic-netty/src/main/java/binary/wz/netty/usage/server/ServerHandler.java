package binary.wz.netty.usage.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author binarywz
 * @date 2022/3/31 22:58
 * @description:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 当有客户端链接后，添加到channelGroup通信组
        ServerContext.channelGroup.add(ctx.channel());
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("连接报告开始");
        System.out.println("连接报告信息: 有一客户端连接到本服务端");
        System.out.println("连接报告IP: " + channel.localAddress().getHostString());
        System.out.println("连接报告Port: " + channel.localAddress().getPort());
        System.out.println("连接报告完毕");

        // 通知客户端连接建立成功
        String content = new Date() + " " + channel.localAddress().getHostString() + " connect success...";
        ctx.writeAndFlush(content);
    }

    /**
     * 客户端主动断开连接后，通道变为不活跃
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开连接: " + ctx.channel().localAddress().toString());
        // 当有客户端退出后，从channelGroup中移除。
        ServerContext.channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理消息
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()) + " 接收到消息: " + msg);
        String content = new Date() + " " + ctx.channel().localAddress().toString() + " " + msg + "\r\n";
        ServerContext.channelGroup.writeAndFlush(content);
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
