package com.example.smarttaskapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Task> taskList = TaskInitializer.initializeTasks();
    ImageView userImageButton;


    private LayoutInflater layoutInflater;

    //Eness El BEKAY ,Ö.Özgür KARTAL
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//Navigate To charts !
        ImageView btnUserProfile = findViewById(R.id.userChart);
        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start UserProfileActivity
                Intent userProfileIntent = new Intent(MainActivity.this, UserCharts.class);
                startActivity(userProfileIntent);
            }
        });

        final ListView listView = findViewById(R.id.taskListView);
        TaskAdapter taskAdapter = new TaskAdapter(this, taskList);
        listView.setAdapter(taskAdapter);

        userImageButton = findViewById(R.id.userProfileBtn);
        userImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

    }


}