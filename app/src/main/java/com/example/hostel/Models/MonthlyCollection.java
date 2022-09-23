package com.example.hostel.Models;

import java.io.Serializable;
import java.util.Locale;

public class MonthlyCollection implements Serializable {
    String month;
    int paidPrice;
    int totalPrice;

    public MonthlyCollection() {
    }

    public MonthlyCollection(String month, int paidPrice, int totalPrice) {
        this.month = month;
        this.paidPrice = paidPrice;
        this.totalPrice = totalPrice;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(int paidPrice) {
        this.paidPrice = paidPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPendingPrice() {
        return getTotalPrice() - getPaidPrice();
    }

    public String getPercentage() {
        float percentage = ((float) paidPrice / totalPrice) * 100;
        return String.format(Locale.getDefault(), "%.2f", percentage) + " %";
    }

    public String getPendingPercentage() {
        float percentage = ((float) paidPrice / totalPrice) * 100;
        return String.format(Locale.getDefault(), "%.2f", 100 - percentage) + " %";
    }

    public int getIntPercentage() {
        int percentage = (int) (((float) paidPrice / totalPrice) * 100);
        return percentage;
    }


    @Override
    public String toString() {
        return "MonthlyCollection{" +
                "month='" + month + '\'' +
                ", paidPrice=" + paidPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
