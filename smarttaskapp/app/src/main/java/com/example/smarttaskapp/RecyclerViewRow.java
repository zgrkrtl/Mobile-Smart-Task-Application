package com.example.smarttaskapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//Eness El BEKAY
public class RecyclerViewRow extends AppCompatActivity {
    ImageView checkbox;
    TextView taskHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskrecyclerview_row);

    }
}