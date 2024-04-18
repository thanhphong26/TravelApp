package com.travel.Model;

import java.sql.Timestamp;

public class ReviewModel {
    private int reviewId;
    private int userId;
    private ReviewType reviewType;
    private int itemId;
    private String review;
    private int rating;
    private Timestamp reviewDate;

    public ReviewModel() {
    }

    public ReviewModel(int reviewId, int userId, ReviewType reviewType, int itemId, String review, int rating, Timestamp reviewDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.reviewType = reviewType;
        this.itemId = itemId;
        this.review = review;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ReviewType getReviewType() {
        return reviewType;
    }

    public void setReviewType(ReviewType reviewType) {
        this.reviewType = reviewType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }
}
