package test;

import main.Account;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    private Account account;

    @BeforeEach
    public void setup() {
        account = new Account(UUID.randomUUID(), 10000);
    }

    @Test
    void testDeposit() {
        account.deposit(1000);
        assertEquals(11000, account.getMoney());
    }

    @Test
    void testWithdraw() {
        account.withdraw(1000);
        assertEquals(9000, account.getMoney());
    }
}
