package com.travel.Model;

public class TourPricingModel {
    private int pricingId;
    private TourModel tour;
    private float adultPrice, childPrice;

    public TourPricingModel() {
    }

    public TourPricingModel(int pricingId, TourModel tour, float adultPrice, float childPrice) {
        this.pricingId = pricingId;
        this.tour = tour;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
    }

    public TourPricingModel(TourModel tour, float adultPrice, float childPrice) {
        this.tour = tour;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
    }

    public int getPricingId() {
        return pricingId;
    }

    public void setPricingId(int pricingId) {
        this.pricingId = pricingId;
    }

    public TourModel getTour() {
        return tour;
    }

    public void setTour(TourModel tour) {
        this.tour = tour;
    }

    public float getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(float adultPrice) {
        this.adultPrice = adultPrice;
    }

    public float getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(float childPrice) {
        this.childPrice = childPrice;
    }
}
