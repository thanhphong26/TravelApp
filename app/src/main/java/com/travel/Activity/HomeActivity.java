package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Adapter.DestinationCommonRatingAdapter;
import com.travel.Database.DestinationDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.TourModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.NumberHelper;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityHomeBinding;
import com.travel.databinding.TourCardBinding;
import com.travel.databinding.TourFavoriteCardBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding homeBinding;
    BottomNavigationView bottomNavigationView;
    UserModel currentUser = null;
    ArrayList<DestinationModel> destinations = new ArrayList<DestinationModel>();
    ArrayList<DestinationDetailModel> destinationDetails = new ArrayList<DestinationDetailModel>();
    DestinationDAO destinationDAO = new DestinationDAO();
    TourDAO tourDAO = new TourDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
//        homeBinding.navigation.setItemIconTintList(null);

        this.setupLayoutRecyclerView();
        this.getDefaultValue();
        this.initPageOpen();
        this.handleSearchGlobal();
        this.handleListCommonTour();
    }

    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        homeBinding.recyclerViewCommonTour.setLayoutManager(layoutManager);
    }

    public void getDefaultValue() {
        currentUser = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        destinations = destinationDAO.getAll();
        destinationDetails = destinationDAO.getDetailCommon(5);
    }

    public void initPageOpen() {
        //*INFO: Load image from url
        Glide.with(this).load(currentUser.getAvatar()).error(R.drawable.avatar).centerCrop().into(homeBinding.avatar.homeAvatar);
        homeBinding.username.setText("Chào, " + currentUser.getUsername());


        //*INFO: Set onclick product
        homeBinding.tourPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open another layout here
                Intent intent = new Intent(HomeActivity.this, TourActivity.class);
                startActivity(intent);
            }
        });

        homeBinding.hotelPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open another layout here
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");
                Intent intent = new Intent(HomeActivity.this, HotelActivity.class);
                startActivity(intent);
            }
        });

        homeBinding.restaurantPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open another layout here
                Intent intent = new Intent(HomeActivity.this, RestaurantActivity.class);
                startActivity(intent);
            }
        });

        homeBinding.flightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open another layout here
                Intent intent = new Intent(HomeActivity.this, FlightActivity.class);
                startActivity(intent);
            }
        });
    }

    public void handleSearchGlobal() {
        // handle get search string
        homeBinding.searchGlobal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Open another layout here
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("search", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    public void handleListCommonTour() {
        DestinationCommonRatingAdapter<DestinationDetailModel> adapter = new DestinationCommonRatingAdapter<>(destinationDetails, this);
        homeBinding.recyclerViewCommonTour.setAdapter(adapter);
    }
}