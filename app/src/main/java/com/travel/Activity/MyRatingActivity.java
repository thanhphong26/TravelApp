package com.travel.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.travel.Adapter.HistoryCardAdapter;
import com.travel.Database.HotelBookingDAO;
import com.travel.Database.HotelDAO;
import com.travel.Database.RestaurantBookingDAO;
import com.travel.Database.ReviewDAO;
import com.travel.Database.TourBookingDAO;
import com.travel.Model.HotelBookingModel;
import com.travel.Model.HotelBookingReviewModel;
import com.travel.Model.RestaurantBookingModel;
import com.travel.Model.RestaurantBookingReviewModel;
import com.travel.Model.ReviewType;
import com.travel.Model.TourBookingModel;
import com.travel.Model.TourBookingReviewModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityMyHistoryBinding;
import com.travel.databinding.ActivityMyRatingBinding;

import java.util.List;

public class MyRatingActivity extends AppCompatActivity {
    ActivityMyRatingBinding binding;
    ReviewDAO reviewDAO = new ReviewDAO();
    HotelBookingDAO hotelBookingDAO = new HotelBookingDAO();
    RestaurantBookingDAO restaurantBookingDAO = new RestaurantBookingDAO();
    TourBookingDAO tourBookingDAO = new TourBookingDAO();

    Object booking = null;

    UserModel currentUser = new UserModel();
    int bookingId, itemId;
    String reviewType;

    // write onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.setDefaultValue();
        this.initHeader();
        this.initPage();
        this.handleReview();
    }

    private void handleReview() {
        binding.ratingButton.setOnClickListener(v -> {
            float rating = binding.ratingBar2.getRating();
            String comment = binding.review.getText().toString();
            reviewDAO.addReview(itemId, reviewType, currentUser.getUserId(), rating, comment);
            Intent intent = new Intent(MyRatingActivity.this, HistoryActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initPage() {
        if (reviewType.equals(ReviewType.TOUR.toString())) {
            booking = (TourBookingReviewModel) tourBookingDAO.getBookingById(bookingId);
            loadBookingDetail(((TourBookingReviewModel) booking).getTour().getImage(), ((TourBookingReviewModel) booking).getTour().getName(), ((TourBookingReviewModel) booking).getTour().getDescription());
        } else if (reviewType.equals(ReviewType.HOTEL.toString())) {
            booking = (HotelBookingReviewModel) hotelBookingDAO.getBookingById(bookingId);
            loadBookingDetail(((HotelBookingReviewModel) booking).getHotel().getImage(), ((HotelBookingReviewModel) booking).getHotel().getName(), ((HotelBookingReviewModel) booking).getHotel().getDescription());
        } else if (reviewType.equals(ReviewType.RESTAURANT.toString())) {
            booking = (RestaurantBookingReviewModel) restaurantBookingDAO.getBookingById(bookingId);
            loadBookingDetail(((RestaurantBookingReviewModel) booking).getRestaurant().getImage(), ((RestaurantBookingReviewModel) booking).getRestaurant().getName(), ((RestaurantBookingReviewModel) booking).getRestaurant().getDescription());
        }

        Glide.with(this).load(currentUser.getAvatar()).error(R.drawable.avatar).centerCrop().into(binding.avt.homeAvatar);
        binding.userName.setText(currentUser.getUsername());
    }

    private void setDefaultValue() {
        currentUser = SharePreferencesHelper.getInstance().get(Constants.USER_SHARE_PREFERENCES, UserModel.class);
        bookingId = getIntent().getIntExtra("bookingId", 0);
        itemId = getIntent().getIntExtra("itemId", 0);
        reviewType = getIntent().getStringExtra("type");
    }

    private void initHeader() {
        binding.imgBack.setOnClickListener(v -> {
            Intent intent = new Intent(MyRatingActivity.this, HistoryActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadBookingDetail(String image, String name, String description) {
        Glide.with(this).load(image).error(R.drawable.adventure).centerCrop().into(binding.tourImg.image);
        binding.tourName.setText(name);
        binding.tourDescription.setText(description);
    }
}
