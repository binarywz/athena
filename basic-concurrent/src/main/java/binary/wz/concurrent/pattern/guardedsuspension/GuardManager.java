package binary.wz.concurrent.pattern.guardedsuspension;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binarywz
 * @date 2022/1/26 23:02
 * @description: 解耦结果产生的线程跟结果接收的线程
 */
public class GuardManager {

    private static Map<Integer, GuardedObject> manager = new ConcurrentHashMap<>();

    private static AtomicInteger ID = new AtomicInteger(0);

    /**
     * 生成id
     * @return
     */
    private static int generateId() {
        return ID.addAndGet(1);
    }

    /**
     * 创建GuardedObject
     * @return
     */
    public static GuardedObject createGuardedObject() {
        GuardedObject guardedObject = new GuardedObject(generateId());
        manager.put(guardedObject.getId(), guardedObject);
        return guardedObject;
    }

    /**
     * 获取所有的id
     * @return
     */
    public static Set<Integer> getIds() {
        return manager.keySet();
    }

    /**
     * 根据id获取GuardedObject
     * @param id
     * @return
     */
    public static GuardedObject getGuardedObject(int id) {
        return manager.remove(id);
    }
}
