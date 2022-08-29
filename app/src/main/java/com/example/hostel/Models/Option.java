package com.example.hostel.Models;

public class Option {
    int imageID;
    String name;
    String description;

    public Option(int imageID, String name, String description) {
        this.imageID = imageID;
        this.name = name;
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
