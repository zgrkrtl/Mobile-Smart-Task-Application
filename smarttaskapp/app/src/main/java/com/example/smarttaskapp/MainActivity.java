package com.example.smarttaskapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final List<Task> taskList = new ArrayList<Task>();

    //Eness El BEKAY ,Ö.Özgür KARTAL
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        taskList.add(new Task("go to the grocery store", R.drawable.uncheked_box, "Shopping"));
        taskList.add(new Task("do your homework", R.drawable.cheked_box, "Homework"));
        taskList.add(new Task("dentist appointment", R.drawable.cheked_box, "Healthcare"));
        taskList.add(new Task("pick up ahmed", R.drawable.cheked_box, "Family"));
        taskList.add(new Task("a coffee meeting", R.drawable.uncheked_box, "Meeting"));
        taskList.add(new Task("theater club meeting", R.drawable.cheked_box, "Meeting"));
        taskList.add(new Task("do your laundry", R.drawable.cheked_box, "Chores"));
        taskList.add(new Task("meet with your freind", R.drawable.uncheked_box, "Meeting"));
        taskList.add(new Task("call your mom", R.drawable.uncheked_box, "Family"));
        taskList.add(new Task("go to the grocery store", R.drawable.uncheked_box, "Chores"));
        taskList.add(new Task("do your homework", R.drawable.cheked_box, "Homework"));
        taskList.add(new Task("dentist appointment", R.drawable.cheked_box, "Healthcare"));
        taskList.add(new Task("pick up ahmed", R.drawable.cheked_box, "Family"));
        taskList.add(new Task("a coffee meeting", R.drawable.uncheked_box, "Meeting"));
        taskList.add(new Task("theater club meeting", R.drawable.cheked_box, "Meeting"));
        taskList.add(new Task("do your laundry", R.drawable.cheked_box, "Chores"));
        taskList.add(new Task("meet with your friend", R.drawable.uncheked_box, "Meeting"));
        taskList.add(new Task("call your mom", R.drawable.uncheked_box, "Family"));



        final ListView listView = findViewById(R.id.taskListView);
        TaskAdapter taskAdapter = new TaskAdapter(this, taskList);
        listView.setAdapter(taskAdapter);

    }



}