package com.example.hostel.Models;

import java.io.Serializable;

public class Tenant implements Serializable {
    String n; // n denotes name of tenant
    String r; // r denotes room number of tenant
    String p; // p denotes mobile number of tenant
    String o; // o denotes occupancy of tenant
    String e; // e denotes email id of tenant
    String pn; // pn denotes PG name
    String rf; // rf denotes reference of property
    String s;
    public Tenant() {
    }

    /**
     * @param n  denotes name of tenant
     * @param r  denotes room number of tenant
     * @param p  denotes mobile number of tenant
     * @param o  denotes occupancy of tenant
     * @param e  denotes email id of tenant
     * @param pn denotes property property name
     * @param rf denotes reference of property
     */

    public Tenant(String n, String r, String p, String o, String e, String pn, String rf) {
        this.n = n;
        this.r = r;
        this.p = p;
        this.o = o;
        this.e = e;
        this.pn = pn;
        this.rf = rf;
        s = "v";
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

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
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

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
