package app;

import banking.BankAccount;

import java.io.IOException;
import java.math.BigDecimal;

import logging.LoggingHashSet;
import logging.LoggingSet;
import shapes.*;
import java.util.HashSet;
import notification.*;
import java.util.List;
import record.*;


public class Main {
    public static void main(String[] args) {
        // BankAccount ba = new BankAccount(new BigDecimal("100"));
        // System.out.println(ba.balance());

        // HashSet<Shape> shapes = new HashSet<Shape>();
        // shapes.add(new Circle(5));
        // shapes.add(new Circle(5));
        // shapes.add(new Circle(6));
        // shapes.add(new Rectangle(3,4));
        // shapes.add(new Rectangle(2,2));
        // shapes.add(new Rectangle(2,2));
        // System.out.println(shapes.size());

//        SmsNotification sms = new SmsNotification("you got free stuff");
//        System.out.println(sms.summary());
//        System.out.println(sms.shouldRetry(0));
//        System.out.println(sms.shouldRetry(1));
//        System.out.println(sms.shouldRetry(2));
//        System.out.println(sms.shouldRetry(3));
//        System.out.println(sms.shouldRetry(4));

//        LoggingSet<Integer> lhs = new LoggingSet<>(new HashSet<>());
//        lhs.addAll(List.of(1,2,3));

        Result<Integer> ok = Result.success(42);
        Result<Integer> bad = Result.failure(new IOException("Disk not found"));
        System.out.println(ok.unwrapOrThrow());
        System.out.println(bad.unwrapOrThrow());

    }
}
