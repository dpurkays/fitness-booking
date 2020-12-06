package com.example.iat359project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String DEFAULT = "20";
    public static final String DEFAULT2 = "Have Not Sign In";

    Spinner daySpinner, daytimeSpinner, monthSpinner, hourSpinner,locationSpinner;
    Button bookButton, viewBookingButton;
    TextView sessionTextview, usernameTextview, monthTextview, dayTextview, timeTextview, hourTextview, locationTextview;
    ArrayAdapter<CharSequence> dayAdapter,hourAdapter;
    MyDatabase db;

    String gymName;
    LatLng gymLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //get gym's data from mapsActivity
        getGymData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new MyDatabase(this);

        bookButton = (Button) findViewById(R.id.bookButton);
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSession();
            }
        });

        viewBookingButton = (Button) findViewById(R.id.viewBookingButton);
        viewBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBooking();
            }
        });

        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        dayAdapter = ArrayAdapter.createFromResource(this, R.array.dayAArray, R.layout.support_simple_spinner_dropdown_item);
        dayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(this);

        daytimeSpinner = (Spinner) findViewById(R.id.daytimeSpinner);
        ArrayAdapter<CharSequence> daytimeAdapter = ArrayAdapter.createFromResource(this, R.array.daytimeArray, R.layout.support_simple_spinner_dropdown_item);
        daytimeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        daytimeSpinner.setAdapter(daytimeAdapter);
        daytimeSpinner.setOnItemSelectedListener(this);

        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this, R.array.monthArray, R.layout.support_simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemSelectedListener(this);

        hourSpinner = (Spinner) findViewById(R.id.hourSpinner);
        hourAdapter = ArrayAdapter.createFromResource(this, R.array.morningHoursArray, R.layout.support_simple_spinner_dropdown_item);
        hourAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);
        hourSpinner.setOnItemSelectedListener(this);

        locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.locationArray, R.layout.support_simple_spinner_dropdown_item);
        locationAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);
        locationSpinner.setOnItemSelectedListener(this);

        sessionTextview = (TextView)findViewById(R.id.textViewSession);
        usernameTextview = (TextView)findViewById(R.id.textViewUsername);
        monthTextview = (TextView)findViewById(R.id.textViewMonth);
        dayTextview = (TextView)findViewById(R.id.textViewDay);
        timeTextview = (TextView)findViewById(R.id.textViewTime);
        hourTextview = (TextView)findViewById(R.id.textViewHour);
        locationTextview = (TextView)findViewById(R.id.textViewLocation);

    }

    //collect gym data from MapsActivity
    private void getGymData() {
        gymName = getIntent().getStringExtra("TITLE");
        Double lat = getIntent().getDoubleExtra("LAT", 0);
        Double lng = getIntent().getDoubleExtra("LNG", 0);
        gymLatLng = new LatLng(lat,lng);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPrefs = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        String getSize = sharedPrefs.getString("selectedTextSize", DEFAULT);
        Float size = Float.parseFloat(getSize);
        bookButton.setTextSize(size);
        sessionTextview.setTextSize(size);
        usernameTextview.setTextSize(size);
        monthTextview.setTextSize(size);
        dayTextview.setTextSize(size);
        timeTextview.setTextSize(size);
        hourTextview.setTextSize(size);
        locationTextview.setTextSize(size);

        SharedPreferences sharedPrefs2 = getSharedPreferences("username", Context.MODE_PRIVATE);
        String getName = sharedPrefs2.getString("getName", DEFAULT2);
        usernameTextview.setText("Welcome " + getName);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {

            case R.id.monthSpinner:

                switch (position) {
                    case 0:
                    case 2:
                    case 4:
                    case 6:
                    case 7:
                    case 9:
                    case 11:
                        daySpinner.setAdapter(null);
                        dayAdapter = ArrayAdapter.createFromResource(this, R.array.dayAArray, R.layout.support_simple_spinner_dropdown_item);
                        daySpinner.setAdapter(dayAdapter);
                        break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                        daySpinner.setAdapter(null);
                        dayAdapter = ArrayAdapter.createFromResource(this, R.array.dayBArray, R.layout.support_simple_spinner_dropdown_item);
                        daySpinner.setAdapter(dayAdapter);
                        break;
                    case 1:
                        daySpinner.setAdapter(null);
                        dayAdapter = ArrayAdapter.createFromResource(this, R.array.dayCArray, R.layout.support_simple_spinner_dropdown_item);
                        daySpinner.setAdapter(dayAdapter);
                        break;

                }

                break;
            case R.id.daytimeSpinner:
                if(position == 0) {
                    hourSpinner.setAdapter(null);
                    ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.morningHoursArray, R.layout.support_simple_spinner_dropdown_item);
                    hourSpinner.setAdapter(hourAdapter);
                }
                else if(position == 1) {
                    hourSpinner.setAdapter(null);
                    ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.afternoonHoursArray, R.layout.support_simple_spinner_dropdown_item);
                    hourSpinner.setAdapter(hourAdapter);
                }
                else {
                    hourSpinner.setAdapter(null);
                    ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.eveningHoursArray, R.layout.support_simple_spinner_dropdown_item);
                    hourSpinner.setAdapter(hourAdapter);
                }
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void bookSession(){

        String hour = hourSpinner.getSelectedItem().toString();
        String day = daySpinner.getSelectedItem().toString();
        String month = monthSpinner.getSelectedItem().toString();
        String location = locationSpinner.getSelectedItem().toString();

        long id = db.insertSession(hour, day, month, location);
        if (id < 0)
        {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Your session on "+ day + " " + month +" is booked", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewBooking(){
        Intent intent = new Intent(this, ViewBookingActivity.class);
        startActivity(intent);
    }


}
