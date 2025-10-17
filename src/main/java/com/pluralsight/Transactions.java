package com.pluralsight;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Transactions(LocalDate date, LocalTime time, String description, String vendor, double amount) {
    //Formatter for Date & Time
    private static final DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    //Method to create a transaction from the CSV line
    public static Transactions fromCsvLine(String line) {
        String[] parts = line.split("\\|");

        // Parses the date, time, and amount from the string parts
        LocalDate date = LocalDate.parse(parts[0], date_formatter);
        LocalTime time = LocalTime.parse(parts[1], time_formatter);
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transactions(date, time, description, vendor, amount);
    }

    //Method to convert a Transaction into a CSV line
    public String toCsvLine() {
        //Format: date|time|description|vendor|amount
        return String.format("%s|%s|%s|%s|%.2f", date.format(date_formatter), time.format(time_formatter), description, vendor, amount);
    }


    // Method to convert a Transaction to a formatted display
    public String toDisplayString() {
        return String.format("%-10s|%-8s|%-60s|%-30s|$%10.2f", date.format(date_formatter), time.format(time_formatter), description, vendor, amount);
    }

}
