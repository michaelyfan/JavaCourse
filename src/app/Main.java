package app;

import banking.BankAccount;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BankAccount ba = new BankAccount(new BigDecimal("100"));
        System.out.println(ba.balance());
    }
}
