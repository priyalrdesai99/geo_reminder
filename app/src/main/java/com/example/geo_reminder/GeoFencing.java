package com.example.geo_reminder;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoFencing {
    public double radius;
    public double velocity;
    public Context context;
    Map<String, List<Item>> itemsCategoryWise;
    String userId;

    public GeoFencing(Context context, String userId) {
        this.radius = 2;
        this.velocity = 30;
        this.context = context;
        this.userId = userId;
    }

    public boolean shouldNotify(LatLng currentLocation, LatLng storeLocation, double velocity) {
        if (velocity > 30)
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

    public void fetchAllItems(LatLng currentLocation, double velocity) {
        itemsCategoryWise = new HashMap<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ToDoList");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.child(userId).getChildren()) {
                    String itemName = childSnapshot.child("itemName").getValue(String.class);
                    String category = childSnapshot.child("category").getValue(String.class);
                    Item i = new Item(itemName, category);
                    List<Item> itemList = itemsCategoryWise.getOrDefault(category, new ArrayList<>());
                    System.out.println("Item Name: " + itemName);
                    itemList.add(i);
                    itemsCategoryWise.put(category, itemList);
                }
                    onLocationChange(currentLocation, velocity);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    public void onLocationChange(LatLng currentLocation, double velocity) {
        for (String category : itemsCategoryWise.keySet()) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Categories");
            databaseReference.child(category).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                            double latitude = itemSnapshot.child("lat").getValue(Double.class);
                            double longitude = itemSnapshot.child("long").getValue(Double.class);
                            String storeName = itemSnapshot.child("Store").getValue(String.class);
                            System.out.println("Latitude : " + latitude);
                            System.out.println("Store Name : " + storeName);
                            System.out.println("Longitude : " + longitude);
                            if (shouldNotify(currentLocation, new LatLng(latitude, longitude), velocity)) {
                                System.out.println("User should be notified");
                                showAlertDialog(itemsCategoryWise.get(category), currentLocation, storeName, latitude, longitude);
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
    }

    private void showAlertDialog(List<Item> items, LatLng currentLocation, String storeName, double latitude, double longitude) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation")
                .setMessage("Do you want to buy items from " + storeName + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("User selected yes");
                        dialog.dismiss();
                        Intent intent = new Intent(context, MapsActivity.class);
                        intent.putExtra("SOURCE", currentLocation.latitude + ", " + currentLocation.longitude);
                        intent.putExtra("DESTINATION", latitude + ", " + longitude);
                        context.startActivity(intent);
                        deleteValuesFromDatabase(items.get(0).category);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("User selected no");
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void deleteValuesFromDatabase(String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ToDoList");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.child(userId).getChildren()) {
                    String itemCategory = itemSnapshot.child("category").getValue(String.class);

                    if (category.equals(itemCategory)) {
                        deleteItem(itemSnapshot.getRef());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }

    private void deleteItem(DatabaseReference ref) {
        ref.removeValue().addOnSuccessListener(aVoid -> {
            System.out.println("Item deleted successfully!");
        }).addOnFailureListener(e -> {
            System.out.println("Error deleting item: " + e.getMessage());
        });
    }

}
