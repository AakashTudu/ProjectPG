package com.example.hostel.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Floor implements Serializable {

    private String n; // used for floor name
    private int roomsQuantity = 0;
    private String reference;

    public Floor() {
    }

    public Floor(String n) {
        this.n = n;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
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

    @Override
    public String toString() {
        return "Floor{" +
                "n='" + n + '\'' +
                ", roomsQuantity=" + roomsQuantity +
                '}';
    }
}
