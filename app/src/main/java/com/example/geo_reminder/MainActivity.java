package com.example.geo_reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    public List<String> items = new ArrayList<>();

    void onLocationChange(LatLng currentLocation, String item, double velocity){
        GeoFencing geoFencing  = new GeoFencing();
        DatabaseReference itemStoreRef = FirebaseDatabase.getInstance().getReference("itemstore");
        Query query = itemStoreRef.orderByChild("itemName").equalTo(item);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        double latitude = itemSnapshot.child("latitude").getValue(Double.class);
                        double longitude = itemSnapshot.child("longitude").getValue(Double.class);
                        String storeName = itemSnapshot.child("storeName").getValue(String.class);

                        if(geoFencing.shouldNotify(currentLocation, new LatLng(latitude, longitude), velocity)){
                            showAlertDialog(item, storeName);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LatLng currentLocation = new LatLng(33.423920, -111.928150);
        items.add("Apple");
        items.add("Banana");
        double velocity = 20;
        if(GeoFencing.isLocationChanged()) {
            for (int i = 0; i < items.size(); i++) {
                onLocationChange(currentLocation, items.get(i), velocity);
            }
        }
    }


    private void showAlertDialog(String item, String storeName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Do you want to buy " + item + " from store: "+ storeName)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("User selected yes");
                        items.remove(item);
                        dialog.dismiss();
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

}