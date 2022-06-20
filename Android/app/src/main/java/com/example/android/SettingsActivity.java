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
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        TextView serverAddress = findViewById(R.id.serverInput);
        String address = serverAddress.getText().toString();

        Button changeButton = findViewById(R.id.SetServerBtn);
        changeButton.setOnClickListener(view -> MyApp.url = "http://" + address + "/api");
    }

}
