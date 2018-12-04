package com.inti.student.androidassignment;

import java.util.Date;

public class BookingDetails {

    private String seatNo;
    private int seatQuatity;
    private double price;
    private String passengerName;
    private String passengerIC;
    private String passengerContact;
    private String userID;
    private String orderDate;
    private String orderStatus;
    private String scheduleID;

    public BookingDetails() {
    }

    public BookingDetails(String seatNo, int seatQuatity, double price, String passengerName, String passengerIC, String passengerContact, String userID, String orderDate, String orderStatus, String scheduleID) {
        this.seatNo = seatNo;
        this.seatQuatity = seatQuatity;
        this.price = price;
        this.passengerName = passengerName;
        this.passengerIC = passengerIC;
        this.passengerContact = passengerContact;
        this.userID = userID;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.scheduleID = scheduleID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public int getSeatQuatity() {
        return seatQuatity;
    }

    public void setSeatQuatity(int seatQuatity) {
        this.seatQuatity = seatQuatity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerIC() {
        return passengerIC;
    }

    public void setPassengerIC(String passengerIC) {
        this.passengerIC = passengerIC;
    }

    public String getPassengerContact() {
        return passengerContact;
    }

    public void setPassengerContact(String passengerContact) {
        this.passengerContact = passengerContact;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "BookingDetails{" +
                "seatNo='" + seatNo + '\'' +
                ", seatQuatity=" + seatQuatity +
                ", price=" + price +
                ", passengerName='" + passengerName + '\'' +
                ", passengerIC='" + passengerIC + '\'' +
                ", passengerContact='" + passengerContact + '\'' +
                ", userID='" + userID + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
