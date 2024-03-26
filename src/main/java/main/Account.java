package main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {

    private final UUID id;
    private final AtomicInteger money;

    public Account(UUID id, int money) {
        this.id = id;
        this.money = new AtomicInteger(money);
    }

    public static List<Account> generateAccounts(int numberOfAccounts) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < numberOfAccounts; i++) {
            accounts.add(new Account(UUID.randomUUID(), 10000));
        }
        return accounts;
    }

    public UUID getId() {
        return id;
    }

    public int getMoney() {
        return money.get();
    }

    public void withdraw(int amount) {
        money.addAndGet(-amount);
    }

    public void deposit(int amount) {
        money.addAndGet(amount);
    }
}
