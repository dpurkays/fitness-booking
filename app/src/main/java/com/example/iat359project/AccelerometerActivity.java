package com.example.iat359project;

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

public class AccelerometerActivity extends AppCompatActivity {

    private TextView stepCountDisplay, recordingDisplay,caloriesDisplay;
    private Button toggleButton;
    private boolean isOn = false;
    private double magnitudeLatest = 0;
    private double caloriesBurnt = 0;
    private Integer stepCount = 0;
    private SensorManager mySensorManager;
    private Sensor accSensor;
    private SensorEventListener accSensorListener;
    private float[] gravity = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        stepCountDisplay = (TextView) findViewById(R.id.stepCountDisplay);
        caloriesDisplay = (TextView) findViewById(R.id.caloriesDisplay);
        recordingDisplay = (TextView) findViewById(R.id.recordingDisplay);
        toggleButton = (Button) findViewById(R.id.accelerometerToggleButton);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSensor(isOn);
            }
        });

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

                    Toast.makeText(getApplicationContext(), String.valueOf(magnitudeDifference), Toast.LENGTH_SHORT).show();
                    if (magnitudeDifference > 1) {
                        stepCount++;
                        stepCountDisplay.setText(stepCount.toString());
                        caloriesBurnt = stepCount * 0.04;
                        if(stepCount > 1) {
                            caloriesDisplay.setText(String.valueOf(caloriesBurnt));
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

    public void toggleSensor(boolean value){
        if(!isOn) {
            mySensorManager.registerListener(accSensorListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
            stepCount = 0;
            recordingDisplay.setText("Recording");
        }
        else {
            mySensorManager.unregisterListener(accSensorListener);
            recordingDisplay.setText("Sensor Deactivated");
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
}
