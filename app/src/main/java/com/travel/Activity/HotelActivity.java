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
import com.travel.Adapter.DetailDestinationAdapter;
import com.travel.Adapter.HotelCommonAdapter;
import com.travel.Adapter.HotelFavoriteAdapter;
import com.travel.Database.HotelDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.R;
import com.travel.databinding.ActivityHotelBinding;

import java.util.ArrayList;

public class HotelActivity extends AppCompatActivity {
    HotelDAO hotelDAO = new HotelDAO();
    ArrayList<HotelModel> commonHotels = new ArrayList<HotelModel>();
    ArrayList<HotelModel> hotels = new ArrayList<HotelModel>();
    ActivityHotelBinding hotelBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotelBinding = ActivityHotelBinding.inflate(getLayoutInflater());
        setContentView(hotelBinding.getRoot());

        this.setupLayoutRecyclerView();
        this.initHeaderEvent();
        this.initPage();
        this.handleSearchGlobal();
    }

    public  void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHotelCommon = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hotelBinding.hotelCommonRecyclerViewContainer.setLayoutManager(layoutManagerHotelCommon);
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        hotelBinding.hotelFavouriteRecyclerViewContainer.setLayoutManager(layoutManagerHotel);
    }

    public void initPage() {
        hotels = hotelDAO.getAll("", 10, 0);
        commonHotels = hotelDAO.getCommon(5);

        this.handleListHotel();
        this.handleListCommonHotel();
        this.handleBottomNavigation();
    }

    private void handleListHotel() {
        HotelFavoriteAdapter<HotelModel> hotelAdapter = new HotelFavoriteAdapter<>(hotels, this);
        hotelBinding.hotelFavouriteRecyclerViewContainer.setAdapter(hotelAdapter);
    }


    private void handleListCommonHotel() {
        HotelCommonAdapter<HotelModel> hotelCommonAdapter = new HotelCommonAdapter<>(commonHotels, this);
        hotelBinding.hotelCommonRecyclerViewContainer.setAdapter(hotelCommonAdapter);
    }

    public void initHeaderEvent() {
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void handleSearchGlobal() {
        hotelBinding.searchHotel.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hotels = hotelDAO.getAll(query.trim(), 10, 0);
                handleListHotel();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hotels = hotelDAO.getAll(newText.trim(), 10, 0);
                handleListHotel();
                return false;
            }
        });
    }

    private void handleBottomNavigation() {
        hotelBinding.navigation.setItemIconTintList(null);
        hotelBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(HotelActivity.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(HotelActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    return true;
                }else if (id == R.id.navigation_translate) {
//                    intent = new Intent(HomeActivity.this, A.class);
                }
                else if (id == R.id.navigation_profile) {
                    intent = new Intent(HotelActivity.this, PersonalInforActivity.class);
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