package binary.wz.concurrent.basic.pool.custom;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author binarywz
 * @date 2022/2/13 18:15
 * @description:
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1, 1, 1000, TimeUnit.MILLISECONDS, (queue, task) -> {
            queue.offer(task, 1500, TimeUnit.MILLISECONDS);
        });
        for (int i = 0; i < 3; i++) {
            int j = i; // 变量i是变化的，lambda表达式不能直接使用
            threadPool.execute(() -> {
                log.info("execute task-{}", j);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
