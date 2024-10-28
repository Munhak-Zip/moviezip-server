package com.example.moviezip.dao.mybatis;

import com.example.moviezip.dao.TheaterDao;
import com.example.moviezip.dao.mybatis.mapper.TheaterMapper;
import com.example.moviezip.domain.ScreenDetail;
import com.example.moviezip.domain.ScreenTime;
import com.example.moviezip.domain.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MybatisTheaterDao implements TheaterDao {
    @Autowired
    private TheaterMapper theaterMapper;
    @Override
    public List<Theater> getTheaterByLocation(String location) throws DataAccessException {
        return theaterMapper.getTheaterByLocation(location);
    }

    @Override
    public List<ScreenDetail> getScreeningDetails(Long movieId, String screenDate, String theaterName) throws DataAccessException {
        return theaterMapper.getScreeningDetails(movieId, screenDate, theaterName);
    }

    @Override
    public Long getTheaterId(String theater) throws DataAccessException {
        return theaterMapper.getTheaterId(theater);
    }

    @Override
    public Long getScreenTimeId(String starttime, String screenDate, Long movieId) throws DataAccessException {
        return theaterMapper.getScreenTimeId(starttime, screenDate, movieId);
    }


}
