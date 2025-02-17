package com.example.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import com.example.android.Data.AppDB;
import com.example.android.Data.Contact;
import com.example.android.Data.ContactDao;
import com.example.android.Data.UserCred;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private AppDB db;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp.configureRetrofit();
        MyApp.messageNotify = new MessageNotify();

        ImageButton settings = findViewById(R.id.settings_button);
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        settings.setOnClickListener(view-> startActivity(settingsIntent));

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(view -> {
            EditText username = findViewById(R.id.usernameInput);
            EditText password = findViewById(R.id.passwordInput);
            String strUsername = username.getText().toString();
            String strPassword = password.getText().toString();
            Intent intent = new Intent(this, ContactsActivity.class);
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, instanceIdResult -> {
                MyApp.FirebaseToken = instanceIdResult.getToken();
            });
            UserCred user = new UserCred(strUsername, strPassword, "hello","http://localhost:7225", MyApp.FirebaseToken);
            this.tryToLogin(user, intent);


        });
        TextView registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterPage.class);
            startActivity(intent);
        });

        //create room database:
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        contactDao = db.contactDao();
    }

    public void tryToLogin(UserCred userCred, Intent chatsIntent){
        db.clearAllTables();
        Call<String> call = MyApp.webServiceAPI.logIn(userCred);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    MyApp.token = response.body();
                    MyApp.userId = userCred.username;
                    getContactsList(MyApp.token, chatsIntent);
                    return;
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void getContactsList(String token, Intent chatsIntent) {
        Call<List<Contact>> call = MyApp.webServiceAPI.getContacts("Bearer "+token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                MyApp.contactList = response.body();
                startActivity(chatsIntent);
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

            }

    });
    }
}



