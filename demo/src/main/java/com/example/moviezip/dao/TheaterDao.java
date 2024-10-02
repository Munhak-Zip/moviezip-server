package com.example.moviezip.dao;

import com.example.moviezip.domain.ScreenDetail;
import com.example.moviezip.domain.ScreenTime;
import com.example.moviezip.domain.Theater;
import com.example.moviezip.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface TheaterDao {

    List<Theater> getTheaterByLocation(String location) throws DataAccessException;

    List<ScreenDetail> getScreeningDetails(@Param("movieId") Long movieId,
                                         @Param("screenDate") String screenDate,
                                         @Param("theaterName") String theaterName) throws DataAccessException;
}
