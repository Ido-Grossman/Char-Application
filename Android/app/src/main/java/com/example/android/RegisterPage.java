package com.example.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        getSupportActionBar().hide();


        ImageButton UploadImg = findViewById(R.id.UploadImg);
        UploadImg.setOnClickListener(view -> {
            ChooseImage();
        });
        Button registerBtn = findViewById(R.id.RegisterBtn);
        registerBtn.setOnClickListener(view -> {
            EditText username = findViewById(R.id.RegisterUserName);
            EditText password = findViewById(R.id.RegisterPassword1);
            EditText passwordValidate = findViewById(R.id.RegisterPassword2);
            String s = password.getText().toString();
            String k = passwordValidate.getText().toString();
            if (!s.equals(k)) {
                TextInputLayout layout = findViewById(R.id.Password1Layout);
                layout.setErrorEnabled(false);
                layout.setError(null);
                layout.setErrorEnabled(true);
                layout.setError("The password doesn't match");
            }
        });
    }

    void ChooseImage() {
        Intent takePicture = new Intent();
        takePicture.setType("image/*");
        takePicture.setAction(Intent.ACTION_GET_CONTENT);

        startActivityIntent.launch(takePicture);
    }

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                assert result.getData() != null;
                Uri selectImage = result.getData().getData();
                if (selectImage != null) {
                    ImageView img = findViewById(R.id.UserImage);
                    if (img.getVisibility() == View.INVISIBLE) {
                        img.setVisibility(View.VISIBLE);
                    }
                    img.setImageURI(selectImage);
                }
            });
}
