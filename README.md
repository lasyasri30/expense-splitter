# Expense Splitter — Core Java CLI App

A command-line expense splitting application built in Core Java, inspired by apps like Splitwise and PhonePe's bill-split feature.

## Features
- Create multiple groups (trip, flat, friends)
- Add members to each group
- Log expenses — who paid, who shares the cost
- View individual balances (who owes, who is owed)
- Get a smart settlement plan (minimum transactions to settle all debts)
- View full expense history with totals

## Concepts Used
- Object-Oriented Programming (Person, Expense, Group classes)
- Collections — ArrayList, HashMap
- Exception Handling (invalid input, number format)
- Greedy Algorithm (settlement calculation)
- Scanner for CLI input

## Project Structure
```
ExpenseSplitter/
├── src/
│   └── com/
│       └── expensesplitter/
│           ├── Person.java             # Member model with balance tracking
│           ├── Expense.java            # Expense model
│           ├── Group.java              # Core logic — split, balance, settle
│           └── ExpenseSplitterApp.java # Main CLI interface
└── README.md
```

## How to Run

### Step 1 — Compile
```bash
javac -d out src/com/expensesplitter/*.java
```

### Step 2 — Run
```bash
java -cp out com.expensesplitter.ExpenseSplitterApp
```

## Sample Usage
```
Create group: "Goa Trip"
Add members: Lasya, Priya, Ravi, Kiran

Add expense: Hotel — ₹4000 — Paid by Lasya — Split among all
Add expense: Food  — ₹1200 — Paid by Ravi  — Split among all

View balances:
  Lasya is owed ₹3700
  Ravi  is owed ₹900
  Priya owes   ₹1300
  Kiran owes   ₹1200 (wait this one's wrong)

Settlement Plan:
  → Priya pays ₹1300 to Lasya
  → Kiran pays ₹1200 to Lasya
  ...
```

## Real-World Relevance
This mirrors the core logic used in PhonePe's bill-split, Google Pay groups, and Splitwise — rebuilt from scratch using pure Core Java with no external libraries.
