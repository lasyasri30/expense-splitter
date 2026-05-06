package com.expensesplitter;

import java.util.*;

public class Group {
    private String groupName;
    private List<Person> members;
    private List<Expense> expenses;

    public Group(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public void addMember(String name) {
        for (Person p : members) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.println("  Member '" + name + "' already exists.");
                return;
            }
        }
        members.add(new Person(name));
        System.out.println("  ✔ Added " + name + " to group.");
    }

    public Person findMember(String name) {
        for (Person p : members) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    public void addExpense(String description, double amount, String paidByName, List<String> splitNames) {
        if (amount <= 0) {
            System.out.println("  ✘ Amount must be greater than zero.");
            return;
        }
        Person paidBy = findMember(paidByName);
        if (paidBy == null) {
            System.out.println("  ✘ Member '" + paidByName + "' not found.");
            return;
        }
        List<Person> splitAmong = new ArrayList<>();
        for (String name : splitNames) {
            Person p = findMember(name);
            if (p == null) {
                System.out.println("  ✘ Member '" + name + "' not found. Skipping.");
            } else {
                splitAmong.add(p);
            }
        }
        if (splitAmong.isEmpty()) {
            System.out.println("  ✘ No valid members to split among.");
            return;
        }

        double share = amount / splitAmong.size();
        paidBy.updateBalance(amount); // paidBy gets full amount credited
        for (Person p : splitAmong) {
            p.updateBalance(-share); // each person owes their share
        }

        expenses.add(new Expense(description, amount, paidBy, splitAmong));
        System.out.printf("  ✔ Expense '%s' of ₹%.2f added. Each person owes ₹%.2f%n",
                description, amount, share);
    }

    public void showBalances() {
        if (members.isEmpty()) {
            System.out.println("  No members in group.");
            return;
        }
        System.out.println("\n  ── Balances in group: " + groupName + " ──");
        for (Person p : members) {
            if (p.getBalance() > 0.01) {
                System.out.printf("  ✔ %s is owed ₹%.2f%n", p.getName(), p.getBalance());
            } else if (p.getBalance() < -0.01) {
                System.out.printf("  ✘ %s owes ₹%.2f%n", p.getName(), Math.abs(p.getBalance()));
            } else {
                System.out.printf("  ✓ %s is settled up%n", p.getName());
            }
        }
    }

    public void showSettlements() {
        System.out.println("\n  ── Settlement Plan ──");
        // Build working copies
        List<Person[]> debtors = new ArrayList<>();
        List<Person[]> creditors = new ArrayList<>();

        for (Person p : members) {
            Person[] copy = new Person[]{new Person(p.getName())};
            copy[0].updateBalance(p.getBalance());
            if (p.getBalance() < -0.01) debtors.add(copy);
            else if (p.getBalance() > 0.01) creditors.add(copy);
        }

        if (debtors.isEmpty()) {
            System.out.println("  Everyone is settled up! No payments needed.");
            return;
        }

        // Use greedy settlement
        int i = 0, j = 0;
        double[] debts = new double[debtors.size()];
        double[] credits = new double[creditors.size()];
        String[] debtorNames = new String[debtors.size()];
        String[] creditorNames = new String[creditors.size()];

        for (int k = 0; k < debtors.size(); k++) {
            debts[k] = Math.abs(debtors.get(k)[0].getBalance());
            debtorNames[k] = debtors.get(k)[0].getName();
        }
        for (int k = 0; k < creditors.size(); k++) {
            credits[k] = creditors.get(k)[0].getBalance();
            creditorNames[k] = creditors.get(k)[0].getName();
        }

        while (i < debts.length && j < credits.length) {
            double payment = Math.min(debts[i], credits[j]);
            System.out.printf("  → %s pays ₹%.2f to %s%n", debtorNames[i], payment, creditorNames[j]);
            debts[i] -= payment;
            credits[j] -= payment;
            if (debts[i] < 0.01) i++;
            if (credits[j] < 0.01) j++;
        }
    }

    public void showExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("  No expenses recorded yet.");
            return;
        }
        System.out.println("\n  ── Expense History ──");
        double total = 0;
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            System.out.printf("  %d. %s | ₹%.2f | Paid by: %s | Split among: %d people%n",
                    i + 1, e.getDescription(), e.getAmount(),
                    e.getPaidBy().getName(), e.getSplitAmong().size());
            total += e.getAmount();
        }
        System.out.printf("  Total group spending: ₹%.2f%n", total);
    }

    public void showMembers() {
        if (members.isEmpty()) {
            System.out.println("  No members yet.");
            return;
        }
        System.out.print("  Members: ");
        for (int i = 0; i < members.size(); i++) {
            System.out.print(members.get(i).getName());
            if (i < members.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }

    public String getGroupName() { return groupName; }
    public List<Person> getMembers() { return members; }
}
