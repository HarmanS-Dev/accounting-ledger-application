package com.pluralsight;

import java.io.*;
import java.time.*;
import java.util.*;

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
                    break;
                case "D": // Display deposits only
                    break;
                case "P": // Display payments only
                    break;
                case "R": // Display reports screen
                    break;
                case "H": // Display home screen
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }while (!choice.equals("H"));
    }
 }
