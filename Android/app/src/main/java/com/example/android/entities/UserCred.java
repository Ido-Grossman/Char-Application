package com.example.android.entities;

public class UserCred
{
    public String username;
    public String password;
    public String nickname;
    public String server;
    public String firebaseToken;

    public UserCred(String username, String password, String nickname, String server, String firebaseToken){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.server = server;
        this.firebaseToken = firebaseToken;
    }
}