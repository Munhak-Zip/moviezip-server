package com.example.moviezip.domain.jwt;

import lombok.Getter;

@Getter
public class AuthenticationResponse{

    private String accessToken;
    private String refreshToken;

    public AuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}