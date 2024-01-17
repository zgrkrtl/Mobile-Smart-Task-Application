package com.example.smarttaskapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;
//Eness El BEKAY
public class TaskAdapter   extends BaseAdapter {
    private List<Task> taskList;
    private LayoutInflater layoutInflater;

    public ArrayList<String> labels = new ArrayList<>();
    public ArrayList<Integer> colors = new ArrayList<>();

    public TaskAdapter(Activity activity, List<Task> taskList) {
        this.taskList = taskList;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = layoutInflater.inflate(R.layout.activity_tasklistview_row, null);
        TextView textView = rowView.findViewById(R.id.label);
        ImageView imageView = rowView.findViewById(R.id.pic);
        //CardView added
        CardView cardView = rowView.findViewById(R.id.TaskCard);
        TextView labelColor = rowView.findViewById(R.id.coloredLabel);


        Task task = taskList.get(position);
        textView.setText(task.getType());
        imageView.setImageResource(task.getPicId());
        //Label coloring handled
        int colorhexadecimal = LabelColoring(labels, colors,task);
        labelColor.setText(task.getLabel());
        labelColor.setTextColor(colorhexadecimal);

        // CLİCK LİSTENER on the check boxes
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = R.drawable.cheked_box;
                int unChecked = R.drawable.uncheked_box;
                // Log an informational message
                Log.i("MyAppOnClick", "clicked");
                // Set the updated Bitmap to the ImageView
                if (task.getPicId() == checked) {
                    imageView.setImageResource(unChecked);
                    task.setPicId(unChecked);
                } else {
                    imageView.setImageResource(checked);
                    task.setPicId(checked);
                }
            }
        });
        //Listener Changed text to card
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(layoutInflater.getContext(), ViewTaskActivity.class);
                intent.putExtra("taskType", task.getType());
                layoutInflater.getContext().startActivity(intent);


            }
        });

        return rowView;
    }

    //Ö. Özgür KARTAL
    public int LabelColoring(ArrayList<String> labels, ArrayList<Integer> colors, Task task) {
        int color = 0xFFF21202;
        labels.add("Shopping");
        labels.add("Homework");
        labels.add("Chores");
        labels.add("Family");
        labels.add("Meeting");
        labels.add("Healthcare");
        colors.add(0xFFF21202); // Red color
        colors.add(0xFFE6F202); // Yellow color
        colors.add(0xFF02F202); // Green color
        colors.add(0xFF1202F2); // Blue color
        colors.add(0xFFF2029A); // Pink color
        colors.add(0xFF9C27B0); // Purple color
        for (int i = 0; i < labels.size(); i++) {
            if(task.getLabel().equals("Shopping")){
                color =0xFFF21202;
            }
            else if(task.getLabel().equals("Homework")){
                color =0xFFE6F202;
            }
            else if(task.getLabel().equals("Chores")){
                color =0xFF02F202;
            }
            else if(task.getLabel().equals("Family")){
                color =0xFF1202F2;
            }
            else if(task.getLabel().equals("Meeting")){
                color =0xFFF2029A;
            }
            else if(task.getLabel().equals("Healthcare")){
                color =0xFF9C27B0;
            }
        }
        return color;
    }

}
