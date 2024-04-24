package com.travel.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.travel.App;
import com.travel.Model.HotelModel;
import com.travel.Model.WishlistModel;

import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {
    DatabaseHelper databaseHelper  = new DatabaseHelper(App.self());
    SQLiteDatabase database;
    UserDAO userDAO = new UserDAO();
    TourDAO tourDAO=new TourDAO();
    HotelDAO hotelDAO =new HotelDAO();
    RestaurantDAO restaurantDAO =new RestaurantDAO();
    DestinationDAO destinationDAO = new DestinationDAO();
    public WishlistDAO(Context context) {
    }

    public WishlistDAO() {
    }
    public void insertDestinationWhishlist(int userId, int destinationId){
        SQLiteDatabase database = null;
        try {
            database = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("destination_id", destinationId);
            long rowId = database.insert("whishlist", null, values);
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
    public void insertHotelWhishlist(int userId, int hotelId){
        SQLiteDatabase database = null;
        try {
            database = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("hotel_id", hotelId);
            long rowId = database.insert("whishlist", null, values);
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
    public void insertTourWhishlist(int userId, int tourId){
        SQLiteDatabase database = null;
        try {
            database = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("tour_id", tourId);
            long rowId = database.insert("whishlist", null, values);
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
    public void insertRestaurantWhishlist(int userId, int restaurantId){
        SQLiteDatabase database = null;
        try {
            database = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("restaurant_id", restaurantId);
            long rowId = database.insert("whishlist", null, values);
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
                cursor = database.query("whishlist", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        WishlistModel wishlistModel = new WishlistModel();
                        int tourId = cursor.getInt(cursor.getColumnIndex("tour_id"));
                        int hotelId = cursor.getInt(cursor.getColumnIndex("hotel_id"));
                        int restaurantId = cursor.getInt(cursor.getColumnIndex("restaurant_id"));
                        int destinationId = cursor.getInt(cursor.getColumnIndex("destination_id"));
                        wishlistModel.setWishlistId(cursor.getInt(cursor.getColumnIndex("whishlist_id")));
                        wishlistModel.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
                        wishlistModel.setDestinationId(destinationId);
                        wishlistModel.setTourId(tourId);
                        wishlistModel.setHotelId(hotelId);
                        wishlistModel.setRestaurantId(restaurantId);

                        if (tourId != 0) {
                            wishlistModel.setTourModel(tourDAO.getTourById(tourId));
                        }
                        if (hotelId != 0) {
                            wishlistModel.setHotelModel(hotelDAO.getHotelById(hotelId));
                        }
                        if (restaurantId != 0) {
                            wishlistModel.setRestaurantModel(restaurantDAO.getRestaurantById(restaurantId));
                        }
                        if (destinationId != 0) {
                            wishlistModel.setDestinationModel(destinationDAO.getDestinationDetailById(destinationId));
                        }

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
    public void removeWishListDestinationId(int userId,int destinationId) {
        database = databaseHelper.getWritableDatabase();
        try {
            database.delete("whishlist", "user_id=? and destination_id = ?", new String[]{String.valueOf(userId), String.valueOf(destinationId)});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHelper.closeDatabase(database);
        }
    }
    public void removeWhishlistHotelId(int userId,int hotelId) {
        database = databaseHelper.getWritableDatabase();
        try {
            database.delete("whishlist", "user_id=? and hotel_id = ?",  new String[]{String.valueOf(userId), String.valueOf(hotelId)});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHelper.closeDatabase(database);
        }
    }
    public void removeWhishlistRestaurantId(int userId,int restaurantId) {
        database = databaseHelper.getWritableDatabase();
        try {
            database.delete("whishlist", "user_id=? and restaurant_id = ?", new String[]{String.valueOf(userId), String.valueOf(restaurantId)});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHelper.closeDatabase(database);
        }
    }
    public void removeWhishlistTourId(int userId,int tourId) {
        database = databaseHelper.getWritableDatabase();
        try {
            database.delete("whishlist", "user_id=? and tour_id = ?",  new String[]{String.valueOf(userId), String.valueOf(tourId)});
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseHelper.closeDatabase(database);
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
    public boolean checkFavoriteHotel(int hotelId, int userId) {
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query("whishlist", null, "user_id = ? AND hotel_id = ?", new String[]{String.valueOf(userId), String.valueOf(hotelId)}, null, null, null);
            if (cursor.moveToFirst()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkFavoriteDestination(int destinationId, int userId) {
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query("whishlist", null, "user_id = ? AND destination_id = ? ", new String[]{String.valueOf(userId), String.valueOf(destinationId)}, null, null, null);
            if (cursor.moveToFirst()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkFavoriteRestaurant(int restaurantId, int userId) {
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query("whishlist", null, "user_id = ? AND restaurant_id = ?", new String[]{String.valueOf(userId), String.valueOf(restaurantId)}, null, null, null);
            if (cursor.moveToFirst()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
