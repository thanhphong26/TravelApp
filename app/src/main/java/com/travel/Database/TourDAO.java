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

import java.sql.Timestamp;
import java.util.ArrayList;

public class TourDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;

    public TourDAO() {
    }

    @SuppressLint("Range")
    public ArrayList<TourModel> getCommon(int limit) {
        ArrayList<TourModel> commonTours = new ArrayList<TourModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                //*TODO: Refactor this query to use rawQuery
                String[] columns = {
                        "tours.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM tours " +
                        "INNER JOIN destinations ON tours.destination_id = destinations.destination_id " +
                        "ORDER BY rating DESC LIMIT " + limit;

                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        TourModel tour = new TourModel();
                        tour.setTourId(cursor.getInt(cursor.getColumnIndex("tour_id")));
                        tour.setName(cursor.getString(cursor.getColumnIndex("name")));
                        tour.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        tour.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        tour.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        tour.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        tour.setDestination(destination);

                        commonTours.add(tour);
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

        return commonTours;
    }

    public ArrayList<TourModel> getCommon() {
        return this.getCommon(5);
    }

    @SuppressLint("Range")
    public ArrayList<TourModel> getAll(String string, int pageSize, int pageNumber) {
        ArrayList<TourModel> tours = new ArrayList<TourModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                //*TODO: Refactor this query to use rawQuery
                String[] columns = {
                        "tours.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM tours " +
                        "INNER JOIN destinations ON hotels.destination_id = destinations.destination_id " +
                        "WHERE tours.name LIKE '%" + string + "%' " +
                        "ORDER BY rating DESC LIMIT " + pageSize + " OFFSET " + (pageNumber - 1) * pageSize;

                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        TourModel tour = new TourModel();
                        tour.setTourId(cursor.getInt(cursor.getColumnIndex("tour_id")));
                        tour.setName(cursor.getString(cursor.getColumnIndex("name")));
                        tour.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        tour.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        tour.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        tour.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        tour.setDestination(destination);

                        tours.add(tour);
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

        return tours;
    }
}
