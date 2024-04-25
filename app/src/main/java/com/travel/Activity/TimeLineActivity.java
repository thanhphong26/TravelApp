package com.travel.Activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.travel.Adapter.TourLineAdapter;
import com.travel.Database.DatabaseHelper;
import com.travel.Database.TourLineDAO;
import com.travel.Model.TourLineModel;
import com.travel.databinding.ActivityTimelineBinding;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity {
        ActivityTimelineBinding timelineBinding;
        TourLineAdapter tourLineAdapter;
        ArrayList<TourLineModel> tourLineList=new ArrayList<>();
        TourLineDAO tourLineDAO=new TourLineDAO();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            timelineBinding = ActivityTimelineBinding.inflate(getLayoutInflater());
            int destinationId=getIntent().getIntExtra("destinationId",0);
            Log.d("destinationId", String.valueOf(destinationId));
            int tourId = getIntent().getIntExtra("tourId", 0);
            tourLineList=tourLineDAO.getTourLineList(tourId);
            tourLineAdapter = new TourLineAdapter(tourLineList, this);
            timelineBinding.recyclerViewTimeline.setAdapter(tourLineAdapter);
            setContentView(timelineBinding.getRoot());
            timelineBinding.imgBack.setOnClickListener(v -> {
                Intent intent = new Intent(TimeLineActivity.this, DetailTourActivity.class);
                intent.putExtra("destinationId", destinationId);
                intent.putExtra("tourId", tourId);
                startActivity(intent);
            });
        }
}
