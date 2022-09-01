package com.example.hostel.Models;

public class PropertiesOption {
    String name, type, location;

    public PropertiesOption(String name, String type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public String getPropName() {
        return name;
    }
    public void setPropName(String name) {
        this.name = name;
    }

    public String getPropType() {
        return type;
    }
    public void setPropType(String type) {
        this.type = type;
    }

    public String getPropLocation() {
        return location;
    }
    public void setPropLocation(String location) {
        this.location = location;
    }

}