package banking;

import banking.exceptions.InvalidMoneyException;
import banking.exceptions.OverdrawException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setup() {
        bankAccount = new BankAccount();
    }

    @Test
    void depositDoubleShouldIncreaseBalance() {
        bankAccount.deposit(10.0);

        assertEquals(bankAccount.balance(), BigDecimal.valueOf(10.0));
    }

    @Test
    void depositBigDecimalShouldIncreaseBalance() {
        bankAccount.deposit(new BigDecimal("10"));

        assertEquals(bankAccount.balance(), BigDecimal.valueOf(10.0));
    }

    @Test
    void withdrawDoubleShouldIncreaseBalance() {
        bankAccount = new BankAccount(10.0);

        bankAccount.withdraw(7.0);

        assertEquals(bankAccount.balance(), BigDecimal.valueOf(3.0));
    }

    @Test
    void withdrawBigDecimalShouldIncreaseBalance() {
        bankAccount = new BankAccount(10.0);
        bankAccount.withdraw(new BigDecimal("7"));

        assertEquals(bankAccount.balance(), BigDecimal.valueOf(3.0));
    }

    @Test
    void constructingWithNegativeShouldThrow() {
        assertThrows(InvalidMoneyException.class, () ->
                bankAccount = new BankAccount(-10.0)
        );
    }

    @Test
    void operationsShouldApplyOrThrow() {
        bankAccount.deposit(10.0);
        assertEquals(BigDecimal.valueOf(10.0), bankAccount.balance());

        bankAccount.deposit(17.0);
        assertEquals(BigDecimal.valueOf(27.0), bankAccount.balance());

        bankAccount.withdraw(7.0);
        assertEquals(BigDecimal.valueOf(20.0), bankAccount.balance());

        assertThrows(OverdrawException.class, () ->
                bankAccount.withdraw(21.0)
        );

        bankAccount.withdraw(19.0);
        assertEquals(BigDecimal.valueOf(1.0), bankAccount.balance());
    }
}
