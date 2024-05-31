package com.travel.Database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travel.App;
import com.travel.Model.TourLineModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TourLineDAO {
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;
    public TourLineDAO() {
    }
    public ArrayList<TourLineModel> getTourLineList(int tourId) {
        database= databaseHelper.openDatabase();
        ArrayList<TourLineModel> tourLineList=new ArrayList<>();
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
    public List<TourLineModel> getAllTourLine(){
        database= databaseHelper.openDatabase();
        List<TourLineModel> tourLineList=new ArrayList<>();
        Cursor cursor=null;
        try{
            cursor=database.query("tour_line",null,null,null,null,null,null);
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
