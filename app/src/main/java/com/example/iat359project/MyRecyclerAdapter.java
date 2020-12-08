package com.example.iat359project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: edit url to use gym's latLng

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    public JSONArray list;
    MyDatabase db;
    Context context;
    String requestUrl, username;
    RequestQueue requestQueue;
    public ViewBookingActivity activity;

    public MyRecyclerAdapter(JSONArray jsonList, MyDatabase db, Context context, String username,ViewBookingActivity activity) {
        this.list = jsonList;
        this.db = db;
        this.context = context;
        this.username = username;
        this.activity = activity;
    }

    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v, db, context, activity);
        Log.d("noti","recyclerview written");

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, int position) {
        JSONArray row = null;
        try {
            row = list.getJSONArray(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        String[]  results = (list.get(position)).split(";");
        if(row != null) {

            try {
                holder.rowUser = row.getString(0);
                holder.rowHour = row.getString(1);
                holder.rowDay = row.getString(2);
                holder.rowMonth = row.getString(3);
                holder.rowLocation = row.getString(4);
                holder.sesHour.setText(holder.rowHour);
                holder.sesDate.setText(holder.rowDay + " " + holder.rowMonth);
                holder.sesLocation.setText(holder.rowLocation);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
//        Log.d("noti","called");

    }

    @Override
    public int getItemCount() {
        return list.length();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        MyDatabase db;
        public TextView sesHour;
        public TextView sesDate;
        public TextView sesLocation;
        public Button cancelButton;
        public LinearLayout myLayout;
        String sesIdIndex = "0";
        public ViewBookingActivity activity;
        Context context;
        public String rowUser,rowHour, rowDay, rowMonth, rowLocation;

        public MyViewHolder(View itemView, MyDatabase db, Context context, ViewBookingActivity activity) {
            super(itemView);
            this.activity = activity;
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
            activity.deleteRow(rowUser, rowHour, rowDay, rowMonth, rowLocation);

        }

        public void jumptoImplicit(){
            String location1 = "Fitness World";
            String location2 = "Good Life";
            String location3 = "Steve Nash Sports Club";
            String location4 = "F45 Training Lougheed";
            String location5 = "Fitness 2000 Athletic Club";
            String location6 = "Cameron Recreation Complex";
            String location = String.valueOf(sesLocation.getText());

            Uri geoLocation_1 = Uri.parse("https://www.google.com/maps/place/Fitness+World/@49.1900748,-122.8393383,15z/data=!4m2!3m1!1s0x0:0x736f7c4fec76ef38?sa=X&ved=2ahUKEwiMvYP0_L3tAhWYCjQIHekyBDIQ_BIwEnoECCgQBQ");
            Uri geoLocation_2 = Uri.parse("https://www.google.com/maps/place/GoodLife/@49.2388465,-122.8962411,14z/data=!4m8!1m2!2m1!1sGood+Life!3m4!1s0x0:0x7e47a20810bccd0f!8m2!3d49.2528065!4d-122.8933668");
            Uri geoLocation_3 = Uri.parse("https://www.google.com/maps/place/Steve+Nash+Sports+Club/@49.2505877,-122.8996621,15.29z/data=!3m1!5s0x5486783a30d22095:0x2d95b7e2289fd110!4m8!1m2!2m1!1sSteve+Nash+Sports+Club!3m4!1s0x0:0x55910b8232f1e12c!8m2!3d49.2513639!4d-122.8962851");
            Uri geoLocation_4 = Uri.parse("https://www.google.com/maps/place/F45+Training+Lougheed/@49.255206,-122.8946707,17z/data=!4m12!1m6!3m5!1s0x548679053f43b9d1:0x53a0d29026cc3f7e!2sF45+Training+Lougheed!8m2!3d49.2552025!4d-122.892482!3m4!1s0x548679053f43b9d1:0x53a0d29026cc3f7e!8m2!3d49.2552025!4d-122.892482");
            Uri geoLocation_5 = Uri.parse("https://www.google.com/maps/place/Fitness+2000+Athletic+Club/@49.2518078,-122.9035482,17z/data=!4m12!1m6!3m5!1s0x54867830097fe27d:0x69fac5a50e2852bb!2sFitness+2000+Athletic+Club!8m2!3d49.2518043!4d-122.9013595!3m4!1s0x54867830097fe27d:0x69fac5a50e2852bb!8m2!3d49.2518043!4d-122.9013595");
            Uri geoLocation_6 = Uri.parse("https://www.google.com/maps/place/Cameron+Recreation+Complex/@49.2539422,-122.9014288,17z/data=!4m12!1m6!3m5!1s0x54867830b92ec437:0x6b1f108f0647f62!2sCameron+Recreation+Complex!8m2!3d49.2539387!4d-122.8992401!3m4!1s0x54867830b92ec437:0x6b1f108f0647f62!8m2!3d49.2539387!4d-122.8992401");

            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW);

            switch (location){
                case "Fitness World":
                    intent.setData(geoLocation_1);
                    break;
                case "Good Life":
                    intent.setData(geoLocation_2);
                case "Steve Nash Sports Club":
                    intent.setData(geoLocation_3);
                    break;
                case "F45 Training Lougheed":
                    intent.setData(geoLocation_4);
                case "Fitness 2000 Athletic Club":
                    intent.setData(geoLocation_5);
                    break;
                case "Cameron Recreation Complex":
                    intent.setData(geoLocation_6);
                default:
                    throw new IllegalStateException("Unexpected value: " + location);
            }

            List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            boolean safeIntent = activities.size() > 0;


            if (safeIntent) {
                context.startActivity(intent);
            }

        }
    }


}