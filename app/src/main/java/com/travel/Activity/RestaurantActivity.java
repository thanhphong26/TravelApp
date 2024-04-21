package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.Adapter.RestaurantCommonAdapter;
import com.travel.Adapter.RestaurantFavoriteAdapter;
import com.travel.Adapter.TourCommonAdapter;
import com.travel.Adapter.TourFavoriteAdapter;
import com.travel.Database.RestaurantDAO;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.databinding.ActivityRestaurantBinding;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {
    ActivityRestaurantBinding restaurantBinding;
    ArrayList<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();
    ArrayList<RestaurantModel> commonRestaurants = new ArrayList<RestaurantModel>();
    RestaurantDAO restaurantDAO = new RestaurantDAO();

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
        commonRestaurants = restaurantDAO.getCommon(5);
        restaurants = restaurantDAO.getAll("", 10, 0);

        this.handleListCommonRestaurant();
        this.handleListRestaurant();
    }

    public void initHeaderEvent() {
        ImageView img_back = (ImageView) findViewById(R.id.imgBack);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, HomeActivity.class);
                startActivity(intent);
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
                restaurants = restaurantDAO.getAll(query, 10, 0);
                handleListRestaurant();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                restaurants = restaurantDAO.getAll(newText, 10, 0);
                handleListRestaurant();
                return false;
            }
        });
    }

    public void handleListCommonRestaurant() {
        RestaurantCommonAdapter<RestaurantModel> adapter = new RestaurantCommonAdapter<>(commonRestaurants, this);
        restaurantBinding.rvCommonRestaurant.setAdapter(adapter);
    }

    public void handleListRestaurant() {
        RestaurantFavoriteAdapter<RestaurantModel> adapter = new RestaurantFavoriteAdapter<>(restaurants, this);
        restaurantBinding.rvRestaurant.setAdapter(adapter);
    }
}