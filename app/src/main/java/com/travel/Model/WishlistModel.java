package com.travel.Model;

public class WishlistModel {
    private int wishlistId;
    private UserModel user;
    private DestinationModel destination;
    private HotelModel hotel;
    private TourModel tour;
    private RestaurantModel restaurant;

    public WishlistModel() {
    }

    public WishlistModel(int wishlistId, UserModel user, DestinationModel destination, HotelModel hotel, TourModel tour, RestaurantModel restaurant) {
        this.wishlistId = wishlistId;
        this.user = user;
        this.destination = destination;
        this.hotel = hotel;
        this.tour = tour;
        this.restaurant = restaurant;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public DestinationModel getDestination() {
        return destination;
    }

    public void setDestination(DestinationModel destination) {
        this.destination = destination;
    }

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }

    public TourModel getTour() {
        return tour;
    }

    public void setTour(TourModel tour) {
        this.tour = tour;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }
}
