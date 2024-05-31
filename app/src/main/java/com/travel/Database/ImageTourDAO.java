package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.ImageTourModel;

import java.util.ArrayList;
import java.util.List;

public class ImageTourDAO {
    DatabaseHelper databaseHelper=new DatabaseHelper(App.self());
    SQLiteDatabase database;
    public ImageTourDAO() {
    }
    public List<ImageTourModel> getImagesForTour(int tourId) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        List<ImageTourModel> imageList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query("image_tours",
                    new String[]{"image_id", "tour_id", "image"},
                    "tour_id = ?",
                    new String[]{String.valueOf(tourId)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("image_id"));
                    @SuppressLint("Range") int fetchedTourId = cursor.getInt(cursor.getColumnIndex("tour_id"));
                    @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex("image"));
                    ImageTourModel imageTour = new ImageTourModel(imageId, fetchedTourId, imageUrl);
                    imageList.add(imageTour);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return imageList;
    }

}
