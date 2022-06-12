package com.example.android.entities;


public class Contact {
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLast() {
        return Last;
    }

    public void setLast(String last) {
        Last = last;
    }

    public String getLastDate() {
        return LastDate;
    }

    public void setLastDate(String lastDate) {
        LastDate = lastDate;
    }

    public int getLastMessageRead() {
        return lastMessageRead;
    }

    public void setLastMessageRead(int lastMessageRead) {
        this.lastMessageRead = lastMessageRead;
    }

    public int getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(int lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public String name;
    public String server;
    public String Last;
    public String LastDate;
    public int lastMessageRead;
    public int lastMessageId;

    public Contact(User user){
        this.id = user.id;
        this.name = user.name;
        this.server = user.server;
        this.lastMessageRead = 0;
        this.lastMessageId = 0;
    }
    public Contact(ContactPost contactPost)
    {
        this.id = contactPost.id;
        this.name = contactPost.name;
        this.server = contactPost.server;
    }
    public Contact(String id, String name, String server)
    {
        this.id = id;
        this.name = name;
        this.server = server;
    }
}
