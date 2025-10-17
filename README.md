# 💰 Accounting Ledger Application

This is a **console-based Java application** designed to manage and track financial transactions in a simple accounting ledger. It provides a user-friendly command-line interface for recording deposits, making payments, and generating various financial reports.

The application uses **Java 17** and stores all financial records persistently in a pipe-delimited `transactions.csv` file.

-----

## 📋 Table of Contents

  * [Features](#-features)
  * [Technologies](#%EF%B8%8F-technologies)
  * [File Structure](-file-structure)
  * [Application Screens](#%EF%B8%8F-application-screens)
      * [1. Home Screen](#1-home-screen)
      * [2. Ledger Screen](#2-ledger-screen)
      * [3. Reports Screen](#3-reports-screen)
      * [4. Example Report Output](#4-example-report-output)
  * [Interesting Code: Data Sorting and Presentation](#-interesting-code-data-sorting-and-presentation-1)

-----

## ✨ Features

The Accounting Ledger Application provides a comprehensive set of features for managing simple personal finances:

  * **Transaction Management:** Easily add new **Deposits** and record **Payments** (Debits).
  * **Data Persistence:** Transactions are automatically saved to and loaded from a local `transactions.csv` file.
  * **Filtered Ledger Views:** Quickly view All transactions, Deposits only, or Payments only.
  * **Standard Reports:** Generate predefined reports for Month to Date, Previous Month, Year to Date, and Previous Year.
  * **Custom Filtering:** Use the **Custom Search** feature to filter entries by date range, description keyword, vendor keyword, and exact amount.
  * **Sorted Display:** All transaction lists are sorted by date and time in **descending** order (newest first).

-----

## 🛠️ Technologies

  * **Language:** Java
  * **JDK Version:** 17
  * **Build Tool:** Apache Maven
  * **Core Libraries:** `java.time` (for date/time handling) and `java.util.stream` (for filtering/reporting)

-----

## 📂 File Structure

The project follows a standard Maven project structure, with configuration files (`pom.xml`, `.gitignore`) and the source code located in `src/main/java`. Transaction data is stored in the root-level `transactions.csv` file.

```text
.
├── .gitignore
├── .idea/
│   ├── .gitignore
│   ├── encodings.xml
│   ├── material_theme_project_new.xml
│   ├── misc.xml
│   └── vcs.xml
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── pluralsight/
│                   ├── LedgerApplication.java
│                   └── Transactions.java
└── transactions.csv
```

-----

## 🖥️ Application Screens

The application is navigated through simple menu choices (`D`, `P`, `L`, `X` for the Home screen, and numerical/alphabetical options for sub-menus).

### 1\. Home Screen

The main entry point for adding new transactions or navigating to the Ledger.

```text
=== Home Screen ===
D) Add Deposit
P) Make Payment (Debit)
L) Ledger
X) Exit

Enter your choice: 
```

### 2\. Ledger Screen

Allows users to view all transactions, or filter them quickly by Deposits only (`D`) or Payments only (`P`), or proceed to the Reports menu (`R`).

```text
=== LEDGER SCREEN ===
A) All Entries
D) Deposits
P) Payments
R) Reports
H) Home

Enter your choice: 
```

### 3\. Reports Screen

A sub-menu for generating pre-defined and custom financial reports.

```text
--- REPORTS SCREEN ---
1) Month to Date
2) Previous Month
3) Year to Date
4) Previous Year
5) Search by Vendor
6) Custom Search
0) Back to Ledger
```

### 4\. Example Report Output

All report results are displayed in a formatted table, sorted by date/time in **descending** order (newest first).

```text
--- VIEW: ALL TRANSACTIONS ---
DATE      |TIME    |DESCRIPTION                                                 |VENDOR                        |    AMOUNT
--------------------------------------------------------------------------------------------------------------------------
2025-03-20|17:00:00|Professional Attire for Interviews                          |Macy's                        |   $-120.50
2025-02-01|12:00:00|Rent - February                                             |Apartment Management          |   $-800.00
... (truncated)
```

-----

## 💡 Interesting Code: Data Sorting and Presentation

The **`displayTransactionsTable`** method is essential for presenting any transaction list (from the ledger, deposits, payments, or reports) in a clean, consistent, and readable format.

The method utilizes the modern **Java Comparator API** for efficient, multi-field sorting. It sorts the transactions first by **Date**, then by **Time**, ensuring the **newest entries are always displayed first** (descending order).

```java
// harmans-dev/accounting-ledger-application/accounting-ledger-application-06c955b02d149845af3f6c0a6ee1d353dc2fdbbd/src/main/java/com/pluralsight/LedgerApplication.java

private static void displayTransactionsTable(List<Transactions> list) {
    if (list.isEmpty()) {
        System.out.println("--- No transactions to display. ---");
        return;
    }

    // Sort the list by date then time, newest first (descending)
    list.sort(Comparator.comparing(Transactions::date, Comparator.reverseOrder()).thenComparing(Transactions::time, Comparator.reverseOrder()));
    
    // Create the formatted header
    String header = String.format("%-10s|%-8s|%-60s|%-30s|%10s", "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");
    
    System.out.println(header);
    System.out.println("-".repeat(header.length()));
    
    for (Transactions t : list) {
        System.out.println(t.toCsvLine());
    }
}
```
