package com.example.android.Data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey(autoGenerate=true)
    private Integer id;
    @NonNull
    private String contactId; //id of other contact
    private String content;
    private String created; //date and time
    private Boolean sent;

    public Message(@NonNull String contactId, String content, String created, Boolean sent) {
        this.contactId = contactId;
        this.content = content;
        this.created = created;
        this.sent = sent;
    }
    @NonNull
    public String getContactId() {
        return contactId;
    }

    public void setContactId(@NonNull String contactId) {
        this.contactId = contactId;
    }

    public Boolean getSent() {
        return sent;
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
