package com.travel.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.BookFlightModel;
import com.travel.Model.FlightModel;
import com.travel.Model.TypeOfFlightModel;
import com.travel.Model.UserModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookFlightDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;
    BookFlightModel bookFlightModel;


    public BookFlightDAO() {
    }
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
    public TypeOfFlightModel getType(int flightId)
    {
        TypeOfFlightModel typeOfFlightModel = new TypeOfFlightModel();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                cursor = database.query("type_of_flights", null, "flight_id=?", new String[]{String.valueOf(flightId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        typeOfFlightModel.setTypeId(cursor.getInt(0));
                        typeOfFlightModel.setType(cursor.getString(2));
                        FlightModel flightModel = new FlightModel();
                        flightModel.setFlightId(cursor.getInt(1));
                        typeOfFlightModel.setFlightModel(flightModel);
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

        return typeOfFlightModel;

    }
    public FlightModel getInfor(int flightId) {
        FlightModel flightModel = new FlightModel();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                cursor = database.query("flights", null, "flight_id=?", new String[]{String.valueOf(flightId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        flightModel.setFlightId(cursor.getInt(0));
                        flightModel.setDepartureAirportCode(cursor.getString(1));
                        flightModel.setArrivalAirportCode(cursor.getString(2));
                        flightModel.setDepartureTime(Timestamp.valueOf(cursor.getString(3)));
                        flightModel.setArrivalTime(Timestamp.valueOf(cursor.getString(4)));
                        flightModel.setFlightDuration(cursor.getInt(5));
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date departureDate = sdf.parse(cursor.getString(6));
                            Date arrivalDate = sdf.parse(cursor.getString(7));

                            Timestamp departureTimestamp = new Timestamp(departureDate.getTime());
                            Timestamp arrivalTimestamp = new Timestamp(arrivalDate.getTime());

                            flightModel.setDepartureDate(departureTimestamp);
                            flightModel.setArrivalDate(arrivalTimestamp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        flightModel.setStatus(cursor.getString(8));
                        flightModel.setAvailableSeats(cursor.getInt(9));
                        flightModel.setPrice(cursor.getFloat(10));
                        flightModel.setDescription(cursor.getString(11));
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

        return flightModel;
    }
    public void addBookFlight(int userId, int flightId,int typeId, int quantityAdults, int quantityChilds, float totalPrice){
        database = databaseHelper.openDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("flight_id", flightId);
        values.put("type_id", typeId);
        values.put("number_of_adults", quantityAdults);
        values.put("number_of_childs", quantityChilds);
        values.put("total_price", totalPrice);
        database.insert("flight_bookings", null, values);
        databaseHelper.closeDatabase(database);
    }

    @SuppressLint("Range")
    public  List<BookFlightModel>  getAll(int userId) {
        List<BookFlightModel> flightBookings = new ArrayList<>();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                cursor = database.query("flight_bookings", null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        BookFlightModel flightBooking = new BookFlightModel();
                        flightBooking.setBookingId(cursor.getInt(cursor.getColumnIndex("booking_id")));
                        flightBooking.setUser(getUser(cursor.getInt(cursor.getColumnIndex("user_id"))));
                        flightBooking.setFlight(getInfor(cursor.getInt(cursor.getColumnIndex("flight_id"))));
                        flightBooking.setTypeId(cursor.getInt(cursor.getColumnIndex("type_id")));
                        flightBooking.setNumberOfAdults(cursor.getInt(cursor.getColumnIndex("number_of_adults")));
                        flightBooking.setNumberOfChilds(cursor.getInt(cursor.getColumnIndex("number_of_childs")));
                        flightBooking.setTotalPrice(cursor.getFloat(cursor.getColumnIndex("total_price")));

                        flightBookings.add(flightBooking);
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

        return flightBookings;
    }
}
