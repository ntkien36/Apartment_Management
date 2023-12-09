package com.management.apartment_management.Models;

public class Apartment {
    private int ID;
    private int number;
    private int size;
    private int rent;
    private int buildingID;

    public Apartment() {
    }

//    public Apartment(int ID, int number, int size, int rent, int buildingID) {
//        this.ID = ID;
//        this.number = number;
//        this.size = size;
//        this.rent = rent;
//        this.buildingID = buildingID;
//    }
    public Apartment(int ID, int buildingID, int number, int size, int rent) {
        this.ID = ID;
        this.buildingID = buildingID;
        this.number = number;
        this.size = size;
        this.rent = rent;
    }

    public Apartment(int buildingID, int number, int size, int rent) {
        this.buildingID = buildingID;
        this.number = number;
        this.size = size;
        this.rent = rent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }
    //    public String toString() {
//        return this.name;
//    }
}
