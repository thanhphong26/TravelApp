package com.travel.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.Adapter.HistoryCardAdapter;
import com.travel.Database.HotelBookingDAO;
import com.travel.Database.RestaurantBookingDAO;
import com.travel.Database.TourBookingDAO;
import com.travel.Model.HotelBookingModel;
import com.travel.Model.HotelBookingReviewModel;
import com.travel.Model.RestaurantBookingModel;
import com.travel.Model.RestaurantBookingReviewModel;
import com.travel.Model.TourBookingModel;
import com.travel.Model.TourBookingReviewModel;
import com.travel.databinding.ActivityMyHistoryBinding;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ActivityMyHistoryBinding binding;
    TourBookingDAO tourBookingDAO = new TourBookingDAO();
    TourBookingModel tourBooking = new TourBookingModel();
    RestaurantBookingDAO restaurantDAO = new RestaurantBookingDAO();
    HotelBookingDAO hotelDAO = new HotelBookingDAO();
    List<HotelBookingModel> hotelBookingModels;
    List<RestaurantBookingModel> restaurantBookingModels;
    List<TourBookingModel> tourBookingModels;
    // write onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setupLayoutRecyclerView();
        this.displayTourBooking();
        this.displayRestaurantBooking();
        this.displayHotelBooking();
    }
    // create displayTourBooking method
    public void displayTourBooking() {
        List<TourBookingReviewModel> tourBookingModels = tourBookingDAO.getAllTourBookingsWithReview(2);
        HistoryCardAdapter<TourBookingReviewModel> historyCardAdapter = new HistoryCardAdapter<>(tourBookingModels, this);
        binding.recyclerViewHistoryTour.setAdapter(historyCardAdapter);
    }
    // create displayRestaurantBooking method
    public void displayRestaurantBooking() {
        List<RestaurantBookingReviewModel> restaurantBookingModels = restaurantDAO.getAllRestaurantBookingsWithReview(2);
        HistoryCardAdapter<RestaurantBookingReviewModel> historyCardAdapter = new HistoryCardAdapter<>(restaurantBookingModels, this);
        binding.recyclerViewHistoryRestaurant.setAdapter(historyCardAdapter);
    }
    public void displayHotelBooking(){
        List<HotelBookingReviewModel> hotelBookingModels = hotelDAO.getAllHotelBookingsWithReview(2);
        HistoryCardAdapter<HotelBookingReviewModel> historyCardAdapter1 = new HistoryCardAdapter<>(hotelBookingModels, this);
        binding.recyclerViewHistoryHotel.setAdapter(historyCardAdapter1);

    }
    //create setupLayoutRecyclerView method
    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHistoryTour = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewHistoryTour.setLayoutManager(layoutManagerHistoryTour);
        LinearLayoutManager layoutManagerHistoryHotel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewHistoryHotel.setLayoutManager(layoutManagerHistoryHotel);
        LinearLayoutManager layoutManagerHistoryRestaurant = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewHistoryRestaurant.setLayoutManager(layoutManagerHistoryRestaurant);
    }
}
