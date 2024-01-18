package com.example.smarttaskapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskRecyclerViewInterface {
    List<Task> taskList = new ArrayList<Task>();
    ImageView userImageButton;
    ImageView btnUserProfile;
    private TaskRecyclerViewAdapter adapter; // Declare the adapter
    private ImageView addTaskButton;
    private TextView todayBtn, allBtn, laterBtn;

    private static final int YOUR_REQUEST_CODE = 1;
    Fragment fragment;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("tasks");

    //Eness El BEKAY ,Ö.Özgür KARTAL
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    GenericTypeIndicator<List<Task>> taskListType = new GenericTypeIndicator<List<Task>>() {
                    };
                    List<Task> updatedTaskList = snapshot.getValue(taskListType);
                    Log.i("databasetasklist", "onDataChange: size " + updatedTaskList.size());
                    if (updatedTaskList != null) {
                        taskList.clear(); // Clear existing tasks
                        taskList.addAll(updatedTaskList); // Add tasks from Firebase

                        // Update your UI or perform any other actions with the updated taskList
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Restart initialization
       /* if (taskList.isEmpty()) {
            taskList = TaskInitializer.initializeTasks();
            myRef.setValue(taskList); // Update tasks in the database
        }*/

        allBtn = findViewById(R.id.allbtn);
        laterBtn = findViewById(R.id.laterbtn);
        todayBtn = findViewById(R.id.todaybtn);
        fragment = new TodayFragment(taskList, this, adapter);
        replaceFragment(fragment);

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("allBtn", "Clicked: ");
                setAllBtnToDefaultColors();
                allBtn.setBackgroundColor(getResources().getColor(R.color.selectedBackgroundColor));
                allBtn.setTextColor(getResources().getColor(R.color.selectedTextColor));

                replaceFragment(new AllFragment(taskList, MainActivity.this));
            }
        });

        todayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllBtnToDefaultColors();
                todayBtn.setBackgroundColor(getResources().getColor(R.color.selectedBackgroundColor));
                todayBtn.setTextColor(getResources().getColor(R.color.selectedTextColor));
                replaceFragment(new TodayFragment(taskList, MainActivity.this, adapter));

            }
        });
        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllBtnToDefaultColors();
                laterBtn.setBackgroundColor(getResources().getColor(R.color.selectedBackgroundColor));
                laterBtn.setTextColor(getResources().getColor(R.color.selectedTextColor));
                replaceFragment(new LaterFragment(taskList, MainActivity.this));
            }
        });
        /*RecyclerView recyclerView = findViewById(R.id.taskRycyclerView);
        adapter = new TaskRecyclerViewAdapter(this,taskList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        addTaskButton = findViewById(R.id.addTaskBtn);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewTaskActivity.class);
                intent.putExtra("task", (Serializable) new Task(""));
                startActivityForResult(intent, YOUR_REQUEST_CODE + 1);

            }
        });
        //Navigate To charts !
        btnUserProfile = findViewById(R.id.userChart);
        btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start UserProfileActivity
                Intent userProfileIntent = new Intent(MainActivity.this, UserCharts.class);
                startActivity(userProfileIntent);
            }
        });
        userImageButton = findViewById(R.id.userProfileBtn);
        userImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setAllBtnToDefaultColors() {
        laterBtn.setBackgroundColor(getResources().getColor(R.color.defaultBackgroundColor));
        laterBtn.setTextColor(getResources().getColor(R.color.defaultTextColor));
        todayBtn.setBackgroundColor(getResources().getColor(R.color.defaultBackgroundColor));
        todayBtn.setTextColor(getResources().getColor(R.color.defaultTextColor));
        allBtn.setBackgroundColor(getResources().getColor(R.color.defaultBackgroundColor));
        allBtn.setTextColor(getResources().getColor(R.color.defaultTextColor));
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.taskRycyclerView, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onTaskClick(int position) {
        FragmentButtonsInterface fragmentInterface = (FragmentButtonsInterface) getSupportFragmentManager().findFragmentById(R.id.taskRycyclerView);
        Intent intent = new Intent(MainActivity.this, ViewTaskActivity.class);
        intent.putExtra("task", (Serializable) fragmentInterface.getList().get(position));
        intent.putExtra("taskPosition", position);
        startActivityForResult(intent, YOUR_REQUEST_CODE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult", "clicked");
        FragmentButtonsInterface fragmentInterface = (FragmentButtonsInterface) getSupportFragmentManager().findFragmentById(R.id.taskRycyclerView);

        if (requestCode == YOUR_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.i("onActivityResult", "Received result from ViewTaskActivity");
            Task updatedTask = (Task) data.getSerializableExtra("updatedTask");
            int position = data.getIntExtra("taskPosition", -1);

            if (position != -1) {
                Log.i("onActivityResult", "Updating task in position: " + position);

                if (fragmentInterface != null) {
                    fragmentInterface.getList().set(position, updatedTask);
                    fragmentInterface.getAdapter().notifyItemChanged(position);
                }
                // adapter.notifyDataSetChanged(); // Notify the adapter that the data set has changed
            } else {
                Log.e("onActivityResult", "Invalid task position received");
            }
        } else if (requestCode == YOUR_REQUEST_CODE + 1 && resultCode == RESULT_OK) {
            Task updatedTask = (Task) data.getSerializableExtra("updatedTask");
            taskList.add(0, updatedTask);
            allBtn.callOnClick();
            myRef.setValue(taskList);

        } else {
            Log.e("onActivityResult", "Invalid requestCode or resultCode");
        }

    }


}