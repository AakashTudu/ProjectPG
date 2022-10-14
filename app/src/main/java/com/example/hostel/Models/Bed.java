package com.example.hostel.Models;

import java.io.Serializable;

public class Bed implements Serializable {
    String n; // represent bed number
    String s; // represent status of bed o-occupied, b-booked, v-vacant

    public Bed() {
    }

    public Bed(String n, String s) {
        this.n = n;
        this.s = s;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
