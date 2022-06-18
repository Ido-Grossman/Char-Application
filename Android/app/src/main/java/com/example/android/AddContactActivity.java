package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.android.Data.AppDB;
import com.example.android.Data.Contact;
import com.example.android.Data.ContactDao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            contactDao = db.contactDao();
            Intent intent = new Intent(this, ChatActivity.class);

            Button registerBtn = findViewById(R.id.add_contact_btn);
            registerBtn.setOnClickListener(view -> {
                EditText c_nickname = findViewById(R.id.add_contact_name);
                EditText c_id = findViewById(R.id.add_contact_id);
                EditText c_server = findViewById(R.id.add_contact_server);
                String a = c_id.getText().toString();
                String b = c_nickname.getText().toString();
                String c = c_server.getText().toString();
                Contact contact = new Contact(a,b,c);
                createContact(contact);
                contactDao.insert(contact);

            });
        }
    public void createContact(Contact contact){
        Call<String> call = MyApp.webServiceAPI.createContact("Bearer "+MyApp.token, contact);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    String uri = response.body();

                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
