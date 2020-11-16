package com.example.iat359project;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button accelerometerButton, signInButton, signUpButton;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}