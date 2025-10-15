package com.pluralsight;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transactions {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    //Formatter for Date & Time
    private static final DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Transactions(LocalDate date, LocalTime time, String description, String vendor, double amount){
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    //Getters
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public String getDescription() {
        return  description;
    }
    public String getVendor(){
        return vendor;
    }
    public double getAmount(){
        return amount;
    }

    //Method to create a transaction from the CSV line
    public static Transactions fromCsvLine(String line) {
        String[] parts = line.split("\\|");

        LocalDate date = LocalDate.parse(parts[0], date_formatter);
        LocalTime time = LocalTime.parse(parts[1]), time_formatter);
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transactions(date, time, description, vendor, amount);
    }



}
