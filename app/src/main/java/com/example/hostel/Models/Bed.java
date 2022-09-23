package com.example.hostel.Models;

public class Bed {
    String n; // represent bed number
    String p; // p denotes bed price
    String s; // represent status of bed o-occupied, b-booked, v-vacant

    public Bed() {
    }

    public Bed(String n, String p, String s) {
        this.n = n;
        this.p = p;
        this.s = s;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
