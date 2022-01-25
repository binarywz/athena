package binary.wz.pattern.guardedsuspension;

/**
 * @author binarywz
 * @date 2022/1/25 23:00
 * @description:
 */
public class GuardedObject {
    /**
     * 结果
     */
    private Object response;

    /**
     * 获取结果
     * @return
     */
    public Object get() {
        synchronized (this) {
            /**
             * 没有结果则一直循环等待结果
             */
            while (response == null) {
                try {
                    this.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    /**
     * 产生结果
     */
    public void execute(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}
