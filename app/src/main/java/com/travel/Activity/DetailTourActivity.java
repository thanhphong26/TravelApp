package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.travel.Adapter.ImageTourDetailAdapter;
import com.travel.Adapter.ReviewAdapter;
import com.travel.Database.DatabaseHelper;
import com.travel.Database.ImageTourDAO;
import com.travel.Database.ReviewDAO;
import com.travel.Database.TourDAO;
import com.travel.Database.TourLineDAO;
import com.travel.Database.WishlistDAO;
import com.travel.Model.ImageTourModel;
import com.travel.Model.ReviewModel;
import com.travel.Model.ReviewType;
import com.travel.Model.TourLineModel;
import com.travel.Model.TourModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;
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
    WishlistDAO wishlistDAO;
    private boolean isFavorite;
    int destinationId;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailTourBinding=ActivityDetailTourBinding.inflate(getLayoutInflater());
        setContentView(detailTourBinding.getRoot());
        detailTourBinding.viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
       // int tourId = 1;
        destinationId=getIntent().getIntExtra("destinationId",0);
        int tourId=getIntent().getIntExtra("tourId",1);
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        wishlistDAO=new WishlistDAO(this);
        AppBarLayout appBarLayout = findViewById(com.travel.R.id.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView backIcon = findViewById(R.id.imgBack);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (Math.abs(verticalOffset) - appBarLayout1.getTotalScrollRange() == 0) {
                toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
                backIcon.setColorFilter(Color.parseColor("#000000"));
            } else {
                toolbar.setBackgroundColor(Color.parseColor("#00000000"));
                backIcon.setColorFilter(Color.parseColor("#ffffff"));
            }
        });

        tourModel=tourDAO.getTourById(tourId);
        isFavorite = wishlistDAO.checkFavoriteTour(tourModel.getTourId(), userModel.getUserId());
        setHeartColor(detailTourBinding.fabLove, isFavorite);
        detailTourBinding.fabLove.setOnClickListener(v -> addToWhislist(tourModel,userModel));
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

        detailTourBinding.btnItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTourActivity.this, TimeLineActivity.class);
                intent.putExtra("tourId", tourId);
                startActivity(intent);

            }
        });

        detailTourBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTourActivity.this, DetailDestinationActivity.class);
                intent.putExtra("destinationId", destinationId);
                startActivity(intent);
            }
        });
    }
    private void addToWhislist(TourModel tourModel, UserModel userModel) {
        isFavorite = !isFavorite;
        if (isFavorite) {
            wishlistDAO.insertTourWhishlist(userModel.getUserId(), tourModel.getTourId());
            showSnackbar("Đã thêm vào danh sách yêu thích");
        } else {
            wishlistDAO.removeWhishlistTourId(userModel.getUserId(), tourModel.getTourId());
            showSnackbar("Đã xóa khỏi danh sách yêu thích");
        }
        setHeartColor(detailTourBinding.fabLove, isFavorite);
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
    private void setHeartColor(ImageView imageView, boolean isHeartRed) {
        if (isHeartRed) {
            imageView.setImageResource(R.drawable.red_heart);
        } else {
            imageView.setImageResource(R.drawable.heart);
        }
    }
    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(204, 153, 255)));
        snackbar.show();
    }
}