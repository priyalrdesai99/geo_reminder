package com.example.geo_reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private LogInFragment lif;
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