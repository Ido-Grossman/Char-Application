package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.android.Data.AppDB;
import com.example.android.Data.ContactDao;
import com.example.android.api.UserApi;
import com.example.android.entities.UserCred;

public class MainActivity extends AppCompatActivity {
    private AppDB db;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();



        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            EditText username = findViewById(R.id.usernameInput);
            EditText password = findViewById(R.id.passwordInput);
            String strUsername = username.getText().toString();
            String strPassword = password.getText().toString();
            System.out.println(username);
            System.out.println(password);
            String strPass = password.toString();
            UserApi userApi = new UserApi();
            UserCred user = new UserCred(strUsername, strPassword, "hello","http://localhost:7225");
            userApi.tryToLogin(user);
        });
        TextView registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterPage.class);
            startActivity(intent);
        });

        //create room database:
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        contactDao = db.userDao();
    }
}