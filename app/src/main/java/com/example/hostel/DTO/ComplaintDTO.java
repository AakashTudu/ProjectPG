package com.example.hostel.DTO;

import com.example.hostel.Models.Property;

import java.io.Serializable;

public class ComplaintDTO implements Serializable {
    private String propertyRefKey;
    private Property property;

    public String getPropertyRefKey() {
        return propertyRefKey;
    }

    public void setPropertyRefKey(String propertyRefKey) {
        this.propertyRefKey = propertyRefKey;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}

