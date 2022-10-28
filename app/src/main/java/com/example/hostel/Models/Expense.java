package com.example.hostel.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Expense implements Serializable {
    private String t;   // t denotes the title of expense
    private String p;   // p denotes amount
    private String r;   // r denotes the description od expense
    private String d_r;   // d denotes date of expense & r denoted property ref
    private String e;   // e denotes expense type

    public Expense() {
    }

    public Expense(String t, String p, String r, String d_r, String e) {
        this.t = t;
        this.p = p;
        this.r = r;
        this.d_r = d_r;
        this.e = e;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public void setD_r(String d_r) {
        this.d_r = d_r;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getD_r() {
        return d_r;
    }

    @Exclude
    public String getExpenseTitle() {
        return getT();
    }

    @Exclude
    public void setExpenseTitle(String expenseTitle) {
        this.t = expenseTitle;
    }

    @Exclude
    public String getAmount() {
        return getP();
    }

    @Exclude
    public void setAmount(String amount) {
        this.p = amount;
    }

    @Exclude
    public String getDescription() {
        return r;
    }

    @Exclude
    public void setDescription(String description) {
        this.r = description;
    }

    @Exclude
    public String getDate() {
        return d_r.split("_")[0];
    }

    @Exclude
    public String getPropertyRef() {
        return d_r.split("_")[1];
    }

    @Exclude
    public void setDate_propertyRef(String date_propertyRef) {
        this.d_r = date_propertyRef;
    }

    @Exclude
    public String getExpenseType() {
        return e;
    }

    @Exclude
    public void setExpenseType(String expenseType) {
        this.e = expenseType;
    }

}
