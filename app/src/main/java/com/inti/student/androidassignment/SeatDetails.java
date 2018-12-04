package com.inti.student.androidassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class SeatDetails implements Parcelable {

    private String seatID;
    private String scheduleID;

    public SeatDetails() {
    }

    public SeatDetails(String seatID, String scheduleID) {
        this.seatID = seatID;
        this.scheduleID = scheduleID;

    }

    protected SeatDetails(Parcel in) {
        seatID = in.readString();
        scheduleID = in.readString();
    }

    public static final Creator<SeatDetails> CREATOR = new Creator<SeatDetails>() {
        @Override
        public SeatDetails createFromParcel(Parcel in) {
            return new SeatDetails(in);
        }

        @Override
        public SeatDetails[] newArray(int size) {
            return new SeatDetails[size];
        }
    };

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(seatID);
        dest.writeString(scheduleID);
    }
}
