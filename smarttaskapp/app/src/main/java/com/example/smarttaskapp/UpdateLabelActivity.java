package com.example.smarttaskapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdateLabelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_label);

        ArrayList<TaskTag> tagList = new ArrayList<>();

        // Create and populate the list of TaskTags
        tagList.add(new TaskTag("Shopping", 0xFFF21202));
        tagList.add(new TaskTag("Homework", 0xFFE6F202));
        tagList.add(new TaskTag("Chores")); // Assuming you have a default color for this case
        tagList.add(new TaskTag("Family", 0xFF1202F2));
        tagList.add(new TaskTag("Meeting", 0xFF9C27B0));

        /*// Create an ArrayAdapter to display the tags in a ListView
        ArrayAdapter<TaskTag> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tagList);

        // Get the ListView from the layout and set the adapter
        ListView listView = findViewById(R.id.tagListView);
        listView.setAdapter(adapter);*/
        // Create TagAdapter and set it to the ListView
        TagAdapter tagAdapter = new TagAdapter(this, tagList);
        ListView listView = findViewById(R.id.tagListView);
        listView.setAdapter(tagAdapter);

        // Set an item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click
                TaskTag clickedTag = tagList.get(position);
                returnSelectedTag(clickedTag);
            }
        });

    }
    // Method to return the selected tag to the previous activity
    private void returnSelectedTag(TaskTag selectedTag) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedTag", selectedTag.getLabel());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}