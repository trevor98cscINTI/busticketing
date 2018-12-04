package com.inti.student.androidassignment;

public class Seat {
    private String seatNo;

    public Seat(String seatNo) {
        this.seatNo = seatNo;
    }

    public Seat() {
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNo='" + seatNo + '\'' +
                '}';
    }
}
