package com.example.moviezip.controller;

import com.example.moviezip.domain.Movie;
import com.example.moviezip.service.MovieImpl;
import com.example.moviezip.service.recommend.MovieRecommenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/")
public class MovieController {
    private MovieImpl movieService;

    @Autowired
    public void setMovieService(MovieImpl movieService) {
        this.movieService = movieService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("movie/{mvId}")
    public Movie getMovie(@PathVariable int mvId) throws Exception {
        System.out.println("Entering getMovie method with mvId: " + mvId);
        Movie mv = movieService.getAllMoviedetail(mvId);
        if (mv != null) {
            System.out.println("영화제목222: " + mv.getMvTitle());
            System.out.println("영화제목: " + mv.getMvStar());
            System.out.println(mv.getMvDetail());
            System.out.println(mv.getMvId());
            System.out.println(mv.getOpenDate());
            System.out.println(mv.getMvDirector());
            System.out.println(mv.getGenre2());
        } else {
            System.out.println("영화 정보를 찾을 수 없습니다.");
        }
        return mv;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/main")
    public List<Movie> getRecentMovie() {
        List<Movie> movieList = movieService.getRecentMovie();
        for(Movie movie : movieList) {
            System.out.println("최신영화"+movie.getMvTitle());
        }
        return movieList;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("search/{mvTitle}")
    public List<Movie> getSearchMovie(@PathVariable String mvTitle) throws Exception {
        System.out.println("Entering getMovie method with mvTitle: " + mvTitle);
        List<Movie> movieList = movieService.searchMoviesByTitle(mvTitle);
        for(Movie mv : movieList) {
            System.out.println("검색제목검색: " + mv.getMvTitle());
            System.out.println("검색제목별점: " + mv.getMvStar());
            System.out.println(mv.getMvId());
            System.out.println(mv.getMvDirector());
        }
        return movieList;
    }
}