package com.example.geo_reminder;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.example.geo_reminder.databinding.ActivityMapsBinding;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String source;
    private String destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            source = intent.getStringExtra("SOURCE");
            destination = intent.getStringExtra("DESTINATION");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));
            mMap = googleMap;
            LatLng sourceLatLng = getLocationFromAddress(source);
            if (sourceLatLng != null) {
                mMap.addMarker(new MarkerOptions().position(sourceLatLng).title("Source: " + source));
            }
            LatLng destLatLng = getLocationFromAddress(destination);
            if (destLatLng != null) {
                mMap.addMarker(new MarkerOptions().position(destLatLng).title("Destination: " + destination));
            }
            if (sourceLatLng != null && destLatLng != null) {
                View mapView = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getView();
                if (mapView != null) {
                    mapView.post(() -> {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(sourceLatLng);
                        builder.include(destLatLng);
                        LatLngBounds bounds = builder.build();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                        new GetDirectionsTask().execute(sourceLatLng, destLatLng);
                    });
                }
            }
            if (!success) {
                Log.e("Maps Activity", "Maps Activity Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Maps Activity", "Can't find style. Error: ", e);
        }

    }

    private LatLng getLocationFromAddress(String address) {
        if (address != null && !address.isEmpty()) {
            // Split the string into latitude and longitude
            String[] latLngArray = address.split(",");
            if (latLngArray.length == 2) {
                try {
                    double latitude = Double.parseDouble(latLngArray[0].trim());
                    double longitude = Double.parseDouble(latLngArray[1].trim());
                    return new LatLng(latitude, longitude);
                } catch (NumberFormatException e) {
                    Log.e("Maps Activity", "Error parsing latitude or longitude", e);
                }
            }
        }
        return null;
    }

    private class GetDirectionsTask extends AsyncTask<LatLng, Void, DirectionsResult> {

        @Override
        protected DirectionsResult doInBackground(LatLng... params) {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyCKw9nR_kYnCiCl88ZDarzV2hCp_sjw-kE")
                    .build();

            try {
                return DirectionsApi.newRequest(context)
                        .mode(TravelMode.DRIVING)
                        .origin(new com.google.maps.model.LatLng(params[0].latitude, params[0].longitude))
                        .destination(new com.google.maps.model.LatLng(params[1].latitude, params[1].longitude))
                        .units(Unit.METRIC)
                        .await();
            } catch (Exception e) {
                Log.e("Maps Activity", "Error fetching directions", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(DirectionsResult directionsResult) {
            if (directionsResult != null) {
                List<LatLng> path = PolyUtil.decode(directionsResult.routes[0].overviewPolyline.getEncodedPath());
                mMap.addPolyline(new PolylineOptions().addAll(path).color(R.color.colorPrimary));
                String distance = directionsResult.routes[0].legs[0].distance.humanReadable;
                String duration = directionsResult.routes[0].legs[0].duration.humanReadable;
                // Display the distance and duration
                TextView directionsTextView = findViewById(R.id.directionsTextView);
                String info = "Distance: " + distance + "\nDuration: " + duration;
                directionsTextView.setText(info);
                //Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
            }
        }
    }
}