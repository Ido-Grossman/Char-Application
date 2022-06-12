package com.example.android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.android.Data.AppDB;
import com.example.android.Data.User;
import com.example.android.Data.UserDao;

public class AddContactActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_contact);

            //create room database:
            db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
            userDao = db.userDao();

            Button registerBtn = findViewById(R.id.add_contact_btn);
            registerBtn.setOnClickListener(view -> {
                EditText c_nickname = findViewById(R.id.add_contact_name);
                EditText c_id = findViewById(R.id.add_contact_id);
                EditText c_server = findViewById(R.id.add_contact_server);
                int a = Integer.parseInt(c_id.getText().toString());
                String b = c_nickname.getText().toString();
                String c = c_server.getText().toString();
                User user = new User(a,b,c);
                userDao.insert(user);

                finish();
            });
        }

}
