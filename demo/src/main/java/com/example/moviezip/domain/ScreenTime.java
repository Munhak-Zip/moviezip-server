package com.example.moviezip.domain;
import java.util.Date;

public class ScreenTime{
    private Long screenTimeId;  // ScreenTime 테이블의 기본 키
    private Long movieId;       // Movie 테이블의 외래 키
    private Long screenId;      // Screens 테이블의 외래 키

    private Date startTime;     // 상영 시작 시간
    private Date endTime;       // 상영 종료 시간
    private Date screenDate;       // 상영 종료 시간

    public ScreenTime(Long movieId, Long screenId, Date startTime, Date endTime, Date screenDate) {
        this.movieId = movieId;
        this.screenId = screenId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.screenDate = screenDate;
    }
}
