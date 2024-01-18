package com.example.smarttaskapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {
    private String selectedDate;
    Button btnDatePicker;
    TextView tvSelectedDate;
    private AlertDialog avatarDialog;
    private ImageView ivSelectedAvatar;
    private Map<String, Integer> avatarResourceMap;
    private Button saveUserInfoButton;
    private TextView userSuccessText;
    private EditText userName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user");

    private void saveUserInfo() {
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        ivSelectedAvatar = findViewById(R.id.avatarImg);
        userName = findViewById(R.id.userName);
        userSuccessText = findViewById(R.id.userSuccessText);


        String userNameStr = userName.getText().toString();
        String userBirthDate = tvSelectedDate.getText().toString();
        String userAvatarTagName = ivSelectedAvatar.getTag().toString();


        User user = new User();
        user.setUserName(userNameStr);
        user.setUserBirthDate(userBirthDate);
        user.setAvatarId(userAvatarTagName);


        // Add the list of tasks to the user object

        // Save the user information to the database
        myRef.setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "User information saved successfully");
                        userSuccessText.setText("Successfully Saved");
                        userSuccessText.setTextColor(getResources().getColor(R.color.green));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error saving user information", e);
                    }
                });
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        btnDatePicker = findViewById(R.id.btnDatePicker);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        ivSelectedAvatar = findViewById(R.id.avatarImg);
        saveUserInfoButton = findViewById(R.id.saveUserInfoBtn);
        userSuccessText = findViewById(R.id.userSuccessText);
        userName = findViewById(R.id.userName);


        avatarResourceMap = new HashMap<>();
        avatarResourceMap.put("avatar6", R.mipmap.tea);
        avatarResourceMap.put("avatar2", R.mipmap.cat);
        avatarResourceMap.put("avatar7", R.mipmap.coffee);
        avatarResourceMap.put("avatar1", R.mipmap.astronot);
        avatarResourceMap.put("avatar5", R.mipmap.warrior);
        avatarResourceMap.put("avatar4", R.mipmap.wizz);
        avatarResourceMap.put("avatar3", R.mipmap.dog);
        avatarResourceMap.put("avatar", R.mipmap.dog);

        saveUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }

        });
        // Set up ValueEventListener to listen for changes in the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User updatedUser = dataSnapshot.getValue(User.class);

                    // Update UI elements with the new data
                    userName.setText(updatedUser.getUserName());
                    tvSelectedDate.setText(updatedUser.getUserBirthDate());
                    ivSelectedAvatar.setImageResource(avatarResourceMap.get(updatedUser.getAvatarId()));

                    // Check if the avatarId exists in the map

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }


    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, (datePicker, selectedYear, selectedMonth, selectedDay) -> {
            // Do something with the selected date
            selectedDate = selectedDay + "." + (selectedMonth + 1) + "." + selectedYear;
            // Update the TextView with the selected date
            tvSelectedDate.setText(selectedDate);
        }, year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    public void showAvatarSelectionDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.avatar, null);
        builder.setView(dialogView);

        avatarDialog = builder.create();
        avatarDialog.show();
    }

    public void onAvatarClick(View view) {
        String selectedAvatar = view.getTag().toString();

        // Log available keys for debugging
        Log.d("UserProfileActivity", "Available avatar keys: " + avatarResourceMap.keySet().toString());

        // Check if the selectedAvatar is in the map
        if (avatarResourceMap.containsKey(selectedAvatar)) {
            int resourceId = avatarResourceMap.get(selectedAvatar);
            Log.d("UserProfileActivity", selectedAvatar + " " + resourceId);
            ivSelectedAvatar.setImageResource(resourceId);
            ivSelectedAvatar.setTag(selectedAvatar);
        } else {
            // Log an error or show a toast indicating that the resource was not found
            Log.e("UserProfileActivity", selectedAvatar);
        }

        avatarDialog.dismiss();
    }

}

