package com.management.apartment_management.Models;

public class Building {
    private int id;
    private String name;
    private String address;
    private int total;

    public Building() {
    }

    public Building(int id, String name, String address, int total) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.total = total;
    }
    public Building(String name, String address, int total) {
        this.name = name;
        this.address = address;
        this.total = total;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
