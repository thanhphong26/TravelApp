package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.travel.Database.HotelDAO;
import com.travel.Database.RestaurantDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.databinding.ActivitySearchBinding;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding searchBinding;
    TourDAO tourDAO = new TourDAO();
    HotelDAO hotelDAO = new HotelDAO();
    RestaurantDAO restaurantDAO = new RestaurantDAO();
    ArrayList<TourModel> matchedTours = new ArrayList<TourModel>();
    ArrayList<RestaurantModel> matchedRestaurants = new ArrayList<RestaurantModel>();
    ArrayList<HotelModel> matchedHotels = new ArrayList<HotelModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(searchBinding.getRoot());

        this.handleSearchGlobal();
    }

    public void handleSearchGlobal() {
        Intent intent = getIntent();
        String query = intent.getStringExtra("search");
        searchBinding.searchDetail.setIconified(false);
        searchBinding.searchDetail.setQuery(query, false);

        matchedHotels = hotelDAO.getAll(query, 5, 1);
        matchedRestaurants = restaurantDAO.getAll(query, 5, 1);
        matchedTours = tourDAO.getAll(query, 5, 1);

        //*TODO: Handle load data to UI
    }
}