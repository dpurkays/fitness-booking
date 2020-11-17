package com.example.iat359project;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    Spinner daySpinner, daytimeSpinner, monthSpinner, hourSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        daytimeSpinner = (Spinner) findViewById(R.id.daytimeSpinner);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        hourSpinner = (Spinner) findViewById(R.id.hourSpinner);

    }
}
