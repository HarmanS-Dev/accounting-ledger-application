package com.pluralsight;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class LedgerApplication {
    private static final String fileName = "transactions.csv";
    private static final Scanner input = new Scanner(System.in);
    private static final List<Transactions> transactions = new ArrayList<>();

    public static void main(String[] args) {

    }

    private void displayHomeScreen() {
        String choice;
        do {
            System.out.println("""
                    === Home Screen ===
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger
                    X) Exit""");
            choice = input.nextLine().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    displayLedgerScreen();
                    break;
                case "X":
                    System.out.println("Exiting Application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again");
            }
        } while (!choice.equals("X"));
    }

    private Transactions getTransactionDetails(boolean isDeposit){
        System.out.print("Description: ");
        String description = input.nextLine();
        System.out.print("Vendor: ");
        String vendor = input.nextLine();
        System.out.print("Amount: ");
        double amount = input.nextDouble();

        //Making sure deposits are positive and payments are negative
        if (isDeposit && amount < 0) {
            System.out.println("A deposit must be a positive amount. Saving as a positive.");
            amount = Math.abs(amount);
        } else if (!isDeposit && amount > 0) {
            System.out.println("A payment must be a negative amount. Saving as a negative.");
        }

        return new Transactions(LocalDate.now(), LocalTime.now(), description, vendor, amount);
    }

    private void addDeposit() {
        System.out.println("--- ADD DEPOSIT ---");
        Transactions t = getTransactionDetails(true);
        saveTransaction(t);
    }

    private void makePayment() {
        System.out.println("--- MAKE PAYMENT ---");
        Transactions t = getTransactionDetails(false);
        saveTransaction(t);
    }

    private void saveTransaction(Transactions t) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(t.toCsvLine());
            bw.newLine();
            System.out.println("Transaction saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }

    private void displayLedgerScreen(){
        String choice;
        do {
            System.out.println("""
                    === LEDGER SCREEN ===
                    A) All Entries
                    D) Deposits
                    P) Payments
                    R) Reports
                    H) Home""");
            System.out.print("Enter your choice: ");
            choice = input.nextLine().toUpperCase();

            switch (choice) {
                case "A": // Display all transactions
                    System.out.println("--- VIEW: ALL TRANSACTIONS ---");
                    displayTransactionsTable(transactions);
                    break;
                case "D": // Display deposits only
                    List<Transactions> deposits = transactions.stream().filter(t -> t.getAmount() > 0).toList();
                    System.out.println("--- VIEW: DEPOSITS ---");
                    displayTransactionsTable(deposits);
                    break;
                case "P": // Display payments only
                    List<Transactions> payments = transactions.stream().filter(t -> t.getAmount() < 0).toList();
                    System.out.println("--- VIEW: PAYMENTS ---");
                    displayTransactionsTable(payments);
                    break;
                case "R": // Display reports screen
                    break;
                case "H": // Display home screen
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("H"));
    }

    private void displayTransactionsTable (List<Transactions> list) {
        if (list.isEmpty()) {
            System.out.println("--- No transactions to display. ---");
            return;
        }

        // Sort the list by date then time, newest first (descending)
        list.sort(Comparator.comparing(Transactions::getDate, Comparator.reverseOrder()).thenComparing(Transactions::getTime, Comparator.reverseOrder()));

        System.out.println("date|time|description|vendor|amount");
        System.out.println("----------------------------------------");
        for (Transactions t : list) {
            System.out.println(t);
        }
    }

    private void displayReportsScreen() {
        String choice;

        do {
            System.out.println("""
                    --- REPORTS SCREEN ---
                    1) Month to Date
                    2) Previous Month
                    3) Year to Date
                    4) Previous Year
                    5) Search by Vendor
                    0) Back to Ledger
                    """);
            System.out.print("Enter your choice: ");
            choice = input.nextLine();

            switch (choice) {
                case "1":
                    runReportMonthToDate();
                    break;
                case "2":
                    runReportPreviousMonth();
                    break;
                case "3":
                    runReportYearToDate();
                    break;
                case "4":
                    runReportPreviousYear();
                    break;
                case  "5":
                    runReportByVendor();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (true);
    }

    private void runDateRangeReport(String title, LocalDate startDate, LocalDate endDate) {
        System.out.println("--- REPORT: " + title + "---");
        List<Transactions> filtered = transactions.stream().filter(t -> !t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate)).toList();
        displayTransactionsTable(filtered);
    }

    private void runReportMonthToDate() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfMonth(1);
        runDateRangeReport("Month To Date", startDate, today);
    }

    private void runReportPreviousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate previousMonth = today.minusMonths(1);
        LocalDate startDate = previousMonth.withDayOfMonth(1);
        LocalDate endDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth());
        runDateRangeReport("Previous Month", startDate, endDate);
    }

    private void runReportYearToDate() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.withDayOfYear(1);
        runDateRangeReport("Previous Month", startDate, today);
    }

    private void runReportPreviousYear() {
        LocalDate today = LocalDate.now();
        LocalDate previousYear = today.minusYears(1);
        LocalDate startDate = previousYear.withDayOfYear(1);
        LocalDate endDate = previousYear.withDayOfYear(previousYear.lengthOfYear());
        runDateRangeReport("Previous Year", startDate, endDate);
    }

    private void runReportByVendor() {
        System.out.println("Enter Vendor Name to search: ");
        String vendorName = input.nextLine().trim();
        System.out.println("--- Report: Transactions for " + vendorName + "---");

        List<Transactions> filtered = transactions.stream().filter(t -> t.getVendor().toLowerCase().contains(vendorName.toLowerCase())).toList();

        displayTransactionsTable(filtered);
    }

 }
