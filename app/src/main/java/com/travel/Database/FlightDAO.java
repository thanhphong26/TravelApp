package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.DestinationModel;
import com.travel.Model.FlightModel;
import com.travel.Model.HotelModel;
import com.travel.Utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;

public class FlightDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    ;
    SQLiteDatabase database;

    public FlightDAO() {
    }

    @SuppressLint("Range")
    public ArrayList<FlightModel> search(String arrival, String departure, String arrivalDate, String departureDate) {
        ArrayList<FlightModel> flights = new ArrayList<FlightModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                if (!DateTimeHelper.checkIsValidDateTimeFormat("YYYY-MM-dd", arrivalDate)) {
                    arrivalDate = DateTimeHelper.convertToDateFormat("YYYY-MM-dd", arrivalDate);
                }
                if (!DateTimeHelper.checkIsValidDateTimeFormat("YYYY-MM-dd", departureDate)) {
                    departureDate = DateTimeHelper.convertToDateFormat("YYYY-MM-dd", departureDate);
                }

                cursor = database.query("flights", null, "arrival_airport_code = ? AND departure_airport_code = ? AND departure_date >= date(?) AND arrival_date <= date(?)",
                        new String[]{arrival.trim(), departure.trim(), departureDate, arrivalDate}, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        FlightModel flight = new FlightModel();
                        flight.setFlightId(cursor.getInt(cursor.getColumnIndex("flight_id")));
                        flight.setDepartureAirportCode(cursor.getString(cursor.getColumnIndex("departure_airport_code")));
                        flight.setArrivalAirportCode(cursor.getString(cursor.getColumnIndex("arrival_airport_code")));
                        flight.setDepartureDate(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("departure_date"))));
                        flight.setDepartureTime(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("departure_time"))));
                        flight.setArrivalDate(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("arrival_date"))));
                        flight.setArrivalTime(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("arrival_time"))));
                        flight.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        flight.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        flight.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                        flight.setAvailableSeats(cursor.getInt(cursor.getColumnIndex("available_seats")));

                        flights.add(flight);
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

        return flights;
    }
    @SuppressLint("Range")
    public FlightModel getFlightById(int flightId) {
        FlightModel flight = new FlightModel();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                cursor = database.query("flights", null, "flight_id = ?", new String[]{String.valueOf(flightId)}, null, null, null);

                if (cursor.moveToFirst()) {
                    flight.setFlightId(cursor.getInt(cursor.getColumnIndex("flight_id")));
                    flight.setDepartureAirportCode(cursor.getString(cursor.getColumnIndex("departure_airport_code")));
                    flight.setArrivalAirportCode(cursor.getString(cursor.getColumnIndex("arrival_airport_code")));
                    flight.setDepartureDate(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("departure_date"))));
                    flight.setDepartureTime(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("departure_time"))));
                    flight.setArrivalDate(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("arrival_date"))));
                    flight.setArrivalTime(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("arrival_time"))));
                    flight.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                    flight.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    flight.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                    flight.setAvailableSeats(cursor.getInt(cursor.getColumnIndex("available_seats")));
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
        return flight;
    }

}
