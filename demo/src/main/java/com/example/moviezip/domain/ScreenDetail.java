package com.example.moviezip.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ScreenDetail implements Serializable {
    private Long timeId;   // Unique ID for the screening time
    private String screenName;
    private String theaterName;  // Name of the screen/theater
    private int seats;      // Total seats in the theater
    private String startTime;    // Screening start time
    private String endTime;      // Screening end time
    private int remainingSeats;  // Remaining available seats

    // Constructors, getters, and setters
    public ScreenDetail() {}

    public ScreenDetail(Long timeId, String screenName, String theaterName, int seats, String startTime, String endTime, int remainingSeats) {
        this.timeId = timeId;
        this.screenName = screenName;
        this.theaterName = theaterName;
        this.seats = seats;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remainingSeats = remainingSeats;
    }
}
