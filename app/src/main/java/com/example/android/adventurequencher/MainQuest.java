package com.example.android.adventurequencher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainQuest extends AppCompatActivity {

    private final String TAG = "MainQuest";
    private ArrayList<String> locations= new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();

    private void initArrayList()
    {
        Log.d(TAG,"adding to list...");

        locations.add("item 1");
        locations.add("item 2");
        locations.add("item 3");
        locations.add("item 1");
        locations.add("item 2");
        locations.add("item 3");
        locations.add("item 1");
        locations.add("item 2");
        locations.add("item 3");

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
