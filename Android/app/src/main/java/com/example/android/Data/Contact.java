package com.example.android.Data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String lastDate;
    private int lastMessageId;
    private String last; //last message content string
    private String server;
    public byte[] image;

    @Ignore
    public Contact(@NonNull String id, String name, String server) {
        this.id = id;
        this.name = name;
        this.server = server;
    }

    public Contact(@NonNull String id, String name, String lastDate, int lastMessageId, String last, String server, byte[] image) {
        this.id = id;
        this.name = name;
        this.lastDate = lastDate;
        this.lastMessageId = lastMessageId;
        this.last = last;
        this.server = server;
        this.image = image;
    }

    @NonNull
    public String getUserId() {
        return id;
    }
    public void setUserId(@NonNull String userId) {
        this.id = userId;
    }
    public void setServer(String server) {
        this.server = server;}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public void setLastMessageId(int lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setImage(byte[] image) {this.image = image;}

    public String getName() {
        return name;
    }
    public String getLastDate() {
        return lastDate;
    }
    public int getLastMessageId() {
        return lastMessageId;
    }
    public String getLast() {
        return last;
    }
    public String getServer() {return server;}
    public byte[] getImage() {return image;}

    @NonNull
    public String getId() {
        return this.id;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "Id=" + id +
                ", Name='" + name + '\'' +
                ", LastDate='" + lastDate + '\'' +
                ", LastMessageId=" + lastMessageId +
                ", Last='" + last + '\'' +
                ", Server='" + server + '\'' +
                '}';
    }
}
