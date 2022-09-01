package com.example.hostel.Models;

import java.io.Serializable;

public class Floor implements Serializable {
    Boolean isSelected;
    String floorName;
    int roomsQuantity;

    public Floor() {
        isSelected = true;
        floorName = "";
        roomsQuantity = 0;
    }

    public Floor(Boolean isSelected, String floorName) {
        this.isSelected = isSelected;
        this.floorName = floorName;
        roomsQuantity = 0;
    }

    public Floor(String floorName) {
        this.floorName = floorName;
        roomsQuantity = 0;
        isSelected = true;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public int getRoomsQuantity() {
        return roomsQuantity;
    }

    public void setRoomsQuantity(int roomsQuantity) {
        this.roomsQuantity = roomsQuantity;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "isSelected=" + isSelected +
                ", floorName='" + floorName + '\'' +
                ", roomsQuantity=" + roomsQuantity +
                '}';
    }
}
