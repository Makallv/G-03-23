package com.example.braucam;

import java.io.Serializable;
import java.util.Date;

public class Ride implements Serializable {

    private String startDestination;
    private String endDestination;
    private Date dateAndTime;
    private String carPlate;
    private String additionalInfo;
    private double price;
    private int maxSeats;
    private int reservedSeats;
    private int ownerId;

    public Ride(int ownerId, String startDestination, String endDestination, Date dateAndTime, String carPlate, String additionalInfo, double price, int maxSeats, int reservedSeats) {
        this.ownerId = ownerId;
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.dateAndTime = dateAndTime;
        this.carPlate = carPlate;
        this.additionalInfo = additionalInfo;
        this.price = price;
        this.maxSeats = maxSeats;
        this.reservedSeats = reservedSeats;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String plate) {
        this.carPlate = plate;
    }

    public int getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(int reserved) {
        this.reservedSeats = reserved;
    }

    public void addReservedSeats() {
        this.reservedSeats++;
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