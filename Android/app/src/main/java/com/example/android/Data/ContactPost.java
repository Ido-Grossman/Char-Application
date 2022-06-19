package com.example.android.Data;

public class ContactPost {

    public String from;

    public String to;

    public String server;

    public String content;

    public ContactPost(String from, String to, String server) {
        this.from = from;
        this.to = to;
        this.server = server;
        this.content = null;
    }

    public ContactPost(String from, String to, String server, String content) {
        this.from = from;
        this.to = to;
        this.server = server;
        this.content = content;
    }
}
