package test;

import org.junit.jupiter.api.Test;
import transfer.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void testAddMoney() {
        Account account = new Account("test", 100);
        account.addMoney(50);
        assertEquals(150, account.getMoney());
    }

    @Test
    void testWithdrawMoney() {
        Account account = new Account("test", 100);
        account.withdrawMoney(50);
        assertEquals(50, account.getMoney());
    }

    @Test
    void testWithdrawMoneyInsufficientFunds() {
        Account account = new Account("test", 100);
        assertThrows(IllegalArgumentException.class, () -> account.withdrawMoney(150));
    }
}
