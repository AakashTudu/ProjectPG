package com.example.hostel.Models;

public class Pending {

    String amount;
    String date;
    String tenantRef;
    String paymentType;

    public Pending() {
    }

    public Pending(String amount, String date, String tenantRef, String paymentType) {
        this.amount = amount;
        this.date = date;
        this.tenantRef = tenantRef;
        this.paymentType = paymentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTenantRef() {
        return tenantRef;
    }

    public void setTenantRef(String tenantRef) {
        this.tenantRef = tenantRef;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
