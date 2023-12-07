package com.example.geo_reminder;

public class LocationDetails {
    double latitude;
    double longitude;
    String storeName;

    public LocationDetails(double latitude, double longitude, String storeName){
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
