package com.example.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.Data.AppDB;
import com.example.android.Data.ContactDao;
import com.example.android.api.WebServiceAPI;
import com.example.android.Data.UserCred;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterPage extends AppCompatActivity {

    private AppDB db;
    private ContactDao contactDao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        getSupportActionBar().hide();
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApp.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        Intent intent = new Intent(this, ContactsActivity.class);

        ImageButton UploadImg = findViewById(R.id.UploadImg);
        UploadImg.setOnClickListener(view -> {
            ChooseImage();
        });
        ImageView img = findViewById(R.id.UserImage);
        Button registerBtn = findViewById(R.id.RegisterBtn);
        registerBtn.setOnClickListener(view -> {
            EditText username = findViewById(R.id.RegisterUserName);
            EditText password = findViewById(R.id.RegisterPassword1);
            EditText passwordValidate = findViewById(R.id.RegisterPassword2);
            String s = password.getText().toString();
            String k = passwordValidate.getText().toString();
            if (!s.equals(k)) {
                TextInputLayout layout1 = findViewById(R.id.Password1Layout);
                layout1.setError("The password doesn't match");
                TextInputLayout layout2 = findViewById(R.id.Password2Layout);
                layout2.setError("The password doesn't match");
            } else if (img.getVisibility() == View.INVISIBLE) {

            } else {
                String userName = username.getText().toString();
                UserCred newUser = new UserCred(userName, s, "hello", "http://localhost:7225", "");
                this.checkIfUsernameExists(userName, newUser, intent);
            }

        });
    }

    void ChooseImage() {
        Intent takePicture = new Intent();
        takePicture.setType("image/*");
        takePicture.setAction(Intent.ACTION_GET_CONTENT);

        startActivityIntent.launch(takePicture);
    }

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                assert result.getData() != null;
                Uri selectImage = result.getData().getData();
                if (selectImage != null) {
                    ImageView img = findViewById(R.id.UserImage);
                    if (img.getVisibility() == View.INVISIBLE) {
                        img.setVisibility(View.VISIBLE);
                    }
                    img.setImageURI(selectImage);
                }
            });

    public void create(UserCred userCred, Intent chatIntent){
        Call<String> call = webServiceAPI.registerUser(userCred);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    MyApp.token = response.body();
                    MyApp.userId = userCred.username;
                    startActivity(chatIntent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void checkIfUsernameExists(String username, UserCred newUser, Intent chatIntent){
        Call<Void> call = webServiceAPI.checkIfUsernameExists(username);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // if there is a username in db already we return true
                if (response.isSuccessful()){

                    // else there is no username with this name so we create one new
                } else {
                    create(newUser, chatIntent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
