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
import com.travel.Model.ReviewType;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        Cursor cursor = null;
        if (database != null) {
            try {
                // get tour bookings by user id
                String query = "SELECT tour_bookings.*, reviews.*,tours.* FROM tour_bookings JOIN tours ON tour_bookings.tour_id = tours.tour_id LEFT JOIN reviews ON tours.tour_id = reviews.item_id AND reviews.review_type = ? WHERE tour_bookings.user_Id = ?;";
                cursor = database.rawQuery(query, new String[]{ReviewType.TOUR.toString(), String.valueOf(userId)});
                if (cursor.moveToFirst()) {
                    do {
                        TourBookingReviewModel tourBookingModel = new TourBookingReviewModel();
                        tourBookingModel.setBookingId(cursor.getInt(cursor.getColumnIndex("booking_id")));
                        tourBookingModel.setTour(new TourDAO().getTourById(cursor.getInt(cursor.getColumnIndex("tour_id"))));
                        tourBookingModel.setBookingDate(cursor.getString(cursor.getColumnIndex("booking_date")));
                        tourBookingModel.setTotalPrice(cursor.getFloat(cursor.getColumnIndex("total_price")));
                        tourBookingModel.setNumberOfAdults(cursor.getInt(cursor.getColumnIndex("number_of_adults")));
                        tourBookingModel.setNumberOfChildren(cursor.getInt(cursor.getColumnIndex("number_of_childs")));
                        String timestampString = cursor.getString(cursor.getColumnIndex("created_at"));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            Date parsedDate = dateFormat.parse(timestampString);
                            long timeInMillis = parsedDate.getTime();
                            Timestamp timestamp = new Timestamp(timeInMillis);
                            tourBookingModel.setCreatedAt(timestamp);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


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

    @SuppressLint("Range")
    //get all tour bookings from tour_booking table
    public TourBookingReviewModel getBookingById(int bookingId) {
        TourBookingReviewModel tourBookingModel = new TourBookingReviewModel();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                // get tour bookings by user id
                String query = "SELECT tour_bookings.*, reviews.*,tours.* FROM tour_bookings JOIN tours ON tour_bookings.tour_id = tours.tour_id LEFT JOIN reviews ON tours.tour_id = reviews.item_id AND reviews.review_type = ? WHERE tour_bookings.booking_id = ?;";
                cursor = database.rawQuery(query, new String[]{ReviewType.TOUR.toString(), String.valueOf(bookingId)});
                if (cursor.moveToFirst()) {
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
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
        return tourBookingModel;
    }
}
