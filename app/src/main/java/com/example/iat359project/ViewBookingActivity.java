package com.example.iat359project;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ViewBookingActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{
    public static final float defaultSize = 20;
    public static final int defaultFont = 1;
    public static final int defaultTheme = 1;



public class ViewBookingActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener, StringRequestResponse{
    RecyclerView myRecycler;
    MyDatabase db;
    MyRecyclerAdapter myAdapter;
    MyHelper helper;
    RequestQueue requestQueue;
    String requestUrl, username;
    JSONArray resultArray;


    private TextView textViewBooking, textViewHour, textViewDate, textViewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbooking);
        myRecycler = (RecyclerView) findViewById(R.id.bookingList);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

//        Cursor cursor = (Cursor) db.getSession();
//        Bundle extras = getIntent().getExtras();
//        if(extras == null) {
//            userInputType = null;
//            cursor = db.getData();
//        } else {
//            userInputType = extras.getString("CURSOR_INDEX");
//            cursor = (Cursor) db.getSelectedData(userInputType);
//        }


//        int index1 = cursor.getColumnIndex(Constants.COLUMN_HOUR);
//        int index2 = cursor.getColumnIndex(Constants.COLUMN_DAY);
//        int index3 = cursor.getColumnIndex(Constants.COLUMN_MONTH);
//        int index4 = cursor.getColumnIndex(Constants.COLUMN_SESID);
//        int index5 = cursor.getColumnIndex(Constants.COLUMN_LOCATION);
//
//        ArrayList<String> mArrayList = new ArrayList<String>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            String sesHour = cursor.getString(index1);
//            String sesDay = cursor.getString(index2);
//            String sesMonth = cursor.getString(index3);
//            String sesId = cursor.getString(index4);
//            String sesLocation = cursor.getString(index5);
//
//            String s = sesId +";" + sesHour +";" + sesDay + " " + sesMonth +";" + sesLocation;
//            mArrayList.add(s);
//            cursor.moveToNext();
//
//        }

        requestQueue = Volley.newRequestQueue(this);
        requestUrl ="https://project-359-team.000webhostapp.com/returnSession.php";

        SharedPreferences sharedPrefs = getSharedPreferences("username", Context.MODE_PRIVATE);
        if (sharedPrefs != null){
            username = sharedPrefs.getString("getName", "");
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                new onResponseCustom(this, this) {
                    @Override
                    public void onResponse(String response) {
                        try {
                            stringRequestResponse.exportResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new onResponseErrorCustom(this) {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("logging in", error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {
                Map<String, String> prms = new HashMap<>();
                prms.put("sentUsername", username);
                return prms;
            }
        };
        requestQueue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout clickedRow = (LinearLayout) view;

    }

    @Override
    public void exportResponse(String response) throws JSONException {
        if (response.equals("cancel failed")){
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT);
        }
        else if (response.equals("cancelled")){

            Intent intent = new Intent(this, SignUpActivity.class);
            this.startActivity(intent);

            Toast.makeText(this, "Booking cancelled", Toast.LENGTH_SHORT);
        }
        else {
            resultArray = new JSONArray(response);
            myAdapter = new MyRecyclerAdapter(resultArray, db, this, username, this);
            myRecycler.setAdapter(myAdapter);
            myRecycler.setLayoutManager(new LinearLayoutManager(this));
        }
//        JSONObject row = resultArray.getJSONObject(0);
//        Log.d("returnSession","row " + row);
//        String hour = resultArray.getString(2);
//        String day = resultArray.getString(3);
//        Log.d("returnSession", "hour: "+ hour +" day: "+ day);
        myAdapter = new MyRecyclerAdapter(mArrayList, db, this);
        myRecycler.setAdapter(myAdapter);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        textViewBooking = (TextView) findViewById(R.id.textViewBooking);
        textViewHour = (TextView) findViewById(R.id.textViewHour);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
    }

    public String deleteRow(String user, final String hour, final String day, final String month, final String location){
        String result = "nothing";
        requestQueue = Volley.newRequestQueue(this);
        requestUrl ="https://project-359-team.000webhostapp.com/deleteSession.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                new onResponseCustom(this, this) {
                    @Override
                    public void onResponse(String response) {
                        try {
                            stringRequestResponse.exportResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new onResponseErrorCustom(this) {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("logging in", error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError
            {
                Map<String, String> prms = new HashMap<>();
                prms.put("sentUsername", username);
                prms.put("sentHour", hour);
                prms.put("sentDay", day);
                prms.put("sentMonth", month);
                prms.put("sentLocation", location);
                return prms;
            }
        };
        requestQueue.add(stringRequest);


        return result;
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
