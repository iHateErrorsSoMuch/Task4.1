package com.example.taskmanagerapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerapp.models.Task;

import java.util.List;

@Dao
public interface Task_CRUD {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM Task ORDER BY dueDate ASC")
    List<Task> getAllTasks();
}
