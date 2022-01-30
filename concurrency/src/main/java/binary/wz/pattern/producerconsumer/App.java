package binary.wz.pattern.producerconsumer;

import java.util.concurrent.TimeUnit;

/**
 * @author binarywz
 * @date 2022/1/30 15:31
 * @description:
 */
public class App {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                queue.put(new Message(id, "value-" + id));
            }, "生产者-" + i).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message m = queue.take();
            }
        }, "消费者").start();
    }
}
