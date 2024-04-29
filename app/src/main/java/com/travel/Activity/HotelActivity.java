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
import com.travel.Database.DestinationDAO;
import com.travel.Database.HotelDAO;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.databinding.ActivityHotelBinding;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HotelActivity extends AppCompatActivity {
    HotelDAO hotelDAO = new HotelDAO();
    ArrayList<HotelModel> commonHotels = new ArrayList<HotelModel>();
    ArrayList<HotelModel> hotels = new ArrayList<HotelModel>();
    ActivityHotelBinding hotelBinding;
    DestinationDAO destinationDAO = new DestinationDAO();
    int destinationId = 0;
    
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
        destinationId = getIntent().getIntExtra("destinationId", 0);
        hotels = hotelDAO.getAll("", Constants.MAX_RECORD, 0);
        commonHotels = hotelDAO.getCommon(Constants.COMMON_RECORD);

        if (destinationId != 0) {
            DestinationModel destination = destinationDAO.getDestinationById(destinationId);
            String title = hotelBinding.title.getText().toString();
            hotelBinding.title.setText(title + " - " + destination.getName());
        }

        this.handleListHotel();
        this.handleListCommonHotel();
        this.handleBottomNavigation();
    }

    private void handleListHotel() {
        if (destinationId != 0) {
            hotels = hotels.stream().filter(item -> item.getDestination().getDestinationId() == destinationId).collect(Collectors.toCollection(ArrayList::new));
        }
        HotelFavoriteAdapter<HotelModel> hotelAdapter = new HotelFavoriteAdapter<>(hotels, this);
        hotelBinding.hotelFavouriteRecyclerViewContainer.setAdapter(hotelAdapter);
    }


    private void handleListCommonHotel() {
        if (destinationId != 0) {
            commonHotels = commonHotels.stream().filter(item -> item.getDestination().getDestinationId() == destinationId).collect(Collectors.toCollection(ArrayList::new));
        }
        HotelCommonAdapter<HotelModel> hotelCommonAdapter = new HotelCommonAdapter<>(commonHotels, this);
        hotelBinding.hotelCommonRecyclerViewContainer.setAdapter(hotelCommonAdapter);
    }

    public void initHeaderEvent() {
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    public void handleSearchGlobal() {
        hotelBinding.searchHotel.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hotels = hotelDAO.getAll(query.trim(), Constants.MAX_RECORD, 0);
                handleListHotel();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hotels = hotelDAO.getAll(newText.trim(), Constants.MAX_RECORD, 0);
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