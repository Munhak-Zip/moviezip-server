package com.example.moviezip.controller;

import com.example.moviezip.domain.*;
import com.example.moviezip.service.TheaterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class TheaterController {
    @Autowired
    private TheaterImpl theater;

    @GetMapping("/theater/location/{locationId}")
    public List<Theater> getTheaterByLocation(@PathVariable int locationId) {
        // 숫자를 지역 이름으로 매핑
        String location;
        switch (locationId) {
            case 0:
                location = "서울";
                break;
            case 1:
                location = "경기";
                break;
            case 2:
                location = "인천";
                break;
            case 3:
                location = "강원";
                break;
            case 4:
                location = "대전/충청";
                break;
            case 5:
                location = "대구";
                break;
            case 6:
                location = "부산/울산";
                break;
            case 7:
                location = "경상";
                break;
            case 8:
                location = "광주/전라/제주";
                break;
            default:
                throw new IllegalArgumentException("Invalid location ID: " + locationId);
        }

        List<Theater> theaterLoc = theater.getTheaterByLocation(location);

        for (Theater t : theaterLoc) {
            System.out.println(location + "의 영화관 : " + t.getTheaterName());
        }
        return theaterLoc;
    }

    @GetMapping("/screenings")
    public List<ScreenDetail> getScreeningDetails(@RequestParam Long movieId,
                                                  @RequestParam String screenDate,
                                                  @RequestParam String theaterName) {

        List<ScreenDetail> sd = theater.getScreeningDetails(movieId, screenDate, theaterName);

        System.out.println(screenDate + "날짜!!");

        for (ScreenDetail t : sd) {
            System.out.println("ScreenID: " + t.getTimeId());
            System.out.println("Theater: " + t.getScreenName());
            System.out.println("전체석: " + t.getSeats());
            System.out.println("잔여석: " + t.getRemainingSeats());
            System.out.println("시작시간: " + t.getStartTime());
            System.out.println("끝나는 시간: " + t.getEndTime());
            System.out.println("--------------------------");
        }
        return sd;
    }
}
