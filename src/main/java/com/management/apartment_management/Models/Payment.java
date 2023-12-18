package com.management.apartment_management.Models;

//import java.util.Date;
import java.sql.Date;

public class Payment {
    private int id;
    private String tenantName;
    private Date paymentDate;
    private int amount;
    private int contractID;
    private String note;

    public Payment() {
    }
    public Payment(int id, int contractID, Date paymentDate, int amount) {
        this.id = id;
        this.contractID = contractID;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
    public Payment(int id, String note, int amount, Date paymentDate){
        this.id = id;
        this.note = note;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }
    public Payment(int id, String tenantName, int amount, Date paymentDate, String note) {
        this.id = id;
        this.tenantName = tenantName;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.note = note;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
