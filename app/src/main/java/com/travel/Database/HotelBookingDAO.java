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
import com.travel.Model.ReviewType;

import java.util.ArrayList;
import java.util.List;

public class HotelBookingDAO {
    DatabaseHelper databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;

    public HotelBookingDAO() {
    }

    @SuppressLint("Range")
    public List<HotelBookingReviewModel> getAllHotelBookingsWithReview(int userId) {
        List<HotelBookingReviewModel> hotelBookingModels = new ArrayList<>();
        database = databaseHelper.openDatabase();
        if (database != null) {
            Cursor cursor = null;
            try {
                String query = "SELECT hotel_bookings.*, reviews.*,hotels.* FROM hotel_bookings JOIN hotels ON hotel_bookings.hotel_id = hotels.hotel_id LEFT JOIN reviews ON hotel_bookings.booking_id = reviews.item_id AND reviews.review_type = ? WHERE hotel_bookings.user_Id = ?;";
                cursor = database.rawQuery(query, new String[]{ReviewType.HOTEL.toString(), String.valueOf(userId)});
                if (cursor.moveToFirst()) {
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


    @SuppressLint("Range")
    public HotelBookingReviewModel getBookingById(int bookingId) {
        HotelBookingReviewModel hotelBookingModel = new HotelBookingReviewModel();
        database = databaseHelper.openDatabase();
        if (database != null) {
            Cursor cursor = null;
            try {
                String query = "SELECT hotel_bookings.*, reviews.*,hotels.* FROM hotel_bookings JOIN hotels ON hotel_bookings.hotel_id = hotels.hotel_id LEFT JOIN reviews ON hotels.hotel_id = reviews.item_id AND reviews.review_type = ? WHERE hotel_bookings.booking_id = ?;";
                cursor = database.rawQuery(query, new String[]{ReviewType.HOTEL.toString(), String.valueOf(bookingId)});
                if (cursor.moveToFirst()) {
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
        return hotelBookingModel;
    }
}
