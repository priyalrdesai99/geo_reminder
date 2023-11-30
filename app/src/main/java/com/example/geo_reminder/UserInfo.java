package com.example.geo_reminder;

public class UserInfo {

    // string variable for
    // storing employee name.
    private String firstName;

    // string variable for storing
    // employee contact number
    private String lastName;
    private String phone;
    // string variable for storing
    // employee address.
    private String email;
    private String password;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public UserInfo() {

    }

    // created getter and setter methods
    // for all our variables.
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
