package com.travel.Model;

import java.sql.Timestamp;

public class RestaurantBookingReviewModel extends RestaurantBookingModel{
    ReviewModel review;

    public RestaurantBookingReviewModel() {
    }

    public RestaurantBookingReviewModel(int bookingId, RestaurantModel restaurant, String bookingDate, float totalPrice, int numberOfAdults, int numberOfChildren, Timestamp createdAt, ReviewModel review) {
        super(bookingId, restaurant, bookingDate, totalPrice, numberOfAdults, numberOfChildren, createdAt);
        this.review = review;
    }

    public ReviewModel getReview() {
        return review;
    }

    public void setReview(ReviewModel review) {
        this.review = review;
    }
}
