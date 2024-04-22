package com.travel.Model;

import java.sql.Timestamp;

public class TourBookingReviewModel extends TourBookingModel{
    ReviewModel review;

    public TourBookingReviewModel() {
    }

    public TourBookingReviewModel(int bookingId, TourModel tour, String bookingDate, float totalPrice, int numberOfAdults, int numberOfChildren, Timestamp createdAt, ReviewModel review) {
        super(bookingId, tour, bookingDate, totalPrice, numberOfAdults, numberOfChildren, createdAt);
        this.review = review;
    }

    public ReviewModel getReview() {
        return review;
    }

    public void setReview(ReviewModel review) {
        this.review = review;
    }
}
