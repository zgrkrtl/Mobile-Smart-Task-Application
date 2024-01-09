package com.example.smarttaskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

//Eness El BEKAY
public class ViewTaskActivity extends AppCompatActivity {
    TextInputEditText taskTitle;
    TextView save;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        Intent intent = getIntent();
        String taskType = intent.getStringExtra("taskType");

        taskTitle = findViewById(R.id.taskheaderinput);
        taskTitle.setText(taskType);

        save = findViewById(R.id.savebtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskTitle != null && taskTitle.equals(taskType)){

                }
            }
        });
    }
}