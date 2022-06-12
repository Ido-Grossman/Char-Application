package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.android.Data.AppDB;
import com.example.android.Data.UserDao;

public class MainActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            EditText username = findViewById(R.id.usernameInput);
            EditText password = findViewById(R.id.passwordInput);
        });
        TextView registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterPage.class);
            startActivity(intent);
        });

        //create room database:
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        userDao = db.userDao();
    }
}