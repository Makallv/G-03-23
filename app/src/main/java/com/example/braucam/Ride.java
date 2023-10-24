package com.example.braucam;

import java.io.Serializable;
import java.util.Date;

public class Ride implements Serializable {

    private String startDestination;
    private String endDestination;
    private Date dateAndTime;
    private String additionalInfo;
    private double price;
    private int maxSeats;
    private boolean isBooked;

    public Ride(String startDestination, String endDestination, Date dateAndTime, String additionalInfo, double price, int maxSeats, boolean isBooked) {
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.dateAndTime = dateAndTime;
        this.additionalInfo = additionalInfo;
        this.price = price;
        this.maxSeats = maxSeats;
        this.isBooked = isBooked;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
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

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getSeats() {
        return maxSeats;
    }

    public void setSeats(int seats) {
        this.maxSeats = maxSeats;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}