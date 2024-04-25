package com.travel.Model;

public enum ReviewType {
    TOUR("tour"),
    HOTEL("hotel"),
    RESTAURANT("restaurant");

    private final String type;

    ReviewType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
