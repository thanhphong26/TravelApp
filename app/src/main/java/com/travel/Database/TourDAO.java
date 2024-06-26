package com.travel.Database;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.List;


public class TourDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;
    public TourDAO(Context context){

    }
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
                        "INNER JOIN destinations ON tours.destination_id = destinations.destination_id " +
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
    @SuppressLint("Range")
    public List<TourModel> getTourByDestinationId(int destinationId){
        database=databaseHelper.openDatabase();
        List<TourModel> listTour=new ArrayList<>();
        Cursor cursor = null;
        if (database != null) {
            try {
                String[] columns = {
                        "tours.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM tours " +
                        "INNER JOIN destinations ON tours.destination_id = destinations.destination_id " +
                        "WHERE tours.destination_id = " + destinationId +
                        " ORDER BY rating DESC";
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
                        listTour.add(tour);
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
        return listTour;
    }
    public List<TourModel> getAllTours(int destinationId) {
        database = databaseHelper.openDatabase();
        List<TourModel> tourModels = new ArrayList<>();
        Cursor cursor = null;
        if (database != null) {
            try {
                cursor = database.query("tours", null, "destination_id= ?", new String[]{String.valueOf(destinationId)}, null, null, null);
                if (cursor!=null && cursor.moveToFirst()) {
                    do {
                        TourModel tourModel = new TourModel();
                        tourModel.setTourId(cursor.getInt(0));
                        tourModel.setName(cursor.getString(2));
                        tourModel.setDescription(cursor.getString(3));
                        tourModel.setImage(cursor.getString(4));
                        tourModel.setRating(cursor.getFloat(5));
                        tourModel.setPrice(cursor.getFloat(6));
                        tourModels.add(tourModel);
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
        return tourModels;
    }
    @SuppressLint("Range")
    public TourModel getTourById(int id) {
        database = databaseHelper.openDatabase();
        TourModel tourModel = new TourModel();
        Cursor cursor=null;
        if (database != null) {
            try {
                String[] columns = {
                        "tours.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM tours " +
                        "INNER JOIN destinations ON tours.destination_id = destinations.destination_id " +
                        "WHERE tours.tour_id = " + id;

                cursor = database.rawQuery(query, null);
                if (cursor != null && cursor.moveToFirst()) {
                    DestinationModel destination = new DestinationModel();
                    destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                    destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                    destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                    tourModel.setTourId(cursor.getInt(cursor.getColumnIndex("tour_id")));
                    tourModel.setName(cursor.getString(cursor.getColumnIndex("name")));
                    tourModel.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    tourModel.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    tourModel.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                    tourModel.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                    tourModel.setDestination(destination);
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
    @SuppressLint("Range")
    public TourModel getDestinationId(int tourId){
        database=databaseHelper.openDatabase();
        TourModel tour=new TourModel();
        Cursor cursor = null;
        if (database != null) {
            try {
                String[] columns = {
                        "tours.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM tours " +
                        "INNER JOIN destinations ON tours.destination_id = destinations.destination_id " +
                        "WHERE tours.tour_id = " + tourId;
                        cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        tour.setTourId(cursor.getInt(cursor.getColumnIndex("tour_id")));
                        tour.setName(cursor.getString(cursor.getColumnIndex("name")));
                        tour.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        tour.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        tour.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        tour.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        tour.setDestination(destination);
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
        return tour;
    }
}