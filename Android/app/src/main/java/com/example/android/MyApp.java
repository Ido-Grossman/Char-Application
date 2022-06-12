package com.example.android;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    public static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
