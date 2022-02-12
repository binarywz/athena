package binary.wz.concurrent.basic.unsafe;

import sun.misc.Unsafe;

/**
 * @author binarywz
 * @date 2022/2/12 22:32
 * @description:
 */
public class AtomicAccount implements Account {
    private volatile int value;
    private static final long valueOffset;
    private static final Unsafe UNSAFE;
    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(AtomicAccount.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public void decrement(int amount) {
        int prev;
        do {
            prev = this.value;
        } while (!UNSAFE.compareAndSwapInt(this, valueOffset, prev, prev - amount));
    }

    public AtomicAccount(int value) {
        this.value = value;
    }

    @Override
    public Integer getBalance() {
        return getValue();
    }

    @Override
    public void withdraw(Integer amount) {
        decrement(amount);
    }
}
