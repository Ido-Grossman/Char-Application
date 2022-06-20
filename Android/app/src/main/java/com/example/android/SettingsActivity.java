package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity
{
    private final String defaultApi = "http://10.0.2.2:7225/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        TextView serverAddress = findViewById(R.id.serverInput);


        Button changeButton = findViewById(R.id.SetServerBtn);
        changeButton.setOnClickListener(view -> {
            String address = serverAddress.getText().toString();
            if (address.equals("")){
                MyApp.url = defaultApi;
            } else {
                MyApp.url = "http://" + address + "/api/";
            }
            MyApp.configureRetrofit();
        });

    }

}
