package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.R;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        this.initPageOpen();
    }

    public void initPageOpen() {
        LinearLayout ly_tour = (LinearLayout) findViewById(R.id.tour_page);
        LinearLayout ly_hotel = (LinearLayout) findViewById(R.id.hotel_page);
        LinearLayout ly_restaurant = (LinearLayout) findViewById(R.id.restaurant_page);
        LinearLayout ly_flight = (LinearLayout) findViewById(R.id.flight_page);

        //*INFO: Load image from url
        ImageView avatar = (ImageView) findViewById(R.id.home_avatar);
        Glide.with(this).load("https://storage.googleapis.com/web-budget1/Image/Items/10100101_2.png").centerCrop().into(avatar);
        
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
}