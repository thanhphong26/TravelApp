package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Adapter.DestinationFavoriteAdapter;
import com.travel.Adapter.HotelFavoriteAdapter;
import com.travel.Adapter.RestaurantFavoriteAdapter;
import com.travel.Adapter.TourFavoriteAdapter;
import com.travel.Database.WishlistDAO;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.Model.UserModel;
import com.travel.Model.WishlistModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityFavoriteBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    ActivityFavoriteBinding favoriteBinding;
    List<WishlistModel> wishlists;
    WishlistDAO wishlistDAO = new WishlistDAO();
    UserModel currentUser = new UserModel();
    ArrayList<TourModel> matchedTours = new ArrayList<TourModel>();
    ArrayList<RestaurantModel> matchedRestaurants = new ArrayList<RestaurantModel>();
    ArrayList<HotelModel> matchedHotels = new ArrayList<HotelModel>();
    ArrayList<DestinationDetailModel> matchedDestinations = new ArrayList<DestinationDetailModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteBinding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(favoriteBinding.getRoot());


        this.setupLayoutRecyclerView();
        this.setDefaultDate();
        this.initPage();
    }

    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoriteBinding.hotelRecycleView.setLayoutManager(layoutManagerHotel);
        LinearLayoutManager layoutManagerTour = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoriteBinding.tourRecycleView.setLayoutManager(layoutManagerTour);
        LinearLayoutManager layoutManagerRestaurant = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoriteBinding.restaurantRecycleView.setLayoutManager(layoutManagerRestaurant);
        LinearLayoutManager layoutManagerDestination = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoriteBinding.cityRecycleView.setLayoutManager(layoutManagerDestination);
    }

    private void setDefaultDate() {
        currentUser = SharePreferencesHelper.getInstance().get(Constants.USER_SHARE_PREFERENCES, UserModel.class);
        wishlists = wishlistDAO.getWishlistByUserId(currentUser.getUserId());

        for (WishlistModel wishlist : wishlists) {
            if (wishlist.getTourId() != 0) {
                matchedTours.add(wishlist.getTourModel());
            }
            if (wishlist.getHotelId() != 0) {
                matchedHotels.add(wishlist.getHotelModel());
            }
            if (wishlist.getRestaurantId() != 0) {
                matchedRestaurants.add(wishlist.getRestaurantModel());
            }
            if (wishlist.getDestinationId() != 0) {
                matchedDestinations.add(wishlist.getDestinationModel());
            }
        }
    }

    private void initPage() {
        this.handleListTour();
        this.handleListHotel();
        this.handleListRestaurant();
        this.handleListDestination();
        this.handleBottomNavigation();
    }

    public void handleListTour() {
        if (matchedTours.size() == 0) {
            favoriteBinding.titleTour.setVisibility(View.GONE);
        } else {
            favoriteBinding.titleTour.setVisibility(View.VISIBLE);
        }
        TourFavoriteAdapter<TourModel> adapter = new TourFavoriteAdapter<>(matchedTours, this);
        favoriteBinding.tourRecycleView.setAdapter(adapter);
    }

    public void handleListHotel() {
        if (matchedHotels.size() == 0) {
            favoriteBinding.titleHotel.setVisibility(View.GONE);
        } else {
            favoriteBinding.titleHotel.setVisibility(View.VISIBLE);
        }
        HotelFavoriteAdapter<HotelModel> adapter = new HotelFavoriteAdapter<>(matchedHotels, this);
        favoriteBinding.hotelRecycleView.setAdapter(adapter);
    }

    public void handleListRestaurant() {
        if (matchedRestaurants.size() == 0) {
            favoriteBinding.titleRestaurant.setVisibility(View.GONE);
        } else {
            favoriteBinding.titleRestaurant.setVisibility(View.VISIBLE);
        }
        RestaurantFavoriteAdapter<RestaurantModel> adapter = new RestaurantFavoriteAdapter<>(matchedRestaurants, this);
        favoriteBinding.restaurantRecycleView.setAdapter(adapter);
    }

    public void handleListDestination() {
        if (matchedDestinations.size() == 0) {
            favoriteBinding.titleCity.setVisibility(View.GONE);
        } else {
            favoriteBinding.titleCity.setVisibility(View.VISIBLE);
        }
        DestinationFavoriteAdapter<DestinationDetailModel> adapter = new DestinationFavoriteAdapter<DestinationDetailModel>(matchedDestinations, this);
        favoriteBinding.cityRecycleView.setAdapter(adapter);
    }

    private void handleBottomNavigation() {
        favoriteBinding.navigation.setItemIconTintList(null);
        favoriteBinding.navigation.setSelectedItemId(R.id.navigation_favorite);
        favoriteBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(FavoriteActivity.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    return true;
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(FavoriteActivity.this, DestinationActivity.class);
                }else if (id == R.id.navigation_translate) {
//                    intent = new Intent(HomeActivity.this, A.class);
                }
                else if (id == R.id.navigation_profile) {
                    intent = new Intent(FavoriteActivity.this, PersonalInforActivity.class);
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