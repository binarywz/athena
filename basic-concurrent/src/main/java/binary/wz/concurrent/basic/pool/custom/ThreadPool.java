package binary.wz.concurrent.basic.pool.custom;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author binarywz
 * @date 2022/2/13 18:01
 * @description:
 */
@Slf4j
public class ThreadPool {
    /** 任务队列 **/
    private BlockingQueue<Runnable> taskQueue;

    /** 线程集合 **/
    private Set<Worker> workers = new HashSet<>();

    /** 核心线程数 **/
    private int coreSize;

    /** 获取任务的超时时间 **/
    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, int capacity, long timeout, TimeUnit timeUnit, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(capacity);
        this.rejectPolicy = rejectPolicy;
    }

    /**
     * 执行任务
     * 1.若任务数没超过coreSize时，直接交给worker对象执行
     * 1.若任务数超过coreSize时，加入任务队列暂存
     */
    public void execute(Runnable task) {
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.info("add worker: {}, task: {}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        /**
         * 执行任务
         * 1.当task不为空，执行任务
         * 2.当task执行完，从任务队列中获取任务并执行
         */
        @Override
        public void run() {
            while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("worker execute failed", e);
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                workers.remove(this);
                log.info("worker-{} removed", this);
            }
        }
    }
}
