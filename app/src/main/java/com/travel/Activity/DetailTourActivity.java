package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.travel.Adapter.ImageTourDetailAdapter;
import com.travel.Adapter.ReviewAdapter;
import com.travel.Database.DatabaseHelper;
import com.travel.Database.ImageTourDAO;
import com.travel.Database.ReviewDAO;
import com.travel.Database.TourDAO;
import com.travel.Database.TourLineDAO;
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
    ImageTourDetailAdapter imageTourDetailAdapter;
    List<ImageTourModel> imageList=new ArrayList<ImageTourModel>();
    ReviewAdapter reviewAdapter;
    List<ReviewModel> reviewList=new ArrayList<ReviewModel>();
    List<TourLineModel> tourLineList=new ArrayList<TourLineModel>();
    TourLineDAO tourLineDAO=new TourLineDAO();
    ReviewDAO reviewDAO=new ReviewDAO();
    ImageTourDAO imageTourDAO=new ImageTourDAO();
    TourDAO tourDAO=new TourDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailTourBinding=ActivityDetailTourBinding.inflate(getLayoutInflater());
        setContentView(detailTourBinding.getRoot());
        detailTourBinding.viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
       // int tourId = 1;
        int tourId=getIntent().getIntExtra("tourId",1);
        tourModel=tourDAO.getTourById(tourId);
        detailTourBinding.txtNameTour.setText(tourModel.getName());
        detailTourBinding.txtRating.setText(String.valueOf(tourModel.getRating()));
        detailTourBinding.txtDescription.setText(tourModel.getDescription());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedMinValue = decimalFormat.format(tourModel.getPrice());
        detailTourBinding.txtPrice.setText("đ "+formattedMinValue);

        imageList=imageTourDAO.getImagesForTour(tourId);
        imageTourDetailAdapter=new ImageTourDetailAdapter(imageList,detailTourBinding.viewPager2);
        detailTourBinding.viewPager2.setAdapter(imageTourDetailAdapter);
        startAutoSlide(2500);

        reviewList=reviewDAO.getReviewsForTour(tourId);
        reviewAdapter=new ReviewAdapter(this,reviewList);
        detailTourBinding.recyclerViewReview.setAdapter(reviewAdapter);


        setRating((int) ratingAverage(reviewList));
        tourLineList=tourLineDAO.getTourLineList(tourId);
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
        detailTourBinding.txtRating.setText(String.valueOf(ratingAverage(reviewList)));
        detailTourBinding.txtCount.setText("("+reviewList.size()+" đánh giá)");
        String review=reviewList.isEmpty()?"Chưa có đánh giá": "Từ " + reviewList.size() + " đánh giá";
        detailTourBinding.txtCountRating.setText(review);
    }
    private void startAutoSlide(int intervalInMillis) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = detailTourBinding.viewPager2.getCurrentItem() + 1;
                if (nextItem >= imageTourDetailAdapter.getItemCount()) {
                    nextItem = 0;
                }
                detailTourBinding.viewPager2.setCurrentItem(nextItem, true);
                handler.postDelayed(this, intervalInMillis);
            }
        };

        handler.postDelayed(runnable, intervalInMillis);
        detailTourBinding.viewPager2.setTag(runnable);
    }
}