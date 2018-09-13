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

public class BottomNavigate extends AppCompatActivity {

    private TextView mTextMessage;
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
                 getSupportFragmentManager().beginTransaction().replace(R.id.m_frame,mapFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                   mTextMessage.setText("null");
                   return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigate);

        mTextMessage = findViewById(R.id.message);
        mFrame = findViewById(R.id.m_frame);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
