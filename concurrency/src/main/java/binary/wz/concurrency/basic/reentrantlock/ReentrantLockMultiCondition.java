package binary.wz.concurrency.basic.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static binary.wz.concurrency.util.Sleeper.sleep;

/**
 * @author binarywz
 * @date 2022/2/4 13:37
 * @description: ReentrantLock多条件变量
 */
@Slf4j
public class ReentrantLockMultiCondition {
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;
    static ReentrantLock ROOM = new ReentrantLock();
    // 等待烟的休息室
    static Condition waitCigaretteSet = ROOM.newCondition();
    // 等外卖的休息室
    static Condition waitTakeoutSet = ROOM.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("t1 has cigarette?[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("t1 no cigarette");
                    try {
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("t1 begin work");
            } finally {
                ROOM.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("t2 has takeout?[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("t2 no takeout");
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("t2 begin work");
            } finally {
                ROOM.unlock();
            }
        }, "t2").start();

        sleep(1);
        new Thread(() -> {
            ROOM.lock();
            try {
                hasTakeout = true;
                waitTakeoutSet.signal();
            } finally {
                ROOM.unlock();
            }
        }, "Takeout").start();

        sleep(1);
        new Thread(() -> {
            ROOM.lock();
            try {
                hasCigarette = true;
                waitCigaretteSet.signal();
            } finally {
                ROOM.unlock();
            }
        }, "Cigarette").start();
    }
}
