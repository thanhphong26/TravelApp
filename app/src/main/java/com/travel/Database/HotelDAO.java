package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.travel.App;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.TourModel;
import java.util.List;
import java.util.ArrayList;

public class HotelDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    ;
    SQLiteDatabase database;

    public HotelDAO() {
    }

    @SuppressLint("Range")
    public ArrayList<HotelModel> getAll(String string, int pageSize, int pageNumber) {
        ArrayList<HotelModel> hotels = new ArrayList<HotelModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                //*TODO: Refactor this query to use rawQuery
                String[] columns = {
                        "hotels.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM hotels " +
                        "INNER JOIN destinations ON hotels.destination_id = destinations.destination_id " +
                        "WHERE hotels.name LIKE '%" + string + "%' " +
                        "ORDER BY rating DESC LIMIT " + pageSize + " OFFSET " + (pageNumber - 1) * pageSize;

                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        HotelModel hotel = new HotelModel();
                        hotel.setHotelId(cursor.getInt(cursor.getColumnIndex("hotel_id")));
                        hotel.setName(cursor.getString(cursor.getColumnIndex("name")));
                        hotel.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        hotel.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        hotel.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        hotel.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        hotel.setLongitude(cursor.getFloat(cursor.getColumnIndex("longitude")));
                        hotel.setLatitude(cursor.getFloat(cursor.getColumnIndex("latitude")));
                        hotel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        hotel.setDestination(destination);

                        hotels.add(hotel);
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

        return hotels;
    }

    @SuppressLint("Range")
    public List<HotelModel> getByDestinationId(int destinationId) {
        List<HotelModel> hotels = new ArrayList<>();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                String[] columns = {
                        "hotels.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM hotels " +
                        "INNER JOIN destinations ON hotels.destination_id = destinations.destination_id " +
                        "WHERE hotels.destination_id = " + destinationId +
                        " ORDER BY rating DESC";
                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        HotelModel hotel = new HotelModel();
                        hotel.setHotelId(cursor.getInt(cursor.getColumnIndex("hotel_id")));
                        hotel.setName(cursor.getString(cursor.getColumnIndex("name")));
                        hotel.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        hotel.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        hotel.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        hotel.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        hotel.setLongitude(cursor.getFloat(cursor.getColumnIndex("longitude")));
                        hotel.setLatitude(cursor.getFloat(cursor.getColumnIndex("latitude")));
                        hotel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        hotel.setDestination(destination);
                        hotels.add(hotel);
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
        return hotels;
    }


    @SuppressLint("Range")
    public ArrayList<HotelModel> getCommon(int limit) {
        ArrayList<HotelModel> commonHotels = new ArrayList<HotelModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                String[] columns = {
                        "hotels.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM hotels " +
                        "INNER JOIN destinations ON hotels.destination_id = destinations.destination_id " +
                        "ORDER BY rating DESC LIMIT " + limit;

                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        HotelModel hotel = new HotelModel();
                        hotel.setHotelId(cursor.getInt(cursor.getColumnIndex("hotel_id")));
                        hotel.setName(cursor.getString(cursor.getColumnIndex("name")));
                        hotel.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        hotel.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        hotel.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        hotel.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        hotel.setLongitude(cursor.getFloat(cursor.getColumnIndex("longitude")));
                        hotel.setLatitude(cursor.getFloat(cursor.getColumnIndex("latitude")));
                        hotel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        hotel.setDestination(destination);

                        commonHotels.add(hotel);
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

        return commonHotels;
    }
}
