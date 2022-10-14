package com.example.hostel.Models;

import com.google.firebase.database.Exclude;

public class BedCard {
    private int t; // total bed
    private int o;  // onboard bed

    public BedCard() {
    }

    public BedCard(int t, int o) {
        this.t = t;
        this.o = o;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getO() {
        return o;
    }

    public void setO(int o) {
        this.o = o;
    }


    @Exclude
    public int getTotalBed() {
        return getT();
    }

    @Exclude
    public int getOnboardBed(){
        return getO();
    }

    @Exclude
    public int getVacantBed(){
        return getT() - getO();
    }

}
