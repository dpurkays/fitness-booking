package com.example.iat359project;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewBookingActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    public static final float defaultSize = 20;
    public static final int defaultFont = 1;
    public static final int defaultTheme = 1;

    RecyclerView myRecycler;
    MyDatabase db;
    MyRecyclerAdapter myAdapter;
    MyHelper helper;


    private TextView textViewBooking, textViewHour, textViewDate, textViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbooking);
        myRecycler = (RecyclerView) findViewById(R.id.bookingList);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        Cursor cursor = (Cursor) db.getSession();

        int index1 = cursor.getColumnIndex(Constants.COLUMN_HOUR);
        int index2 = cursor.getColumnIndex(Constants.COLUMN_DAY);
        int index3 = cursor.getColumnIndex(Constants.COLUMN_MONTH);
        int index4 = cursor.getColumnIndex(Constants.COLUMN_SESID);
        int index5 = cursor.getColumnIndex(Constants.COLUMN_LOCATION);

        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String sesHour = cursor.getString(index1);
            String sesDay = cursor.getString(index2);
            String sesMonth = cursor.getString(index3);
            String sesId = cursor.getString(index4);
            String sesLocation = cursor.getString(index5);

            String s = sesId +";" + sesHour +";" + sesDay + " " + sesMonth +";" + sesLocation;
            mArrayList.add(s);
            cursor.moveToNext();
        }

        myAdapter = new MyRecyclerAdapter(mArrayList, db, this);
        myRecycler.setAdapter(myAdapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        textViewBooking = (TextView) findViewById(R.id.textViewBooking);
        textViewHour = (TextView) findViewById(R.id.textViewHour);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Load TEXT SIZE from SharedPreferences
        SharedPreferences textSize = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        float getSize = textSize.getFloat("selectedTextSize", defaultSize);
        textViewBooking.setTextSize(getSize);
        textViewHour.setTextSize(getSize);
        textViewDate.setTextSize(getSize);
        textViewLocation.setTextSize(getSize);

        //Load TEXT FONT from SharedPreferences
        SharedPreferences textFont = getSharedPreferences("textFont", Context.MODE_PRIVATE);
        int getFont = textFont.getInt("selectedTextFont", defaultFont);
        if(getFont == 1){
            Typeface typeface = getResources().getFont(R.font.roboto_light);
            textViewBooking.setTypeface(typeface);
            textViewHour.setTypeface(typeface);
            textViewDate.setTypeface(typeface);
            textViewLocation.setTypeface(typeface);
        } else if(getFont == 2){
            Typeface typeface = getResources().getFont(R.font.jet_brains_monowght);
            textViewBooking.setTypeface(typeface);
            textViewHour.setTypeface(typeface);
            textViewDate.setTypeface(typeface);
            textViewLocation.setTypeface(typeface);
        }else if(getFont == 3) {
            Typeface typeface = getResources().getFont(R.font.nerko_one);
            textViewBooking.setTypeface(typeface);
            textViewHour.setTypeface(typeface);
            textViewDate.setTypeface(typeface);
            textViewLocation.setTypeface(typeface);
        } else if(getFont == 4){
            Typeface typeface = getResources().getFont(R.font.permanent_marker);
            textViewBooking.setTypeface(typeface);
            textViewHour.setTypeface(typeface);
            textViewDate.setTypeface(typeface);
            textViewLocation.setTypeface(typeface);
        }
    }
}
