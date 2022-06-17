package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.android.Adapters.MsgsListAdapter;
import com.example.android.Data.AppDB;
import com.example.android.Data.Message;
import com.example.android.Data.messageDao;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity{

        ImageView profilePictureView;
        TextView userNameView;

        private AppDB db;
        private messageDao msgDao;
        private Intent intent;
        private MsgsListAdapter ml_adapter;
        private List<com.example.android.Data.Message> msg_list;
        private int contactId;

    void createPageButtons(){
            ImageButton backButton = findViewById(R.id.back_button);
            Intent back_intent = new Intent(getApplicationContext(), ContactsActivity.class);
            backButton.setOnClickListener(view -> startActivity(back_intent));


            AppCompatImageButton sendButton = findViewById(R.id.send_button);
            sendButton.setOnClickListener(view -> {
                TextInputEditText content = findViewById(R.id.inputMessages);
                String content_str = content.getText().toString();
                //create string of date_time
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
                String date = formatter.format(new Date());
                Message msg = new Message(contactId, content_str, date, true);
                msgDao.insert(msg);
                msg_list = msgDao.index(contactId);
                refreshListInDB();
                ml_adapter.notifyDataSetChanged();
                content.getText().clear(); //delete keyboard content after sending
            });
        }
    /**send the list to the adapter, organized and updated **/
    void refreshListInDB(){
            msg_list = msgDao.index(contactId);
            Collections.reverse(msg_list); //flip msgs order to start from newst
            ml_adapter.setMsgs(msg_list);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            getSupportActionBar().hide();
            intent = getIntent();
            contactId = Integer.parseInt(intent.getStringExtra("contactId"));

            //create room database:
            db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
            msgDao = db.messageDao();


            profilePictureView = findViewById(R.id.profile_image);
            userNameView = findViewById(R.id.user_name);

            //set recycler view
            RecyclerView lst_msgs = findViewById(R.id.list_msgs);
            ml_adapter = new MsgsListAdapter(this);
            lst_msgs.setAdapter(ml_adapter);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
            mLinearLayoutManager.setReverseLayout(true);
            lst_msgs.setLayoutManager(mLinearLayoutManager);
            refreshListInDB();


            if (intent != null) {
                String userName = intent.getStringExtra("contactName");
                //todo - support image
             /*   int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.blue);
                profilePictureView.setImageResource(profilePicture);*/
                userNameView.setText(userName);
            }

            createPageButtons();
    }
}
