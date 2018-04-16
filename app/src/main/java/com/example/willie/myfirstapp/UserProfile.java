package com.example.willie.myfirstapp;

public class UserProfile {

    private String First_Name;
    private String Last_Name;
    private String Age;
    private int highscore;

    public UserProfile() {};

    public UserProfile(String First_Name, String Last_Name, String Age, int highscore) {
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Age = Age;
        this.highscore = highscore;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
