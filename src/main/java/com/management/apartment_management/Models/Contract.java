package com.management.apartment_management.Models;

//import java.util.Date;
import java.sql.Date;

public class Contract {
    private int id;
    private String tenantName;
    private Date startDate;
    private Date endDate;
    private int paymentAmount;
    private int tenantID;
    private String notes;

    public Contract() {
    }
    public Contract(int id, String tenantName, int paymentAmount, Date endDate, String notes) {
        this.id = id;
        this.tenantName = tenantName;
        this.paymentAmount = paymentAmount;
        this.endDate = endDate;
        this.notes = notes;
    }
    public Contract(int id, String tenantName, Date startDate, Date endDate, String notes) {
        this.id = id;
        this.tenantName = tenantName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }
    public Contract(int id, String tenantName, Date startDate, Date endDate, int paymentAmount, String notes) {
        this.id = id;
        this.tenantName = tenantName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentAmount = paymentAmount;
        this.notes = notes;
    }
    public Contract(int id, String notes, int paymentAmount, Date paymentDate){
        this.id = id;
        this.notes = notes;
        this.paymentAmount = paymentAmount;
        this.endDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
