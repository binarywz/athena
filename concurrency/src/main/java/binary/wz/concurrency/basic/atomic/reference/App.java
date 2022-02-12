package binary.wz.concurrency.basic.atomic.reference;

import binary.wz.concurrency.basic.atomic.reference.DecimalAccountImpl;
import binary.wz.concurrency.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author binarywz
 * @date 2022/2/11 22:01
 * @description: 原子引用
 */
@Slf4j
public class App {
    public static void main(String[] args) {
//        atomicReferenceUsage();
        atomicStampReferenceUsage();
    }

    /**
     * AtomicReference示例
     */
    public static void atomicReferenceUsage() {
        DecimalAccountImpl account = new DecimalAccountImpl(BigDecimal.valueOf(100L));
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info("balance is {}", account.getBalance());
    }

    /**
     * AtomicStampedReference示例
     */
    public static void atomicStampReferenceUsage() {
        AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);
        String prev = ref.getReference(); // 获取值
        Integer stamp = ref.getStamp();   // 获取版本号
        log.info("prev: {}, stamp: {}", prev, stamp);
        doSomething(ref);
        Sleeper.sleep(1);
        log.info("change A->C: {}", ref.compareAndSet(prev, "C", stamp, stamp + 1));
        log.info("stamp: {}", ref.getStamp());
    }

    public static void doSomething(AtomicStampedReference<String> ref) {
        new Thread(() -> {
            log.info("change A->B: {}", ref.compareAndSet(ref.getReference(), "B", ref.getStamp(), ref.getStamp() + 1));
            log.info("stamp: {}", ref.getStamp());
        }, "t1").start();
        Sleeper.sleep(0.5);
        new Thread(() -> {
            log.info("change B->A: {}", ref.compareAndSet(ref.getReference(), "A", ref.getStamp(), ref.getStamp() + 1));
            log.info("stamp: {}", ref.getStamp());
        }, "t2").start();
    }
}
