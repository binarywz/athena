package binary.wz.concurrent.basic.unsafe;

/**
 * @author binarywz
 * @date 2022/2/12 22:36
 * @description:
 */
public interface Account {
    /**
     * 获取余额
     * @return
     */
    Integer getBalance();

    /**
     * 取款
     * @param amount
     */
    void withdraw(Integer amount);
}
