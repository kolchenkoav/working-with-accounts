package main;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Transaction implements Runnable {

    private final Account from;
    private final Account to;
    private final int amount;
    public static final Random random = new Random();

    public Transaction(Account from, Account to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        synchronized (from) {
            synchronized (to) {
                if (from.getMoney() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    log.info("{} Transfer {} from [{}] to [{}]",
                        Thread.currentThread().getName(),
                        amount,
                        from.getId(),
                        to.getId());
                } else {
                    log.error("{} Not transferred {}. Insufficient funds {} on account [{}]",
                        Thread.currentThread().getName(),
                        amount,
                        from.getMoney(),
                        from.getId());
                }
                try {
                    Thread.sleep(Transaction.random.nextInt(1000, 2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
