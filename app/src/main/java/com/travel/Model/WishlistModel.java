package com.travel.Model;

public class WishlistModel {
    private int wishlistId;
    private int userId;
    private int destinationId,tourId ,hotelId, restaurantId;

    public WishlistModel() {
    }

    public WishlistModel(int wishlistId, int userId, int destinationId, int tourId, int hotelId, int restaurantId) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.destinationId = destinationId;
        this.tourId = tourId;
        this.hotelId = hotelId;
        this.restaurantId = restaurantId;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
