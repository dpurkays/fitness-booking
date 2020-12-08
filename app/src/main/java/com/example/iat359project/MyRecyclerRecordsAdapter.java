package com.example.iat359project;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerRecordsAdapter extends RecyclerView.Adapter<MyRecyclerRecordsAdapter.MyViewRecordHolder> {

    ArrayList<String> recordsList;
    MyDatabase db;
    Context context;
    String username;

    public MyRecyclerRecordsAdapter(MyDatabase db, Context context, String username) {
        this.db = db;
        this.context = context;
        this.username = username;
        recordsList = db.getRecords();
    }

    @Override
    public MyViewRecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_record_item,parent,false);
        MyViewRecordHolder viewHolder = new MyViewRecordHolder(v, db, context);
        Log.d("noti","recyclerview written");

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyRecyclerRecordsAdapter.MyViewRecordHolder holder, int position) {


        String[]  results = (recordsList.get(position)).split(";");
        if(results != null) {
            holder.caloriesView.setText(results[1]);
            holder.stepsView.setText(results[2]);
            holder.dateView.setText(results[3]);
        }
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }


    public static class MyViewRecordHolder extends RecyclerView.ViewHolder{

        MyDatabase db;
        public TextView caloriesView;
        public TextView stepsView;
        public TextView dateView;
        public LinearLayout myLayout;
        String sesIdIndex = "0";
        public ViewBookingActivity activity;
        Context context;

        public MyViewRecordHolder(View itemView, MyDatabase db, Context context) {
            super(itemView);
            myLayout = (LinearLayout) itemView;
            this.db = db;
            this.context = context;

            caloriesView = (TextView) itemView.findViewById(R.id.textViewCalories);
            stepsView = (TextView) itemView.findViewById(R.id.textViewSteps);
            dateView = (TextView) itemView.findViewById(R.id.textViewDate);

        }

    }


}
