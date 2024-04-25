package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.RestaurantBookingReviewModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.ReviewModel;
import com.travel.Model.ReviewType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RestaurantBookingDAO {
    DatabaseHelper databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;

    public RestaurantBookingDAO() {
    }

    @SuppressLint("Range")
    public List<RestaurantBookingReviewModel> getAllRestaurantBookingsWithReview(int userId) {
        List<RestaurantBookingReviewModel> restaurantBookingModels = new ArrayList<>();
        database = databaseHelper.openDatabase();
        if (database != null) {
            Cursor cursor = null;
            try {
                // get restaurant bookings by user id
                String query = "SELECT restaurant_bookings.*, reviews.*, restaurant.* FROM restaurant_bookings JOIN restaurant ON restaurant_bookings.restaurant_id = restaurant.restaurant_id LEFT JOIN reviews ON restaurant.restaurant_id = reviews.item_id AND reviews.review_type = ? WHERE restaurant_bookings.user_Id = ?;";
                cursor = database.rawQuery(query, new String[]{ReviewType.RESTAURANT.toString(), String.valueOf(userId)});
                if (cursor.moveToFirst()) {
                    do {
                        RestaurantBookingReviewModel restaurantBookingModel = new RestaurantBookingReviewModel();
                        restaurantBookingModel.setBookingId(cursor.getInt(cursor.getColumnIndex("booking_id")));
                        restaurantBookingModel.setTotalPrice(cursor.getFloat(cursor.getColumnIndex("total_price")));
                        restaurantBookingModel.setNumberOfAdults(cursor.getInt(cursor.getColumnIndex("number_of_adults")));
                        restaurantBookingModel.setNumberOfChildren(cursor.getInt(cursor.getColumnIndex("number_of_childs")));

                        RestaurantModel restaurant = new RestaurantModel();
                        restaurant.setRestaurantId(cursor.getInt(cursor.getColumnIndex("restaurant_id")));
                        restaurant.setName(cursor.getString(cursor.getColumnIndex("name")));
                        restaurant.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        restaurant.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        restaurant.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        restaurantBookingModel.setRestaurant(restaurant);

                        ReviewModel review = new ReviewModel();
                        review.setReviewId(cursor.getInt(cursor.getColumnIndex("review_id")));
//                        review.setReviewType(ReviewType.valueOf(cursor.getString(cursor.getColumnIndex("review_type"))));
                        restaurantBookingModel.setReview(review);
                        restaurantBookingModels.add(restaurantBookingModel);
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
        return restaurantBookingModels;
    }


    @SuppressLint("Range")
    public RestaurantBookingReviewModel getBookingById(int bookingId) {
        RestaurantBookingReviewModel restaurantBookingModel = new RestaurantBookingReviewModel();
        database = databaseHelper.openDatabase();
        if (database != null) {
            Cursor cursor = null;
            try {
                // get restaurant bookings by user id
                String query = "SELECT restaurant_bookings.*, reviews.*, restaurant.* FROM restaurant_bookings JOIN restaurant ON restaurant_bookings.restaurant_id = restaurant.restaurant_id LEFT JOIN reviews ON restaurant.restaurant_id = reviews.item_id AND reviews.review_type = ? WHERE restaurant_bookings.booking_id = ?;";
                cursor = database.rawQuery(query, new String[]{ReviewType.RESTAURANT.toString(), String.valueOf(bookingId)});
                if (cursor.moveToFirst()) {
                    restaurantBookingModel.setBookingId(cursor.getInt(cursor.getColumnIndex("booking_id")));
                    restaurantBookingModel.setTotalPrice(cursor.getFloat(cursor.getColumnIndex("total_price")));
                    restaurantBookingModel.setNumberOfAdults(cursor.getInt(cursor.getColumnIndex("number_of_adults")));
                    restaurantBookingModel.setNumberOfChildren(cursor.getInt(cursor.getColumnIndex("number_of_childs")));

                    RestaurantModel restaurant = new RestaurantModel();
                    restaurant.setRestaurantId(cursor.getInt(cursor.getColumnIndex("restaurant_id")));
                    restaurant.setName(cursor.getString(cursor.getColumnIndex("name")));
                    restaurant.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    restaurant.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                    restaurant.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    restaurantBookingModel.setRestaurant(restaurant);

                    ReviewModel review = new ReviewModel();
                    review.setReviewId(cursor.getInt(cursor.getColumnIndex("review_id")));
                    restaurantBookingModel.setReview(review);
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
        return restaurantBookingModel;
    }
}
