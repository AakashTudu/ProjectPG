package com.example.hostel.Models;

import java.io.Serializable;

public class Occupancy implements Serializable {
    String name;
    Boolean isSelected;
    int roomsQuantity;

    public Occupancy(String name) {
        this.name = name;
        isSelected = false;
        roomsQuantity = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getRoomsQuantity() {
        return roomsQuantity;
    }

    public void setRoomsQuantity(int roomsQuantity) {
        this.roomsQuantity = roomsQuantity;
    }
}
