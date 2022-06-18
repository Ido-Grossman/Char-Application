package com.example.android.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface messageDao {
    @Query("SELECT * FROM message Where contactId = :contactId")
    List<Message> index(int contactId);

    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Insert
    void insert(Message...messages);

    @Update
    void update(Message...messages);

    @Delete
    void delete(Message...messages);
}
