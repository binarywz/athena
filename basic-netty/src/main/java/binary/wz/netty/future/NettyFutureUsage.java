package binary.wz.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author binarywz
 * @date 2022/4/3 18:51
 * @description:
 */
@Slf4j
public class NettyFutureUsage {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoopGroup executors = new NioEventLoopGroup();
        EventLoop executor = executors.next();
        Future<Integer> future = executor.submit(() -> {
            try {
                log.debug("execute something...");
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 50;
        });
        // 同步方式获取
//        log.debug("wait result...");
//        log.debug("result: {}", future.get());
        // 异步方式获取
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.debug("result: {}", future.get());
            }
        });
    }
}
