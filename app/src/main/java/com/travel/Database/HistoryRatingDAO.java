package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.HistoryRatingModel;

import java.util.ArrayList;

public class HistoryRatingDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;
    HistoryRatingModel historyRatingModel;

    public Cursor getReviewTourByUserId(int userId) {
        database=databaseHelper.openDatabase();
        String query = "SELECT tours.name, tours.description, tours.image, reviews.rating, reviews.review,reviews.reviewDate " +
                "FROM reviews JOIN tours ON reviews.item_id = tours.tour_id " +
                "WHERE reviews.user_id = ? and reviews.review_type='tour'";
        String[] selectionArgs = {String.valueOf(userId)};
        return database.rawQuery(query, selectionArgs);
    }
    public ArrayList<HistoryRatingModel> getReviewTour(int userId) {
        ArrayList<HistoryRatingModel> list = new ArrayList<>();
        Cursor cursor = getReviewTourByUserId(userId);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex("image"));
            @SuppressLint("Range") float rating = cursor.getFloat(cursor.getColumnIndex("rating"));
            @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("reviewDate"));
            HistoryRatingModel historyRatingModel = new HistoryRatingModel(name, description, image, rating, review,date);
            list.add(historyRatingModel);
        }
        return list;
    }
    public Cursor getReviewHotelByUserId(int userId) {
        database=databaseHelper.openDatabase();
        String query = "SELECT hotels.name, hotels.description, hotels.image, reviews.rating, reviews.review,reviews.reviewDate " +
                "FROM reviews JOIN hotels ON reviews.item_id = hotels.hotel_id " +
                "WHERE reviews.user_id = ? and reviews.review_type='hotel'";
        String[] selectionArgs = {String.valueOf(userId)};
        return database.rawQuery(query, selectionArgs);
    }
    public ArrayList<HistoryRatingModel> getReviewHotel(int userId) {
        ArrayList<HistoryRatingModel> list = new ArrayList<>();
        Cursor cursor = getReviewHotelByUserId(userId);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex("image"));
            @SuppressLint("Range") float rating = cursor.getFloat(cursor.getColumnIndex("rating"));
            @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("reviewDate"));
            HistoryRatingModel historyRatingModel = new HistoryRatingModel(name, description, image, rating, review,date);
            list.add(historyRatingModel);
        }
        return list;
    }
    public Cursor getReviewRestaurantByUserId(int userId) {
        database=databaseHelper.openDatabase();
        String query = "SELECT restaurant.name, restaurant.description, restaurant.image, reviews.rating, reviews.review,reviews.reviewDate " +
                "FROM reviews JOIN restaurant ON reviews.item_id = restaurant.restaurant_id " +
                "WHERE reviews.user_id = ? and reviews.review_type='restaurant'";
        String[] selectionArgs = {String.valueOf(userId)};
        return database.rawQuery(query, selectionArgs);
    }
    public ArrayList<HistoryRatingModel> getReviewRestaurant(int userId) {
        ArrayList<HistoryRatingModel> list = new ArrayList<>();
        Cursor cursor = getReviewRestaurantByUserId(userId);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex("image"));
            @SuppressLint("Range") float rating = cursor.getFloat(cursor.getColumnIndex("rating"));
            @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("reviewDate"));
            HistoryRatingModel historyRatingModel = new HistoryRatingModel(name, description, image, rating, review, date);
            list.add(historyRatingModel);
        }
        return list;
    }

}
