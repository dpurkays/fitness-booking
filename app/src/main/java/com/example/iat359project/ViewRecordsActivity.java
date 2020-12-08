package com.example.iat359project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ViewRecordsActivity  extends AppCompatActivity {
    RecyclerView myRecycler;
    MyDatabase  db;
    MyRecyclerRecordsAdapter myAdapter;
    MyHelper helper;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecords);


        db = new MyDatabase(this);
        helper = new MyHelper(this);

        SharedPreferences sharedPrefs = getSharedPreferences("username", Context.MODE_PRIVATE);
        if (sharedPrefs != null){
            username = sharedPrefs.getString("getName", "");
        }
        else username = "";

        myRecycler =(RecyclerView) findViewById(R.id.recordsList);
        myAdapter = new MyRecyclerRecordsAdapter(db, this, username);
        myRecycler.setAdapter(myAdapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
