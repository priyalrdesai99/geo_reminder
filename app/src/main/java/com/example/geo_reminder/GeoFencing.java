package com.example.geo_reminder;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GeoFencing {
    public double radius;
    public double velocity;

    private String apiKey;


    public GeoFencing(){
        this.radius = 2;
        this.velocity = 30;
        this.apiKey = "dummy-api-key";
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


    public static boolean isLocationChanged() {
        return false;
    }
}
