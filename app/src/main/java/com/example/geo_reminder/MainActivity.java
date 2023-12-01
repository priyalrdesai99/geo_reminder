package com.example.geo_reminder;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GeoFencing geoFencing  = new GeoFencing();
        geoFencing.addCoordinate(33.422580, -111.924900);
        geoFencing.addCoordinate(33.414332,-111.925852);
        geoFencing.addCoordinate(33.408986,-111.925494);
        String item = "milk";
        List<String> storeList = new ArrayList<>();

        // Adding store names to the list
        storeList.add("Trader Joe's");
        storeList.add("CVS");
        storeList.add("Safeway");

        LatLng currentLocation = new LatLng(33.423920,-111.928150);
        double velocity = 20;
        for(int i=0; i< geoFencing.coordinates.size(); i++){
            if(geoFencing.shouldNotify(currentLocation, geoFencing.coordinates.get(i), velocity)){
                showAlertDialog("Do you want to buy " + item + " from store: "+ storeList.get(i));
            }
        }
    }


    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("User selected yes");
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