package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Adapter.DestinationCommonRatingAdapter;
import com.travel.Adapter.ImageSliderAdapter;
import com.travel.Adapter.ImageTourDetailAdapter;
import com.travel.Database.DestinationDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.TourModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.NumberHelper;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityHomeBinding;
import com.travel.databinding.TourCardBinding;
import com.travel.databinding.TourFavoriteCardBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding homeBinding;
    BottomNavigationView bottomNavigationView;
    UserModel currentUser = null;
    ArrayList<DestinationDetailModel> destinations = new ArrayList<DestinationDetailModel>();
    ArrayList<DestinationDetailModel> destinationDetails = new ArrayList<DestinationDetailModel>();
    DestinationDAO destinationDAO = new DestinationDAO();
    TourDAO tourDAO = new TourDAO();
    ImageSliderAdapter imageSliderAdapter;

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
        currentUser = SharePreferencesHelper.getInstance().get(Constants.USER_SHARE_PREFERENCES, UserModel.class);
        destinations = destinationDAO.getAll("", Constants.MAX_RECORD, 0);
        destinationDetails = destinationDAO.getDetailCommon(5);
    }

    public void initPageOpen() {
        //*INFO: Load image from url
        Glide.with(this).load(currentUser.getAvatar()).error(R.drawable.avatar).centerCrop().into(homeBinding.avatar.homeAvatar);
        homeBinding.username.setText("Chào, " + currentUser.getUsername());

        //*INFO: Set slider event
        this.handleDestinationSlider();
        this.handleBottomNavigation();

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
                Intent intent = new Intent(HomeActivity.this, HotelActivity.class);
                startActivity(intent);
            }
        });

        homeBinding.restaurantPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RestaurantActivity.class);
                startActivity(intent);
            }
        });

        homeBinding.flightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FlightActivity.class);
                startActivity(intent);
            }
        });

        homeBinding.viewAllDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DestinationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void handleSearchGlobal() {
        homeBinding.searchGlobal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("search", query.trim());
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

    private void startAutoSlide(int intervalInMillis) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = homeBinding.viewPager2.getCurrentItem() + 1;
                if (nextItem >= imageSliderAdapter.getItemCount()) {
                    nextItem = 0;
                }
                homeBinding.viewPager2.setCurrentItem(nextItem, true);
                handler.postDelayed(this, intervalInMillis);
            }
        };

        handler.postDelayed(runnable, intervalInMillis);
        homeBinding.viewPager2.setTag(runnable);
    }

    private void handleDestinationSlider() {
        List<String> imageUrls = new ArrayList<>();
        for (DestinationModel destination : destinations) {
            imageUrls.add(destination.getImage());
        }
        imageSliderAdapter = new ImageSliderAdapter(imageUrls, homeBinding.viewPager2);
        homeBinding.viewPager2.setAdapter(imageSliderAdapter);
        startAutoSlide(2500);
    }

    private void handleBottomNavigation() {
        homeBinding.navigation.setItemIconTintList(null);
        homeBinding.navigation.setSelectedItemId(R.id.navigation_home);
        homeBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    return true;
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(HomeActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(HomeActivity.this, DestinationActivity.class);
                }else if (id == R.id.navigation_translate) {
//                    intent = new Intent(HomeActivity.this, A.class);
                }
                else if (id == R.id.navigation_profile) {
                    intent = new Intent(HomeActivity.this, AccountActivity.class);
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