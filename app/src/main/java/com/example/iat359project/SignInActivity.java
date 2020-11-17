package com.example.iat359project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    public static final String DEFAULT = "20";
    EditText inputUsername, inputPassword;
    Button signInButton, addButton;
    String username, password;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputUsername = (EditText) findViewById(R.id.inputUsername);
        signInButton = (Button) findViewById((R.id.signInButton));
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        addButton = (Button) findViewById((R.id.addButton));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });

        db = new MyDatabase(this);
    }

    public void logIn(){
        username = inputUsername.getText().toString();
        password = inputPassword.getText().toString();

        if(!username.matches("")) {

            Cursor cursor = db.getAccount(username);
            cursor.moveToFirst();

            int passwordIndex = cursor.getColumnIndex(Constants.COLUMN_PASSWORD);
            String passwordRetrieved = cursor.getString(passwordIndex);

            if (cursor != null) {
                if (passwordRetrieved.equals(password)) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "You're logged in", Toast.LENGTH_LONG).show();
                    //add username to sharedpreferences
                } else {
                    Toast.makeText(this, "Your password is incorrect", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Your username is incorrect", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }
    }

    public void addAccount(){
        long id = db.insertAccount("device","astra");
        if (id < 0)
        {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "account added" + String.valueOf(id), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        String getSize = sharedPrefs.getString("selectedTextSize", DEFAULT);
        Float size = Float.parseFloat(getSize);

        addButton.setTextSize(size);
        signInButton.setTextSize(size);
        inputUsername.setTextSize(size);
        inputPassword.setTextSize(size);
    }
}
