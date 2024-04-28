package com.travel.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.BookTourModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.TourLineModel;
import com.travel.Model.TourModel;
import com.travel.Model.TourPricingModel;
import com.travel.Model.UserModel;

import java.sql.Array;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class BookTourDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;
    BookTourModel bookTourModel;

    public BookTourDAO() {
    }
    public TourModel getInformationTour(int tourId){
        TourModel tourModel = new TourModel();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null)
        {
            try {
                cursor = database.query("tours", null, "tour_id=?", new String[]{String.valueOf(tourId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        tourModel.setTourId(cursor.getInt(0));
                        tourModel.setDestinationId(cursor.getInt(1));
                        tourModel.setName(cursor.getString(2));
                        tourModel.setDescription(cursor.getString(3));
                        tourModel.setImage(cursor.getString(4));
                        tourModel.setRating(cursor.getFloat(5));
                        tourModel.setPrice(cursor.getFloat(6));
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
        return tourModel;
    }
    public UserModel getUser(int userId){
        UserModel userModel = new UserModel();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null)
        {
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
    public void addBookTour(int userId, int tourId, String bookingDate,int quantityAdults, int quantityChilds, float totalPrice, String createdAt){
        database = databaseHelper.openDatabase();
        ContentValues values = new ContentValues();
        values.put("tour_id", tourId);
        values.put("user_id", userId);
        values.put("booking_date", String.valueOf(bookingDate));
        values.put("created_at", String.valueOf(createdAt));
        values.put("number_of_adults", quantityAdults);
        values.put("number_of_childs", quantityChilds);;
        values.put("total_price", totalPrice);
        database.insert("tour_bookings", null, values);
        databaseHelper.closeDatabase(database);
    }
    public TourPricingModel getInforPrice(int tourId){
        TourPricingModel tourPricingModel = new TourPricingModel();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null)
        {
            try {
                cursor = database.query("tour_pricing", null, "tour_id=?", new String[]{String.valueOf(tourId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        tourPricingModel.setPricingId(cursor.getInt(0));
                        tourPricingModel.setTourId(cursor.getInt(1));
                        tourPricingModel.setAdultPrice(cursor.getFloat(2));
                        tourPricingModel.setChildPrice(cursor.getFloat(3));
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
        return tourPricingModel;
    }

}
