package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.Adapter.HistoryCardAdapter;
import com.travel.Database.BookFlightDAO;
import com.travel.Database.HotelBookingDAO;
import com.travel.Database.RestaurantBookingDAO;
import com.travel.Database.TourBookingDAO;
import com.travel.Model.BookFlightModel;
import com.travel.Model.HotelBookingModel;
import com.travel.Model.HotelBookingReviewModel;
import com.travel.Model.RestaurantBookingModel;
import com.travel.Model.RestaurantBookingReviewModel;
import com.travel.Model.TourBookingModel;
import com.travel.Model.TourBookingReviewModel;
import com.travel.Model.UserModel;
import com.travel.Utils.Constants;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityMyHistoryBinding;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ActivityMyHistoryBinding binding;
    TourBookingDAO tourBookingDAO = new TourBookingDAO();
    RestaurantBookingDAO restaurantDAO = new RestaurantBookingDAO();
    HotelBookingDAO hotelDAO = new HotelBookingDAO();
    BookFlightDAO flightBookingDAO = new BookFlightDAO();

    UserModel userModel;
    // write onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.initPage();
        this.setupLayoutRecyclerView();
        this.displayTourBooking();
        this.displayRestaurantBooking();
        this.displayHotelBooking();
        this.displayFlightBooking();
        this.initHeader();
    }

    private void initPage() {
        this.setDefaultData();
    }

    private void setDefaultData() {
        userModel = SharePreferencesHelper.getInstance().get(Constants.USER_SHARE_PREFERENCES, UserModel.class);
    }

    private void initHeader() {
        binding.imgBack.setOnClickListener(v -> {
            startActivity(new Intent(HistoryActivity.this, AccountActivity.class));
            finish();
        });
    }

    // create displayTourBooking method
    public void displayTourBooking() {
        List<TourBookingReviewModel> tourBookingModels = tourBookingDAO.getAllTourBookingsWithReview(userModel.getUserId());
        HistoryCardAdapter<TourBookingReviewModel> historyCardAdapter = new HistoryCardAdapter<>(tourBookingModels, this);
        binding.recyclerViewHistoryTour.setAdapter(historyCardAdapter);
    }

    // create displayRestaurantBooking method
    public void displayRestaurantBooking() {
        List<RestaurantBookingReviewModel> restaurantBookingModels = restaurantDAO.getAllRestaurantBookingsWithReview(userModel.getUserId());
        HistoryCardAdapter<RestaurantBookingReviewModel> historyCardAdapter = new HistoryCardAdapter<>(restaurantBookingModels, this);
        binding.recyclerViewHistoryRestaurant.setAdapter(historyCardAdapter);
    }

    // create displayHotelBooking method
    public void displayHotelBooking(){
        List<HotelBookingReviewModel> hotelBookingModels = hotelDAO.getAllHotelBookingsWithReview(userModel.getUserId());
        HistoryCardAdapter<HotelBookingReviewModel> historyCardAdapter1 = new HistoryCardAdapter<>(hotelBookingModels, this);
        binding.recyclerViewHistoryHotel.setAdapter(historyCardAdapter1);
    }

    // create displayFlightBooking method
    public void displayFlightBooking(){
        List<BookFlightModel> flightBookingModels = flightBookingDAO.getAll(userModel.getUserId());
        HistoryCardAdapter<BookFlightModel> historyCardAdapter = new HistoryCardAdapter<BookFlightModel>(flightBookingModels, this);
        binding.recyclerViewHistoryFlight.setAdapter(historyCardAdapter);
    }

    //create setupLayoutRecyclerView method
    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHistoryTour = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewHistoryTour.setLayoutManager(layoutManagerHistoryTour);
        LinearLayoutManager layoutManagerHistoryHotel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewHistoryHotel.setLayoutManager(layoutManagerHistoryHotel);
        LinearLayoutManager layoutManagerHistoryRestaurant = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewHistoryRestaurant.setLayoutManager(layoutManagerHistoryRestaurant);
        LinearLayoutManager layoutManagerHistoryFlight = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewHistoryFlight.setLayoutManager(layoutManagerHistoryFlight);
    }
}
