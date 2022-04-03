package binary.wz.netty.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author binarywz
 * @date 2022/4/3 18:41
 * @description:
 */
@Slf4j
public class JdkFutureUsage {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // 2.提交任务
        Future<Integer> future = executor.submit(() -> {
            try {
                log.debug("execute something...");
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 50;
        });
        // 3.主线程通过future获取结果
        log.debug("wait result...");
        log.debug("result: {}", future.get());
    }
}
