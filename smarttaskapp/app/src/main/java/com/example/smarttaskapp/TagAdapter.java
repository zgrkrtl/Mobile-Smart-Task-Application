package com.example.smarttaskapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TagAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<TaskTag> tagList;

    public TagAdapter(Activity activity, List<TaskTag> tagList) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tagList = tagList;
    }

    @Override
    public int getCount() {
        return tagList.size();
    }

    @Override
    public Object getItem(int position) {
        return tagList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            // Inflate the layout for each item in the list
            view = layoutInflater.inflate(R.layout.tag_list_item, parent, false);
        }

        // Get the current tag object
        TaskTag currentTag = tagList.get(position);

        // Set the data for the item
        TextView tagNameTextView = view.findViewById(R.id.tagNameTextView);
        tagNameTextView.setText(currentTag.getLabel());
        tagNameTextView.setBackgroundColor(currentTag.getColor());



        // You can add more views and set their data here


        return view;
    }
}
