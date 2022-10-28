package com.example.hostel.BottomSheetDialog.Generic;

public class Row {
    private String title;
    private Integer icon;

    public Row(String title) {
        this.title = title;
    }

    public Row(String title, Integer icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
