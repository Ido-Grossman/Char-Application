package com.example.android;

public class User {
    private String Id;
    private String Name;
    private String LastDate;
    private int LastMessageId;
    private String Last; //last message content string
    //private String Server; //optional to add if need


    public User(String Id,String Name, String LastDate, int LastMessageId, String Last ) {
        this.Id = Id;
        this.Name = Name;
        this.LastDate = LastDate;
        this.LastMessageId = LastMessageId;
        this.Last = Last;
    }

    public String getName() {
        return Name;
    }
    public String getId() {
        return Id;
    }
    public String getLastDate() {
        return LastDate;
    }
    public int getLastMessageId() {
        return LastMessageId;
    }
    public String getLast() {
        return Last;
    }

}
