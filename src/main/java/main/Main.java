package main;

import static main.Transaction.random;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 8;
        int numberOfAccounts = 4;       // Должно быть кратно 4

        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);

        List<Account> accounts = Account.generateAccounts(numberOfAccounts);

        executeTransaction(accounts, service, numberOfAccounts);

        service.shutdown();
        if (service.awaitTermination(1, TimeUnit.MINUTES)) {
            printBalances(accounts);
        }
    }

    private static void executeTransaction(List<Account> accounts,
        ExecutorService service, int numberOfAccounts) throws InterruptedException {

        int numberOfTransactions = 30;
        int upperLimitOfTheAmountOfMoney = 5000;
        log.info("=== Transaction logs ===");
        for (int i = 0; i < numberOfTransactions; i++) {
            for (int j = 0; j < numberOfAccounts; j += 2) {
                service.submit(
                    new Transaction(accounts.get(j), accounts.get(j + 1),
                        random.nextInt(upperLimitOfTheAmountOfMoney)));
            }
        }
    }

    private static void printBalances(List<Account> accounts) {
        log.info("Final balances: a1= {}, a2= {}, a3= {}, a4= {}",
            accounts.get(0).getMoney(),
            accounts.get(1).getMoney(),
            accounts.get(2).getMoney(),
            accounts.get(3).getMoney());

    }
}
