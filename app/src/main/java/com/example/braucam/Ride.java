package com.example.braucam;

import static java.sql.Types.NULL;

import java.io.Serializable;
import java.util.Date;

public class Ride implements Serializable {

    private int ID;
    private String startDestination;
    private String endDestination;
    private Date dateAndTime;
    private String carPlate;
    private String additionalInfo;
    private double price;
    private int maxSeats;
    private int reservedSeats;
    private int ownerId;
    private String bookerIDs;

    public Ride(int ID, int ownerId, String startDestination, String endDestination, Date dateAndTime, String carPlate, String additionalInfo, double price, int maxSeats, int reservedSeats, String bookerIDs) {
        this.ownerId = ownerId;
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.dateAndTime = dateAndTime;
        this.carPlate = carPlate;
        this.additionalInfo = additionalInfo;
        this.price = price;
        this.maxSeats = maxSeats;
        this.reservedSeats = reservedSeats;
        this.bookerIDs = bookerIDs;
        this.ID = ID;
    }

    public String getbookerIDs() {
        return this.bookerIDs;
    }

    public int getID() {
        return this.ID;
    }

    public void setbookerIDs(String newBookerIDs) {
        this.bookerIDs = newBookerIDs;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public String getCarPlate() {
        return this.carPlate;
    }

    public void setCarPlate(String plate) {
        this.carPlate = plate;
    }

    public int getReservedSeats() {
        return this.reservedSeats;
    }

    public void addReservedSeats() {
        this.reservedSeats++;
    }
    public String getStartDestination() {
        return this.startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public String getEndDestination() {
        return this.endDestination;
    }

    public void setEndDestination(String endDestination) {
        this.endDestination = endDestination;
    }

    public Date getDateAndTime() {
        return this.dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getSeats() {
        return this.maxSeats;
    }

    public void setSeats(int seats) {
        this.maxSeats = maxSeats;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}