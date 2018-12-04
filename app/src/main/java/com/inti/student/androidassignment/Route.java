package com.inti.student.androidassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class Route implements Parcelable {
    private String routeID;
    private String busName;
    private String boardingLocation;
    private String destinationLocation;
    private String boardingTime;
    private String destinationTime;

    public Route() {
    }

    public Route(String routeID, String busName, String boardingLocation, String destinationLocation, String boardingTime, String destinationTime) {
        this.routeID = routeID;
        this.busName = busName;
        this.boardingLocation = boardingLocation;
        this.destinationLocation = destinationLocation;
        this.boardingTime = boardingTime;
        this.destinationTime = destinationTime;
    }

    protected Route(Parcel in) {
        routeID = in.readString();
        busName = in.readString();
        boardingLocation = in.readString();
        destinationLocation = in.readString();
        boardingTime = in.readString();
        destinationTime = in.readString();
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBoardingLocation() {
        return boardingLocation;
    }

    public void setBoardingLocation(String boardingLocation) {
        this.boardingLocation = boardingLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(String boardingTime) {
        this.boardingTime = boardingTime;
    }

    public String getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        this.destinationTime = destinationTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(routeID);
        dest.writeString(busName);
        dest.writeString(boardingLocation);
        dest.writeString(destinationLocation);
        dest.writeString(boardingTime);
        dest.writeString(destinationTime);
    }
}
