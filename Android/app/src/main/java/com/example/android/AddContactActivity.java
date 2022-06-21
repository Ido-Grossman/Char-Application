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
import com.example.android.Data.ContactPost;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactActivity extends AppCompatActivity {
    private AppDB db;
    private ContactDao contactDao;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_contact);
            Intent intent = new Intent(this, ContactsActivity.class);

            //create room database:
            db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
            contactDao = db.contactDao();

            Button registerBtn = findViewById(R.id.add_contact_btn);
            registerBtn.setOnClickListener(view -> {
                EditText c_nickname = findViewById(R.id.add_contact_name);
                EditText c_id = findViewById(R.id.add_contact_id);
                EditText c_server = findViewById(R.id.add_contact_server);
                String a = c_id.getText().toString();
                String b = c_nickname.getText().toString();
                String c = c_server.getText().toString();
                Contact contact = new Contact(a,b,c);
                ContactPost contactPost = new ContactPost(MyApp.userId, a, c);
                inviteContact(contactPost, b, intent);
                contactDao.insert(contact);

            });
        }
    public void createContact(Contact contact, Intent intent){
        Call<Void> call = MyApp.webServiceAPI.createContact("Bearer "+MyApp.token, contact);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    finish();
                    startActivity(intent);
                } else {

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (MyApp.userId == null)
            finish();
    }

    public void inviteContact(ContactPost contact, String nickname, Intent intent){
            Call<Void> call = MyApp.webServiceAPI.inviteContact("Bearer "+MyApp.token, contact);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()){
                        Contact newContact = new Contact(contact.to, nickname, contact.server);
                        createContact(newContact, intent);
                    } else {
                        // contact not found or already in list
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
    }
}
