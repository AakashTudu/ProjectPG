package com.example.hostel.Models;

import java.io.Serializable;

public class Property implements Serializable {
    String name, type, city ,location;
    String isLive;

    public Property() {
    }

    public Property(String name, String type, String city, String location, String isLive) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.location = location;
        this.isLive = isLive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIsLive() {
        return isLive;
    }

    public void setIsLive(String isLive) {
        this.isLive = isLive;
    }
}