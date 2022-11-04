package com.example.tenant.model;

public class Menu {
    private String title;
    private String time;
    private Integer dishImage;

    public Menu(String title, String time, Integer dishImage) {
        this.title = title;
        this.time = time;
        this.dishImage = dishImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getDishImage() {
        return dishImage;
    }

    public void setDishImage(Integer dishImage) {
        this.dishImage = dishImage;
    }
}
