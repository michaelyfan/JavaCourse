public class BankAccount {
    private BigDecimal balance;

    constructor() {
        this(new BigDecimal(0.0))
    }

    constructor(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void deposit(BigDecimal amount) {

    }

    public void withdraw(BigDecimal amount) {

    }
}