package com.example.android;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity{

        ImageView profilePictureView;
        TextView userNameView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            profilePictureView = findViewById(R.id.profile_image);
            userNameView = findViewById(R.id.user_name);

            Intent activityIntent = getIntent();

            if (activityIntent != null) {
                String userName = activityIntent.getStringExtra("userName");
                //todo - change
             /*   int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.blue);
                profilePictureView.setImageResource(profilePicture);*/
                userNameView.setText(userName);
            }
    }
}
