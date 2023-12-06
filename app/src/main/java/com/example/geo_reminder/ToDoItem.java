package com.example.geo_reminder;
public class ToDoItem {

    private String itemName;
    private String description;
    private String category;  // Added category field

    public ToDoItem() {
        // Default constructor required for calls to DataSnapshot.getValue(ToDoItem.class)
    }

    // Constructor with parameters, including category
    public ToDoItem(String itemName, String description, String category) {
        this.itemName = itemName;
        this.description = description;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
