package com.inti.student.androidassignment;

import java.io.Serializable;

public class User implements Serializable {

    private String userID;
    private String userEmail;
    private String userPassword;
    private String userContact;
    private String userName;
    private String userIC;
    private String userGender;
    private int userAge;

    public User(String userID, String userEmail, String userPassword, String userContact, String userName, String userIC, String userGender, int userAge) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userContact = userContact;
        this.userName = userName;
        this.userIC = userIC;
        this.userGender = userGender;
        this.userAge = userAge;
    }

    public User(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User(int userAge, String userContact, String userEmail, String userGender, String userIC, String userID, String userName, String userPassword)
    {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userContact = userContact;
        this.userName = userName;
        this.userIC = userIC;
        this.userGender = userGender;
        this.userAge = userAge;
    }

    public User(String userEmail, String userPassword, String userContact, String userName, String userIC, String userGender) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userContact = userContact;
        this.userName = userName;
        this.userIC = userIC;
        this.userGender = userGender;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIC() {
        return userIC;
    }

    public void setUserIC(String userIC) {
        this.userIC = userIC;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
