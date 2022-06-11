package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.android.Data.AppDB;
import com.example.android.Data.User;
import com.example.android.Data.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    ListView listView;
    CustomListAdapter adapter;

    private AppDB db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ArrayList<User> users;

        //create room database:
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        userDao = db.userDao();

        FloatingActionButton btnAdd = findViewById(R.id.add_contact_btn);
        btnAdd.setOnClickListener(view-> {
            Intent i = new Intent(this,AddContactActivity.class);
            startActivity(i);
        });

        users = (ArrayList<User>) userDao.index();
        listView = findViewById(R.id.list_view);
        adapter = new CustomListAdapter(getApplicationContext(), users);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

                User user = users.get(i);
                intent.putExtra("userId", user.getId());
                intent.putExtra("userName", user.getName());
                intent.putExtra("lastDateTime", user.getLastDate());
                //intent.putExtra("profilePicture", user.); //todo - support image

                startActivity(intent);
            }
        }
        );
    }

}
