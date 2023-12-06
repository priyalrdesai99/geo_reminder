package com.example.geo_reminder;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.location.LocationManager;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    private LogInFragment lif;
    private SignUpFragment suf;

    GeoFencing geoFencing;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        lif = new LogInFragment();
        transaction.replace(R.id.flMain, lif);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            lif = new LogInFragment();
            transaction.replace(R.id.flMain, lif);
            transaction.commit();
        }
    }
}


        geoFencing = new GeoFencing();
        GeoFencing.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Item i = new Item("Apple", "Grocery");
        i.addLocation(33.422580, -111.924900, "Trader Joe's");
        geoFencing.addItem(i);
    }


    private void showAlertDialog(Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Do you want to buy " + item.getItemName())
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("User selected yes");
                        geoFencing.removeItem(item);
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
//        thread = new Thread(
//                () -> {
//                    while (true){
//                        System.out.println("Hello");
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//        );


    }

}

