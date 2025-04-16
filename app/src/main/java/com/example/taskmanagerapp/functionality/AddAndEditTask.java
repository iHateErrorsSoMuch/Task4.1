package com.example.taskmanagerapp.functionality;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.database.TaskDatabase;
import com.example.taskmanagerapp.models.Task;

public class AddAndEditTask extends AppCompatActivity {
    EditText edtTitle, edtDesc, edtDate;
    Button btnSave;
    Task taskToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDescription);
        edtDate = findViewById(R.id.edtDueDate);
        btnSave = findViewById(R.id.btnSave);

        int taskId = getIntent().getIntExtra("task_id", -1);
        if (taskId != -1) {
            taskToEdit = TaskDatabase.getInstance(this).taskCrud().getAllTasks()
                    .stream().filter(t -> t.id == taskId).findFirst().orElse(null);
            if (taskToEdit != null) {
                edtTitle.setText(taskToEdit.title);
                edtDesc.setText(taskToEdit.description);
                edtDate.setText(taskToEdit.dueDate);
            }
        }

        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                if (taskToEdit == null) {
                    Task newTask = new Task();
                    newTask.title = edtTitle.getText().toString();
                    newTask.description = edtDesc.getText().toString();
                    newTask.dueDate = edtDate.getText().toString();
                    TaskDatabase.getInstance(this).taskCrud().insert(newTask);
                } else {
                    taskToEdit.title = edtTitle.getText().toString();
                    taskToEdit.description = edtDesc.getText().toString();
                    taskToEdit.dueDate = edtDate.getText().toString();
                    TaskDatabase.getInstance(this).taskCrud().update(taskToEdit);
                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private boolean validateFields() {
        if (edtTitle.getText().toString().isEmpty() || edtDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title and Due Date are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
