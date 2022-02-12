package binary.wz.concurrent.basic.reentrantlock;

import binary.wz.concurrent.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author binarywz
 * @date 2022/2/2 17:39
 * @description:
 */
@Slf4j
public class ReentrantLockInterrupt {

    private final static ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args) {
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
                log.info("t1 can't acquire lock, return");
                return;
            }
            log.info("t1 acquire lock");
            LOCK.unlock();
        }, "t1");
        LOCK.lock();
        t1.start();
        Sleeper.sleep(1);
        log.info("interrupt t1");
        t1.interrupt();
    }
}
