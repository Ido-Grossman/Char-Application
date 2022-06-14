package com.example.android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.android.Data.AppDB;
import com.example.android.Data.Contact;
import com.example.android.Data.ContactDao;

public class AddContactActivity extends AppCompatActivity {
    private AppDB db;
    private ContactDao contactDao;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_contact);
            getSupportActionBar().hide();

            //create room database:
            db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
            contactDao = db.userDao();

            Button registerBtn = findViewById(R.id.add_contact_btn);
            registerBtn.setOnClickListener(view -> {
                EditText c_nickname = findViewById(R.id.add_contact_name);
                EditText c_id = findViewById(R.id.add_contact_id);
                EditText c_server = findViewById(R.id.add_contact_server);
                int a = Integer.parseInt(c_id.getText().toString());
                String b = c_nickname.getText().toString();
                String c = c_server.getText().toString();
                Contact contact = new Contact(a,b,c);
                contactDao.insert(contact);

                finish();
            });
        }

}
