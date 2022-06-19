package com.example.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.android.Data.Contact;
import com.example.android.Data.Message;
import com.example.android.api.WebServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyApp extends Application {
    public static Context context;
    public static String token;
    public static Retrofit retrofit;
    public static WebServiceAPI webServiceAPI;
    public static List<Contact> contactList;
    public static String FirebaseToken;
    public static List<Message> messageList;
    public static MessageNotify messageNotify;
    public static String userId;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}

