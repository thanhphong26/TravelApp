package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.ReviewModel;
import com.travel.Model.ReviewType;
import com.travel.Model.TourBookingModel;
import com.travel.Model.TourBookingReviewModel;
import com.travel.Model.TourModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TourBookingDAO {
    DatabaseHelper databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;

    public TourBookingDAO() {
    }

    @SuppressLint("Range")
    //get all tour bookings from tour_booking table
    public List<TourBookingReviewModel> getAllTourBookingsWithReview(int userId) {
        List<TourBookingReviewModel> tourBookingModels = new ArrayList<>();
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                // get tour bookings by user id
                String query = "SELECT tour_bookings.*, reviews.*,tours.*\n" +
                        "FROM tour_bookings\n" +
                        "JOIN tours ON tour_bookings.tour_id = tours.tour_id\n" +
                        "JOIN reviews ON tours.tour_id = reviews.item_id\n" +
                        "WHERE reviews.review_type = 'tour' AND reviews.user_Id = 2;";
                Cursor cursor = database.rawQuery(query, null);
                System.out.println("cursor count: " + query);
                if (cursor.moveToFirst()) {
                    System.out.println("cursor count: " + cursor.getCount());
                    do {
                        TourBookingReviewModel tourBookingModel = new TourBookingReviewModel();
                        tourBookingModel.setBookingId(cursor.getInt(cursor.getColumnIndex("booking_id")));
                        tourBookingModel.setTour(new TourDAO().getTourById(cursor.getInt(cursor.getColumnIndex("tour_id"))));
                        tourBookingModel.setBookingDate(cursor.getString(cursor.getColumnIndex("booking_date")));
                        tourBookingModel.setTotalPrice(cursor.getFloat(cursor.getColumnIndex("total_price")));
                        tourBookingModel.setNumberOfAdults(cursor.getInt(cursor.getColumnIndex("number_of_adults")));
                        tourBookingModel.setNumberOfChildren(cursor.getInt(cursor.getColumnIndex("number_of_childs")));
                        tourBookingModel.setCreatedAt(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("created_at"))));

                        TourModel tour = new TourModel();
                        tour.setTourId(cursor.getInt(cursor.getColumnIndex("tour_id")));
                        tour.setName(cursor.getString(cursor.getColumnIndex("name")));
                        tour.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        tour.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        tour.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        tour.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        tourBookingModel.setTour(tour);

                        ReviewModel review = new ReviewModel();
                        review.setReviewId(cursor.getInt(cursor.getColumnIndex("review_id")));
                        review.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
//                        review.setReviewType(ReviewType.valueOf(cursor.getString(cursor.getColumnIndex("review_type"))));
                        review.setItemId(cursor.getInt(cursor.getColumnIndex("item_id")));
                        review.setReview(cursor.getString(cursor.getColumnIndex("review")));
                        review.setRating(cursor.getInt(cursor.getColumnIndex("rating")));

                        tourBookingModel.setReview(review);

                        tourBookingModels.add(tourBookingModel);
                    } while (cursor.moveToNext());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
        return tourBookingModels;
    }
}
