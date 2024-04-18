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
            cursor = database.query("reviews",
                    new String[]{"review_id", "user_id", "review_type", "item_id", "review", "rating", "reviewDate"},
                    "review_type='tour' and item_id = ?",
                    new String[]{String.valueOf(tourId)},
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
}
