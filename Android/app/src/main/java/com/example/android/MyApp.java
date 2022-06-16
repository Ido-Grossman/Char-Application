package com.example.android;

import android.app.Application;
import android.content.Context;

import com.example.android.Data.Contact;

import java.util.List;

public class MyApp extends Application {
    public static Context context;
    public static String token;
    public static List<Contact> contactList;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
