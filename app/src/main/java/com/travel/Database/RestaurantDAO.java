package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.travel.App;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;

import java.util.ArrayList;
import java.util.List;
public class RestaurantDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;

    public RestaurantDAO() {
    }

    @SuppressLint("Range")
    public ArrayList<RestaurantModel> getAll(String string, int pageSize, int pageNumber) {
        ArrayList<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                //*TODO: Refactor this query to use rawQuery
                String[] columns = {
                        "restaurant.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM restaurant " +
                        "INNER JOIN destinations ON restaurant.destination_id = destinations.destination_id " +
                        "WHERE restaurant.name LIKE '%" + string + "%' " +
                        "ORDER BY rating DESC LIMIT " + pageSize + " OFFSET " + (pageNumber - 1) * pageSize;

                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));
                        RestaurantModel restaurant = new RestaurantModel();
                        restaurant.setRestaurantId(cursor.getInt(cursor.getColumnIndex("restaurant_id")));
                        restaurant.setName(cursor.getString(cursor.getColumnIndex("name")));
                        restaurant.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        restaurant.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        restaurant.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        restaurant.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        restaurant.setLongitude(cursor.getFloat(cursor.getColumnIndex("longitude")));
                        restaurant.setLatitude(cursor.getFloat(cursor.getColumnIndex("latitude")));
                        restaurant.setDestination(destination);
                        restaurants.add(restaurant);
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

        return restaurants;
    }
    @SuppressLint("Range")
    public List<RestaurantModel> getAllRestaurant(int destinationId) {
        List<RestaurantModel> restaurants = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = databaseHelper.openDatabase();
            if (database != null) {
                String[] columns = {
                        "restaurant.*",
                        "destinations.destination_id",
                        "destinations.name AS destination_name",
                        "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM restaurant " +
                        "INNER JOIN destinations ON restaurant.destination_id = destinations.destination_id " +
                        "WHERE restaurant.destination_id = " + destinationId;

                cursor = database.rawQuery(query, null);

                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        RestaurantModel restaurant = new RestaurantModel();
                        restaurant.setRestaurantId(cursor.getInt(cursor.getColumnIndex("restaurant_id")));
                        restaurant.setName(cursor.getString(cursor.getColumnIndex("name")));
                        restaurant.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        restaurant.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        restaurant.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        restaurant.setLongitude(cursor.getFloat(cursor.getColumnIndex("longitude")));
                        restaurant.setLatitude(cursor.getFloat(cursor.getColumnIndex("latitude")));
                        restaurant.setDestination(destination);

                        restaurants.add(restaurant);
                    } while (cursor.moveToNext());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                databaseHelper.closeDatabase(database);
            }
        }
        return restaurants;
    }
    @SuppressLint("Range")
    public RestaurantModel getRestaurantById(int restaurantId){
        RestaurantModel restaurant = new RestaurantModel();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = databaseHelper.openDatabase();
            if (database != null) {
                String[] columns = {
                        "restaurant.*",
                        "destinations.destination_id",
                        "destinations.name AS destination_name",
                        "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM restaurant " +
                        "INNER JOIN destinations ON restaurant.destination_id = destinations.destination_id " +
                        "WHERE restaurant.restaurant_id = " + restaurantId;

                cursor = database.rawQuery(query, null);

                if (cursor.moveToFirst()) {
                    DestinationModel destination = new DestinationModel();
                    destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                    destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                    destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                    restaurant.setRestaurantId(cursor.getInt(cursor.getColumnIndex("restaurant_id")));
                    restaurant.setName(cursor.getString(cursor.getColumnIndex("name")));
                    restaurant.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    restaurant.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    restaurant.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                    restaurant.setLongitude(cursor.getFloat(cursor.getColumnIndex("longitude")));
                    restaurant.setLatitude(cursor.getFloat(cursor.getColumnIndex("latitude")));
                    restaurant.setDestination(destination);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                databaseHelper.closeDatabase(database);
            }
        }
        return restaurant;
    }
    @SuppressLint("Range")
    public List<RestaurantModel> getNearDestinationExcludingCurrent(int destinationId, int restaurantId) {
        List<RestaurantModel> restaurants = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = databaseHelper.openDatabase();
            if (database != null) {
                String[] columns = {
                        "restaurant.*",
                        "destinations.destination_id",
                        "destinations.name AS destination_name",
                        "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM restaurant " +
                        "INNER JOIN destinations ON restaurant.destination_id = destinations.destination_id " +
                        "WHERE restaurant.destination_id = " + destinationId + " AND restaurant.restaurant_id != " + restaurantId;

                cursor = database.rawQuery(query, null);

                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        RestaurantModel restaurant = new RestaurantModel();
                        restaurant.setRestaurantId(cursor.getInt(cursor.getColumnIndex("restaurant_id")));
                        restaurant.setName(cursor.getString(cursor.getColumnIndex("name")));
                        restaurant.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        restaurant.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        restaurant.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        restaurant.setLongitude(cursor.getFloat(cursor.getColumnIndex("longitude")));
                        restaurant.setLatitude(cursor.getFloat(cursor.getColumnIndex("latitude")));
                        restaurant.setDestination(destination);

                        restaurants.add(restaurant);
                    } while (cursor.moveToNext());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                databaseHelper.closeDatabase(database);
            }
        }
        return restaurants;
    }
    @SuppressLint("Range")
    public ArrayList<RestaurantModel> getCommon(int limit) {
        ArrayList<RestaurantModel> commonRestaurants = new ArrayList<RestaurantModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                //*TODO: Refactor this query to use rawQuery
                String[] columns = {
                        "restaurant.*",
                        "destinations.destination_id", "destinations.name AS destination_name", "destinations.image AS destination_image"
                };
                String query = "SELECT " + TextUtils.join(",", columns) + " FROM restaurant " +
                        "INNER JOIN destinations ON restaurant.destination_id = destinations.destination_id " +
                        "ORDER BY rating DESC LIMIT " + limit;

                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destination = new DestinationModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("destination_name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("destination_image")));

                        RestaurantModel tour = new RestaurantModel();
                        tour.setRestaurantId(cursor.getInt(cursor.getColumnIndex("restaurant_id")));
                        tour.setName(cursor.getString(cursor.getColumnIndex("name")));
                        tour.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        tour.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        tour.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        tour.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        tour.setDestination(destination);

                        commonRestaurants.add(tour);
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

        return commonRestaurants;
    }

}