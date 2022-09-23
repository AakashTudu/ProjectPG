package com.example.hostel.Models;

public class Complaint {
    String n;  // n denotes name of tenant
    String r;  // r denotes room number of tenant
    String p;  // p denotes mobile number of tenant
    String pn; // pn denotes PG name
    String rf; // rf denotes reference of property
    String c;  // c denotes complaint description

    public Complaint() {
    }

    public Complaint(String n, String r, String p, String pn, String rf, String c) {
        this.n = n;
        this.r = r;
        this.p = p;
        this.pn = pn;
        this.rf = rf;
        this.c = c;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getRf() {
        return rf;
    }

    public void setRf(String rf) {
        this.rf = rf;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}
