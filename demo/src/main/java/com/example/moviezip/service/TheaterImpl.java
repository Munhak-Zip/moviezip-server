package com.example.moviezip.service;

import com.example.moviezip.dao.mybatis.MybatisTheaterDao;
import com.example.moviezip.domain.ScreenDetail;
import com.example.moviezip.domain.ScreenTime;
import com.example.moviezip.domain.Theater;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterImpl {
    private final MybatisTheaterDao theaterDao;

    public TheaterImpl(MybatisTheaterDao theaterDao) {
        this.theaterDao = theaterDao;
    }

    public List<Theater> getTheaterByLocation(String location){
        return theaterDao.getTheaterByLocation(location);
    }

    public List<ScreenDetail> getScreeningDetails(@Param("movieId") Long movieId,
                                                @Param("screenDate") String screenDate,
                                                @Param("theaterName") String theaterName)
    {   return theaterDao.getScreeningDetails(movieId,screenDate,theaterName);  }

}
