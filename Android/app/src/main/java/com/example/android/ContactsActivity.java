package com.example.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.android.Adapters.CustomListAdapter;
import com.example.android.Data.AppDB;
import com.example.android.Data.Contact;
import com.example.android.Data.ContactDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsActivity extends AppCompatActivity implements IMessageListener {

    ListView listView;
    CustomListAdapter adapter;

    private AppDB db;
    private ContactDao contactDao;

    void createPageButtons(){
        FloatingActionButton btnAdd = findViewById(R.id.add_contact_btn);
        btnAdd.setOnClickListener(view-> {
            Intent i = new Intent(this, AddContactActivity.class);
            MyApp.messageNotify.removeMessageListener(this);
            startActivity(i);
        });

        ImageButton logoutButton = findViewById(R.id.logout_btn);
        //delete dao
        logoutButton.setOnClickListener(view -> {
            MyApp.userId = null;
            finish();});

        ImageButton settings_button = findViewById(R.id.settings_button);
        Intent settings_intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settings_button.setOnClickListener(view -> {
            startActivity(settings_intent);});
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (MyApp.userId == null)
            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, ChatContactActivity.class);
            startActivity(intent);
        }
        createPageButtons();
        ArrayList<Contact> contacts;

        //create room database:
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        contactDao = db.contactDao();

        contacts = (ArrayList<Contact>) contactDao.index();
        int daoSize = contacts.size(); int apiSize;
        if(MyApp.contactList == null)   apiSize = daoSize;
        else    apiSize = MyApp.contactList.size();
        if(apiSize != daoSize) {//if api conatcts empty try from dao todo uncomment*/
            int sizeComp = apiSize - daoSize;
            for (int i = 0; i < sizeComp; i++)
                contactDao.insert(MyApp.contactList.get(i));
            contacts = (ArrayList<Contact>) contactDao.index();
        }
        for (int i = 0; i < apiSize; i++) {
            Contact first = contactDao.get(contacts.get(i).getId());
            Contact api = MyApp.contactList.get(i);
            if (!Objects.equals(first.getLastDate(), api.getLastDate())) {
                Contact contact = contacts.get(i);
                contact.setLast(MyApp.contactList.get(i).getLast());
                contact.setLastDate(MyApp.contactList.get(i).getLastDate());
                contactDao.update(contact);
            }
            contacts = (ArrayList<Contact>) contactDao.index();
        }
        Collections.sort(contacts); //sort by id via the overridden comparator
        listView = findViewById(R.id.list_view);
        adapter = new CustomListAdapter(getApplicationContext(), contacts);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        ArrayList<Contact> finalContacts = contacts;
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

            Contact contact = finalContacts.get(i);
            intent.putExtra("contactId", contact.getId());
            intent.putExtra("contactName", contact.getName());
            intent.putExtra("lastDateTime", contact.getLastDate());
            intent.putExtra("profilePicture", contact.getImage()); //todo - support image
            MyApp.messageNotify.removeMessageListener(this);
            startActivity(intent);
        });
    }

    void logOut(){
        Call<Void> call = MyApp.webServiceAPI.logOut("Bearer "+MyApp.token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        MyApp.userId = null;
        super.onDestroy();
        db.clearAllTables();
        logOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApp.messageNotify.removeMessageListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.messageNotify.addMessageListener(this);
    }

    @Override
    public void messageEvent() {
        runOnUiThread(() -> {
            ArrayList<Contact> contacts = (ArrayList<Contact>) contactDao.index();
            listView = findViewById(R.id.list_view);
            adapter = new CustomListAdapter(getApplicationContext(), contacts);
            listView.setAdapter(adapter);
            listView.setClickable(true);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, ChatContactActivity.class);
            startActivity(intent);
        }
    }
}