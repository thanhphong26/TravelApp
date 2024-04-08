package com.travel.Model;

import java.sql.Time;

public class TourLineModel {
    private int itineraryId;
    private TourModel tour;
    private String locationName;
    private Time time, endTime;
    private String image;
    private float latitude, longitude;

    public TourLineModel() {
    }

    public TourLineModel(int itineraryId, TourModel tour, String locationName, Time time, Time endTime, String image, float latitude, float longitude) {
        this.itineraryId = itineraryId;
        this.tour = tour;
        this.locationName = locationName;
        this.time = time;
        this.endTime = endTime;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TourLineModel(TourModel tour, String locationName, Time time, Time endTime, String image, float latitude, float longitude) {
        this.tour = tour;
        this.locationName = locationName;
        this.time = time;
        this.endTime = endTime;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
    }

    public TourModel getTour() {
        return tour;
    }

    public void setTour(TourModel tour) {
        this.tour = tour;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
