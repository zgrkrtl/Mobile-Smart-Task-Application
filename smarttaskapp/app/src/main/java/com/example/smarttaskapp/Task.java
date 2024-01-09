package com.example.smarttaskapp;

import java.util.ArrayList;
import java.util.List;

//Eness El BEKAY ,Ö.Özgür KARTAL
public class Task {


    private String type;
    private int picId;
    //Label added
    private String label;




    public Task(String type, int picId, String label) {

        this.type = type;
        this.picId = picId;
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
