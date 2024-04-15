package com.travel.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.TourModel;
import com.travel.Model.UserModel;
import com.travel.Model.WishlistModel;

import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());
    SQLiteDatabase database;
    UserDAO userDAO = new UserDAO();
    TourDAO tourDAO=new TourDAO();
    public WishlistDAO() {
    }
   /* public List<WishlistModel> getAllWishlist() {
        database = databaseHelper.openDatabase();
        List<WishlistModel> wishlistModels = new ArrayList<>();
        Cursor cursor = null;
        if (database != null) {
            try {
                cursor = database.query("wishlist", null, null, null, null, null, null);
                if (cursor!=null && cursor.moveToFirst()) {
                    do {
                        WishlistModel wishlistModel = new WishlistModel();
                        wishlistModel.setWishlistId(cursor.getInt(0));
                        wishlistModel.setUser(cursor.getInt(1));
                        wishlistModel.setTourId(cursor.getInt(2));
                        wishlistModels.add(wishlistModel);
                    } while (cursor.moveToNext());
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                databaseHelper.closeDatabase(database);
            }
        }
        return wishlistModels;
    }*/
    public void insert(int userId, int tourId) {
        database = databaseHelper.openDatabase();
        UserModel user=userDAO.getUser(userId);
        TourModel tour=tourDAO.getTourById(tourId);
        if (database != null) {
            try {
                database.execSQL("INSERT INTO wishlist (user_id, tour_id) VALUES (" + userId + ", " + tourId + ")");
            } finally {
                databaseHelper.closeDatabase(database);
            }
        }
    }
}
