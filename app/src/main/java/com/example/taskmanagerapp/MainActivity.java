package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskmanagerapp.database.TaskDatabase;
import com.example.taskmanagerapp.functionality.AddAndEditTask;
import com.example.taskmanagerapp.functionality.ViewAndDeleteTask;
import com.example.taskmanagerapp.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<Task> taskList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.taskListView);

        loadTasks();

        findViewById(R.id.btnAddTask).setOnClickListener(v -> {
            startActivity(new Intent(this, AddAndEditTask.class));
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Task task = taskList.get(position);
            Intent intent = new Intent(MainActivity.this, ViewAndDeleteTask.class);
            intent.putExtra("task_id", task.id);
            startActivity(intent);
        });

    }

    private void loadTasks() {
        taskList = TaskDatabase.getInstance(this).taskCrud().getAllTasks();
        List<String> titles = new ArrayList<>();
        for (Task task : taskList) titles.add(task.title);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
}
