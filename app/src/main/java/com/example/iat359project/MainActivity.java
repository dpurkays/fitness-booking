package com.example.iat359project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.support.v4.app.INotificationSideChannel;
import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final float defaultSize = 20;
    public static final int defaultFont = 1;
    public static final int defaultTheme = 1;

    private Button accelerometerButton, signInButton, signUpButton, testMapButton;
    private ImageButton SettingButton;
    private ConstraintLayout Main;

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

        SettingButton = (ImageButton) findViewById(R.id.SettingButton);
        SettingButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startSettingActivity();
            }
        });

        Main = (ConstraintLayout) findViewById(R.id.Main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Load TEXT SIZE from SharedPreferences
        SharedPreferences textSize = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        float getSize = textSize.getFloat("selectedTextSize", defaultSize);
        accelerometerButton.setTextSize(getSize);
        signInButton.setTextSize(getSize);
        signUpButton.setTextSize(getSize);
        testMapButton.setTextSize(getSize);

        //Load TEXT FONT from SharedPreferences
        SharedPreferences textFont = getSharedPreferences("textFont", Context.MODE_PRIVATE);
        int getFont = textFont.getInt("selectedTextFont", defaultFont);
        if(getFont == 1){
            Typeface typeface = getResources().getFont(R.font.roboto_light);
            accelerometerButton.setTypeface(typeface);
            signInButton.setTypeface(typeface);
            signUpButton.setTypeface(typeface);
            testMapButton.setTypeface(typeface);
        } else if(getFont == 2){
            Typeface typeface = getResources().getFont(R.font.jet_brains_monowght);
            accelerometerButton.setTypeface(typeface);
            signInButton.setTypeface(typeface);
            signUpButton.setTypeface(typeface);
            testMapButton.setTypeface(typeface);
        }else if(getFont == 3) {
            Typeface typeface = getResources().getFont(R.font.nerko_one);
            accelerometerButton.setTypeface(typeface);
            signInButton.setTypeface(typeface);
            signUpButton.setTypeface(typeface);
            testMapButton.setTypeface(typeface);
        } else if(getFont == 4){
            Typeface typeface = getResources().getFont(R.font.permanent_marker);
            accelerometerButton.setTypeface(typeface);
            signInButton.setTypeface(typeface);
            signUpButton.setTypeface(typeface);
            testMapButton.setTypeface(typeface);
        }

        //Load THEME from SharedPreferences
        SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int getMode = theme.getInt("selectedTheme", defaultTheme);
        if(getMode == 1){
            accelerometerButton.setBackgroundResource(R.drawable.button_color);
            signInButton.setBackgroundResource(R.drawable.button_color);
            signUpButton.setBackgroundResource(R.drawable.button_color);
            testMapButton.setBackgroundResource(R.drawable.button_color);
            Main.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_dark));
        }else if(getMode == 2){
            accelerometerButton.setBackgroundResource(R.drawable.button_color_light);
            signInButton.setBackgroundResource(R.drawable.button_color_light);
            signUpButton.setBackgroundResource(R.drawable.button_color_light);
            testMapButton.setBackgroundResource(R.drawable.button_color_light);
            Main.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_light));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.action_settings:
//               // Toast.makeText(this,"Setting Clicked", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(this.getApplicationContext(),SettingActivity.class);
//                startActivity(i);
//                return true;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

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

    public void startSettingActivity(){
        Intent i = new Intent(this.getApplicationContext(),SettingActivity.class);
        startActivity(i);
    }


}