package com.travel.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.BookHotelModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.UserModel;

public class BookHotelDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    ;
    SQLiteDatabase database;
    BookHotelModel bookHotelModel;
    HotelModel hotelModel;
    DestinationModel destinationModel;

    public BookHotelDAO() {
    }

    public HotelModel getInFor(int hotelId) {
        hotelModel = new HotelModel();
        destinationModel = new DestinationModel();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                cursor = database.query("hotels", null, "hotel_id=?", new String[]{String.valueOf(hotelId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        hotelModel.setHotelId(cursor.getInt(0));
                        hotelModel.setDestinationId(cursor.getInt(1));
                        hotelModel.setName(cursor.getString(2));
                        hotelModel.setDescription(cursor.getString(3));
                        hotelModel.setImage(cursor.getString(4));
                        hotelModel.setRating(cursor.getFloat(5));
                        hotelModel.setPrice(cursor.getFloat(6));
                        hotelModel.setLongitude(cursor.getFloat(8));
                        hotelModel.setLatitude(cursor.getFloat(7));
                    } while (cursor.moveToNext());
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
        return hotelModel;
    }

    public UserModel getInforUser(int userId) {
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                cursor = database.query("users", null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        UserModel userModel = new UserModel();
                        userModel.setUserId(cursor.getInt(0));
                        userModel.setUsername(cursor.getString(1));
                        userModel.setPhoneNumber(cursor.getString(2));
                        userModel.setEmail(cursor.getString(3));
                        userModel.setPassword(cursor.getString(4));

                        return userModel;
                    } while (cursor.moveToNext());
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
        return null;
    }
}

