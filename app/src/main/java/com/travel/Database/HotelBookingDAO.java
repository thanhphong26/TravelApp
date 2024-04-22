package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.HotelBookingReviewModel;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantBookingReviewModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class HotelBookingDAO {
    DatabaseHelper databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;

    public HotelBookingDAO() {
    }
    @SuppressLint("Range")
    // get all hotel bookings from hotel_booking table
    public List<HotelBookingReviewModel> getAllHotelBookingsWithReview(int userId) {
        List<HotelBookingReviewModel> hotelBookingModels = new ArrayList<>();
        database = databaseHelper.openDatabase();
        if (database != null) {
            Cursor cursor = null;
            try {
                // get hotel bookings by user id
                String query = " SELECT hotel_bookings.*, reviews.*,hotels.*\n" +
                        "FROM hotel_bookings\n" +
                        "JOIN hotels ON hotel_bookings.hotel_id = hotels.hotel_id\n" +
                        "JOIN reviews ON hotels.hotel_id = reviews.item_id\n" +
                        "WHERE reviews.review_type = 'hotel' AND hotel_bookings.user_Id = 2;";
                cursor = database.rawQuery(query, null);
                System.out.println("cursor count: " + query);
                if (cursor.moveToFirst()) {
                    System.out.println("cursor count: " + cursor.getCount());
                    do {
                        HotelBookingReviewModel hotelBookingModel = new HotelBookingReviewModel();
                        hotelBookingModel.setBookingId(cursor.getInt(cursor.getColumnIndex("booking_id")));
                        hotelBookingModel.setTotalPrice(cursor.getFloat(cursor.getColumnIndex("total_price")));
                        hotelBookingModel.setNumberOfAdults(cursor.getInt(cursor.getColumnIndex("number_of_adults")));
                        hotelBookingModel.setNumberOfChildren(cursor.getInt(cursor.getColumnIndex("number_of_childs")));

                        HotelModel hotel = new HotelModel();
                        hotel.setHotelId(cursor.getInt(cursor.getColumnIndex("hotel_id")));
                        hotel.setName(cursor.getString(cursor.getColumnIndex("name")));
                        hotel.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        hotel.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        hotel.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        hotelBookingModel.setHotel(hotel);

                        ReviewModel review = new ReviewModel();
                        review.setReviewId(cursor.getInt(cursor.getColumnIndex("review_id")));
//                        review.setReviewType(ReviewType.valueOf(cursor.getString(cursor.getColumnIndex("review_type"))));
                        hotelBookingModel.setReview(review);
                        hotelBookingModels.add(hotelBookingModel);
                    }
                    while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                databaseHelper.closeDatabase(database);
            }
        }
        return hotelBookingModels;
    }
}
