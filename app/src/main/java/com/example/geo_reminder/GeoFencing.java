package com.example.geo_reminder;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GeoFencing {
    public double radius;
    public double velocity;
    public static LocationManager locationManager;
    public static LocationListener locationListener;

    List<Item> items;
    public GeoFencing(){
        this.radius = 2;
        this.velocity = 30;
        this.items = new ArrayList<>();

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng currentLocation = new LatLng(latitude, longitude);
                for (int i = 0; i < items.size(); i++) {
                    onLocationChange(currentLocation, items.get(i), velocity);
                }
                locationManager.removeUpdates(this);
            }
        };

    }

    public boolean shouldNotify(LatLng currentLocation, LatLng storeLocation, double velocity){
        if(velocity > 30)
            return false;
        double radiusOfEarth = 3959;

        double lat1 = currentLocation.latitude;
        double lat2 = storeLocation.latitude;
        double lon1 = currentLocation.longitude;
        double lon2 = storeLocation.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double distance = radiusOfEarth * c;

        return (distance < radius);
    }

    void onLocationChange(LatLng currentLocation, Item item, double velocity){
        DatabaseReference itemStoreRef = FirebaseDatabase.getInstance().getReference("itemstore");
        Query query = itemStoreRef.orderByChild("itemName").equalTo(item.getCategory());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        double latitude = itemSnapshot.child("latitude").getValue(Double.class);
                        double longitude = itemSnapshot.child("longitude").getValue(Double.class);
                        String storeName = itemSnapshot.child("storeName").getValue(String.class);

                        if(shouldNotify(currentLocation, new LatLng(latitude, longitude), velocity)){
                            System.out.println("User should be notified");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error);
            }
        });
    }

    void removeItem(Item i){
        this.items.remove(i);
    }

    void addItem(Item i){
        this.items.add(i);
    }

}
