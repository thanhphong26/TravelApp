package com.travel.Model;

import java.sql.Timestamp;

public class HotelBookingReviewModel extends HotelBookingModel{
    ReviewModel review;

    public HotelBookingReviewModel() {
    }

    public HotelBookingReviewModel(int bookingId, HotelModel hotel, String bookingDate, float totalPrice, int numberOfAdults, int numberOfChildren, Timestamp createdAt, ReviewModel review) {
        super(bookingId, hotel, bookingDate, totalPrice, numberOfAdults, numberOfChildren, createdAt);
        this.review = review;
    }

    public ReviewModel getReview() {
        return review;
    }

    public void setReview(ReviewModel review) {
        this.review = review;
    }
}
