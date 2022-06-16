package com.example.android.Data;

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




    public User(String id, String userName, String token, byte[] image) {
        Id = id;
        this.token = token;
        Image = image;
    }

    public void setId(String id) {
        Id = id;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public void setImage(byte[] image) {
        Image = image;
    }
}
