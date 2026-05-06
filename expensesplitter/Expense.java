package com.expensesplitter;

import java.util.List;

public class Expense {
    private String description;
    private double amount;
    private Person paidBy;
    private List<Person> splitAmong;

    public Expense(String description, double amount, Person paidBy, List<Person> splitAmong) {
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitAmong = splitAmong;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Person getPaidBy() { return paidBy; }
    public List<Person> getSplitAmong() { return splitAmong; }
}
