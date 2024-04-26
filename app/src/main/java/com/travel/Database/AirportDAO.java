package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.travel.App;
import com.travel.Model.AirportModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;

import java.util.ArrayList;
import java.util.List;

public class AirportDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    ;
    SQLiteDatabase database;

    public AirportDAO() {
    }

    @SuppressLint("Range")
    public ArrayList<AirportModel> getAll(String string, int pageSize, int pageNumber) {
        ArrayList<AirportModel> airports = new ArrayList<AirportModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                //*TODO: Refactor this query to use rawQuery
                String[] columns = {
                        "airports.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM airports " +
                        "INNER JOIN destinations ON airports.destination_id = destinations.destination_id " +
                        "WHERE airports.name LIKE '%" + string + "%' " +
                        "LIMIT " + pageSize + " OFFSET " + (pageNumber - 1) * pageSize;

                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        AirportModel airport = new AirportModel();
                        airport.setAirportCode(cursor.getString(cursor.getColumnIndex("airport_code")));
                        airport.setName(cursor.getString(cursor.getColumnIndex("name")));
                        airport.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        airport.setDestination(destination);

                        airports.add(airport);
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

        return airports;
    }
    @SuppressLint("Range")
    public AirportModel getAirportByCode(String airportCode) {
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        AirportModel airport = null;
        if (database != null) {
            try {
                cursor = database.query("airports", null, "airport_code = ?", new String[]{airportCode}, null, null, null);
                if (cursor.moveToFirst()) {
                    airport = new AirportModel();
                    airport.setAirportCode(cursor.getString(cursor.getColumnIndex("airport_code")));
                    airport.setName(cursor.getString(cursor.getColumnIndex("name")));
                    airport.setDescription(cursor.getString(cursor.getColumnIndex("description")));
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

        return airport;
    }

}
