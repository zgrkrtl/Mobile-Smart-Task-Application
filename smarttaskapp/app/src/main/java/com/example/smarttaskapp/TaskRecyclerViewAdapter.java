package com.example.smarttaskapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.MyViewHolder>{
    Context context;
    List<Task> taskList;

    private final TaskRecyclerViewInterface taskRecyclerViewInterface;
    public TaskRecyclerViewAdapter(Context context, List<Task> taskList,TaskRecyclerViewInterface taskRecyclerViewInterface){
        this.context = context;
        this.taskList = taskList;
        this.taskRecyclerViewInterface = taskRecyclerViewInterface;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_taskrecyclerview_row,parent,false);
        return new MyViewHolder(view,taskRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.checkBox.setChecked(taskList.get(position).isChecked());
        holder.textViewHeader.setText(taskList.get(position).getType());
        holder.textViewLabel.setText(taskList.get(position).getLabel());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        TextView textViewHeader,textViewLabel;
        public MyViewHolder(@NonNull View itemView,TaskRecyclerViewInterface taskRecyclerViewInterface) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            textViewHeader = itemView.findViewById(R.id.label);
            textViewLabel = itemView.findViewById(R.id.coloredLabel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (taskRecyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            taskRecyclerViewInterface.onTaskClick(position);
                        }
                    }
                }
            });
        }
    }
}
