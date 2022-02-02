package binary.wz.concurrency.binary.wz.concurrency.basic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binarywz
 * @date 2022/2/2 17:52
 * @description:
 */
@Slf4j
public class ReentrantLockTryLock {
    private static final ReentrantLock LOCK = new ReentrantLock();
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.info("t1 try to acquire lock");
            if (!LOCK.tryLock()) {
                log.info("t1 acquire lock failed");
                return;
            }
            log.info("t1 acquire lock");
            LOCK.unlock();
        }, "t1");
        LOCK.lock();
        log.info("main acquire lock");
        t1.start();
    }
}
