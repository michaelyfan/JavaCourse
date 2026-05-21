package banking;

import java.math.BigDecimal;
import banking.exceptions.*;

public class BankAccount {
    private BigDecimal balance;
    private final static BigDecimal ZERO = new BigDecimal("0.0");
    private final static double ZERO_DOUBLE = 0.0;

    public BankAccount() {
        this(new BigDecimal("0.0"));
    }

    public BankAccount(BigDecimal balance) {
        if (balance.compareTo(ZERO) < 0) {
            throw new InvalidMoneyException("Initial balance must not be negative");
        }
        this.balance = balance;
    }

    public BankAccount(Double balance) {
        this(BigDecimal.valueOf(balance));
    }

    public BigDecimal balance() {
        return this.balance;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(ZERO) <= 0) {
            throw new InvalidMoneyException("Input must be positive");
        }

        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(ZERO) <= 0) {
            throw new InvalidMoneyException("Input must be positive");
        }

        BigDecimal temp = this.balance.subtract(amount);
        if (temp.compareTo(ZERO) < 0) {
            throw new OverdrawException("Not enough funds!");
        }

        this.balance = temp;
    }

    public void deposit(Double amount) {
        this.deposit(BigDecimal.valueOf(amount));
    }

    public void withdraw(Double amount) {
        this.withdraw(BigDecimal.valueOf(amount));
    }
}
