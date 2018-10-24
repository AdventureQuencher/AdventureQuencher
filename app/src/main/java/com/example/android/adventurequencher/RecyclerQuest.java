package com.example.android.adventurequencher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerQuest extends RecyclerView.Adapter<RecyclerQuest.ViewHolder>{

    private static final String TAG = "RecyclerQuest";
    private ArrayList<String> locationNames = new ArrayList<>();
    private ArrayList<ImageView> locationImages = new ArrayList<>();
    private Context context;

    public RecyclerQuest(Context context, ArrayList<String> locationNames,
                         ArrayList<ImageView> locationImages)
    {
        this.locationNames = locationNames;
        this.locationImages = locationImages;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  final int position)
    {
        Log.d(TAG, "onBindViewer called: ");

        holder.locationName.setText(locationNames.get(position));


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,locationNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return locationNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

       public TextView locationName;
       public ImageView image;
       public RelativeLayout layout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.image_name);
            locationName = itemView.findViewById(R.id.location_item);
            layout = itemView.findViewById(R.id.list_layout);
        }
    }
}
