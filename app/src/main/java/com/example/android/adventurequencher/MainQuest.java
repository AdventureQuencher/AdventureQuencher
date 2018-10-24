package com.example.android.adventurequencher;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/*
    This class manages the Quests created in the mobile application by creating two seperate ArrayLists
    One ArrayList holds text and the second holds images.
 */
public class MainQuest extends AppCompatActivity {

    private final String TAG = "MainQuest";
    private ArrayList<String> locations= new ArrayList<>();
    private ArrayList<ImageView> images = new ArrayList<>();

    @SuppressLint("ResourceType")
    private void initArrayList()
    {
        Log.d(TAG,"adding to list...");
        ImageView iView;
        locations.add("item 1");
        iView = findViewById(R.drawable.background_snow);
        images.add(iView);
        locations.add("item 2");
        iView = findViewById(R.drawable.forest);
        images.add(iView);
        initRecyclerView();
    }

    private void initRecyclerView()
    {
        Log.d(TAG,"Starting RecyclerView: ");

        RecyclerView recyclerView = findViewById(R.id.itemList);
        RecyclerQuest questAdapter = new RecyclerQuest(this,locations,images);
        recyclerView.setAdapter(questAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quest);
        initArrayList();
    }
}
