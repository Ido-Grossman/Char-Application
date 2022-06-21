package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ServerActivity extends AppCompatActivity
{
    private final String defaultApi = "10.0.2.2:7225";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        TextView serverAddress = findViewById(R.id.serverInput);

        System.out.println();
        Button changeButton = findViewById(R.id.SetServerBtn);
        changeButton.setOnClickListener(view -> {
            String address = serverAddress.getText().toString();
            if (address.equals("")){
                address = defaultApi;
            }
            MyApp.url = "http://" + address + "/api/";
            MyApp.configureRetrofit();
            finish();
        });

        ImageButton backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(view-> finish());

    }

}
