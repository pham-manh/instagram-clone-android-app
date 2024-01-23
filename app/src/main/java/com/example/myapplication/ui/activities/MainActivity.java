package com.example.myapplication.ui.activities;

import static com.example.myapplication.utils.Activity.POST_ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.utils.ActivityUtils;
import com.example.myapplication.ui.fragment.HomeFragment;
import com.example.myapplication.ui.fragment.NotificationFragment;
import com.example.myapplication.ui.fragment.ProfileFragment;
import com.example.myapplication.ui.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            item.setChecked(true);
            Log.i("Click", String.valueOf(item.getItemId()));
            if (item.getItemId() == R.id.nav_home) {
                selectorFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_search) {
                selectorFragment = new SearchFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectorFragment = new ProfileFragment();
            } else if (item.getItemId() == R.id.nav_heart) {
                selectorFragment = new NotificationFragment();
            } else if (item.getItemId() == R.id.nav_add) {
                startActivity(new Intent(this,
                        ActivityUtils.getActivityClass(POST_ACTIVITY)));
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
            }
            if (selectorFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectorFragment)
                        .commit();
            }
            return true;
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }
}