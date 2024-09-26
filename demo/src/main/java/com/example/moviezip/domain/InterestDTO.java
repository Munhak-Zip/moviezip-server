package com.example.moviezip.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class InterestDTO {
    private Long userId;
    private String genre;

}
