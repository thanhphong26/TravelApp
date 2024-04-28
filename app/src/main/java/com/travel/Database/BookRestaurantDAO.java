package com.travel.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.BookRestaurantModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.UserModel;

public class BookRestaurantDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;
    BookRestaurantModel bookRestaurantModel;
    public UserModel getUser(int userId){
        UserModel userModel = new UserModel();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                cursor = database.query("users", null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        userModel.setUserId(cursor.getInt(0));
                        userModel.setUsername(cursor.getString(1));
                        userModel.setPhoneNumber(cursor.getString(2));
                        userModel.setEmail(cursor.getString(3));
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

        return userModel;
    }
    public RestaurantModel getInfor(int restaurantId) {
        RestaurantModel restaurantModel = new RestaurantModel();
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                Cursor cursor = database.query("restaurant", null, "restaurant_id=?", new String[]{String.valueOf(restaurantId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        restaurantModel.setRestaurantId(cursor.getInt(0));
                        restaurantModel.setDestinationId(cursor.getInt(1));
                        restaurantModel.setName(cursor.getString(2));
                        restaurantModel.setDescription(cursor.getString(3));
                        restaurantModel.setImage(cursor.getString(4));
                        restaurantModel.setRating(cursor.getFloat(5));
                        restaurantModel.setLongitude(cursor.getFloat(6));
                        restaurantModel.setLatitude(cursor.getFloat(7));
                        restaurantModel.setPrice(cursor.getFloat(8));
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }

        }
        return restaurantModel;
    }
    public void addBookRestaurant(int userId, int restaurantId, int quantityAdults, int quantityChilds, float totalPrice){
        database = databaseHelper.openDatabase();
        ContentValues values = new ContentValues();
        values.put("restaurant_id", restaurantId);
        values.put("user_id", userId);
        values.put("number_of_adults", quantityAdults);
        values.put("number_of_childs", quantityChilds);;
        values.put("total_price", totalPrice);
        database.insert("restaurant_bookings", null, values);
        databaseHelper.closeDatabase(database);
    }
}
