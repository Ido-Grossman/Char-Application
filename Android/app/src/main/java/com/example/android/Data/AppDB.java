package com.example.android.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Contact.class, Message.class}, version = 10)
@TypeConverters({MessagesConverter.class})
public abstract class AppDB extends RoomDatabase{
    public abstract ContactDao contactDao();
    public abstract messageDao messageDao();
}
