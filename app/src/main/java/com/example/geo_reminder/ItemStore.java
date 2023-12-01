package com.example.geo_reminder;

public class ItemStore {
    private String itemName;
    private String storeName;
    private double latitude;
    private double longitude;

    // Empty constructor required for Firebase Realtime Database
    public ItemStore() {
    }

    // Constructor with parameters
    public ItemStore(String itemName, String storeName, double latitude, double longitude) {
        this.itemName = itemName;
        this.storeName = storeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter and setter methods for all variables

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
