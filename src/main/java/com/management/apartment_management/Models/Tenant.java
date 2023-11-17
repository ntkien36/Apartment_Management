package com.management.apartment_management.Models;

public class Tenant {
    private int id;
    private String name;
    private String contact;
    private String status;
    private int apartmentID;

    public Tenant() {
    }
    public Tenant(int id, String name, String contact, String status) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.status = status;
    }

    public Tenant(int id, String name, String contact, String status, int apartmentID) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.status = status;
        this.apartmentID = apartmentID;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(int apartmentID) {
        this.apartmentID = apartmentID;
    }
}
