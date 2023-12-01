package com.example.geo_reminder;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class GeoFencing {
    public List<LatLng> coordinates;
    public double radius;
    public double velocity;

    public GeoFencing(){
        this.radius = 2;
        this.coordinates = new ArrayList<>();
        this.velocity = 30;
    }
    public void addCoordinate(double latitude, double longitude) {
        LatLng tempeLatLng = new LatLng(latitude, longitude);
        coordinates.add(tempeLatLng);
    }

    public boolean isInRadius(LatLng currentLocation, LatLng shopLocation) {
        double radiusOfEarth = 3959;

        double lat1 = currentLocation.latitude;
        double lat2 = shopLocation.latitude;
        double lon1 = currentLocation.longitude;
        double lon2 = shopLocation.longitude;
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


    public boolean shouldNotify(LatLng currentLocation, LatLng storeLocation, double velocity){
        if(velocity > 30)
            return false;
        return isInRadius(currentLocation, storeLocation);
    }

}
