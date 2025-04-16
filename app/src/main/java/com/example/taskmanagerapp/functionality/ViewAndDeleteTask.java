package com.example.taskmanagerapp.functionality;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.database.TaskDatabase;
import com.example.taskmanagerapp.models.Task;

public class ViewAndDeleteTask extends AppCompatActivity {
    TextView txtTitle, txtDesc, txtDate;
    Button btnEdit, btnDelete;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDescription);
        txtDate = findViewById(R.id.txtDueDate);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        int taskId = getIntent().getIntExtra("task_id", -1);
        task = TaskDatabase.getInstance(this).taskCrud().getAllTasks()
                .stream().filter(t -> t.id == taskId).findFirst().orElse(null);

        if (task != null) {
            txtTitle.setText(task.title);
            txtDesc.setText(task.description);
            txtDate.setText(task.dueDate);
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAndEditTask.class);
            intent.putExtra("task_id", task.id);
            startActivityForResult(intent, 1001);
        });

        btnDelete.setOnClickListener(v -> {
            TaskDatabase.getInstance(this).taskCrud().delete(task);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            int taskId = getIntent().getIntExtra("task_id", -1);
            task = TaskDatabase.getInstance(this).taskCrud().getAllTasks()
                    .stream().filter(t -> t.id == taskId).findFirst().orElse(null);

            if (task != null) {
                txtTitle.setText(task.title);
                txtDesc.setText(task.description);
                txtDate.setText(task.dueDate);

                Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

