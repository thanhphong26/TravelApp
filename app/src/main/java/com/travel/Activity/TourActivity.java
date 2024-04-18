package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.travel.Database.TourDAO;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.Utils.NumberHelper;
import com.travel.databinding.ActivityTourBinding;
import com.travel.databinding.TourCommonCardBinding;
import com.travel.databinding.TourFavoriteCardBinding;

import java.util.ArrayList;

public class TourActivity extends AppCompatActivity {
    ActivityTourBinding tourBinding;
    ArrayList<TourModel> tours = new ArrayList<TourModel>();
    ArrayList<TourModel> commonTours = new ArrayList<TourModel>();
    TourDAO tourDAO = new TourDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tourBinding = ActivityTourBinding.inflate(getLayoutInflater());
        setContentView(tourBinding.getRoot());

        this.initHeaderEvent();
        this.initPage();
        this.handleSearchGlobal();
    }

    public void initHeaderEvent() {
        ImageView img_back = (ImageView) findViewById(R.id.imgBack);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initPage() {
        commonTours = tourDAO.getCommon(5);
        tours = tourDAO.getAll("", 10, 0);

        this.handleListCommonTour();
        this.handleListTour();
    }

    public void handleSearchGlobal() {
        tourBinding.searchTour.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tours = tourDAO.getAll(query, 10, 0);
                handleListTour();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void handleListCommonTour() {
        tourBinding.lyCommonTour.removeAllViews();
        if (commonTours.size() <= 0) {
            tourBinding.lyCommonTour.setVisibility(View.GONE);
        } else {
            for (TourModel tour : commonTours) {
                TourCommonCardBinding tourCardBinding = TourCommonCardBinding.inflate(getLayoutInflater());
                tourCardBinding.cityName.setText(tour.getDestination().getName());
                Glide.with(this).load(tour.getImage()).into(tourCardBinding.roundedCityImage);
                tourCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TourActivity.this, DetailTourActivity.class);
                        intent.putExtra("tourId", tour.getTourId());
                        startActivity(intent);
                    }
                });
                tourBinding.lyCommonTour.addView(tourCardBinding.getRoot());
            }
        }
    }

    public void handleListTour() {
        tourBinding.lyTour.removeAllViews();
        if (tours.size() <= 0) {
            tourBinding.lyTour.setVisibility(View.GONE);
        } else {
            tourBinding.lyTour.setVisibility(View.VISIBLE);
            for (TourModel tour : tours) {
                TourFavoriteCardBinding tourFavoriteCardBinding = TourFavoriteCardBinding.inflate(getLayoutInflater());

                Glide.with(this).load(tour.getImage()).centerCrop().into(tourFavoriteCardBinding.tourRoundedCityImage);
                tourFavoriteCardBinding.tvTourName.setText(tour.getName());
                tourFavoriteCardBinding.tourFavoriteRating.setText(String.valueOf(tour.getRating()));
                tourFavoriteCardBinding.tourFavoriteRatingBar.setRating(tour.getRating());
                tourFavoriteCardBinding.tourFavoritePrice.setText(NumberHelper.getFormattedPrice(tour.getPrice()) + " đ");

                tourFavoriteCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TourActivity.this, DetailTourActivity.class);
                        intent.putExtra("tourId", tour.getTourId());
                        startActivity(intent);
                    }
                });

                tourBinding.lyTour.addView(tourFavoriteCardBinding.getRoot());
            }
        }
    }
}