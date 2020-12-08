package com.example.iat359project;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ViewRecordsActivity  extends AppCompatActivity {
    public static final float defaultSize = 20;
    public static final int defaultFont = 1;
    public static final int defaultTheme = 1;

    RecyclerView myRecycler;
    MyDatabase  db;
    MyRecyclerRecordsAdapter myAdapter;
    MyHelper helper;
    String username;

    private TextView textViewRecord, textViewCalories, textViewSteps, textViewDate;
    private ConstraintLayout RecordsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecords);

        textViewRecord = (TextView) findViewById(R.id.textViewRecord);
        RecordsLayout = (ConstraintLayout) findViewById(R.id.RecordsLayout);

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

    @Override
    protected void onResume() {
        super.onResume();

        //Load TEXT SIZE from SharedPreferences
        SharedPreferences textSize = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        float getSize = textSize.getFloat("selectedTextSize", defaultSize);
        textViewRecord.setTextSize(getSize);

        //Load TEXT FONT from SharedPreferences
        SharedPreferences textFont = getSharedPreferences("textFont", Context.MODE_PRIVATE);
        int getFont = textFont.getInt("selectedTextFont", defaultFont);
        if(getFont == 1){
            Typeface typeface = getResources().getFont(R.font.roboto_light);
            textViewRecord.setTypeface(typeface);
        } else if(getFont == 2){
            Typeface typeface = getResources().getFont(R.font.jet_brains_monowght);
            textViewRecord.setTypeface(typeface);
        }else if(getFont == 3) {
            Typeface typeface = getResources().getFont(R.font.nerko_one);
            textViewRecord.setTypeface(typeface);
        } else if(getFont == 4){
            Typeface typeface = getResources().getFont(R.font.permanent_marker);
            textViewRecord.setTypeface(typeface);
        }

        //Load THEME from SharedPreferences
        SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int getMode = theme.getInt("selectedTheme", defaultTheme);
        if(getMode == 1){
            RecordsLayout.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_dark));
        }else if(getMode == 2){
            RecordsLayout.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_light));
        }
    }
}
