package binary.wz.concurrent.basic.atomic.adder;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author binarywz
 * @date 2022/2/12 10:02
 * @description: 原子累加器
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        // 普通原子类
        for (int i = 0; i < 5; i++) {
            execute(
                    () -> new AtomicLong(0),
                    (adder) -> adder.getAndIncrement()
            );
        }
        /**
         * 累加器: 在有竞争时，设置多个累加单元，thread-0累加cell[0]，thread-1累加cell[1]，
         * 最后将结果汇总。不同线程在累加时操作不同cell变量，因此减少了CAS的重试失败，从而提高性能。
         * 累加单元cell根据CPU核心数设定，不会超过CPU核心数。
         */
        for (int i = 0; i < 5; i++) {
            execute(
                    () -> new LongAdder(),
                    adder -> adder.increment()
            );
        }
    }

    /**
     * Supplier: () -> 结果    提供累加器对象
     * Consumer: (参数) ->     执行累加操作
     * @param adderSupplier
     * @param action
     * @param <T>
     */
    private static <T> void execute(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        // 4 个线程，每人累加 50 万
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500_000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        log.info("elapsed: {}, value: {}", (end - start) / 1000_000, adder);
    }
}
