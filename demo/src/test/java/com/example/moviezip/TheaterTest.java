package com.example.moviezip;
import com.example.moviezip.dao.TheaterDao;
import com.example.moviezip.dao.mybatis.MybatisTheaterDao;
import com.example.moviezip.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.example.moviezip.dao.mybatis.MybatisWishDao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TheaterTest {

    @Autowired
    private MybatisTheaterDao theaterDao;

    @Test // 좋아하는 영화
    public void getTheaterByLocation() throws Exception{
        String location = "서울";
        List<Theater> movie1 = theaterDao.getTheaterByLocation(location);
        System.out.println("서울 영화관 가져오기");
        for (Theater t : movie1) {
            System.out.println("Theater: " + t.getTheaterName());
            System.out.println("--------------------------");
        }
    }

    @Test // 좋아하는 영화
    public void getScrrenTime() throws Exception{
        String date = "24/10/04";
        Long movieId = 141L;
        // Convert the date string to the desired format (YYYY-MM-DD)
        SimpleDateFormat originalFormat = new SimpleDateFormat("yy/MM/dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date formattedDate = originalFormat.parse(date);
        String finalDate = targetFormat.format(formattedDate);

        String location = "강남";
        List<ScreenDetail> movie1 = theaterDao.getScreeningDetails(movieId,finalDate,location);
        System.out.println("상영관 가져오기");
        for (ScreenDetail t : movie1) {
            System.out.println("ScreenID: " + t.getTimeId());
            System.out.println("Theater: " + t.getScreenName());
            System.out.println("전체석: " + t.getSeats());
            System.out.println("잔여석: " + t.getRemainingSeats());
            System.out.println("시작시간: " + t.getStartTime());
            System.out.println("끝나는 시간: " + t.getEndTime());
            System.out.println("--------------------------");
        }
    }
}
