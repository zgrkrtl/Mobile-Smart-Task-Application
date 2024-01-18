package com.example.smarttaskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//Eness El BEKAY
public class ViewTaskActivity extends AppCompatActivity {
    private static final int YOUR_REQUEST_CODE = 2;
    private boolean taskChangedStatus = false;
    TextInputEditText taskTitle;
    TextView save;
    ImageView goBack;
    Button labelbtn;
    private Button dateButton;
    private Calendar selectedDate;
    final List<TaskTag> tagList = new ArrayList<TaskTag>();
    Task task;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        task= (Task) getIntent().getSerializableExtra("task");
        int position = getIntent().getIntExtra("taskPosition", -1);


        taskTitle = findViewById(R.id.taskheaderinput);
        taskTitle.setText(task.getType());

        labelbtn = findViewById(R.id.tagbtn);
        labelbtn.setText(task.getLabel());

        dateButton = findViewById(R.id.datebtn);
        dateButton.setText(task.getDueTo());
        if (dateButton.getText().equals(""))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateButton.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm a")));
                task.setDueTo(dateButton.getText().toString());
            }

        save = findViewById(R.id.savebtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitleStr = taskTitle.getText().toString();
                taskChangedStatus = taskChangedStatus || !(taskTitle.getText().toString().equals(task.getType()));
                if (!taskTitleStr.equals("") && taskChangedStatus){
                    Log.i("Save button", "taskTitle :  " + taskTitle.getText().toString());
                    task.setType(taskTitleStr);
                    task.setLabel(labelbtn.getText().toString());
                    task.setDueTo(dateButton.getText().toString());
                    Log.i("Save button", "taskType :  " + task.getType());
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedTask", (Serializable) task);
                    resultIntent.putExtra("taskPosition", position);
                    setResult(RESULT_OK, resultIntent);
                    finish();

                }
            }
        });



        Button labelbtn = findViewById(R.id.tagbtn);
        labelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*tagList.add(new TaskTag("Shopping",0xFFF21202));
                tagList.add(new TaskTag("Homework",0xFFE6F202));
                tagList.add(new TaskTag("Chores"));
                tagList.add(new TaskTag("Family",0xFF1202F2));
                tagList.add(new TaskTag("Meeting",0xFF9C27B0));

                final ListView listView = findViewById(R.id.tagListView);
                TagAdapter tagAdapter = new TagAdapter(ViewTaskActivity.this, tagList);
                listView.setAdapter(tagAdapter);*/
                Log.i("tagbutton", "onClick: ");
                Intent intent = new Intent(ViewTaskActivity.this,UpdateLabelActivity.class);
                Log.i("tagbutton", "onClick: ");
                startActivityForResult(intent,YOUR_REQUEST_CODE);

            }
        });


        // Set onClickListener for the date button
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        goBack = findViewById(R.id.gobackbtn);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult", "clicked");
        if (requestCode == YOUR_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.i("onActivityResult", "Received result from ViewTaskActivity");
            String updatedTag = (String) data.getStringExtra("selectedTag");
            labelbtn.setText(updatedTag);
            taskChangedStatus = true;
        } else {
            Log.e("onActivityResult", "Invalid requestCode or resultCode");
        }
    }
    // Method to show the date picker dialog
    private void showDatePickerDialog() {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Show time picker after selecting the date
                showTimePickerDialog();
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    // Method to show the time picker dialog
    private void showTimePickerDialog() {
        if (selectedDate != null) {
            int hour = selectedDate.get(Calendar.HOUR_OF_DAY);
            int minute = selectedDate.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDate.set(Calendar.MINUTE, minute);

                    // Update the dateButton text with the selected date and time
                    updateDateButtonText();
                }
            }, hour, minute, false);

            timePickerDialog.show();
        } else {
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to update the dateButton text with the selected date and time
    private void updateDateButtonText() {
        if (selectedDate != null) {
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String dateString = dateFormat.format(selectedDate.getTime());
            dateButton.setText(dateString);
            task.setDueTo(dateString);
            taskChangedStatus = true;
        }
    }
}