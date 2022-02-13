package binary.wz.concurrent.basic.pool.custom;

/**
 * @author binarywz
 * @date 2022/2/13 19:34
 * @description:
 */
@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}
