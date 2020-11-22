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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    public ArrayList<String> list;
    MyDatabase db;
    Context context;

    public MyRecyclerAdapter(ArrayList<String> list, MyDatabase db, Context context) {
        this.list = list;
        this.db = db;
        this.context = context;
        Log.d("noti", String.valueOf(list.size()));
    }

    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v, db, context);
        Log.d("noti","recyclerview written");
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, int position) {

        String[]  results = (list.get(position)).split(";");
        holder.sesIdIndex = results[0];
        holder.sesHour.setText(results[1]);
        holder.sesDate.setText(results[2]);
        holder.sesLocation.setText(results[3]);
        Log.d("noti","called");
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        MyDatabase db;
        public TextView sesHour;
        public TextView sesDate;
        public TextView sesLocation;
        public Button cancelButton;
        public LinearLayout myLayout;
        String sesIdIndex = "0";
        Context context;

        public MyViewHolder(View itemView, MyDatabase db, Context context) {
            super(itemView);
            myLayout = (LinearLayout) itemView;
            this.db = db;
            this.context = context;

            sesHour = (TextView) itemView.findViewById(R.id.sessionHour);
            sesDate = (TextView) itemView.findViewById(R.id.sessionDate);
            sesLocation = (TextView) itemView.findViewById(R.id.sessionLocation);
            cancelButton = (Button) itemView.findViewById(R.id.cancelButton);


            cancelButton.setOnClickListener(this);
            sesLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumptoImplicit();
                }
            });

        }

        @Override
        public void onClick(View view) {
            int id = db.deleteRow(sesIdIndex);
            if (id < 0) {
                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(context, "Booking cancelled", Toast.LENGTH_SHORT);
                Intent intent = new Intent(context, SignUpActivity.class);
                context.startActivity(intent);
            }
        }

        public void jumptoImplicit(){
            String location1 = "Salish Ct, Burnaby";
            String location2 = "Central Ave, Surrey";
            String location = String.valueOf(sesLocation.getText());

            Uri geoLocation_1 = Uri.parse("https://www.google.com/maps/place/Fitness+2000+Athletic+Club/@49.2517273,-122.9012737,16z/data=!4m5!3m4!1s0x0:0x69fac5a50e2852bb!8m2!3d49.2518043!4d-122.9013595");
            Uri geoLocation_2 = Uri.parse("https://www.google.com/maps/place/Fitness+World/@49.1893031,-122.8413226,16.83z/data=!4m8!1m2!2m1!1sfitnessworld!3m4!1s0x0:0x736f7c4fec76ef38!8m2!3d49.1900745!4d-122.8393391");



            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if(location.equals(location1)) {
                intent.setData(geoLocation_1);
            }
            else if (location.equals(location2)) {
                intent.setData(geoLocation_2);
            }

            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            boolean safeIntent = activities.size() > 0;


            if (safeIntent) {
                context.startActivity(intent);
            }

        }
    }
}