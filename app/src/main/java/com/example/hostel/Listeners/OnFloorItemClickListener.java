package com.example.hostel.Listeners;

public interface OnFloorItemClickListener {
    void clicked(int floorNumber, String roomRef);

    void firstItem(int floorNumber, String roomRef);
}
