package com.example.moviezip.domain;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class ScreenTime implements Serializable {
    private Long screenTimeId;  // ScreenTime 테이블의 기본 키
    private Long movieId;       // Movie 테이블의 외래 키
    private Long screenId;      // Screens 테이블의 외래 키

    private String startTime;     // 상영 시작 시간
    private String endTime;       // 상영 종료 시간
    private Date screenDate;       // 상영 날짜
    private int remainingSeats;    // 잔여석

    public ScreenTime(Long movieId, Long screenId, String startTime, String endTime, Date screenDate, int remainingSeats) {
        this.movieId = movieId;
        this.screenId = screenId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.screenDate = screenDate;
        this.remainingSeats = remainingSeats;
    }
}
