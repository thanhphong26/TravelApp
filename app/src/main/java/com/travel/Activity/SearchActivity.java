package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.travel.Database.HotelDAO;
import com.travel.Database.RestaurantDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.Utils.NumberHelper;
import com.travel.databinding.ActivitySearchBinding;
import com.travel.databinding.HotelFavoriteCardBinding;
import com.travel.databinding.RestaurantFavoriteCardBinding;
import com.travel.databinding.TourFavoriteCardBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding searchBinding;
    TourDAO tourDAO = new TourDAO();
    HotelDAO hotelDAO = new HotelDAO();
    RestaurantDAO restaurantDAO = new RestaurantDAO();
    ArrayList<TourModel> matchedTours = new ArrayList<TourModel>();
    ArrayList<RestaurantModel> matchedRestaurants = new ArrayList<RestaurantModel>();
    ArrayList<HotelModel> matchedHotels = new ArrayList<HotelModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(searchBinding.getRoot());
        Intent intent = getIntent();
        String query = intent.getStringExtra("search");

        this.initPage();
        this.handleSearchGlobal(query);
    }

    public  void setDefaultValue() {
        // Remove all view inside lySearchTour, lySearchHotel, lySearchRestaurant except label section
        searchBinding.lySearchTour.removeViews(1, searchBinding.lySearchTour.getChildCount() - 1);
        searchBinding.lySearchHotel.removeViews(1, searchBinding.lySearchHotel.getChildCount() - 1);
        searchBinding.lySearchRestaurant.removeViews(1, searchBinding.lySearchRestaurant.getChildCount() - 1);
    }

    public void handleSearchGlobal(String query) {
        this.setDefaultValue();
        searchBinding.searchDetail.setIconified(false);
        searchBinding.searchDetail.setQuery(query, false);

        matchedHotels = hotelDAO.getAll(query, 5, 1);
        matchedRestaurants = restaurantDAO.getAll(query, 5, 1);
        matchedTours = tourDAO.getAll(query, 5, 1);

        //*INFO: load searched data to UI
        this.handleListTour();
        this.handleListHotel();
        this.handleListRestaurant();
    }

    public void handleListTour() {
        if (matchedTours.size() <= 0) {
            searchBinding.lySearchTour.setVisibility(View.GONE);
        } else {
            searchBinding.lySearchTour.setVisibility(View.VISIBLE);
            for (TourModel tour : matchedTours) {
                TourFavoriteCardBinding tourFavoriteCardBinding = TourFavoriteCardBinding.inflate(getLayoutInflater());

                Glide.with(this).load(tour.getImage()).centerCrop().into(tourFavoriteCardBinding.roundedCityImage);
                tourFavoriteCardBinding.tvTourName.setText(tour.getName());
                tourFavoriteCardBinding.tourFavoriteRating.setText(String.valueOf(tour.getRating()));
                tourFavoriteCardBinding.ratingBar.setRating(tour.getRating());
                tourFavoriteCardBinding.tvDescription.setText(tour.getDescription());
                tourFavoriteCardBinding.tvPrice.setText(NumberHelper.getFormattedPrice(tour.getPrice()) + " đ");

                tourFavoriteCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, DetailTourActivity.class);
                        intent.putExtra("tourId", tour.getTourId());
                        startActivity(intent);
                    }
                });

                searchBinding.lySearchTour.addView(tourFavoriteCardBinding.getRoot());
            }
        }
    }

    public void handleListHotel() {
        if (matchedHotels.size() <= 0) {
            searchBinding.lySearchHotel.setVisibility(View.GONE);
        } else {
            searchBinding.lySearchHotel.setVisibility(View.VISIBLE);
            for (HotelModel hotel : matchedHotels) {
                HotelFavoriteCardBinding hotelFavoriteCardBinding = HotelFavoriteCardBinding.inflate(getLayoutInflater());
                Glide.with(this).load(hotel.getImage()).centerCrop().into(hotelFavoriteCardBinding.roundedCityImage);
                hotelFavoriteCardBinding.tvHotelName.setText(hotel.getName());
                hotelFavoriteCardBinding.tourFavoriteRating.setText(String.valueOf(hotel.getRating()));
                hotelFavoriteCardBinding.ratingBar.setRating(hotel.getRating());
                hotelFavoriteCardBinding.tvDescription.setText(hotel.getDescription());
                hotelFavoriteCardBinding.tvPrice.setText(NumberHelper.getFormattedPrice(hotel.getPrice()) + " đ");

                hotelFavoriteCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, DetailHotelActivity.class);
                        intent.putExtra("hotelId", hotel.getHotelId());
                        startActivity(intent);
                    }
                });

                searchBinding.lySearchHotel.addView(hotelFavoriteCardBinding.getRoot());
            }
        }
    }

    public void handleListRestaurant() {
        if (matchedRestaurants.size() <= 0) {
            searchBinding.lySearchRestaurant.setVisibility(View.GONE);
        } else {
            searchBinding.lySearchRestaurant.setVisibility(View.VISIBLE);
            for (RestaurantModel restaurant : matchedRestaurants) {
                RestaurantFavoriteCardBinding restaurantFavoriteCardBinding = RestaurantFavoriteCardBinding.inflate(getLayoutInflater());
                Glide.with(this).load(restaurant.getImage()).centerCrop().into(restaurantFavoriteCardBinding.roundedCityImage);
                restaurantFavoriteCardBinding.tvRestaurantName.setText(restaurant.getName());
                restaurantFavoriteCardBinding.tourFavoriteRating.setText(String.valueOf(restaurant.getRating()));
                restaurantFavoriteCardBinding.ratingBar.setRating(restaurant.getRating());
                restaurantFavoriteCardBinding.tvDescription.setText(restaurant.getDescription());
                restaurantFavoriteCardBinding.tvPrice.setText(NumberHelper.getFormattedPrice(restaurant.getPrice()) + " đ");

                restaurantFavoriteCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, DetailRestaurantActivity.class);
                        intent.putExtra("restaurantId", restaurant.getRestaurantId());
                        startActivity(intent);
                    }
                });

                searchBinding.lySearchRestaurant.addView(restaurantFavoriteCardBinding.getRoot());
            }
        }
    }

    public void initPage() {
        searchBinding.searchDetail.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearchGlobal(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}