package binary.wz.concurrency.basic.deadlock;

import binary.wz.concurrency.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Philosopher extends Thread {
    Chopstick left;
    Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            //　尝试获得左手筷子
            synchronized (left) {
                // 尝试获得右手筷子
                synchronized (right) {
                    eat();
                }
            }
        }
    }

    private void eat() {
        log.debug("eating...");
        Sleeper.sleep(0.5);
    }
}