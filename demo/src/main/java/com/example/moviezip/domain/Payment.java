package com.example.moviezip.domain;

import java.util.Date;

public class Payment {
    private Long paymentId;
    private Date paymentTime;
    private Long price;
    private Long reservationId;

    public Payment(Long paymentId, Date paymentTime, Long price, Long reservationId) {
        this.paymentId = paymentId;
        this.paymentTime = paymentTime;
        this.price = price;
        this.reservationId = reservationId;
    }
}
