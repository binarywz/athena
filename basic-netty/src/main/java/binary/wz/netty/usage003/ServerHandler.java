package binary.wz.netty.usage003;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author binarywz
 * @date 2022/3/31 21:29
 * @description:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("连接报告开始");
        System.out.println("连接报告信息: 有一客户端连接到本服务端");
        System.out.println("连接报告IP: " + channel.localAddress().getHostString());
        System.out.println("连接报告Port: " + channel.localAddress().getPort());
        System.out.println("连接报告完毕");
        // TODO 执行下面代码会发生什么
//        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理消息
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()) + " 接收到消息: " + msg);
    }
}
