package com.example.hostel.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Floor implements Serializable {

    private int n; // used for floor name
    private int roomsQuantity = 0;
    private String reference;
    private Boolean isSelected = true;

    public Floor() {
    }

    public Floor(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Exclude
    public int getRoomsQuantity() {
        return roomsQuantity;
    }

    @Exclude
    public void setRoomsQuantity(int roomsQuantity) {
        this.roomsQuantity = roomsQuantity;
    }

    @Exclude
    public String getReference() {
        return reference;
    }

    @Exclude
    public void setReference(String reference) {
        this.reference = reference;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "n='" + n + '\'' +
                ", roomsQuantity=" + roomsQuantity +
                '}';
    }
}
