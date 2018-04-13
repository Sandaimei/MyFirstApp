package com.example.willie.myfirstapp;

public class UserProfile {

    public String First_Name;
    public String Last_Name;
    public String Email;
    public String Age;

    public UserProfile() {};

    public UserProfile(String First_Name, String Last_Name, String Email, String Age) {
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Email = Email;
        this.Age = Age;
    }

    public String getUserFirst_Name() {
        return First_Name;
    }

    public void setUserFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getUserLast_Name() {
        return Last_Name;
    }

    public void setUserLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getUserEmail() {
        return Email;
    }

    public void setUserEmail(String email) {
        Email = email;
    }

    public String getUserAge() {
        return Age;
    }

    public void setUserAge(String age) {
        Age = age;
    }
}
