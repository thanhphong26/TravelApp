package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.travel.App;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.UserModel;
import com.travel.Utils.DateTimeHelper;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DestinationDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    ;
    SQLiteDatabase database;

    public DestinationDAO() {
    }
    @SuppressLint("Range")
    public DestinationModel getDestinationById(int id) {
        database = databaseHelper.openDatabase();
        DestinationModel destinationModel = new DestinationModel();
        Cursor cursor = null;
        if (database != null) {
            try {
                cursor = database.query("destinations", null, "destination_id= ?", new String[]{String.valueOf(id)}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    // Retrieve column indices
                    int destinationIdIndex = cursor.getColumnIndex("destination_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int imageIndex = cursor.getColumnIndex("image");
                    int createdAtIndex = cursor.getColumnIndex("created_at");
                    int descriptionIndex = cursor.getColumnIndex("description");

                    // Set DestinationModel properties from Cursor
                    destinationModel.setDestinationId(cursor.getInt(destinationIdIndex));
                    destinationModel.setName(cursor.getString(nameIndex));
                    destinationModel.setImage(cursor.getString(imageIndex));

                    // Convert createdAt String to Timestamp
                    String createdAtString = cursor.getString(createdAtIndex);
                    if (createdAtString != null) {
                        destinationModel.setCreatedAt(Timestamp.valueOf(createdAtString));
                    }

                    // Set description (if available)
                    if (descriptionIndex != -1) {  // Check if description column exists
                        destinationModel.setDescription(cursor.getString(descriptionIndex));
                    }
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
        return destinationModel;
    }

    @SuppressLint("Range")
    public ArrayList<DestinationDetailModel> getAll(String string, int pageSize, int pageNumber) {
        ArrayList<DestinationDetailModel> destinationModels = new ArrayList<DestinationDetailModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                String query = "SELECT destinations.*, ROUND( COALESCE(MIN(hotels.price), 0) + COALESCE(MIN(tours.price), 0) + COALESCE(MIN(restaurant.price), 0) , 1) AS min_price, ROUND( ( COALESCE(AVG(tours.rating), 0) + COALESCE(AVG(hotels.rating), 0) + COALESCE(AVG(restaurant.rating), 0) ) / ( CASE WHEN COUNT(tours.rating) > 0 THEN 1 ELSE 0 END + CASE WHEN COUNT(hotels.rating) > 0 THEN 1 ELSE 0 END + CASE WHEN COUNT(restaurant.rating) > 0 THEN 1 ELSE 0 END ) , 1) AS rating FROM destinations LEFT JOIN tours ON destinations.destination_id = tours.destination_id LEFT JOIN hotels ON destinations.destination_id = hotels.destination_id LEFT JOIN restaurant ON destinations.destination_id = restaurant.destination_id WHERE destinations.name LIKE '%' || ? || '%' GROUP BY destinations.destination_id ORDER BY rating DESC LIMIT ? OFFSET ?;";

                cursor = database.rawQuery(query, new String[]{string, String.valueOf(pageSize), String.valueOf((pageNumber - 1) * pageSize)});

                if (cursor.moveToFirst()) {
                    do {
                        DestinationDetailModel destination = new DestinationDetailModel();
                        destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destination.setName(cursor.getString(cursor.getColumnIndex("name")));
                        destination.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        destination.setCreatedAt(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("created_at"))));
                        destination.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                        destination.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        destination.setMinPrice(cursor.getFloat(cursor.getColumnIndex("min_price")));
                        destinationModels.add(destination);
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
        return destinationModels;
    }

    @SuppressLint("Range")
    public ArrayList<DestinationDetailModel> getDetailCommon(int limit) {
        ArrayList<DestinationDetailModel> destinationDetailModels = new ArrayList<DestinationDetailModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                String query = "SELECT \n" + "    destinations.*,\n" + "    ROUND(\n" + "        COALESCE(MIN(hotels.price), 0) +\n" + "        COALESCE(MIN(tours.price), 0) +\n" + "        COALESCE(MIN(restaurant.price), 0)\n" + "    , 1) AS min_price,\n" + "    ROUND(\n" + "        (\n" + "            COALESCE(AVG(tours.rating), 0) +\n" + "            COALESCE(AVG(hotels.rating), 0) +\n" + "            COALESCE(AVG(restaurant.rating), 0)\n" + "        ) /\n" + "        (\n" + "            CASE WHEN COUNT(tours.rating) > 0 THEN 1 ELSE 0 END +\n" + "            CASE WHEN COUNT(hotels.rating) > 0 THEN 1 ELSE 0 END +\n" + "            CASE WHEN COUNT(restaurant.rating) > 0 THEN 1 ELSE 0 END\n" + "        )\n" + "    , 1) AS rating\n" + "FROM destinations\n" + "LEFT JOIN tours ON destinations.destination_id = tours.destination_id\n" + "LEFT JOIN hotels ON destinations.destination_id = hotels.destination_id\n" + "LEFT JOIN restaurant ON destinations.destination_id = restaurant.destination_id\n" + "GROUP BY destinations.destination_id\n" + "ORDER BY rating DESC\n" + "LIMIT ?;";

                cursor = database.rawQuery(query, new String[]{String.valueOf(limit)});
                if (cursor.moveToFirst()) {
                    do {
                        DestinationDetailModel destinationDetail = new DestinationDetailModel();
                        destinationDetail.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                        destinationDetail.setName(cursor.getString(cursor.getColumnIndex("name")));
                        destinationDetail.setImage(cursor.getString(cursor.getColumnIndex("image")));
                        destinationDetail.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                        destinationDetail.setMinPrice(cursor.getFloat(cursor.getColumnIndex("min_price")));

                        destinationDetailModels.add(destinationDetail);
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
        return destinationDetailModels;
    }

    @SuppressLint("Range")
    public DestinationDetailModel getDestinationDetailById(int destinationId) {
        DestinationDetailModel destination = new DestinationDetailModel();
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        if (database != null) {
            try {
                String query = "SELECT destinations.*, ROUND( COALESCE(MIN(hotels.price), 0) + COALESCE(MIN(tours.price), 0) + COALESCE(MIN(restaurant.price), 0) , 1) AS min_price, ROUND( ( COALESCE(AVG(tours.rating), 0) + COALESCE(AVG(hotels.rating), 0) + COALESCE(AVG(restaurant.rating), 0) ) / ( CASE WHEN COUNT(tours.rating) > 0 THEN 1 ELSE 0 END + CASE WHEN COUNT(hotels.rating) > 0 THEN 1 ELSE 0 END + CASE WHEN COUNT(restaurant.rating) > 0 THEN 1 ELSE 0 END ) , 1) AS rating FROM destinations LEFT JOIN tours ON destinations.destination_id = tours.destination_id LEFT JOIN hotels ON destinations.destination_id = hotels.destination_id LEFT JOIN restaurant ON destinations.destination_id = restaurant.destination_id WHERE destinations.destination_id = ? ;";

                cursor = database.rawQuery(query, new String[]{String.valueOf(destinationId)});

                System.out.println("cursor DESTINATION: " + cursor.getCount());

                if (cursor.moveToFirst()) {
                    destination.setDestinationId(cursor.getInt(cursor.getColumnIndex("destination_id")));
                    destination.setName(cursor.getString(cursor.getColumnIndex("name")));
                    destination.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    destination.setCreatedAt(DateTimeHelper.convertStringToTimeStamp(cursor.getString(cursor.getColumnIndex("created_at"))));
                    destination.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    destination.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
                    destination.setMinPrice(cursor.getFloat(cursor.getColumnIndex("min_price")));
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
        return destination;
    }

}
