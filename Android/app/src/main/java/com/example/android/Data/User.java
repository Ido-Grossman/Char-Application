package com.example.android.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class User {


    public String Id;
    public String fireBaseToken;
    public String server;
    public static String token;
    public byte[] Image;
    public String password;

    public User(String id, String password, String fireBaseToken, String server, String token, byte[] image) {
        Id = id;
        this.password = password;
        this.fireBaseToken = fireBaseToken;
        this.server = server;
        User.token = token;
        Image = image;
    }

    public User(String id, String password, String fireBaseToken, String server, String token) {
        Id = id;
        this.password = password;
        this.fireBaseToken = fireBaseToken;
        this.server = server;
        User.token = token;
        this.Image = null;
    }




}
