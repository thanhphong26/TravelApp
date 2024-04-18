package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDAO {
    DatabaseHelper databaseHelper=databaseHelper=new DatabaseHelper(App.self());
    SQLiteDatabase database;
    public UserDAO() {
    }
    public UserModel getUser(int userId){
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        UserModel user=new UserModel();
        if (database != null) {
            try {
                cursor = database.query("users", null, "user_id= ?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    user.setUserId(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setPhoneNumber(cursor.getString(2));
                    user.setEmail(cursor.getString(3));
                    user.setPassword(cursor.getString(4));
                    user.setAvatar(cursor.getString(6));
                    user.setGender(cursor.getString(7));
                    user.setAddress(cursor.getString(8));
                    @SuppressLint("Range") String dobString = cursor.getString(cursor.getColumnIndex("dob"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dob = dateFormat.parse(dobString);
                        user.setDob(dob);
                    } catch (ParseException e) {
                        e.printStackTrace();
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
        return user;
    }
}
