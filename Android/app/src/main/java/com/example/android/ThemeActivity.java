package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ThemeActivity extends AppCompatActivity {

    RadioGroup themes;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ImageButton backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(view-> finish());
        this.themes = findViewById(R.id.themes);
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                this.radioButton = findViewById(R.id.light_theme);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                this.radioButton = findViewById(R.id.dark_theme);
                break;
            default:
                this.radioButton = findViewById(R.id.system_default_theme);
                break;
        }
        this.radioButton.setChecked(true);
        Button changeBtn = findViewById(R.id.set_theme_btn);
        changeBtn.setOnClickListener(view-> {
            int radioId = themes.getCheckedRadioButtonId();
            this.radioButton = findViewById(radioId);
            String radioText = this.radioButton.getText().toString();
            switch (radioText) {
                case "Light":
                    System.out.println("0");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case "Dark":
                    System.out.println("1");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case "System Default":
                    System.out.println("2");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    break;
            }
            finish();
        });
    }
}