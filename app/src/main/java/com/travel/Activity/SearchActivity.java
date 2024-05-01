package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Adapter.HotelCommonAdapter;
import com.travel.Adapter.HotelFavoriteAdapter;
import com.travel.Adapter.RestaurantFavoriteAdapter;
import com.travel.Adapter.TourFavoriteAdapter;
import com.travel.Database.HotelDAO;
import com.travel.Database.RestaurantDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.NumberHelper;
import com.travel.databinding.ActivitySearchBinding;
import com.travel.databinding.HotelFavoriteCardBinding;
import com.travel.databinding.RestaurantFavoriteCardBinding;
import com.travel.databinding.TourFavoriteCardBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Optional;

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
        Intent intent = getIntent();
        String query = Optional.ofNullable(intent.getStringExtra("search")).orElse("");

        this.setupLayoutRecyclerView();
        this.initPage();
        this.handleSearchGlobal(query);
    }

    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchBinding.hotelRecycleView.setLayoutManager(layoutManagerHotel);
        LinearLayoutManager layoutManagerTour = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchBinding.tourRecycleView.setLayoutManager(layoutManagerTour);
        LinearLayoutManager layoutManagerRestaurant = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchBinding.restaurantRecycleView.setLayoutManager(layoutManagerRestaurant);
    }

    public void handleSearchGlobal(String query) {
        searchBinding.searchDetail.setIconified(false);
        searchBinding.searchDetail.setQuery(query, false);

        matchedHotels = hotelDAO.getAll(query, Constants.MAX_RECORD, 1);
        matchedRestaurants = restaurantDAO.getAll(query, Constants.MAX_RECORD, 1);
        matchedTours = tourDAO.getAll(query, Constants.MAX_RECORD, 1);

        //*INFO: load searched data to UI
        this.handleListTour();
        this.handleListHotel();
        this.handleListRestaurant();
        this.handleBottomNavigation();
    }

    public void handleListTour() {
        if (matchedTours.size() == 0) {
            searchBinding.titleTour.setVisibility(View.GONE);
        } else {
            searchBinding.titleTour.setVisibility(View.VISIBLE);
        }
        TourFavoriteAdapter<TourModel> adapter = new TourFavoriteAdapter<>(matchedTours, this);
        searchBinding.tourRecycleView.setAdapter(adapter);
    }

    public void handleListHotel() {
        if (matchedHotels.size() == 0) {
            searchBinding.titleHotel.setVisibility(View.GONE);
        } else {
            searchBinding.titleHotel.setVisibility(View.VISIBLE);
        }
        HotelFavoriteAdapter<HotelModel> adapter = new HotelFavoriteAdapter<>(matchedHotels, this);
        searchBinding.hotelRecycleView.setAdapter(adapter);
    }

    public void handleListRestaurant() {
        if (matchedRestaurants.size() == 0) {
            searchBinding.titleRestaurant.setVisibility(View.GONE);
        } else {
            searchBinding.titleRestaurant.setVisibility(View.VISIBLE);
        }
        RestaurantFavoriteAdapter<RestaurantModel> adapter = new RestaurantFavoriteAdapter<>(matchedRestaurants, this);
        searchBinding.restaurantRecycleView.setAdapter(adapter);
    }

    public void initPage() {
        searchBinding.searchDetail.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearchGlobal(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void handleBottomNavigation() {
        searchBinding.navigation.setItemIconTintList(null);
        searchBinding.navigation.setSelectedItemId(R.id.navigation_map);
        searchBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(SearchActivity.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(SearchActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    return true;
                }else if (id == R.id.navigation_translate) {
                    intent = new Intent(SearchActivity.this, MapsActivity2.class);
                }
                else if (id == R.id.navigation_profile) {
                    intent = new Intent(SearchActivity.this, PersonalInforActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
    }
}