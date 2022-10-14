package com.example.hostel.DTO;

import com.example.hostel.Enums.FragmentEnum;
import com.example.hostel.Models.Property;

import java.io.Serializable;

public class AddTenantDTO implements Serializable {
    String name;
    String phoneNumber;
    String emailId;
    String occupation;
    String DOB;
    boolean gender;
    boolean martialStatus;
    String propertyRefKey;
    Property property;
    String bedNumber;
    String bedRef;
    String roomNumber;
    int sharingType;
    FragmentEnum fragmentEnum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(boolean martialStatus) {
        this.martialStatus = martialStatus;
    }

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

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBedRef() {
        return bedRef;
    }

    public void setBedRef(String bedRef) {
        this.bedRef = bedRef;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getSharingType() {
        return sharingType;
    }

    public void setSharingType(int sharingType) {
        this.sharingType = sharingType;
    }

    public FragmentEnum getFragmentEnum() {
        return fragmentEnum;
    }

    public void setFragmentEnum(FragmentEnum fragmentEnum) {
        this.fragmentEnum = fragmentEnum;
    }
}
