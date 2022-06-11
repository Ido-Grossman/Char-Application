package com.example.android.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate=true)
    private Integer Id;
    private String Name;
    private String LastDate;
    private int LastMessageId;
    private String Last; //last message content string
    //private String Server; //optional to add if need

    public User(Integer Id, String Name, String LastDate, int LastMessageId, String Last ) {
        this.Id = Id;
        this.Name = Name;
        this.LastDate = LastDate;
        this.LastMessageId = LastMessageId;
        this.Last = Last;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLastDate(String lastDate) {
        LastDate = lastDate;
    }

    public void setLastMessageId(int lastMessageId) {
        LastMessageId = lastMessageId;
    }

    public void setLast(String last) {
        Last = last;
    }

    public String getName() {
        return Name;
    }
    public Integer getId() {
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
