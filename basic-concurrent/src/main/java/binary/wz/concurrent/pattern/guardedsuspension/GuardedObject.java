package binary.wz.concurrent.pattern.guardedsuspension;

/**
 * @author binarywz
 * @date 2022/1/25 23:00
 * @description:
 */
public class GuardedObject {
    /**
     * 标识id
     */
    private int id;
    /**
     * 结果
     */
    private Object response;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    /**
     * 获取结果
     * @param millis
     * @return
     */
    public Object get(long millis) {
        synchronized (this) {
            long base = System.currentTimeMillis(); // 开始时间
            long now = 0; // 经历的时间
            // 没有结果则一直循环等待结果
            while (response == null) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                try {
                    this.wait(delay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                now = System.currentTimeMillis() - base;
            }
            return response;
        }
    }

    /**
     * 产生结果
     */
    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}
