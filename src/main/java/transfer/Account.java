package transfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private final String id;
    private int money;

    public Account(String id, int money) {
        this.id = id;
        this.money = money;
    }

    public synchronized void addMoney(int amount) {
        money += amount;
    }

    public synchronized void withdrawMoney(int amount) {
        if (money < amount) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
        money -= amount;
    }

    @Override
    public String toString() {
        return "Account{" +
            "счет: " + id + '\'' +
            ", сумма: " + money +
            '}';
    }
}
