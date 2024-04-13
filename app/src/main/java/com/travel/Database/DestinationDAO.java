package com.travel.Database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.DestinationModel;
import com.travel.Model.UserModel;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DestinationDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;

    public DestinationDAO() {
    }

    public ArrayList<DestinationModel> getAll() {
        ArrayList<DestinationModel> destinationModels = new ArrayList<DestinationModel>();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                cursor = database.query("destinations", null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        DestinationModel destinationModel = new DestinationModel();
                        destinationModel.setDestinationId(cursor.getInt(0));
                        destinationModel.setName(cursor.getString(1));
                        destinationModel.setImage(cursor.getString(2));
                        destinationModel.setCreatedAt(Timestamp.valueOf(cursor.getString(3)));
                        destinationModels.add(destinationModel);
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

}
