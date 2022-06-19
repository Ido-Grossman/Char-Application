package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.room.Room;

import com.example.android.Adapters.CustomListAdapter;
import com.example.android.Data.AppDB;
import com.example.android.Data.Contact;
import com.example.android.Data.ContactDao;
import com.example.android.Data.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class ContactsActivity extends AppCompatActivity {

    ListView listView;
    CustomListAdapter adapter;

    private AppDB db;
    private ContactDao contactDao;

    void createPageButtons(){
        FloatingActionButton btnAdd = findViewById(R.id.add_contact_btn);
        btnAdd.setOnClickListener(view-> {
            Intent i = new Intent(this,AddContactActivity.class);
            startActivity(i);
        });

        ImageButton logoutButton = findViewById(R.id.logout_btn);
        Intent logout_intent = new Intent(getApplicationContext(), MainActivity.class);
        //delete dao
        logoutButton.setOnClickListener(view -> {
            db.clearAllTables();
            startActivity(logout_intent);});

        ImageButton settings_button = findViewById(R.id.settings_button);
        Intent settings_intent = new Intent(getApplicationContext(), SettingsActivity.class);
        settings_button.setOnClickListener(view -> {
            startActivity(settings_intent);});
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().hide();
        createPageButtons();

        ArrayList<Contact> contacts;

        //create room database:
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        contactDao = db.contactDao();

        contacts = (ArrayList<Contact>) contactDao.index();
        int daoSize = contacts.size(); int apiSize;
        if(MyApp.contactList == null)   apiSize =0;
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

                    startActivity(intent);
                }
        );
    }

}
