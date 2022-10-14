package com.example.hostel.Models;

import java.io.Serializable;

public class Bank implements Serializable {
    String bankName;
    String holderName;
    String accountNumber;
    String ifscCode;

    public Bank() {
    }

    public Bank(String bankName, String holderName, String accountNumber, String ifscCode) {
        this.bankName = bankName;
        this.holderName = holderName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
