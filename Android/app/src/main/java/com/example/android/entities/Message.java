package com.example.android.entities;

public class Message
{
    public int id;
    public String content;
    public String created;
    public Boolean sent;

    public Message(int id, String content, String created, Boolean sent)
    {
        this.id = id;
        this.content = content;
        this.created = created;
        this.sent = sent;
    }
}
