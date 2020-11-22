package com.example.iat359project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SettingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    public static final String DEFAULT = "20";

    private RadioGroup textSizeRadio;
    private Button saveButton;
    String size;
    private TextView selectTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        textSizeRadio = (RadioGroup)findViewById(R.id.radioBgColor);
        textSizeRadio.setOnCheckedChangeListener(this);

        saveButton = (Button) findViewById(R.id.buttonSave);
        selectTextView = (TextView) findViewById(R.id.textViewCount);
    }

    public void saveTextSize(View view){
        SharedPreferences sharedPrefs = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("selectedTextSize", size);
        editor.commit();

        String getSize = sharedPrefs.getString("selectedTextSize", DEFAULT);
        Float size = Float.parseFloat(getSize);
        saveButton.setTextSize(size);
        selectTextView.setTextSize(size);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.radioSmall:
                size = "15";
                break;
            case R.id.radioNormal:
                size = "20";
                break;
            case R.id.radioLarge:
                size = "25";
                break;
            case R.id.radioExtraLarge:
                size = "30";
                break;
            default:
        }
    }
}
