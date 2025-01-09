package com.example.moviezip.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public int getCode() {
        return exceptionStatus.getCode();
    }

    public String getMessage() {
        return exceptionStatus.getMsg();
    }
}
