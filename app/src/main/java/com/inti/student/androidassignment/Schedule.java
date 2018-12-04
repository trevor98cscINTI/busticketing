package com.inti.student.androidassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedule implements Parcelable {

    private String scheduleID;
    private String scheduleDate;
    private String routeID;
    private int totalSeats;
    private String scheduleStatus;

    public Schedule() {

    }

    public Schedule(String scheduleID, String scheduleDate, String routeID, int totalSeats, String scheduleStatus) {
        this.scheduleID = scheduleID;
        this.scheduleDate = scheduleDate;
        this.routeID = routeID;
        this.totalSeats = totalSeats;
        this.scheduleStatus = scheduleStatus;
    }

    protected Schedule(Parcel in) {
        scheduleID = in.readString();
        scheduleDate = in.readString();
        routeID = in.readString();
        totalSeats = in.readInt();
        scheduleStatus = in.readString();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleID='" + scheduleID + '\'' +
                ", scheduleDate='" + scheduleDate + '\'' +
                ", routeID='" + routeID + '\'' +
                ", totalSeats=" + totalSeats +
                ", scheduleStatus='" + scheduleStatus + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(scheduleID);
        dest.writeString(scheduleDate);
        dest.writeString(routeID);
        dest.writeInt(totalSeats);
        dest.writeString(scheduleStatus);
    }
}
