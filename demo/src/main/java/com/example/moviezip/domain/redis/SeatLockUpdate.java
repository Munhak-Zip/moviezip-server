package com.example.moviezip.domain.redis;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class SeatLockUpdate {
    private Long theater;
    private String seat;
    private String status;
    private Long screen;

    public SeatLockUpdate(String seat, Long theater, Long screen, String status) {
        this.seat = seat;
        this.theater = theater;
        this.status = status;
        this.screen = screen;
    }
}