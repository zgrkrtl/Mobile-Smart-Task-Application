package com.example.smarttaskapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserProfileInformative extends AppCompatActivity {
    private TextView displayUserName;
    private TextView displayUserBirthDate;
    private ImageView displayAvatar;
    private Map<String, Integer> avatarResourceMap;
    TextView flexibleText;
    private Button options;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_charts);


        options = findViewById(R.id.optionsBtn);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

//Avatar tags kept in a hashmap to acquire the right image avatarTag from firebasedatabase
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
                Toast.makeText(UserProfileInformative.this, "DATABASE ERROR", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /*  private Map<String, Integer> countLabels(List<Task> taskList) {
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
  */
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popupMenu.getMenu());

        flexibleText = findViewById(R.id.flexibleText);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle menu item click
                if (item.getItemId() == R.id.menu_version) {
                    flexibleText.setText("Version 1.0 - The initial unveiling, introducing the core features of our software.");
                    flexibleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    return true;
                } else if (item.getItemId() == R.id.menu_help) {
                    flexibleText.setText("For assistance and help documentation, please visit our website:www.smartTaskApp.com");
                    flexibleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    return true;
                } else if (item.getItemId() == R.id.menu_information) {
                    flexibleText.setText("Our app is a user-friendly tool designed to streamline tasks, enhance productivity, and provide a seamless experience for users to effortlessly manage their daily activities.");
                    flexibleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    return true;
                } else {
                    return false;
                }
            }
        });

        popupMenu.show();
    }

}
