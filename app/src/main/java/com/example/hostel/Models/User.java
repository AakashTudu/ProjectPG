package com.example.hostel.Models;

public class User {
    String city;
    String name;
    String email;

    public User() {
    }

    public User(String city, String name, String email) {
        this.city = city;
        this.name = name;
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
