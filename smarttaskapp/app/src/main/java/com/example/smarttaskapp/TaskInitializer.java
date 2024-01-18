package com.example.smarttaskapp;

import java.util.ArrayList;
import java.util.List;

public class TaskInitializer {

    public static List<Task> initializeTasks() {
        List<Task> taskList = new ArrayList<>();

        //Older task constructor !Deletable
       /* taskList.add(new Task("go to the grocery store", R.drawable.uncheked_box, "Shopping"));
        taskList.add(new Task("do your homework", R.drawable.cheked_box, "Homework"));
        taskList.add(new Task("dentist appointment", R.drawable.cheked_box, "Healthcare"));
        taskList.add(new Task("pick up ahmed", R.drawable.cheked_box, "Family"));
        taskList.add(new Task("a coffee meeting", R.drawable.uncheked_box, "Meeting"));
        taskList.add(new Task("theater club meeting", R.drawable.cheked_box, "Meeting"));
        taskList.add(new Task("do your laundry", R.drawable.cheked_box, "Chores"));
        taskList.add(new Task("meet with your friend", R.drawable.uncheked_box, "Meeting"));
        taskList.add(new Task("call your mom", R.drawable.uncheked_box, "Family"));*/

        taskList.add(new Task("go to the grocery store"));
        taskList.add(new Task("do your homework"));
        taskList.add(new Task("dentist appointment"));
        taskList.add(new Task("pick up ahmed"));
        taskList.add(new Task("a coffee meeting"));
        taskList.add(new Task("theater club meeting"));
        taskList.add(new Task("do your laundry"));
        taskList.add(new Task("meet with your freind"));
        taskList.add(new Task("call your mom"));
        taskList.add(new Task("go to the grocery store"));
        taskList.add(new Task("do your homework"));
        taskList.add(new Task("dentist appointment"));
        taskList.add(new Task("pick up ahmed"));
        taskList.add(new Task("a coffee meeting"));
        taskList.add(new Task("theater club meeting"));
        taskList.add(new Task("do your laundry"));
        taskList.add(new Task("meet with your freind"));
        taskList.add(new Task("call your mom"));

        return taskList;
    }
}
