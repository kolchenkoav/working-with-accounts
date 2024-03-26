package main;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 2;
        int numberOfAccounts = 4;       // Должно быть кратно 4
        int numberOfTransactions = 30;

        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);

        List<Account> accounts = Account.generateAccounts(numberOfAccounts);

        log.info("=== Transaction logs ===");
        Random random = new Random();
        for (int i = 0; i < numberOfTransactions; i++) {
            log.info("Transaction # : {}", i + 1);
            for (int j = 0; j < numberOfAccounts; j += 2) {
                service.submit(
                    new Transaction(accounts.get(j), accounts.get(j + 1), random.nextInt(5000)));
            }
            Thread.sleep(random.nextInt(1000, 2000));
        }

        service.shutdown();
        if (service.awaitTermination(1, TimeUnit.MINUTES)) {
            log.info("Final balances: a1= {}, a2= {}, a3= {}, a4= {}", accounts.get(0).getMoney(),
                accounts.get(1).getMoney(), accounts.get(2).getMoney(), accounts.get(3).getMoney());
        } else {
            log.error("Something went wrong");
        }
    }
}
