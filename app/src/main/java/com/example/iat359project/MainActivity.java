package com.example.iat359project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.support.v4.app.INotificationSideChannel;
import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button accelerometerButton, signInButton, signUpButton, testMapButton;
    public static final String DEFAULT = "#FFFFFF";
    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerometerButton = (Button) findViewById(R.id.accelerometerButton);
        accelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAccelerometerActivity();
            }
        });

        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startSignInActivity();
            }
        });

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });

        testMapButton = (Button) findViewById(R.id.test_map);
        testMapButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startMapActivity();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPrefs = getSharedPreferences("BgColor", Context.MODE_PRIVATE);
        String BgColor = sharedPrefs.getString("selectedBgColor", DEFAULT);
        main = (ConstraintLayout) findViewById(R.id.Main);
        main.setBackgroundColor(Color.parseColor(BgColor));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this,"Setting Clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this.getApplicationContext(),SettingActivity.class);
                startActivity(i);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startAccelerometerActivity(){
        Intent i = new Intent(this.getApplicationContext(),AccelerometerActivity.class);
        startActivity(i);
    }

    public void startSignInActivity(){
        Intent i = new Intent(this.getApplicationContext(),SignInActivity.class);
        startActivity(i);
    }

    public void startSignUpActivity(){
        Intent i = new Intent(this.getApplicationContext(),SignUpActivity.class);
        startActivity(i);
    }

    public void startMapActivity(){
        Intent i = new Intent(this.getApplicationContext(),MapsActivity.class);
        startActivity(i);
    }
}