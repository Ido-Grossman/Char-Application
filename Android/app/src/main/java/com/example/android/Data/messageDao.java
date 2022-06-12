package com.example.android.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface messageDao {
/*    @Query("SELECT * FROM message")
    List<Message> index();*/ //todo uncomment

    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Insert
    void insert(Message...messages);

    @Update
    void update(Message...messages);

    @Delete
    void delete(Message...messages);
}
