package com.expensesplitter;

import java.util.*;

public class ExpenseSplitterApp {

    static Scanner scanner = new Scanner(System.in);
    static Map<String, Group> groups = new HashMap<>();
    static Group currentGroup = null;

    public static void main(String[] args) {
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": createGroup(); break;
                case "2": switchGroup(); break;
                case "3": addMember(); break;
                case "4": addExpense(); break;
                case "5": viewBalances(); break;
                case "6": viewSettlements(); break;
                case "7": viewExpenses(); break;
                case "8": viewMembers(); break;
                case "0":
                    System.out.println("\n  Goodbye! Happy splitting!");
                    running = false;
                    break;
                default:
                    System.out.println("  ✘ Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    static void printBanner() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     EXPENSE SPLITTER  v1.0           ║");
        System.out.println("║     Split bills. Stay friends.       ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    static void printMenu() {
        String group = (currentGroup != null) ? currentGroup.getGroupName() : "None";
        System.out.println("\n  Active Group: " + group);
        System.out.println("  ─────────────────────────────");
        System.out.println("  1. Create new group");
        System.out.println("  2. Switch group");
        System.out.println("  3. Add member");
        System.out.println("  4. Add expense");
        System.out.println("  5. View balances");
        System.out.println("  6. View settlement plan");
        System.out.println("  7. View expense history");
        System.out.println("  8. View members");
        System.out.println("  0. Exit");
        System.out.print("  Choose: ");
    }

    static void createGroup() {
        System.out.print("  Enter group name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("  ✘ Group name cannot be empty."); return; }
        if (groups.containsKey(name.toLowerCase())) {
            System.out.println("  ✘ Group already exists."); return;
        }
        Group g = new Group(name);
        groups.put(name.toLowerCase(), g);
        currentGroup = g;
        System.out.println("  ✔ Group '" + name + "' created and set as active.");
    }

    static void switchGroup() {
        if (groups.isEmpty()) { System.out.println("  No groups created yet."); return; }
        System.out.println("  Available groups: " + groups.keySet());
        System.out.print("  Enter group name: ");
        String name = scanner.nextLine().trim();
        Group g = groups.get(name.toLowerCase());
        if (g == null) { System.out.println("  ✘ Group not found."); return; }
        currentGroup = g;
        System.out.println("  ✔ Switched to group '" + g.getGroupName() + "'.");
    }

    static void addMember() {
        if (!checkGroup()) return;
        System.out.print("  Enter member name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("  ✘ Name cannot be empty."); return; }
        currentGroup.addMember(name);
    }

    static void addExpense() {
        if (!checkGroup()) return;
        try {
            System.out.print("  Description: ");
            String desc = scanner.nextLine().trim();
            if (desc.isEmpty()) { System.out.println("  ✘ Description cannot be empty."); return; }

            System.out.print("  Amount (₹): ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("  Paid by (member name): ");
            String paidBy = scanner.nextLine().trim();

            System.out.print("  Split among (comma-separated names, or 'all'): ");
            String splitInput = scanner.nextLine().trim();

            List<String> splitNames = new ArrayList<>();
            if (splitInput.equalsIgnoreCase("all")) {
                for (Person p : currentGroup.getMembers()) splitNames.add(p.getName());
            } else {
                for (String s : splitInput.split(",")) splitNames.add(s.trim());
            }

            currentGroup.addExpense(desc, amount, paidBy, splitNames);

        } catch (NumberFormatException e) {
            System.out.println("  ✘ Invalid amount. Please enter a number.");
        }
    }

    static void viewBalances() {
        if (!checkGroup()) return;
        currentGroup.showBalances();
    }

    static void viewSettlements() {
        if (!checkGroup()) return;
        currentGroup.showSettlements();
    }

    static void viewExpenses() {
        if (!checkGroup()) return;
        currentGroup.showExpenses();
    }

    static void viewMembers() {
        if (!checkGroup()) return;
        currentGroup.showMembers();
    }

    static boolean checkGroup() {
        if (currentGroup == null) {
            System.out.println("  ✘ No active group. Please create or switch to a group first.");
            return false;
        }
        return true;
    }
}
