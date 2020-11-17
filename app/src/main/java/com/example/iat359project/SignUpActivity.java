package com.example.iat359project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner daySpinner, daytimeSpinner, monthSpinner, hourSpinner,locationSpinner;
    Button bookButton;
    ArrayAdapter<CharSequence> dayAdapter,hourAdapter;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                        dayAdapter = ArrayAdapter.createFromResource(this, R.array.dayAArray, R.layout.support_simple_spinner_dropdown_item);
                        break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                        dayAdapter = ArrayAdapter.createFromResource(this, R.array.dayBArray, R.layout.support_simple_spinner_dropdown_item);
                        break;
                    case 1:
                        dayAdapter = ArrayAdapter.createFromResource(this, R.array.dayCArray, R.layout.support_simple_spinner_dropdown_item);
                        break;

                }

                break;
            case R.id.daytimeSpinner:
                if(position == 0) {
                    ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.morningHoursArray, R.layout.support_simple_spinner_dropdown_item);
                }
                else if(position == 1) {
                    ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.afternoonHoursArray, R.layout.support_simple_spinner_dropdown_item);
                }
                else {
                    ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.eveningHoursArray, R.layout.support_simple_spinner_dropdown_item);
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
}
