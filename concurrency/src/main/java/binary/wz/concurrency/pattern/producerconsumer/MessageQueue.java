package binary.wz.concurrency.pattern.producerconsumer;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author binarywz
 * @date 2022/1/30 15:18
 * @description: 消息队列类，Java线程间通信
 */
@Slf4j
public class MessageQueue {

    /**
     * 消息的队列集合
     */
    private LinkedList<Message> queue = new LinkedList<>();
    /**
     * 队列容量
     */
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取消息
     * @return
     */
    public Message take() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    log.info("queue is empty, consumer wait.");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 从队列头部获取消息并返回
            Message m = queue.removeFirst();
            log.info("consumer take message: {}", m);
            queue.notifyAll();
            return m;
        }
    }

    /**
     * 存入消息
     * @param m
     */
    public void put(Message m) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    log.info("queue is full, producer wait.");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 将消息加入队列尾部
            queue.addLast(m);
            log.info("producer put message: {}", m);
            queue.notifyAll();
        }
    }
}
