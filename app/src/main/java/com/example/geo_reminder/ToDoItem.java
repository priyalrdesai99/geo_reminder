package com.example.geo_reminder;

public class ToDoItem {
    private String itemName;
    private String description;

    // Empty constructor required for Firebase Realtime Database
    public ToDoItem() {
    }

    // Constructor with parameters
    public ToDoItem(String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
    }

    // Getter and setter methods for all variables

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

