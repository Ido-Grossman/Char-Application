package com.example.android.entities;

import java.util.HashMap;
import java.util.List;

public class User
{
    public String id;
    public String password;
    public String name;
    public String fireBaseToken;
    public String server;
    public HashMap<Contact, List<Message>> contacts;

    public User(String id, String password, String name, String fireBaseToken, String server)
    {
        this.id = id;
        this.password = password;
        this.name = name;
        this.fireBaseToken = fireBaseToken;
        this.server = server;
        this.contacts = new HashMap<Contact, List<Message>>();
    }

    public User(String id, String password, String name, String server)
    {
        this.id = id;
        this.password = password;
        this.name = name;
        this.server = server;
        this.contacts = new HashMap<Contact, List<Message>>();
    }

    public User(UserCred userCred)
    {
        this.id = userCred.username;
        this.password = userCred.password;
        this.name = userCred.nickname;
        this.server = userCred.server;
        this.contacts = new HashMap<Contact, List<Message>>();
    }
}