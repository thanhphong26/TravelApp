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
        String query = "SELECT tours.name, tours.description, tours.image, A1.rating, A1.review, A1.reviewDate \n" +
                "                FROM tours \n" +
                "                JOIN (\n" +
                "                   SELECT tour_bookings.booking_id,tour_bookings.tour_id, reviews.rating, reviews.review, reviews.reviewDate \n" +
                "                    FROM reviews \n" +
                "                    JOIN tour_bookings ON reviews.item_id = tour_bookings.booking_id \n" +
                "                    WHERE reviews.user_id = ? and review_type=\"tour\"\n" +
                "                ) AS A1 \n" +
                "                ON A1.tour_id = tours.tour_id;";
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
        String query = "SELECT hotels.name, hotels.description, hotels.image, A1.rating, A1.review, A1.reviewDate \n" +
                "                FROM hotels\n" +
                "                JOIN (\n" +
                "                   SELECT hotel_bookings.booking_id,hotel_bookings.hotel_id, reviews.rating, reviews.review, reviews.reviewDate \n" +
                "                    FROM reviews \n" +
                "                    JOIN hotel_bookings ON reviews.item_id = hotel_bookings.booking_id \n" +
                "                    WHERE reviews.user_id = ? and review_type=\"hotel\"\n" +
                "                ) AS A1 \n" +
                "                ON A1.hotel_id = hotels.hotel_id;";
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
        String query = "SELECT restaurant.name, restaurant.description, restaurant.image, A1.rating, A1.review, A1.reviewDate\n" +
                "       FROM restaurant\n" +
                "                      JOIN (\n" +
                "                       SELECT restaurant_bookings.booking_id,restaurant_bookings.restaurant_id, reviews.rating, reviews.review, reviews.reviewDate\n" +
                "                              FROM reviews \n" +
                "                              JOIN restaurant_bookings ON reviews.item_id = restaurant_bookings.booking_id \n" +
                "                                 WHERE reviews.user_id = ? and review_type=\"restaurant\"\n" +
                "                               ) AS A1 \n" +
                "                               ON A1.restaurant_id = restaurant.restaurant_id; ";
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
