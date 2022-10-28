package com.example.hostel.DTO;

import com.example.hostel.BottomSheetDialog.OptionDialog;
import com.example.hostel.Models.Expense;
import com.example.hostel.Models.Property;

import java.io.Serializable;

public class AddExpenseDTO implements Serializable {
    private String expenseRef;
    private Expense expense;
    private OptionDialog.Option option;
    private String date;
    private String month;
    private String propertyRefKey;
    private Property property;
    private String propertyName;

    public String getExpenseRef() {
        return expenseRef;
    }

    public void setExpenseRef(String expenseRef) {
        this.expenseRef = expenseRef;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public OptionDialog.Option getOption() {
        return option;
    }

    public void setOption(OptionDialog.Option option) {
        this.option = option;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
