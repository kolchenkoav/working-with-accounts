package main;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Transaction implements Runnable {
    private final Account from;
    private final Account to;
    private final int amount;

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
                    log.info("Transfer {} from [{}] to [{}]",
                        amount,
                        from.getId(),
                        to.getId());
                } else {
                    log.error("Insufficient funds ({}) on account [{}]",
                        from.getMoney(),
                        from.getId());
                }
            }
        }
    }
}
