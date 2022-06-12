package com.example.android.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Message.class}, version = 3)
@TypeConverters({MessagesConverter.class})
public abstract class AppDB extends RoomDatabase{
    public abstract UserDao userDao();
    public abstract messageDao messageDao();
}
