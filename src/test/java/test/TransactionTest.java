package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import main.Account;
import main.Transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionTest {

    private Account fromAccount;
    private Account toAccount;
    private Transaction transaction;

    @BeforeEach
    public void setup() {
        fromAccount = new Account(UUID.randomUUID(), 10000);
        toAccount = new Account(UUID.randomUUID(), 5000);
        transaction = new Transaction(fromAccount, toAccount, 2000);
    }

    @Test
    void testRun() {
        transaction.run();
        assertEquals(8000, fromAccount.getMoney());
        assertEquals(7000, toAccount.getMoney());
    }
}
