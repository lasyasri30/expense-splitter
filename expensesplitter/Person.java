package com.expensesplitter;

public class Person {
    private String name;
    private double balance; // positive = owed money, negative = owes money

    public Person(String name) {
        this.name = name;
        this.balance = 0.0;
    }

    public String getName() { return name; }
    public double getBalance() { return balance; }
    public void updateBalance(double amount) { this.balance += amount; }

    @Override
    public String toString() { return name; }
}
