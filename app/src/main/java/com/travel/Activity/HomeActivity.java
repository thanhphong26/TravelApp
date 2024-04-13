package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Database.DestinationDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.DestinationModel;
import com.travel.Model.TourModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding homeBinding;
    BottomNavigationView bottomNavigationView;
    UserModel currentUser = null;
    ArrayList<DestinationModel> destinations = new ArrayList<DestinationModel>();
    ArrayList<TourModel> commonTours = new ArrayList<TourModel>();
    DestinationDAO destinationDAO = new DestinationDAO();
    TourDAO tourDAO = new TourDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        homeBinding.navigation.setItemIconTintList(null);


        this.getDefaultValue();
        this.initPageOpen();
        this.handleSearchGlobal();
        this.handleClickTour();

        // print out data tourCommon
        for (TourModel tour : commonTours) {
            System.out.println("TOUR " + tour.getName());
            System.out.println("Destination " + tour.getDestination().getImage());
        }
    }

    public void getDefaultValue() {
        currentUser = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        destinations = destinationDAO.getAll();
        commonTours = tourDAO.getCommon(5);
    }

    public void initPageOpen() {
        //*INFO: Load image from url
        Glide.with(this).load(currentUser.getAvatar()).centerCrop().into(homeBinding.avatar.homeAvatar);
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
                Intent intent = new Intent(HomeActivity.this, DestinationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void handleSearchGlobal() {
        SearchView search = (SearchView) findViewById(R.id.search_global);

        // handle get search string
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    public void handleClickTour() {
        // handle click LinearLayout tour then open DetailTourActivity
        LinearLayout ly_tour = (LinearLayout) findViewById(R.id.tour_card);
        ly_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DetailTourActivity.class);
                startActivity(intent);
            }
        });
    }
}