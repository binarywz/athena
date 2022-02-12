package binary.wz.concurrent.basic.unsafe;

import binary.wz.concurrent.basic.atomic.reference.DecimalAccountImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author binarywz
 * @date 2022/2/12 22:21
 * @description:
 */
@Slf4j
public class App {
    public static void main(String[] args) throws NoSuchFieldException {
//        basicUnsafeUsage();
        atomicAccountUsage();
    }

    public static void basicUnsafeUsage() throws NoSuchFieldException {
        Unsafe unsafe = UnsafeAccessor.getUnsafe();

        /**
         * 1.获取域的偏移地址
         * Unsafe通过内存偏移量定位到对象的属性
         */
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher t = new Teacher();
        // 2. 执行 cas 操作
        unsafe.compareAndSwapInt(t, idOffset, 0, 1);
        unsafe.compareAndSwapObject(t, nameOffset, null, "张三");

        // 3. 验证
        log.info("Teacher: {}", t);
    }

    /**
     * 自定义AtomicAccount
     */
    public static void atomicAccountUsage() {
        AtomicAccount account = new AtomicAccount(100);
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info("balance is {}", account.getBalance());
    }
}

@Data
class Teacher {
    volatile int id;
    volatile String name;
}
