package com.example.android.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDB extends RoomDatabase{
    public abstract UserDao userDao();

}
