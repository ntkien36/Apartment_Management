package com.management.apartment_management.Models;

public class Report {
    private int id;
    private String name;
    private String description;
    private String status;
    private int create_by;


    public Report() {
    }

    public Report(int id, String name, String description, String status, int create_by) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.create_by = create_by;
    }
    public Report(int id, String name, String description, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }
    public Report(int id, int create_by, String description, String status) {
        this.id = id;
        this.create_by = create_by;
        this.description = description;
        this.status = status;
    }

//    public Report(int id, String name, int create_by, String description, String status) {
//        this.id = id;
//        this.name = name;
//        this.create_by = create_by;
//        this.description = description;
//        this.status = status;
//    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreate_by() {
        return create_by;
    }

    public void setCreate_by(int create_by) {
        this.create_by = create_by;
    }
}
