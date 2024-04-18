package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.LoyaltyPointModel;
import com.travel.Model.UserModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoyaltyPointDAO{
    DatabaseHelper databaseHelper=new DatabaseHelper(App.self());
    SQLiteDatabase database;
    public LoyaltyPointDAO() {
    }
    @SuppressLint("Range")
    public LoyaltyPointModel getUserPoints(int userId) {
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        LoyaltyPointModel totalPoints = new LoyaltyPointModel();
        if (database != null) {
            try {
                cursor = database.query("loyalty_points", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int pointId = cursor.getInt(cursor.getColumnIndex("point_id"));
                    int pointsValue = cursor.getInt(cursor.getColumnIndex("points"));
                    totalPoints = new LoyaltyPointModel(pointId, userId, pointsValue);
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
        return totalPoints;
    }
    @SuppressLint("Range")
    public List<LoyaltyPointModel> getAllPoint(int userId){
        database = databaseHelper.openDatabase();
        Cursor cursor = null;
        List<LoyaltyPointModel> listPoint=new ArrayList<LoyaltyPointModel>();
        if (database != null) {
            try {
                // cursor get all data from loyalty_points table with user_id
                cursor = database.query("loyalty_points", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    System.out.println("cursor"+ cursor.getCount());
                    LoyaltyPointModel loyaltyPointModel=new LoyaltyPointModel();
                    int pointId = cursor.getInt(cursor.getColumnIndex("point_id"));
                    int pointsValue = cursor.getInt(cursor.getColumnIndex("points"));
                    Timestamp createdAt = Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("created_at")));
                    loyaltyPointModel = new LoyaltyPointModel(pointId, userId, pointsValue,createdAt);
                    listPoint.add(loyaltyPointModel);
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
        return listPoint;
    }

}
