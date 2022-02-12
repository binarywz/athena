package binary.wz.concurrent.basic.atomic.reference;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class DecimalAccountImpl implements DecimalAccount {
    private AtomicReference<BigDecimal> balance;

    public DecimalAccountImpl(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
        BigDecimal value;
        do {
            value = balance.get();
        } while (!balance.compareAndSet(value, value.subtract(amount)));
    }
}