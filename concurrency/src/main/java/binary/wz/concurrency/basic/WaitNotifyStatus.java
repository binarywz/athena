package binary.wz.concurrency.basic;

import binary.wz.concurrency.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author binarywz
 * @date 2022/1/30 17:20
 * @description:
 */
@Slf4j
public class WaitNotifyStatus {
    final static Object LOCK = new Object();
    public static void main(String[] args) {
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
