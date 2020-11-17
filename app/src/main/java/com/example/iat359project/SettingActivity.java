package com.example.iat359project;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SettingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    public static final String DEFAULT = "#FFFFFF";

    private RadioGroup colorRadio;
    private ConstraintLayout setting;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        colorRadio = (RadioGroup)findViewById(R.id.radioBgColor);
        colorRadio.setOnCheckedChangeListener(this);

        SharedPreferences sharedPrefs = getSharedPreferences("BgColor", Context.MODE_PRIVATE);
        String BgColor = sharedPrefs.getString("selectedBgColor", DEFAULT);

        setting = (ConstraintLayout) findViewById(R.id.setting);
        setting.setBackgroundColor(Color.parseColor(BgColor));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void saveBgColor(View view){
        SharedPreferences sharedPrefs = getSharedPreferences("BgColor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("selectedBgColor", color);
        Toast.makeText(this, "Color Saved", Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.radioWhite:
                color = "#FFFFFF";
                break;
            case R.id.radioBlack:
                color = "#000000";
                break;
            case R.id.radioBlue:
                color =  "#337FD6";
                break;
            case R.id.radioOrange:
                color = "#D67133";
                break;
            default:
        }
    }
}
