package com.example.iat359project;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewBookingActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    RecyclerView myRecycler;
    MyDatabase  db;
    MyRecyclerAdapter myAdapter;
    MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbooking);
        myRecycler = (RecyclerView) findViewById(R.id.bookingList);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        Cursor cursor = (Cursor) db.getSession();
//        Bundle extras = getIntent().getExtras();
//        if(extras == null) {
//            userInputType = null;
//            cursor = db.getData();
//        } else {
//            userInputType = extras.getString("CURSOR_INDEX");
//            cursor = (Cursor) db.getSelectedData(userInputType);
//        }


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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;

    }
}
