package com.example.hostel.Models;

import java.io.Serializable;

public class Property implements Serializable {
    String name, type, city ,location;

    public Property() {
    }

    public Property(String name, String type, String city, String location) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.location = location;
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

}