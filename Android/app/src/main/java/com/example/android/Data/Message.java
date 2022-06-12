package com.example.android.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey(autoGenerate=true)
    private Integer id;
    private String content;
    private String created; //date and time
    private Boolean sent;

    public Message(Integer id, String content, String created, Boolean sent) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.sent = sent;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        return created;
    }

    public Boolean isSent() {
        return sent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }
}
