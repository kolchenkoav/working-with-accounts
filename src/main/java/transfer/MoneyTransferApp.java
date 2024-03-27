package transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyTransferApp {

    private static final int NUM_ACCOUNTS = 4;
    private static final int INITIAL_BALANCE = 10000;
    private static final int NUM_THREADS = 2;

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts.add(new Account("account_" + i, INITIAL_BALANCE));
        }
        printAccounts(accounts, "Баланс до транзакций");

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(new TransferTask(accounts));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                log.warn("ExecutorService не завершился за отведенное время");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Ошибка при ожидании завершения потоков", e);
            executor.shutdownNow();
        }

        printAccounts(accounts, "Баланс после всех транзакций");
    }

    private static void printAccounts(List<Account> accounts, String title) {
        log.info(title);
        accounts.forEach(account -> log.info(account.toString()));
        log.info("Итого: {}", accounts.stream().mapToInt(Account::getMoney).sum());
    }
}
