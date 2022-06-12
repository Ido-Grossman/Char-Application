package com.example.android.entities;

public class UserCred
{
    public String username;
    public String password;

    public String nickname;

    public String server;

    public UserCred(String username, String password, String nickname, String server){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.server = server;
    }
}