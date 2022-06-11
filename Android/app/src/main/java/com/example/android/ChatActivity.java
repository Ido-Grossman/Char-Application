package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

            profilePictureView = findViewById(R.id.profile_image);
            userNameView = findViewById(R.id.user_name);

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
