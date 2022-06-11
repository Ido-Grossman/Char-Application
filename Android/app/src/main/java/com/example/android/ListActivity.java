package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.Data.User;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ArrayList<User> users = new ArrayList<>();

        //add users part - insert all users to a list and then send the list to adapter todo
/*        for (int i = 0; i < profilePictures.length; i++) {
            User aUser = new User(
                    userNames[i], profilePictures[i],
                    lastMassages[i], times[i]
            );

            users.add(aUser);
        }*/
        User zUser = new User(1,"santi", "11/11/11", 2, "whatsapppp");
        users.add(zUser);
        User aUser = new User(2,"eli", "11/11/11", 2, "whatsapppp");
        users.add(aUser);
        User bUser = new User(3,"ido", "11/11/11", 2, "whatsapppp");
        users.add(bUser);

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
