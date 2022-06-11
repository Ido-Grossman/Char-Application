package com.example.android.Data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate=true)
    private Integer Id;
    private String Name;
    private String LastDate;
    private int LastMessageId;
    private String Last; //last message content string
    private String Server;

    public User(Integer Id, String Name, String LastDate, int LastMessageId, String Last, String Server ) {
        this.Id = Id;
        this.Name = Name;
        this.LastDate = LastDate;
        this.LastMessageId = LastMessageId;
        this.Last = Last;
        this.Server = Server;
    }

    @Ignore
    public User(Integer Id, String Name, String Server  ) {
        this.Id = Id;
        this.Name = Name;
        this.LastDate = null;
        this.Server = Server;
    }

    public void setServer(String server) {Server = server;}

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
    public String getServer() {return Server;}

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", LastDate='" + LastDate + '\'' +
                ", LastMessageId=" + LastMessageId +
                ", Last='" + Last + '\'' +
                ", Server='" + Server + '\'' +
                '}';
    }
}
