package binary.wz.netty.traffic.server.common;

import io.netty.channel.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author binarywz
 * @date 2022/4/2 23:52
 * @description:
 */
public abstract class CommonServerHandler extends SimpleChannelInboundHandler<String> {

    protected boolean sentFlag;
    private Runnable counterTask;
    private AtomicLong consumeMsgLength = new AtomicLong();
    private long priorProgress;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        counterTask = () -> {
            System.out.println("counter task start...");
            while (true) {
                try {
                    Thread.sleep(500);
                    long length = consumeMsgLength.getAndSet(0);
                    if (0 == length) continue;
                    System.out.println("数据发送速率(B/S): " + length);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        super.handlerAdded(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sendData(ctx);
        // 启动监控线程
        new Thread(counterTask).start();
    }

    protected abstract void sendData(ChannelHandlerContext ctx);

    protected ChannelProgressivePromise getChannelProgressivePromise(ChannelHandlerContext ctx,
                                                                     Consumer<ChannelProgressiveFuture> completedAction) {
        ChannelProgressivePromise channelProgressivePromise = ctx.newProgressivePromise();
        channelProgressivePromise.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                consumeMsgLength.addAndGet(progress - priorProgress);
                priorProgress = progress;
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                sentFlag = false;
                if (future.isSuccess()) {
                    System.out.println("message send success...");
                    priorProgress -= 10;
                    Optional.ofNullable(completedAction).ifPresent(action -> action.accept(future));
                } else {
                    System.out.println("message send failed...");
                    future.cause().printStackTrace();
                }
            }
        });
        return channelProgressivePromise;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("Server received msg: " + s);
    }
}
