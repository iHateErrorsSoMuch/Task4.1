package com.example.taskmanagerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.taskmanagerapp.Task_CRUD;
import com.example.taskmanagerapp.models.Task;

@Database(entities = {Task.class}, version = 1)

public abstract class TaskDatabase extends RoomDatabase{
    private static TaskDatabase instance;

    public abstract Task_CRUD taskCrud();

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
