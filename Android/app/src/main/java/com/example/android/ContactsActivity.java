package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.android.Adapters.CustomListAdapter;
import com.example.android.Data.AppDB;
import com.example.android.Data.Contact;
import com.example.android.Data.ContactDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    ListView listView;
    CustomListAdapter adapter;

    private AppDB db;
    private ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().hide();

        ArrayList<Contact> contacts;

        //create room database:
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        contactDao = db.contactDao();

        FloatingActionButton btnAdd = findViewById(R.id.add_contact_btn);
        btnAdd.setOnClickListener(view-> {
            Intent i = new Intent(this,AddContactActivity.class);
            startActivity(i);
        });
        contacts = (ArrayList<Contact>) contactDao.index();
        int apiSize = MyApp.contactList.size(), daoSize = contacts.size();
        if(apiSize != daoSize) {//if api conatcts empty try from dao todo uncomment*/
            int sizeComp = apiSize - daoSize;
            for (int i = 0; i < sizeComp; i++)
                contactDao.insert(MyApp.contactList.get(i));
            contacts = (ArrayList<Contact>) contactDao.index();
        }

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
