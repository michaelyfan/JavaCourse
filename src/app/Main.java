package app;

import banking.BankAccount;
import java.math.BigDecimal;
import shapes.*;
import java.util.HashSet;


public class Main {
    public static void main(String[] args) {
        // BankAccount ba = new BankAccount(new BigDecimal("100"));
        // System.out.println(ba.balance());

        HashSet<Shape> shapes = new HashSet<Shape>();
        shapes.add(new Circle(5));
        shapes.add(new Circle(5));
        shapes.add(new Circle(6));
        shapes.add(new Rectangle(3,4));
        shapes.add(new Rectangle(2,2));
        shapes.add(new Rectangle(2,2));
        System.out.println(shapes.size());
    }
}
