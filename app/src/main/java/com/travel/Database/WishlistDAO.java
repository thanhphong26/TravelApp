package com.travel.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.travel.App;
import com.travel.Model.WishlistModel;

import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;
    UserDAO userDAO = new UserDAO();
    TourDAO tourDAO=new TourDAO();
    public WishlistDAO(Context context) {
    }
    public void insertWishlist(WishlistModel wishlistModel) {
        SQLiteDatabase database = null;
        try {
            database = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("userId", wishlistModel.getUserId());
            values.put("destinationId", wishlistModel.getDestinationId());
            values.put("tourId", wishlistModel.getTourId());
            values.put("hotelId", wishlistModel.getHotelId());
            values.put("restaurantId", wishlistModel.getRestaurantId());
            long rowId = database.insert("wishlist", null, values);
            if (rowId != -1) {
                Log.d("Insert", "Thêm vào danh sách yêu thích thành công");
            } else {
                Log.e("Insert", "Lỗi thêm vào danh sách yêu thích");
            }
        } catch (SQLException e) {
            Log.e("Insert", "Error inserting row: " + e.getMessage());
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }
    @SuppressLint("Range")
    public List<WishlistModel> getWishlistByUserId(int userId) {
        database = databaseHelper.getReadableDatabase();
        List<WishlistModel> wishlistModelList = new ArrayList<>();
        Cursor cursor = null;
        if (database != null) {
            try {
                cursor = database.query("wishlist", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        WishlistModel wishlistModel = new WishlistModel();
                        wishlistModel.setWishlistId(cursor.getInt(cursor.getColumnIndex("wishlist_id")));
                        wishlistModel.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
                        wishlistModel.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        wishlistModel.setTourId(cursor.getInt(cursor.getColumnIndex("tour_id")));
                        wishlistModel.setHotelId(cursor.getInt(cursor.getColumnIndex("hotel_id")));
                        wishlistModel.setRestaurantId(cursor.getInt(cursor.getColumnIndex("restaurant_id")));
                        wishlistModelList.add(wishlistModel);
                    } while (cursor.moveToNext());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                databaseHelper.closeDatabase(database);
            }
        }
        return wishlistModelList;
    }

    public void removeWishList(int userId, int itemId) {
        database = databaseHelper.getWritableDatabase();
        try {
            switch (itemId) {
                case 1 ->  database.delete("wishlist", "user_id = ? AND destination_id = ?", new String[]{String.valueOf(userId), String.valueOf(itemId)});
                case 2 -> database.delete("wishlist", "user_id = ? AND tour_id = ?", new String[]{String.valueOf(userId), String.valueOf(itemId)});
                case 3 -> database.delete("wishlist", "user_id = ? AND hotel_id = ?", new String[]{String.valueOf(userId), String.valueOf(itemId)});
                case 4 -> database.delete("wishlist", "user_id = ? AND restaurant_id = ?", new String[]{String.valueOf(userId), String.valueOf(itemId)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkFavoriteTour(int tourId, int userId){
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query("whishlist", null, "user_id = ? AND tour_id = ?", new String[]{String.valueOf(userId), String.valueOf(tourId)}, null, null, null);
            if (cursor.moveToFirst()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
