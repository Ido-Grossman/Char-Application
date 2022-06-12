package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Adapters.MsgsListAdapter;
import com.example.android.Data.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity{

        ImageView profilePictureView;
        TextView userNameView;

        void createPageButtons(){
            ImageButton backButton = findViewById(R.id.back_button);
            Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
            backButton.setOnClickListener(view -> startActivity(intent));
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            getSupportActionBar().hide();

            profilePictureView = findViewById(R.id.profile_image);
            userNameView = findViewById(R.id.user_name);

            RecyclerView lst_msgs = findViewById(R.id.list_msgs);
            final MsgsListAdapter adapter = new MsgsListAdapter(this);
            lst_msgs.setAdapter(adapter);
            lst_msgs.setLayoutManager(new LinearLayoutManager(this));
            List<com.example.android.Data.Message> msgs = new ArrayList<com.example.android.Data.Message>();
            msgs.add(new Message(1,"hey eli", "11/7/98", true));
            msgs.add(new Message(2,"hey eli!", "12/7/98", false));
            msgs.add(new Message(3,"hey eli!!", "13/7/98", true));
            msgs.add(new Message(4,"hey eli!!", "14/7/98", false));
            adapter.setMsgs(msgs);
            Intent activityIntent = getIntent();

            if (activityIntent != null) {
                String userName = activityIntent.getStringExtra("userName");
                //todo - support image
             /*   int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.blue);
                profilePictureView.setImageResource(profilePicture);*/
                userNameView.setText(userName);
            }

            createPageButtons();
    }
}
