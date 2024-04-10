package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.travel.Adapter.ImageTourDetailAdapter;
import com.travel.Adapter.ReviewAdapter;
import com.travel.Database.DatabaseHelper;
import com.travel.Model.ImageTourModel;
import com.travel.Model.ReviewModel;
import com.travel.Model.ReviewType;
import com.travel.Model.TourLineModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.databinding.ActivityDetailDestinationBinding;
import com.travel.databinding.ActivityDetailTourBinding;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailTourActivity extends AppCompatActivity {
    private boolean isHeartRed = false;
    ActivityDetailTourBinding detailTourBinding;
    TourModel tourModel=new TourModel();
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    ImageTourDetailAdapter imageTourDetailAdapter;
    List<ImageTourModel> imageList=new ArrayList<ImageTourModel>();
    ReviewAdapter reviewAdapter;
    List<ReviewModel> reviewList=new ArrayList<ReviewModel>();
    List<TourLineModel> tourLineList=new ArrayList<TourLineModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailTourBinding=ActivityDetailTourBinding.inflate(getLayoutInflater());
        setContentView(detailTourBinding.getRoot());
        detailTourBinding.viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        databaseHelper = new DatabaseHelper(this);
        int tourId = 1;

        getTourById(tourId);
        detailTourBinding.txtNameTour.setText(tourModel.getName());
        detailTourBinding.txtRating.setText(String.valueOf(tourModel.getRating()));
        detailTourBinding.txtDescription.setText(tourModel.getDescription());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedMinValue = decimalFormat.format(tourModel.getPrice());
        detailTourBinding.txtPrice.setText("đ "+formattedMinValue);

        imageList=getImagesForTour(tourId);
        imageTourDetailAdapter=new ImageTourDetailAdapter(imageList,detailTourBinding.viewPager2);
        detailTourBinding.viewPager2.setAdapter(imageTourDetailAdapter);
        reviewList=getReviewsForTour(tourId);
        reviewAdapter=new ReviewAdapter(this,reviewList);
        detailTourBinding.recyclerViewReview.setAdapter(reviewAdapter);

        setRating((int) ratingAverage(reviewList));
        getTourLineList(tourId);
        setTourLine();
        detailTourBinding.fabLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHeartRed = !isHeartRed;
                if (isHeartRed) {
                    detailTourBinding.fabLove.setImageResource(R.drawable.red_heart);
                } else {
                    detailTourBinding.fabLove.setImageResource(R.drawable.heart);
                }
            }
        });

        detailTourBinding.btnItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTourActivity.this, TimeLineActivity.class);
                intent.putExtra("tourId", tourId);
                startActivity(intent);

            }
        });
    }
    public void getTourById(int id) {
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                cursor = database.query("tours", null, "tour_id= ?", new String[]{String.valueOf(id)}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    tourModel.setTourId(cursor.getInt(0));
                    tourModel.setName(cursor.getString(2));
                    tourModel.setDescription(cursor.getString(3));
                    tourModel.setImage(cursor.getString(4));
                    tourModel.setRating(cursor.getFloat(5));
                    tourModel.setPrice(cursor.getFloat(6));
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
    }
    public List<ImageTourModel> getImagesForTour(int tourId) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
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

    public List<ReviewModel> getReviewsForTour(int tourId) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query("reviews",
                    new String[]{"review_id", "user_id", "review_type", "item_id", "review", "rating", "reviewDate"},
                    "review_type='tour' and item_id = ?",
                    new String[]{String.valueOf(tourId)},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int reviewId = cursor.getInt(cursor.getColumnIndex("review_id"));
                    @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                    @SuppressLint("Range") String reviewType = cursor.getString(cursor.getColumnIndex("review_type"));
                    @SuppressLint("Range") int itemId = cursor.getInt(cursor.getColumnIndex("item_id"));
                    @SuppressLint("Range") String review = cursor.getString(cursor.getColumnIndex("review"));
                    @SuppressLint("Range") int rating = cursor.getInt(cursor.getColumnIndex("rating"));
                    @SuppressLint("Range") String dateTimeString = cursor.getString(cursor.getColumnIndex("reviewDate"));
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = inputFormat.parse(dateTimeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (date != null) {
                        Timestamp reviewDate = new Timestamp(date.getTime());
                        ReviewModel reviewModel = new ReviewModel(reviewId, userId, ReviewType.TOUR, itemId, review, rating, reviewDate);
                        reviewList.add(reviewModel);
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return reviewList;
    }
    public List<TourLineModel> getTourLineList(int tourId){
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
    public void setTourLine(){
        detailTourBinding.txtTime.setText(tourLineList.get(0).getTime().toString()+"-"+tourLineList.get(tourLineList.size()-1).getEndTime().toString());
        detailTourBinding.txtPlace.setText(tourLineList.get(0).getLocationName());
        if (tourLineList.get(0) == tourLineList.get(tourLineList.size() - 1)) {
            detailTourBinding.txtPlace1.setVisibility(View.GONE);
        } else {
            detailTourBinding.txtPlace1.setText(tourLineList.get(tourLineList.size() - 1).getLocationName());
        }
    }
    public float ratingAverage(List<ReviewModel> reviewList){
        float totalRating=0;
        for (ReviewModel reviewModel:reviewList){
            totalRating+=reviewModel.getRating();
        }
        return totalRating/reviewList.size();
    }
    public void setRating(int ratingAverage){
        detailTourBinding.txtRatingAverage.setText(String.valueOf(ratingAverage));
        detailTourBinding.ratingBar2.setRating(ratingAverage);
        String review=reviewList.isEmpty()?"Chưa có đánh giá": "Từ " + reviewList.size() + " đánh giá";
        detailTourBinding.txtCountRating.setText(review);
    }

}