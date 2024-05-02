package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.ReviewModel;
import com.travel.Model.ReviewType;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;

    public ReviewDAO() {
    }
    public List<ReviewModel> getReviewsForTour(int tourId) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        ArrayList<ReviewModel> reviewList = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM reviews LEFT JOIN tour_bookings ON reviews.item_id = tour_bookings.booking_id WHERE reviews.review_type = ? AND tour_bookings.hotel_id = ?;";
            cursor = database.rawQuery(query, new String[]{ReviewType.TOUR.toString(), String.valueOf(tourId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int reviewId = cursor.getInt(cursor.getColumnIndex("review_id"));
                    @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                    @SuppressLint("Range") String reviewType = cursor.getString(cursor.getColumnIndex("review_type"));
                    @SuppressLint("Range") int itemId = cursor.getInt(cursor.getColumnIndex("item_id"));
                    @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
                    @SuppressLint("Range") int rating = cursor.getInt(cursor.getColumnIndex("rating"));
                    @SuppressLint("Range") String dateTimeString = cursor.getString(cursor.getColumnIndex("reviewDate"));
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = inputFormat.parse(dateTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date != null) {
                        Timestamp reviewDate = new Timestamp(date.getTime());
                        ReviewModel reviewModel = new ReviewModel(reviewId, userId, ReviewType.TOUR, itemId, review, rating, reviewDate);
                        reviewList.add(reviewModel);
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return reviewList;
    }

    public void addReview(int id, String reviewType, int userId, float rating, String comment) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String sql = "INSERT INTO reviews (user_id, review_type, item_id, review, rating, reviewDate) VALUES (?, ?, ?, ?, ?, ?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        database.execSQL(sql, new Object[]{userId, reviewType, id, comment, rating, date});
    }
    public List<ReviewModel> getReviewsForHotel(int hotelId) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        ArrayList<ReviewModel> reviewList = new ArrayList<>();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM reviews LEFT JOIN hotel_bookings ON reviews.item_id = hotel_bookings.booking_id WHERE reviews.review_type = ? AND hotel_bookings.hotel_id = ?;";
            cursor = database.rawQuery(query, new String[]{ReviewType.HOTEL.toString(), String.valueOf(hotelId)});
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int reviewId = cursor.getInt(cursor.getColumnIndex("review_id"));
                    @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                    @SuppressLint("Range") String reviewType = cursor.getString(cursor.getColumnIndex("review_type"));
                    @SuppressLint("Range") int itemId = cursor.getInt(cursor.getColumnIndex("item_id"));
                    @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
                    @SuppressLint("Range") int rating = cursor.getInt(cursor.getColumnIndex("rating"));
                    @SuppressLint("Range") String dateTimeString = cursor.getString(cursor.getColumnIndex("reviewDate"));
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = inputFormat.parse(dateTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date != null) {
                        Timestamp reviewDate = new Timestamp(date.getTime());
                        ReviewModel reviewModel = new ReviewModel(reviewId, userId, ReviewType.HOTEL, itemId, review, rating, reviewDate);
                        reviewList.add(reviewModel);
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return reviewList;
    }
    public List<ReviewModel> getReviewsForRestaurant(int restaurantId) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        ArrayList<ReviewModel> reviewList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query("reviews",
                    new String[]{"review_id", "user_id", "review_type", "item_id", "review", "rating", "reviewDate"},
                    "review_type='restaurant' and item_id = ?",
                    new String[]{String.valueOf(restaurantId)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int reviewId = cursor.getInt(cursor.getColumnIndex("review_id"));
                    @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                    @SuppressLint("Range") String reviewType = cursor.getString(cursor.getColumnIndex("review_type"));
                    @SuppressLint("Range") int itemId = cursor.getInt(cursor.getColumnIndex("item_id"));
                    @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
                    @SuppressLint("Range") int rating = cursor.getInt(cursor.getColumnIndex("rating"));
                    @SuppressLint("Range") String dateTimeString = cursor.getString(cursor.getColumnIndex("reviewDate"));
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = inputFormat.parse(dateTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date != null) {
                        Timestamp reviewDate = new Timestamp(date.getTime());
                        ReviewModel reviewModel = new ReviewModel(reviewId, userId, ReviewType.RESTAURANT, itemId, review, rating, reviewDate);
                        reviewList.add(reviewModel);
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return reviewList;
    }
}
