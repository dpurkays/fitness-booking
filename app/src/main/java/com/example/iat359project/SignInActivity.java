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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SignInActivity extends AppCompatActivity implements StringRequestResponse{

    public static final String DEFAULT = "20";
    EditText inputUsername, inputPassword;
    Button signInButton;
    Button addButton;
    String username, password;
    MyDatabase db;
    RequestQueue requestQueue;
    String requestUrl;
    AsyncTaskDatabaseLogIn asyncTaskDatabaseLogIn;

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
                try {
                    logIn();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        requestQueue = Volley.newRequestQueue(this);
        requestUrl ="https://project-359-team.000webhostapp.com/loginAccount.php";


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPrefs = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        String getSize = sharedPrefs.getString("selectedTextSize", DEFAULT);
        Float size = Float.parseFloat(getSize);

        inputUsername.setTextSize(size);
        inputPassword.setTextSize(size);
        signInButton.setTextSize(size);
        addButton.setTextSize(size);
    }

    public void logIn() throws ExecutionException, InterruptedException{
        username = inputUsername.getText().toString();
        password = inputPassword.getText().toString();

        if (!username.matches("")) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl,
                    new onResponseCustom(this, this) {
                        @Override
                        public void onResponse(String response) {
                            try {
                                stringRequestResponse.exportResponse(response.toLowerCase());
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
                    prms.put("sentPassword", password);

                    return prms;
                }
            };
            requestQueue.add(stringRequest);
//
//
//            else {
//                Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    public void addAccount() {
        long id = db.insertAccount("iat359", "123456");
        if (id < 0) {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "account added" + String.valueOf(id), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void exportResponse(String response) {
        if (response.equals("success")){
            Log.d("logging in",response);
            SharedPreferences sharedPrefs = getSharedPreferences("username", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("getName", username);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "You're logged in", Toast.LENGTH_LONG).show();

        }
        else if (response.equals("wrong")){
            Log.d("logging in",response);
            Toast.makeText(this, "You've entered wrong information", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d("logging in", response);
            Toast.makeText(this, "Logging in failed, please don't leave empty", Toast.LENGTH_LONG).show();
        }
    }
}
