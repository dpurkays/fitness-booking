package com.example.iat359project;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class SettingActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    public static final float defaultSize = 20;
    public static final int defaultSizeRadio = 2;
    public static final int defaultFont = 1;
    public static final int defaultFontRadio = 1;
    public static final int defaultTheme = 1;
    public static final int defaultThemeRadio = 1;


    private RadioGroup textSizeRadio, textFontRadio, themeRadio;
    private RadioButton radioSmall, radioNormal, radioLarge, radioExtraLarge , radioFontOne, radioFontTwo, radioFontThree, radioFontFour, radioDark, radioLight;
    private Button saveButton;
    private TextView textViewSize, textViewFont, textViewTheme;
    private float size;
    private int font, sizeRadio, fontRadio, mode, modeRadio;
    private LinearLayout ThemeLayout, TextSizeLayout, TextFontLayout;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        textSizeRadio = (RadioGroup) findViewById(R.id.radioTextSize);
        textSizeRadio.setOnCheckedChangeListener(this);

        textFontRadio = (RadioGroup) findViewById(R.id.radioTextFont);
        textFontRadio.setOnCheckedChangeListener(this);

        themeRadio = (RadioGroup) findViewById(R.id.themeRadio);
        themeRadio.setOnCheckedChangeListener(this);

        radioSmall = (RadioButton) findViewById(R.id.radioSmall);
        radioNormal = (RadioButton) findViewById(R.id.radioNormal);
        radioLarge = (RadioButton) findViewById(R.id.radioLarge);
        radioExtraLarge = (RadioButton) findViewById(R.id.radioExtraLarge);
        radioFontOne = (RadioButton) findViewById(R.id.radioFontOne);
        radioFontTwo = (RadioButton) findViewById(R.id.radioFontTwo);
        radioFontThree = (RadioButton) findViewById(R.id.radioFontThree);
        radioFontFour = (RadioButton) findViewById(R.id.radioFontFour);
        radioDark = (RadioButton) findViewById(R.id.radioDark);
        radioLight = (RadioButton) findViewById(R.id.radioLight);

        saveButton = (Button) findViewById(R.id.buttonSave);
        textViewSize = (TextView) findViewById(R.id.textViewSize);
        textViewFont = (TextView) findViewById(R.id.textViewFont);
        textViewTheme = (TextView) findViewById(R.id.textViewTheme);

        ThemeLayout = (LinearLayout) findViewById(R.id.ThemeLayout);
        TextSizeLayout = (LinearLayout) findViewById(R.id.TextSizeLayout);
        TextFontLayout = (LinearLayout) findViewById(R.id.TextFontLayout);

        scroll = (ScrollView) findViewById(R.id.scroll);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Load TEXT SIZE from SharedPreferences
        SharedPreferences textSize = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        float getSize = textSize.getFloat("selectedTextSize", defaultSize);
        saveButton.setTextSize(getSize);
        textViewSize.setTextSize(getSize);
        textViewFont.setTextSize(getSize);
        radioFontOne.setTextSize(getSize);
        radioFontTwo.setTextSize(getSize);
        radioFontThree.setTextSize(getSize);
        radioFontFour.setTextSize(getSize);
        radioDark.setTextSize(getSize);
        radioLight.setTextSize(getSize);
        textViewTheme.setTextSize(getSize);

        //Load to check the previous clicked and saved TEXT SIZE RADIO BUTTON
        SharedPreferences clickedSizeRadioButton = getSharedPreferences("textSizeRadio", Context.MODE_PRIVATE);
        int getSizeRadio = clickedSizeRadioButton.getInt("selectedTextSizeRadio", defaultSizeRadio);
        if(getSizeRadio == 1){
            radioSmall.setChecked(true);
        }else if (getSizeRadio == 2){
            radioNormal.setChecked(true);
        }else if (getSizeRadio == 3){
            radioLarge.setChecked(true);
        }else if (getSizeRadio == 4){
            radioExtraLarge.setChecked(true);
        }

        //Load TEXT FONT from SharedPreferences
        SharedPreferences textFont = getSharedPreferences("textFont", Context.MODE_PRIVATE);
        int getFont = textFont.getInt("selectedTextFont", defaultFont);
        if(getFont == 1){
            Typeface typeface = getResources().getFont(R.font.roboto_light);
            saveButton.setTypeface(typeface);
            textViewSize.setTypeface(typeface);
            textViewFont.setTypeface(typeface);
            textViewTheme.setTypeface(typeface);
            radioDark.setTypeface(typeface);
            radioLight.setTypeface(typeface);
        }
        else if(getFont == 2){
            Typeface typeface = getResources().getFont(R.font.jet_brains_monowght);
            saveButton.setTypeface(typeface);
            textViewSize.setTypeface(typeface);
            textViewFont.setTypeface(typeface);
            textViewTheme.setTypeface(typeface);
            radioDark.setTypeface(typeface);
            radioLight.setTypeface(typeface);
        }else if(getFont == 3){
            Typeface typeface = getResources().getFont(R.font.nerko_one);
            saveButton.setTypeface(typeface);
            textViewSize.setTypeface(typeface);
            textViewFont.setTypeface(typeface);
            textViewTheme.setTypeface(typeface);
            radioDark.setTypeface(typeface);
            radioLight.setTypeface(typeface);
        }else if(getFont == 4){
            Typeface typeface = getResources().getFont(R.font.permanent_marker);
            saveButton.setTypeface(typeface);
            textViewSize.setTypeface(typeface);
            textViewFont.setTypeface(typeface);
            textViewTheme.setTypeface(typeface);
            radioDark.setTypeface(typeface);
            radioLight.setTypeface(typeface);
        }

        //Load to check the previous clicked and saved TEXT FONT RADIO BUTTON
        SharedPreferences clickedFontRadioButton = getSharedPreferences("textFontRadio", Context.MODE_PRIVATE);
        int getFontRadio = clickedFontRadioButton.getInt("selectedTextFontRadio", defaultFontRadio);
        if(getFontRadio == 1){
            radioFontOne.setChecked(true);
        }else if (getFontRadio == 2){
            radioFontTwo.setChecked(true);
        }else if (getFontRadio == 3){
            radioFontThree.setChecked(true);
        }else if (getFontRadio == 4){
            radioFontFour.setChecked(true);
        }

        //Load THEME from SharedPreferences
        SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int getMode = theme.getInt("selectedTheme", defaultTheme);
        if(getMode == 1){
            saveButton.setBackgroundResource(R.drawable.button_color);
            ThemeLayout.setBackgroundResource(R.drawable.textview_border);
            TextSizeLayout.setBackgroundResource(R.drawable.textview_border);
            TextFontLayout.setBackgroundResource(R.drawable.textview_border);
            scroll.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_dark));
        }else if(getMode == 2){
            saveButton.setBackgroundResource(R.drawable.button_color_light);
            ThemeLayout.setBackgroundResource(R.drawable.textview_border_light);
            TextSizeLayout.setBackgroundResource(R.drawable.textview_border_light);
            TextFontLayout.setBackgroundResource(R.drawable.textview_border_light);
            scroll.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_light));
        }

        //Load to check the previous clicked and saved THEME RADIO BUTTON
        SharedPreferences themeRadio = getSharedPreferences("themeRadio", Context.MODE_PRIVATE);
        int getThemeRadio = themeRadio.getInt("selectedThemeRadio", defaultThemeRadio);
        if(getThemeRadio == 1){
            radioDark.setChecked(true);
        }else if (getThemeRadio == 2){
            radioLight.setChecked(true);
        }

    }

    public void savePreferences(View view) {
        //SharedPreferences for TEXT SIZE
        SharedPreferences textSize = getSharedPreferences("textSize", Context.MODE_PRIVATE);
        SharedPreferences.Editor textSizeEditor = textSize.edit();
        textSizeEditor.putFloat("selectedTextSize", size);
        textSizeEditor.commit();

        //SharedPreferences for CLICKED TEXT SIZE RADIO BUTTON
        SharedPreferences clickedSizeRadioButton = getSharedPreferences("textSizeRadio", Context.MODE_PRIVATE);
        SharedPreferences.Editor textSizeRadioEditor = clickedSizeRadioButton.edit();
        textSizeRadioEditor.putInt("selectedTextSizeRadio", sizeRadio);
        textSizeRadioEditor.commit();

        //SharedPreferences for TEXT FONT
        SharedPreferences textFont = getSharedPreferences("textFont", Context.MODE_PRIVATE);
        SharedPreferences.Editor textFontEditor = textFont.edit();
        textFontEditor.putInt("selectedTextFont", font);
        textFontEditor.commit();

        //SharedPreferences for TEXT FONT RADIO
        SharedPreferences clickedFontRadioButton = getSharedPreferences("textFontRadio", Context.MODE_PRIVATE);
        SharedPreferences.Editor textFontRadioEditor = clickedFontRadioButton.edit();
        textFontRadioEditor.putInt("selectedTextFontRadio", fontRadio);
        textFontRadioEditor.commit();

        //SharedPreferences for THEME
        SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);
        SharedPreferences.Editor themeEditor = theme.edit();
        themeEditor.putInt("selectedTheme", mode);
        themeEditor.commit();

        //SharedPreferences for THEME RADIO
        SharedPreferences themeRadio = getSharedPreferences("themeRadio", Context.MODE_PRIVATE);
        SharedPreferences.Editor themeRadioEditor = themeRadio.edit();
        themeRadioEditor.putInt("selectedThemeRadio", modeRadio);
        themeRadioEditor.commit();

        //Load TEXT SIZE from SharedPreferences
        float getSize = textSize.getFloat("selectedTextSize", defaultSize);
        saveButton.setTextSize(getSize);
        textViewSize.setTextSize(getSize);
        textViewFont.setTextSize(getSize);
        radioFontOne.setTextSize(getSize);
        radioFontTwo.setTextSize(getSize);
        radioFontThree.setTextSize(getSize);
        radioFontFour.setTextSize(getSize);
        radioDark.setTextSize(getSize);
        radioLight.setTextSize(getSize);
        textViewTheme.setTextSize(getSize);

        //Load TEXT FONT from SharedPreferences
        int getFont = textFont.getInt("selectedTextFont", defaultFont);
        if(getFont == 1){
            Typeface typeface = getResources().getFont(R.font.roboto_light);
            saveButton.setTypeface(typeface);
            textViewSize.setTypeface(typeface);
            textViewFont.setTypeface(typeface);
            textViewTheme.setTypeface(typeface);
            radioDark.setTypeface(typeface);
            radioLight.setTypeface(typeface);
        }
        else if(getFont == 2){
            Typeface typeface = getResources().getFont(R.font.jet_brains_monowght);
            saveButton.setTypeface(typeface);
            textViewSize.setTypeface(typeface);
            textViewFont.setTypeface(typeface);
            textViewTheme.setTypeface(typeface);
            radioDark.setTypeface(typeface);
            radioLight.setTypeface(typeface);
        }else if(getFont == 3){
            Typeface typeface = getResources().getFont(R.font.nerko_one);
            saveButton.setTypeface(typeface);
            textViewSize.setTypeface(typeface);
            textViewFont.setTypeface(typeface);
            textViewTheme.setTypeface(typeface);
            radioDark.setTypeface(typeface);
            radioLight.setTypeface(typeface);
        }else if(getFont == 4){
            Typeface typeface = getResources().getFont(R.font.permanent_marker);
            saveButton.setTypeface(typeface);
            textViewSize.setTypeface(typeface);
            textViewFont.setTypeface(typeface);
            textViewTheme.setTypeface(typeface);
            radioDark.setTypeface(typeface);
            radioLight.setTypeface(typeface);
        }

        //Load THEME from SharedPreferences
        int getMode = theme.getInt("selectedTheme", defaultTheme);
        if(getMode == 1){
            saveButton.setBackgroundResource(R.drawable.button_color);
            ThemeLayout.setBackgroundResource(R.drawable.textview_border);
            TextSizeLayout.setBackgroundResource(R.drawable.textview_border);
            TextFontLayout.setBackgroundResource(R.drawable.textview_border);
            scroll.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_dark));
        }else if(getMode == 2){
            saveButton.setBackgroundResource(R.drawable.button_color_light);
            ThemeLayout.setBackgroundResource(R.drawable.textview_border_light);
            TextSizeLayout.setBackgroundResource(R.drawable.textview_border_light);
            TextFontLayout.setBackgroundResource(R.drawable.textview_border_light);
            scroll.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.background_light));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        textSizeButton(checkedId);
        textFontButton(checkedId);
        themeMode(checkedId);
    }

    public void textSizeButton(int checkedId){
        switch (checkedId) {
            case R.id.radioSmall:
                size = 15;
                sizeRadio = 1;
                break;
            case R.id.radioNormal:
                size = 20;
                sizeRadio = 2;
                break;
            case R.id.radioLarge:
                size = 25;
                sizeRadio = 3;
                break;
            case R.id.radioExtraLarge:
                size = 30;
                sizeRadio = 4;
                break;
            default:
        }
    }

    public void textFontButton(int checkedId){
        switch (checkedId) {
            case R.id.radioFontOne:
                font = 1;
                fontRadio = 1;
                break;
            case R.id.radioFontTwo:
                font = 2;
                fontRadio = 2;
                break;
            case R.id.radioFontThree:
                font = 3;
                fontRadio = 3;
                break;
            case R.id.radioFontFour:
                font = 4;
                fontRadio = 4;
                break;
            default:
        }
    }

    public void themeMode(int checkedId) {
        switch (checkedId) {
            case R.id.radioDark:
                mode = 1;
                modeRadio = 1;
                break;
            case R.id.radioLight:
                mode = 2;
                modeRadio = 2;
                break;
            default:
        }
    }
}
