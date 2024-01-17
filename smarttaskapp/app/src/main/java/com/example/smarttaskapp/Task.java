package com.example.smarttaskapp;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Eness El BEKAY ,Ö.Özgür KARTAL
public class Task implements Serializable, Parcelable {


    private String type;
    //Label added
    private String label;
    private boolean isChecked = false;
    private String dueTo = "";

    public String getDueTo() {
        return dueTo;
    }

    public void setDueTo(String dueTo) {
        this.dueTo = dueTo;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Task() {
        //database
    }

    public Task(String type) {

        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.label);
        dest.writeString(this.dueTo);
    }

    public void readFromParcel(Parcel source) {
        this.type = source.readString();
        this.label = source.readString();
        this.dueTo = source.readString();
    }

    protected Task(Parcel in) {
        this.type = in.readString();
        this.label = in.readString();
        this.dueTo = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
