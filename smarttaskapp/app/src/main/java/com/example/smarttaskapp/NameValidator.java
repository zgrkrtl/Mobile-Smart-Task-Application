package com.example.smarttaskapp;

public class NameValidator {
    public boolean isValidName(String name) {

        return name != null && name.length() >= 3;
    }
}