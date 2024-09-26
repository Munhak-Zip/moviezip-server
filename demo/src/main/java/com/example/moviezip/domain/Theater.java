package com.example.moviezip.domain;

public class Theater {

    private Long theaterId;
    private String locationID;
    private String theaterName;

    public Theater(Long theaterId) {
        this.theaterId = theaterId;
    }


    public Theater(Long theaterId, String locationID, String theaterName) {
        this.theaterId = theaterId;
        this.locationID = locationID;
        this.theaterName = theaterName;
    }
}
