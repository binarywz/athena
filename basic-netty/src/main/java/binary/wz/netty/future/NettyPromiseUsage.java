package binary.wz.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author binarywz
 * @date 2022/4/3 19:09
 * @description:
 */
@Slf4j
public class NettyPromiseUsage {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.创建 EventLoop
        EventLoop executor = new NioEventLoopGroup().next();
        // 2.主动创建 promise
        DefaultPromise<Integer> promise = new DefaultPromise<>(executor);
        new Thread(() -> {
            log.debug("execute something...");
            try {
                int i = 1 / 0;
                Thread.sleep(1_000);
                promise.setSuccess(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();
        // 3.接收结果
        log.debug("wait result...");
        log.debug("result: {}", promise.get());
    }
}
