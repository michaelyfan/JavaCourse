import java.math.BigDecimal;

public class BankAccount {
    private BigDecimal balance;
    private final static BigDecimal ZERO = new BigDecimal("0.0");

    public BankAccount() {
        this(new BigDecimal("0.0"));
    }

    public BankAccount(BigDecimal balance) {
        if (balance.compareTo(ZERO) < 0) {
            throw new InvalidMoneyException("Initial balance must not be negative");
        }
        this.balance = balance;
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

    public static void main(String[] args) {
        BankAccount ba = new BankAccount(new BigDecimal("100"));
        System.out.println(ba.balance());
    }
}

class OverdrawException extends IllegalStateException {
    public OverdrawException(String msg) {
        super(msg);
    }
}

class InvalidMoneyException extends IllegalArgumentException {
    public InvalidMoneyException(String msg) {
        super(msg);
    }
}