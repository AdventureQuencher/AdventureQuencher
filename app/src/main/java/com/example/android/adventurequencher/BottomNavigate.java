package com.example.android.adventurequencher;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;


import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
/*
    This class acts as the main activity for the application after the user log into his/her account
    OnStart() will open the the Google Maps API and The bottom navigation selection which takes in a
    fragment for its frame. Currently there are 3 options: QuestLog, Leaderboard and Rewards.

    onItemSelect() calls the fragment or activity and displays the layout of the fragment inside the
    FrameLayout stored in this xml file above the navigation bar.
 */

public class BottomNavigate extends AppCompatActivity {

    private FrameLayout mFrame;
    private MapFragment mapFragment = new MapFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                   Intent quest = new Intent(getApplicationContext(),MainQuest.class);
                   startActivity(quest);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                   return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigate);
        getSupportFragmentManager().beginTransaction().replace(R.id.m_frame,mapFragment).commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
