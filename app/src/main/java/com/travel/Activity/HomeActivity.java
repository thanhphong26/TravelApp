package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Database.DestinationDAO;
import com.travel.Model.DestinationModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    UserModel currentUser = null;
    ArrayList<DestinationModel> destinationModels = new ArrayList<DestinationModel>();
    DestinationDAO destinationDAO = new DestinationDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);


        this.getDefaultValue();
        this.initPageOpen();
        this.handleSearchGlobal();
        this.handleClickTour();
    }

    public void getDefaultValue() {
        currentUser = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        destinationModels = destinationDAO.getAll();
    }

    public void initPageOpen() {
        LinearLayout ly_tour = (LinearLayout) findViewById(R.id.tour_page);
        LinearLayout ly_hotel = (LinearLayout) findViewById(R.id.hotel_page);
        LinearLayout ly_restaurant = (LinearLayout) findViewById(R.id.restaurant_page);
        LinearLayout ly_flight = (LinearLayout) findViewById(R.id.flight_page);
        TextView tv_username = (TextView) findViewById(R.id.username);
        ImageView avatar = (ImageView) findViewById(R.id.home_avatar);

        //*INFO: Load image from url
        Glide.with(this).load(currentUser.getAvatar()).centerCrop().into(avatar);
        tv_username.setText("Chào, " + currentUser.getUsername());


        //*INFO: Set onclick product
        ly_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open another layout here
                Intent intent = new Intent(HomeActivity.this, TourActivity.class);
                startActivity(intent);
            }
        });

        ly_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open another layout here
                Intent intent = new Intent(HomeActivity.this, HotelActivity.class);
                startActivity(intent);
            }
        });

        ly_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open another layout here
                Intent intent = new Intent(HomeActivity.this, RestaurantActivity.class);
                startActivity(intent);
            }
        });

        ly_flight.setOnClickListener(new View.OnClickListener() {
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

                System.out.println("Search: " + query);
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