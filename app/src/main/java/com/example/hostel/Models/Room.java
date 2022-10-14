package com.example.hostel.Models;

import java.io.Serializable;

public class Room implements Serializable {
    String n;

    public Room() {
    }

    public Room(String n) {
        this.n = n;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }
}
