package com.example.android.Data;

public class User {


    public String id;
    public String name;
    public String server;
    public byte[] image;
    public String password;

    public User(String id, String password, String name, String server, byte[] image) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.server = server;
        this.image = image;
    }

    public User(String id, String password, String fireBaseToken, String server, String token) {
        this.id = id;
        this.password = password;
        this.name = fireBaseToken;
        this.server = server;
        this.image = null;
    }




    public User(String id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }



    public void setImage(byte[] image) {
        this.image = image;
    }
}
