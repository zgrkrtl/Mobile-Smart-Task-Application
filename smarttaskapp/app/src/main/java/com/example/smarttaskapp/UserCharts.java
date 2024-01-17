package com.example.smarttaskapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCharts extends AppCompatActivity {
    private TextView displayUserName;
    private TextView displayUserBirthDate;
    private ImageView displayAvatar;

    List<Task> taskList = TaskInitializer.initializeTasks();
    private ChartView chartView;
    private Map<String, Integer> avatarResourceMap;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_charts);

        saveTaskList();
        avatarResourceMap = new HashMap<>();
        avatarResourceMap.put("avatar6", R.mipmap.tea);
        avatarResourceMap.put("avatar2", R.mipmap.cat);
        avatarResourceMap.put("avatar7", R.mipmap.coffee);
        avatarResourceMap.put("avatar1", R.mipmap.astronot);
        avatarResourceMap.put("avatar5", R.mipmap.warrior);
        avatarResourceMap.put("avatar4", R.mipmap.wizz);
        avatarResourceMap.put("avatar3", R.mipmap.dog);
        avatarResourceMap.put("avatar", R.mipmap.dog);


        displayUserName = findViewById(R.id.displayUserName);
        displayUserBirthDate = findViewById(R.id.displayUserBirthDate);
        displayAvatar = findViewById(R.id.displayAvatar);


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    displayUserName.setText("User Name: " + user.getUserName());
                    displayUserBirthDate.setText("Birth Date: " + user.getUserBirthDate());
                    // Update UI with user information
                    // Set the avatar image using user.getAvatarKey()
                    String avatarTag = user.getAvatarId();
                    displayAvatar.setImageResource(avatarResourceMap.get(avatarTag));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference("tasks");
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    GenericTypeIndicator<List<Task>> taskListType = new GenericTypeIndicator<List<Task>>() {
                    };
                    List<Task> retrievedTaskList = snapshot.getValue(taskListType);

                    // You can now use retrievedTaskList as needed
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chartView = findViewById(R.id.chartView);

        // Assuming you have a taskList with Task objects

        // Count the labels
        Map<String, Integer> labelCountMap = countLabels(taskList);

        // Set up the chart based on the label counts
        chartView.setData(labelCountMap);
    }

    private Map<String, Integer> countLabels(List<Task> taskList) {
        Map<String, Integer> labelCountMap = new HashMap<>();

        for (Task task : taskList) {
            String label = task.getLabel();

            if (labelCountMap.containsKey(label)) {
                int count = labelCountMap.get(label);
                labelCountMap.put(label, count + 1);
            } else {
                labelCountMap.put(label, 1);
            }
        }

        return labelCountMap;
    }

    private void saveTaskList() {
        DatabaseReference tasksRef = FirebaseDatabase.getInstance().getReference("tasks");
        tasksRef.setValue(taskList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Task list saved successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error saving task list", e);
                    }
                });


    }

}
