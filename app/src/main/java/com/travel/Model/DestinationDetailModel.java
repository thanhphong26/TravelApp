package com.travel.Model;

import java.sql.Timestamp;

public class DestinationDetailModel extends DestinationModel {
    private float rating;
    private float minPrice;

    public DestinationDetailModel() {
    }

    public DestinationDetailModel(int destinationId, String name, String description, String image, Timestamp createdAt, float rating, float minPrice) {
        super(destinationId, name, description, image, createdAt);
        this.rating = rating;
        this.minPrice = minPrice;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }
}
