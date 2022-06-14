package com.example.android.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate=true)
    private Integer Id;
    private String userName;
    private String token;
    private byte[] Image;

}
