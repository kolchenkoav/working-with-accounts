package transfer;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferTask implements Runnable {

    private static final int MAX_SLEEP_TIME = 2000;
    private static final int TRANSACTION_LIMIT = 30;
    private static final int MONEY_FOR_MOVE = 5000;

    private static final AtomicInteger transactionCount = new AtomicInteger(0);

    private final List<Account> accounts;
    private final Random random = new Random();

    public TransferTask(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public void run() {
        while (transactionCount.get() < TRANSACTION_LIMIT) {
            int fromIndex = random.nextInt(accounts.size());
            int toIndex = random.nextInt(accounts.size());
            while (fromIndex == toIndex) {
                toIndex = random.nextInt(accounts.size());
            }

            Account fromAccount = accounts.get(fromIndex);
            Account toAccount = accounts.get(toIndex);

            int amount = random.nextInt(MONEY_FOR_MOVE);

            transferMoney(fromAccount, toAccount, amount);
        }
    }

    private synchronized void transferMoney(Account fromAccount, Account toAccount, int amount) {
        transactionCount.incrementAndGet();

        if (fromAccount == null && toAccount == null) {
            return;
        }

        try {
            assert fromAccount != null;
            fromAccount.withdrawMoney(amount);
            toAccount.addMoney(amount);

            log.info("# {} Перевод {} со счета [{}] на счет [{}] выполнен",
                transactionCount.get(), amount, fromAccount.getId(), toAccount.getId());
        } catch (IllegalArgumentException e) {
            log.warn(
                "Недостаточно средств для перевода со счета [{}] (На счете: {} а сумма перевода: {})",
                fromAccount.getId(), fromAccount.getMoney(), amount);
        }

        try {
            Thread.sleep(random.nextInt(MAX_SLEEP_TIME / 2) + (long) MAX_SLEEP_TIME / 2);
        } catch (InterruptedException e) {
            log.warn("Поток прерван", e);
        }
    }
}

