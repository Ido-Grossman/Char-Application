package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        ImageButton UploadImg = findViewById(R.id.UploadImg);
        UploadImg.setOnClickListener(view -> {
            Intent takePicture = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivity(takePicture);
        });
        Button registerBtn = findViewById(R.id.RegisterBtn);
        registerBtn.setOnClickListener(view -> {
            EditText username = findViewById(R.id.RegisterUserName);
            EditText password = findViewById(R.id.RegisterPassword1);
        });

    }
}
