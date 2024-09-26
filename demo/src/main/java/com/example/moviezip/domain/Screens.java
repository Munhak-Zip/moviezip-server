package com.example.moviezip.domain;

public class Screens extends Theater{
    private Long screenId;

    private Long seatNum;
    private String screenName;

    public Screens(Long theaterId, Long screenId, Long seatNum, String screenName) {
        super(theaterId);
        this.screenId = screenId;
        this.seatNum = seatNum;
        this.screenName = screenName;
    }

}
