package com.travel.Model;

public class HistoryRatingModel {
    private String name;
    private String description;
    private String image;
    private float rating;
    private String review;
    private String date;

    public HistoryRatingModel() {
    }

    public HistoryRatingModel(String name, String description, String image, float rating, String review, String date) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.review = review;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
