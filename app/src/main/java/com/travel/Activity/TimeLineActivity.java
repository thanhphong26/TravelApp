package com.travel.Activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.travel.Adapter.TourLineAdapter;
import com.travel.Database.DatabaseHelper;
import com.travel.Model.TourLineModel;
import com.travel.databinding.ActivityTimelineBinding;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity {
        ActivityTimelineBinding timelineBinding;
        TourLineAdapter tourLineAdapter;
        DatabaseHelper databaseHelper;
        SQLiteDatabase database;
        ArrayList<TourLineModel> tourLineList=new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            timelineBinding = ActivityTimelineBinding.inflate(getLayoutInflater());
            databaseHelper = new DatabaseHelper(this);
            int tourId = getIntent().getIntExtra("tourId", 0);
            getTourLineList(tourId);
            tourLineAdapter = new TourLineAdapter(tourLineList, this);
            timelineBinding.recyclerViewTimeline.setAdapter(tourLineAdapter);
            setContentView(timelineBinding.getRoot());
        }
        public ArrayList<TourLineModel> getTourLineList(int tourId) {
            database= databaseHelper.openDatabase();
            Cursor cursor=null;
            try{
                cursor=database.query("tour_line",null,"tour_id = ?",new String[]{String.valueOf(tourId)},null,null,null);
                if(cursor!=null && cursor.moveToFirst()){
                    do{
                        @SuppressLint("Range") int tourLineId=cursor.getInt(cursor.getColumnIndex("itinerary_id"));
                        @SuppressLint("Range") int tourID=cursor.getInt(cursor.getColumnIndex("tour_id"));
                        @SuppressLint("Range") String tourLineName=cursor.getString(cursor.getColumnIndex("location_name"));
                        @SuppressLint("Range") String time=cursor.getString(cursor.getColumnIndex("time"));
                        @SuppressLint("Range") String endTime=cursor.getString(cursor.getColumnIndex("end_time"));
                        @SuppressLint("Range") String image=cursor.getString(cursor.getColumnIndex("image"));
                        @SuppressLint("Range") float latitude=cursor.getFloat(cursor.getColumnIndex("latitude"));
                        @SuppressLint("Range") float longitude=cursor.getFloat(cursor.getColumnIndex("longitude"));
                        TourLineModel tourLineModel=new TourLineModel(tourLineId,tourID,tourLineName, Time.valueOf(time),Time.valueOf(endTime),image,latitude,longitude);
                        tourLineList.add(tourLineModel);
                    }while(cursor.moveToNext());
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }finally {
                if (cursor != null) {
                    cursor.close();
                }
                databaseHelper.closeDatabase(database);
            }
            return tourLineList;
        }
}
