package com.example.hostel.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Tenant implements Serializable {
    private String n;  // n denotes name of tenant
    private String r;  // r denotes room number of tenant
    private String p;  // p denotes mobile number of tenant
    private int o;     // o denotes occupancy of tenant
    private String e;  // e denotes email id of tenant
    private String pn; // pn denotes PG name
    private String rf; // rf denotes reference of property
    private String d;  // d denotes date of birth of tenant
    private Boolean g;  // g denotes gender of tenant if g is true means male otherwise female
    private Boolean m;  // m denotes martialStatus of tenant if m is true means single otherwise married
    private String j;  // j denotes the job the tenant is doing
    private String bn; // bn denotes bed number
    private String re;  // r denotes rent of tenant

    public Tenant() {
    }

    public Tenant(String tenantName, String roomNumber, String mobileNumber, int sharingType, String emailId, String propertyName, String propertyRef, String dob, Boolean gender, Boolean martialStatus, String occupation, String bedNumber, String rent) {
        this.n = tenantName;
        this.r = roomNumber;
        this.p = mobileNumber;
        this.o = sharingType;
        this.e = emailId;
        this.pn = propertyName;
        this.rf = propertyRef;
        this.d = dob;
        this.g = gender;
        this.m = martialStatus;
        this.j = occupation;
        this.bn = bedNumber;
        this.re = rent;
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

    public Tenant(String n, String r, String p, int o, String e, String pn, String rf) {
        this.n = n;
        this.r = r;
        this.p = p;
        this.o = o;
        this.e = e;
        this.pn = pn;
        this.rf = rf;
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

    public int getO() {
        return o;
    }

    @Exclude
    public String getOccupancy() {
        String occupancy = null;
        switch (getO()) {
            case 1:
                occupancy = "Single Occupancy";
                break;
            case 2:
                occupancy = "Double Occupancy";
                break;
            case 3:
                occupancy = "Triple Occupancy";
                break;
            case 4:
                occupancy = "Four Occupancy";
                break;
            case 5:
                occupancy = "Five Occupancy";
                break;
            case 6:
                occupancy = "Six Occupancy";
                break;
            case 7:
                occupancy = "Seven Occupancy";
                break;
            case 8:
                occupancy = "Eight Occupancy";
                break;
            case 9:
                occupancy = "Nine Occupancy";
                break;
            case 10:
                occupancy = "Ten Occupancy";
                break;
        }
        return occupancy;
    }


    public void setO(int o) {
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

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public Boolean getG() {
        return g;
    }

    public void setG(Boolean g) {
        this.g = g;
    }

    public Boolean getM() {
        return m;
    }

    public void setM(Boolean m) {
        this.m = m;
    }

    public String getJ() {
        return j;
    }

    public void setJ(String j) {
        this.j = j;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }
}
