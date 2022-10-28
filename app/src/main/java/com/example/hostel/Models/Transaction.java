package com.example.hostel.Models;

public class Transaction {
    String tenantRef;    // tenant reference
    String date;         // date of payment
    String paymentMode;  // mode of payment cash , upi
    String paymentType;  // type of payment rent, security deposit
    String amountPaid;   // amount of money
    String receivedBy;   // name of person whom which money is received by

    public Transaction() {
    }


    public Transaction(String tenantRef, String date, String paymentMode, String paymentType, String amountPaid, String receivedBy) {
        this.tenantRef = tenantRef;
        this.date = date;
        this.paymentMode = paymentMode;
        this.paymentType = paymentType;
        this.amountPaid = amountPaid;
        this.receivedBy = receivedBy;
    }

    public String getTenantRef() {
        return tenantRef;
    }

    public void setTenantRef(String tenantRef) {
        this.tenantRef = tenantRef;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }
}
