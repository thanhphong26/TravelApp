package com.travel.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.Adapter.HistoryCardAdapter;
import com.travel.Database.TourBookingDAO;
import com.travel.Model.TourBookingModel;
import com.travel.databinding.ActivityMyHistoryBinding;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ActivityMyHistoryBinding binding;
    TourBookingDAO tourBookingDAO = new TourBookingDAO();
    TourBookingModel tourBooking = new TourBookingModel();
    List<TourBookingModel> tourBookingModels;
    // write onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setupLayoutRecyclerView();
        this.displayTourBooking();
    }
    // create displayTourBooking method
    public void displayTourBooking() {
        List<TourBookingModel> tourBookingModels = tourBookingDAO.getAllTourBookings();
        System.out.println("Tour Booking: " + tourBookingModels.size());
        HistoryCardAdapter<TourBookingModel> historyCardAdapter = new HistoryCardAdapter<>(tourBookingModels, this);
        binding.recyclerViewHistory.setAdapter(historyCardAdapter);
    }
    //create setupLayoutRecyclerView method
    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHistory = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewHistory.setLayoutManager(layoutManagerHistory);
    }
}
