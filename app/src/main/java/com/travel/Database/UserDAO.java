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
                    @SuppressLint("Range") String dobString = cursor.getString(cursor.getColumnIndex("date_of_birth"));
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
    public void updateImageProfile(UserModel user){
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                database.execSQL("UPDATE users SET avatar = ? WHERE user_id = ?", new String[]{user.getAvatar(), String.valueOf(user.getUserId())});
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }

    public void updateUserName(UserModel user){
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                database.execSQL("UPDATE users SET username = ? WHERE user_id = ?", new String[]{user.getUsername(), String.valueOf(user.getUserId())});
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }
    public void updateDob(UserModel user){
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dobString = dateFormat.format(user.getDob());
                database.execSQL("UPDATE users SET date_of_birth = ? WHERE user_id = ?", new String[]{dobString, String.valueOf(user.getUserId())});
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }
    public void updatePassword(UserModel user){
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                database.execSQL("UPDATE users SET password = ? WHERE user_id = ?", new String[]{user.getPassword(), String.valueOf(user.getUserId())});
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }
    public void updateEmail(UserModel user){
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                database.execSQL("UPDATE users SET email = ? WHERE user_id = ?", new String[]{user.getEmail(), String.valueOf(user.getUserId())});
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }
    public void updatePhoneNumber(UserModel user){
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                database.execSQL("UPDATE users SET phone_number = ? WHERE user_id = ?", new String[]{user.getPhoneNumber(), String.valueOf(user.getUserId())});
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }
    public void updateAddress(UserModel user){
        database = databaseHelper.openDatabase();
        if (database != null) {
            try {
                database.execSQL("UPDATE users SET address = ? WHERE user_id = ?", new String[]{user.getAddress(), String.valueOf(user.getUserId())});
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }
}
