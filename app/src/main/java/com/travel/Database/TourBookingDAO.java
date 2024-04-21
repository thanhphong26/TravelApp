package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.TourBookingModel;

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
    public List<TourBookingModel> getAllTourBookings() {
        List<TourBookingModel> tourBookingModels = new ArrayList<>();
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                // get tour bookings by user id
                Cursor cursor = database.rawQuery("SELECT * FROM tour_bookings WHERE user_id = ?" , null);
                if (cursor.moveToFirst() ){
                    do {
                        TourBookingModel tourBookingModel = new TourBookingModel();
                        tourBookingModel.setBookingId(cursor.getInt(cursor.getColumnIndex("booking_id")));
                        tourBookingModel.setTour(new TourDAO().getTourById(cursor.getInt(cursor.getColumnIndex("tour_id"))));
                        tourBookingModel.setBookingDate(cursor.getString(cursor.getColumnIndex("booking_date")));
                        tourBookingModel.setTotalPrice(cursor.getFloat(cursor.getColumnIndex("total_price")));
                        tourBookingModel.setNumberOfAdults(cursor.getInt(cursor.getColumnIndex("number_of_adults")));
                        tourBookingModel.setNumberOfChildren(cursor.getInt(cursor.getColumnIndex("number_of_childs")));
                        tourBookingModel.setCreatedAt(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("created_at"))));
                        tourBookingModels.add(tourBookingModel);
                    } while (cursor.moveToNext());
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                databaseHelper.closeDatabase(database);
            }
        }
        return tourBookingModels;
    }
}
