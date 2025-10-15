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

    private Transactions getTransactionDetails(){
        System.out.print("Description: ");
        String description = input.nextLine();
        System.out.print("Vendor: ");
        String vendor = input.nextLine();
        System.out.print("Amount: ");
        double amount = input.nextDouble();

        return new Transactions(LocalDate.now(), LocalTime.now(), description, vendor, amount);
    }

    private void addDeposit() {

    }

    private void makePayment() {

    }

    private void displayLedgerScreen(){

    }
 }
