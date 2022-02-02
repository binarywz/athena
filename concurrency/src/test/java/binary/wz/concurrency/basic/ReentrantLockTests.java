package binary.wz.concurrency.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binarywz
 * @date 2022/2/2 17:13
 * @description:
 */
@Slf4j
public class ReentrantLockTests {

    private final ReentrantLock LOCK = new ReentrantLock();

    /**
     * ReentrantLock基本使用方法
     */
    @Test
    public void reentrantLockBasic() {
        LOCK.lock(); // lock()方法不可打断
        try {
            log.info("main acquire lock, reference: {}", LOCK);
            m1();
        } finally {
            LOCK.unlock();
        }
    }

    private void m1() {
        LOCK.lock();
        try {
            log.info("m1 acquire lock, reference: {}", LOCK);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * TODO 打断锁使用JUNIT存在问题，后续分析
     */
    @Test
    public void reentrantLockInterrupt() {
        Thread t1 = new Thread(() -> {
            try {
                /**
                 * 如果没有竞争那么此方法就会获取LOCK的对象锁
                 * 如果有竞争就会进入阻塞队列，可以被其他线程用interrupt()方法打断
                 */
                log.info("t1 try to acquire lock");
                LOCK.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.info("t1 can't acquire lock");
                return;
            }
            log.info("t1 acquire lock");
            LOCK.unlock();
        }, "t1");
        LOCK.lock();
        t1.start();
    }

}
