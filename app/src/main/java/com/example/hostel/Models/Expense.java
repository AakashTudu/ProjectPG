package com.example.hostel.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Expense implements Serializable {
    private String t;   // t denotes the title of expense
    private String p;   // p denotes amount
    private String r;   // r denotes the description od expense
    private String d;   // d denotes date if expense
    private Boolean m;  // m denotes traction type i.e transaction or shopping

    public Expense() {
    }

    public Expense(String t, String p, String r, String d, Boolean m) {
        this.t = t;
        this.p = p;
        this.r = r;
        this.d = d;
        this.m = m;
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

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Boolean getM() {
        return m;
    }

    public void setM(Boolean m) {
        this.m = m;
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
        return d;
    }
    @Exclude
    public void setDate(String date) {
        this.d = date;
    }

    @Exclude
    public Boolean isShoppingChecked() {
        return m;
    }

    @Exclude
    public void setShoppingChecked(Boolean isShoppingChecked) {
        this.m = isShoppingChecked;
    }
}
