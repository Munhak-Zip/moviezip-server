package com.example.moviezip.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class Reservation extends Movie implements Serializable {
    private Long reserveId;
    private Long id;
    private Date dateR;
    private String seat;
    private String region;
    private Long price;

    public void setId(Long id) {
        this.id = id;
    }

    private String time;

    public Reservation() {
        super(); // 부모 클래스의 기본 생성자 호출
    }

    public Reservation(Long mvId, Long id, Date date, String seat, String time,String region,Long price) {
        super.setMvId(mvId);
        this.id = id;
        this.dateR = date;
        this.seat = seat;
        this.time = time;
        this.region = region;
        this.price = price;
    }

    public Reservation(Long mvId, Long id, Date date, String seat) {
        super.setMvId(mvId);
        this.id = id;
        this.dateR = date;
        this.seat = seat;
    }

    public Reservation(Long mvId, String mvTitle, Integer mvStar, String mvDetail, String mvDirector, String mvImg, Long reserveId, Long id, Date date, String seat) {
        super(mvId, mvTitle, mvStar, mvDetail, mvDirector, mvImg);
        this.reserveId = reserveId;
        this.id = id;
        this.dateR = date;
        this.seat = seat;
    }

    public Reservation(Date date, String seat, String movieTitle) {
        this.dateR = date;
        this.seat = seat;
        super.setMvTitle(movieTitle);
    }

    public void setReserveId(Long reserveId) {
        this.reserveId = reserveId;
    }

    public void setDateR(Date dateR) {
        this.dateR = dateR;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
