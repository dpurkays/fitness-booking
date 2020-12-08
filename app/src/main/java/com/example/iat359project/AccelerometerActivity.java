package com.example.iat359project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AccelerometerActivity extends AppCompatActivity {

    public static final float defaultSize = 20;
    public static final int defaultFont = 1;
    public static final int defaultTheme = 1;
    private TextView stepCountDisplay, recordingDisplay,caloriesDisplay, caloriesTextview, countTextview, bestDisplay;
    private Button toggleButton, viewRecordsButton, clearRecordsButton;
    private boolean isOn = false;
    private double magnitudeLatest = 0;
    private double caloriesBurnt = 0;
    private Integer stepCount = 0;
    private SensorManager mySensorManager;
    private Sensor accSensor;
    private SensorEventListener accSensorListener;
    private float[] gravity = new float[3];
    MyDatabase db;
    MyHelper helper;
    String username;

    private ConstraintLayout AccLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        SharedPreferences sharedPrefs = getSharedPreferences("username", Context.MODE_PRIVATE);
        if (sharedPrefs != null){
            username = sharedPrefs.getString("getName", "");
        }
        else username = " ";

        bestDisplay = (TextView) findViewById((R.id.bestDisplay));
        stepCountDisplay = (TextView) findViewById(R.id.stepCountDisplay);
        caloriesDisplay = (TextView) findViewById(R.id.caloriesDisplay);
        recordingDisplay = (TextView) findViewById(R.id.recordingDisplay);
        caloriesTextview= (TextView) findViewById(R.id.textViewCalories);
        countTextview= (TextView) findViewById(R.id.textViewCount);
        toggleButton = (Button) findViewById(R.id.accelerometerToggleButton);

        AccLayout = (ConstraintLayout) findViewById(R.id.AccLayout);
        viewRecordsButton = (Button) findViewById(R.id.viewRecordButton);
        clearRecordsButton = (Button) findViewById(R.id.clearRecordsButton);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSensor(isOn);
            }
        });

        viewRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecords();
            }
        });

        String lastSteps = "";
        String lastCalories = "";

        SharedPreferences sharedPrefsRecord = getSharedPreferences("bestRecord", Context.MODE_PRIVATE);
        if (sharedPrefs != null){

            lastCalories = sharedPrefsRecord.getString("calories","0");
            lastSteps = sharedPrefsRecord.getString("steps","0");

            bestDisplay.setText(lastSteps + " steps/ " + lastCalories + "kCal");
        }

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null) {

                    final float alpha = 0.8f;//to remove to gravitational offset

                    float xRaw = event.values[0];
                    float yRaw = event.values[1];
                    float zRaw = event.values[2];

                    gravity[0] = 0; //alpha * gravity[0] + (1 - alpha) * xRaw;
                    gravity[1] = 0; //alpha * gravity[1] + (1 - alpha) * yRaw;
                    gravity[2] = 0; //alpha * gravity[2] + (1 - alpha) * zRaw;

                    float xAcceleration = event.values[0] - gravity[0];
                    float yAcceleration = event.values[1] - gravity[1];
                    float zAcceleration = event.values[2] - gravity[2];

                    double magnitude = Math.sqrt(xAcceleration * xAcceleration + yAcceleration * yAcceleration + zAcceleration * zAcceleration);
                    double magnitudeDifference = magnitude - magnitudeLatest;

                    //Toast.makeText(getApplicationContext(), String.valueOf(magnitudeDifference), Toast.LENGTH_SHORT).show();
                    if (magnitudeDifference > 1) {
                        stepCount++;
                        stepCountDisplay.setText(stepCount.toString() + " Steps");
                        caloriesBurnt = stepCount * 0.04;
                        if(stepCount > 1) {
                            caloriesDisplay.setText(String.valueOf(caloriesBurnt) + " kCal");
                        }
                    }

                    magnitudeLatest = magnitude;
                }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //nothing here
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPrefs = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        float getSize = sharedPrefs.getFloat("selectedTextSize", defaultSize);
        stepCountDisplay.setTextSize(getSize);
        caloriesDisplay.setTextSize(getSize);
        recordingDisplay.setTextSize(getSize);
        caloriesTextview.setTextSize(getSize);
        countTextview.setTextSize(getSize);
        toggleButton.setTextSize(getSize);

        //Load TEXT FONT from SharedPreferences
        SharedPreferences textFont = getSharedPreferences("textFont", Context.MODE_PRIVATE);
        int getFont = textFont.getInt("selectedTextFont", defaultFont);
        if(getFont == 1){
            Typeface typeface = getResources().getFont(R.font.roboto_light);
            stepCountDisplay.setTypeface(typeface);
            caloriesDisplay.setTypeface(typeface);
            recordingDisplay.setTypeface(typeface);
            caloriesTextview.setTypeface(typeface);
            countTextview.setTypeface(typeface);
            toggleButton.setTypeface(typeface);
        } else if(getFont == 2){
            Typeface typeface = getResources().getFont(R.font.jet_brains_monowght);
            stepCountDisplay.setTypeface(typeface);
            caloriesDisplay.setTypeface(typeface);
            recordingDisplay.setTypeface(typeface);
            caloriesTextview.setTypeface(typeface);
            countTextview.setTypeface(typeface);
            toggleButton.setTypeface(typeface);
        }else if(getFont == 3) {
            Typeface typeface = getResources().getFont(R.font.nerko_one);
            stepCountDisplay.setTypeface(typeface);
            caloriesDisplay.setTypeface(typeface);
            recordingDisplay.setTypeface(typeface);
            caloriesTextview.setTypeface(typeface);
            countTextview.setTypeface(typeface);
            toggleButton.setTypeface(typeface);
        } else if(getFont == 4){
            Typeface typeface = getResources().getFont(R.font.permanent_marker);
            stepCountDisplay.setTypeface(typeface);
            caloriesDisplay.setTypeface(typeface);
            recordingDisplay.setTypeface(typeface);
            caloriesTextview.setTypeface(typeface);
            countTextview.setTypeface(typeface);
            toggleButton.setTypeface(typeface);
        }

        //Load THEME from SharedPreferences
        SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int getMode = theme.getInt("selectedTheme", defaultTheme);
        if(getMode == 1){
            toggleButton.setBackgroundResource(R.drawable.button_color);
            AccLayout.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_dark));
        }else if(getMode == 2){
            toggleButton.setBackgroundResource(R.drawable.button_color_light);
            AccLayout.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_light));
        }
    }

    public void toggleSensor(boolean value){
        if(!isOn) {
            mySensorManager.registerListener(accSensorListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
            stepCount = 0;
            recordingDisplay.setText("Recording");
            isOn = true;
            toggleButton.setText("Stop");
        }
        else {
            mySensorManager.unregisterListener(accSensorListener);
            recordingDisplay.setText("Sensor Deactivated");
            isOn = false;

            toggleButton.setText("Start");
            if (username != null) {
                int lastSteps = 0;
                Float lastCalories  = 0f;
                SharedPreferences sharedPrefs = getSharedPreferences("bestRecord", Context.MODE_PRIVATE);
                if (sharedPrefs != null){

                    lastCalories = Float.valueOf(sharedPrefs.getString("calories","0"));
                    lastSteps = Integer.valueOf(sharedPrefs.getString("steps","0"));
                }

                if(lastSteps < stepCount) {
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString("calories", String.valueOf(caloriesBurnt));
                    editor.putString("steps", String.valueOf(stepCount));
                    editor.commit();

                    bestDisplay.setText(lastSteps + " steps/ " + String.valueOf(lastCalories) + "kCal");
                }

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = myFormat.format(date);
                long id = db.insertRecord(username, String.valueOf(caloriesBurnt), String.valueOf(stepCount), formattedDate);
                if (id >=0){
                    Toast.makeText(this, "Record added", Toast.LENGTH_SHORT).show();
                }
            }

            stepCount = 0;
            caloriesBurnt = 0;
            stepCountDisplay.setText(String.valueOf(stepCount));
            caloriesDisplay.setText(String.valueOf(caloriesBurnt));
        }
    }

    public void onResumed(){
        if(isOn) {
            mySensorManager.registerListener(accSensorListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPaused(){
        if(!isOn){
            mySensorManager.unregisterListener(accSensorListener);
        }
    }

    public void viewRecords(){
        Intent intent = new Intent(this, ViewRecordsActivity.class);
        this.startActivity(intent);
    }

    public void clearRecords(){
        db.deleteRecords();
    }
}
