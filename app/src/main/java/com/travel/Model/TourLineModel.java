package com.travel.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;

public class TourLineModel implements Parcelable {
    private int itineraryId;
    private int tourId;
    private String locationName;
    private Time time, endTime;
    private String image;
    private float latitude, longitude;

    public TourLineModel() {
    }

    public TourLineModel(int itineraryId, int tourId, String locationName, Time time, Time endTime, String image, float latitude, float longitude) {
        this.itineraryId = itineraryId;
        this.tourId = tourId;
        this.locationName = locationName;
        this.time = time;
        this.endTime = endTime;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TourLineModel(int tourId, String locationName, Time time, Time endTime, String image, float latitude, float longitude) {
        this.tourId = tourId;
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

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
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

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<TourLineModel> CREATOR = new Creator<TourLineModel>() {
        @Override
        public TourLineModel createFromParcel(Parcel in) {
            return new TourLineModel(in);
        }

        @Override
        public TourLineModel[] newArray(int size) {
            return new TourLineModel[size];
        }
    };
    private TourLineModel(Parcel in) {
        itineraryId = in.readInt();
        tourId = in.readInt();
        locationName = in.readString();
        time = (Time) in.readSerializable();
        endTime = (Time) in.readSerializable();
        image = in.readString();
        latitude = in.readFloat();
        longitude = in.readFloat();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(itineraryId);
        dest.writeInt(tourId);
        dest.writeString(locationName);
        dest.writeSerializable(time);
        dest.writeSerializable(endTime);
        dest.writeString(image);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }
}
