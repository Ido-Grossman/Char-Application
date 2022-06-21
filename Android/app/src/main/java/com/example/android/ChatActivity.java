package com.example.android;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.android.Adapters.MsgsListAdapter;
import com.example.android.Data.AppDB;
import com.example.android.Data.ContactDao;
import com.example.android.Data.ContactPost;
import com.example.android.Data.Content;
import com.example.android.Data.Message;
import com.example.android.Data.messageDao;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements IMessageListener{

    ImageView profilePictureView;
    TextView userNameView;

        private AppDB db;
        private messageDao msgDao;
        private Intent intent;
        private ContactDao contactDao;
        private MessageListAdapter ml_adapter;
        private List<com.example.android.Data.Message> msg_list;
        private String contactId;
        private List<Message> messageList;


    void createPageButtons(){
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> {
            MyApp.messageNotify.removeMessageListener(this);
            finish();
        });

        ImageButton logoutButton = findViewById(R.id.logout_button);
        //delete dao
        logoutButton.setOnClickListener(view -> {
            MyApp.userId = null;
            finish();});


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
                ContactPost contactPost = new ContactPost(MyApp.userId, contactId, contactDao.get(contactId).getServer(), content_str);
                transferMessage(contactPost);
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
    protected void onStart() {
        super.onStart();
        if (MyApp.userId == null)
            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp.messageNotify.addMessageListener(this);
        setContentView(R.layout.activity_chat);
        intent = getIntent();
        contactId = intent.getStringExtra("contactId");

            //create room database:
            db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
            msgDao = db.messageDao();
            contactDao = db.contactDao();


        profilePictureView = findViewById(R.id.profile_image);
        userNameView = findViewById(R.id.user_name);

        //set recycler view
        RecyclerView lst_msgs = findViewById(R.id.list_msgs);
        ml_adapter = new MessageListAdapter(this, messageList);
        lst_msgs.setAdapter(ml_adapter);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        lst_msgs.setLayoutManager(mLinearLayoutManager);
        getMessages();
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

    void getMessages() {
        Call<List<Message>> call = MyApp.webServiceAPI.getMessages("Bearer "+MyApp.token, contactId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messageList = response.body();
                assert messageList != null;
                int size = messageList.size() - msgDao.index(contactId).size();
                for (int i = 0; i < size; i++) {
                    messageList.get(i).setContactId(contactId);
                    msgDao.insert(messageList.get(i));
                }
                messageEvent();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    void transferMessage(ContactPost contactPost){
        Call<Void> call = MyApp.webServiceAPI.transferMessage("Bearer "+MyApp.token, contactPost);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    String userId = contactPost.to;
                    String message = contactPost.content;
                    Content content = new Content(message);
                    sendMessage(userId, content);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    void sendMessage(String apiCallUrl, Content message){
        Call<Void> call = MyApp.webServiceAPI.postMessage("Bearer "+MyApp.token, apiCallUrl, message);
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
    protected void onStop() {
        super.onStop();
        MyApp.messageNotify.removeMessageListener(this);
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
        refreshListInDB();
        runOnUiThread(() -> ml_adapter.notifyDataSetChanged());
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