package com.example.braucam;

import java.io.Serializable;

public class Ride implements Serializable {

    private String startDestination;
    private String endDestination;
    private String dateAndTime;
    private String additionalInfo;
    private double price;

    public Ride(String startDestination, String endDestination, String dateAndTime, String additionalInfo, double price) {
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.dateAndTime = dateAndTime;
        this.additionalInfo = additionalInfo;
        this.price = price;
    }

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public String getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(String endDestination) {
        this.endDestination = endDestination;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}