package com.example.moviezip.dao.mybatis.mapper;

import com.example.moviezip.domain.ScreenDetail;
import com.example.moviezip.domain.ScreenTime;
import com.example.moviezip.domain.Theater;
import com.example.moviezip.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TheaterMapper {

    List<Theater> getTheaterByLocation(@Param("location") String location);
    List<ScreenDetail> getScreeningDetails(@Param("movieId") Long movieId,
                                         @Param("screenDate") String screenDate,
                                         @Param("theaterName") String theaterName);
}
