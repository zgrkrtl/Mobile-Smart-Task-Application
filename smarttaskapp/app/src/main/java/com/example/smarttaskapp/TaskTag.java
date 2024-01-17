package com.example.smarttaskapp;

public class TaskTag {
    private String label;
    private int color =0xFF02F202;// green defaults color

    public TaskTag(String label, int color) {
        this.label = label;
        this.color = color;
    }

    public TaskTag(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
