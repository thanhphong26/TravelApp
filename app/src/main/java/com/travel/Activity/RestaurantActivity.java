package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Adapter.RestaurantCommonAdapter;
import com.travel.Adapter.RestaurantFavoriteAdapter;
import com.travel.Adapter.TourCommonAdapter;
import com.travel.Adapter.TourFavoriteAdapter;
import com.travel.Database.DestinationDAO;
import com.travel.Database.RestaurantDAO;
import com.travel.Model.DestinationModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.databinding.ActivityRestaurantBinding;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RestaurantActivity extends AppCompatActivity {
    ActivityRestaurantBinding restaurantBinding;
    ArrayList<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();
    ArrayList<RestaurantModel> commonRestaurants = new ArrayList<RestaurantModel>();
    RestaurantDAO restaurantDAO = new RestaurantDAO();
    DestinationDAO destinationDAO = new DestinationDAO();
    int destinationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantBinding = ActivityRestaurantBinding.inflate(getLayoutInflater());
        setContentView(restaurantBinding.getRoot());

        this.setupLayoutRecyclerView();
        this.initHeaderEvent();
        this.initPage();
        this.handleSearchGlobal();
    }

    public void initPage() {
        destinationId = getIntent().getIntExtra("destinationId", 0);
        commonRestaurants = restaurantDAO.getCommon(Constants.COMMON_RECORD);
        restaurants = restaurantDAO.getAll("", Constants.MAX_RECORD, 0);

        if (destinationId != 0) {
            DestinationModel destination = destinationDAO.getDestinationById(destinationId);
            String title = restaurantBinding.title.getText().toString();
            restaurantBinding.title.setText(title + " - " + destination.getName());
        }

        this.handleListCommonRestaurant();
        this.handleListRestaurant();
        this.handleBottomNavigation();
    }

    public void initHeaderEvent() {
        ImageView img_back = (ImageView) findViewById(R.id.imgBack);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerCommon = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        restaurantBinding.rvCommonRestaurant.setLayoutManager(layoutManagerCommon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        restaurantBinding.rvRestaurant.setLayoutManager(layoutManager);
    }

    public void handleSearchGlobal() {
        restaurantBinding.searchRestaurant.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                restaurants = restaurantDAO.getAll(query.trim(), Constants.MAX_RECORD, 0);
                handleListRestaurant();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                restaurants = restaurantDAO.getAll(newText.trim(), Constants.MAX_RECORD, 0);
                handleListRestaurant();
                return false;
            }
        });
    }

    public void handleListCommonRestaurant() {
        if (destinationId != 0) {
            commonRestaurants = commonRestaurants.stream().filter(restaurant -> restaurant.getDestination().getDestinationId() == destinationId).collect(Collectors.toCollection(ArrayList::new));
        }
        RestaurantCommonAdapter<RestaurantModel> adapter = new RestaurantCommonAdapter<>(commonRestaurants, this);
        restaurantBinding.rvCommonRestaurant.setAdapter(adapter);
    }

    public void handleListRestaurant() {
        if (destinationId != 0) {
            restaurants = restaurants.stream().filter(restaurant -> restaurant.getDestination().getDestinationId() == destinationId).collect(Collectors.toCollection(ArrayList::new));
        }
        RestaurantFavoriteAdapter<RestaurantModel> adapter = new RestaurantFavoriteAdapter<>(restaurants, this);
        restaurantBinding.rvRestaurant.setAdapter(adapter);
    }

    private void handleBottomNavigation() {
        restaurantBinding.navigation.setItemIconTintList(null);
        restaurantBinding.navigation.setSelectedItemId(R.id.navigation_map);
        restaurantBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(RestaurantActivity.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(RestaurantActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    return true;
                } else if (id == R.id.navigation_translate) {
//                    intent = new Intent(HomeActivity.this, A.class);
                } else if (id == R.id.navigation_profile) {
                    intent = new Intent(RestaurantActivity.this, PersonalInforActivity.class);
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