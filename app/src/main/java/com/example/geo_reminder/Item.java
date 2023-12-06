package com.example.geo_reminder;

import java.util.ArrayList;
import java.util.List;

public class Item {
    List<LocationDetails> locations;
    String itemName;
    String category;

    public Item(String itemName, String category){
        this.itemName = itemName;
        this.category = category;
        this.locations = new ArrayList<>();
    }

    public void addLocation(double latitude, double longitude, String storeName){
        LocationDetails location = new LocationDetails(latitude, longitude, storeName);
        locations.add(location);
    }

    public List<LocationDetails> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDetails> locations) {
        this.locations = locations;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
