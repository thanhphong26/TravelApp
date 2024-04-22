package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.HistoryRatingModel;
import com.travel.Model.TourModel;

import java.util.ArrayList;

public class HistoryRatingDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;
    HistoryRatingModel historyRatingModel;
    public Cursor getUserReviews(int userId) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String query = "SELECT tours.name, tours.description, tours.image, reviews.rating, reviews.review " +
                "FROM reviews JOIN tours ON reviews.item_id = tours.tour_id " +
                "WHERE reviews.user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        return database.rawQuery(query, selectionArgs);
    }
    public ArrayList<HistoryRatingModel> getReviewTour(int userId) {
        ArrayList<HistoryRatingModel> list = new ArrayList<>();
        Cursor cursor = getUserReviews(userId);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") String image = cursor.getString(cursor.getColumnIndex("image"));
            @SuppressLint("Range") float rating = cursor.getFloat(cursor.getColumnIndex("rating"));
            @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
            HistoryRatingModel historyRatingModel = new HistoryRatingModel(name, description, image, rating, review);
            list.add(historyRatingModel);
        }
        return list;
    }
}
