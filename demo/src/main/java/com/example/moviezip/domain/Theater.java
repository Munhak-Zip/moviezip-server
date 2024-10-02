package com.example.moviezip.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Theater implements Serializable {

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
