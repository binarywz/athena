package binary.wz.concurrency.basic.atomic.reference;

import java.math.BigDecimal;

/**
 * @author binarywz
 * @date 2022/2/11 21:51
 * @description:
 */
public interface DecimalAccount {
    /**
     * 获取余额
     * @return
     */
    BigDecimal getBalance();

    /**
     * 取款
     * @param amount
     */
    void withdraw(BigDecimal amount);
}
