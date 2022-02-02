package binary.wz.concurrency.basic;

import binary.wz.concurrency.util.Sleeper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author binarywz
 * @date 2022/2/2 17:19
 * @description:
 */
@Slf4j
public class WaitNotifyTests {
    private final Object LOCK = new Object();

    @Test
    public void WaitNotifyStatusTest() {
        new Thread(() -> {
            synchronized (LOCK) {
                log.debug("执行....");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码...."); // 断点
            }
        },"t1").start();
        new Thread(() -> {
            synchronized (LOCK) {
                log.debug("执行....");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("其它代码...."); // 断点
            }
        },"t2").start();

        Sleeper.sleep(0.5);
        log.debug("唤醒 LOCK 上其它线程");
        synchronized (LOCK) {
            LOCK.notifyAll(); // 唤醒obj上所有等待线程 断点
        }
    }
}
