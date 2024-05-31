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

import com.bumptech.glide.Glide;
import com.travel.Adapter.DestinationCommonRatingAdapter;
import com.travel.Adapter.TourCommonAdapter;
import com.travel.Adapter.TourFavoriteAdapter;
import com.travel.Database.DestinationDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.NumberHelper;
import com.travel.databinding.ActivityTourBinding;
import com.travel.databinding.TourCommonCardBinding;
import com.travel.databinding.TourFavoriteCardBinding;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TourActivity extends AppCompatActivity {
    ActivityTourBinding tourBinding;
    ArrayList<TourModel> tours = new ArrayList<TourModel>();
    ArrayList<TourModel> commonTours = new ArrayList<TourModel>();
    TourDAO tourDAO = new TourDAO();
    DestinationDAO destinationDAO = new DestinationDAO();
    int destinationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tourBinding = ActivityTourBinding.inflate(getLayoutInflater());
        setContentView(tourBinding.getRoot());

        this.setupLayoutRecyclerView();
        this.initHeaderEvent();
        this.initPage();
        this.handleSearchGlobal();
    }

    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerCommon = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tourBinding.rcvCommonTour.setLayoutManager(layoutManagerCommon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tourBinding.rcvTour.setLayoutManager(layoutManager);
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

    public void initPage() {
        destinationId = getIntent().getIntExtra("destinationId", 0);
        commonTours = tourDAO.getCommon(5);
        tours = tourDAO.getAll("", Constants.MAX_RECORD, 0);

        if (destinationId != 0) {
            DestinationModel destination = destinationDAO.getDestinationById(destinationId);
            String title = tourBinding.title.getText().toString();
            tourBinding.title.setText(title + " - " + destination.getName());
        }

        this.handleListCommonTour();
        this.handleListTour();
        this.handleBottomNavigation();
    }

    public void handleSearchGlobal() {
        tourBinding.searchTour.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tours = tourDAO.getAll(query, Constants.MAX_RECORD, 0);
                handleListTour();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tours = tourDAO.getAll(newText, Constants.MAX_RECORD, 0);
                handleListTour();
                return false;
            }
        });
    }

    public void handleListCommonTour() {
        if (destinationId != 0) {
            commonTours = commonTours.stream().filter(item -> item.getDestination().getDestinationId() == destinationId).collect(Collectors.toCollection(ArrayList::new));
        }
        TourCommonAdapter<TourModel> adapter = new TourCommonAdapter<>(commonTours, this);
        tourBinding.rcvCommonTour.setAdapter(adapter);
    }

    public void handleListTour() {
        if (destinationId != 0) {
            tours = tours.stream().filter(item -> item.getDestination().getDestinationId() == destinationId).collect(Collectors.toCollection(ArrayList::new));
        }
        TourFavoriteAdapter<TourModel> adapter = new TourFavoriteAdapter<>(tours, this);
        tourBinding.rcvTour.setAdapter(adapter);
    }

    private void handleBottomNavigation() {
        tourBinding.navigation.setItemIconTintList(null);
        tourBinding.navigation.setSelectedItemId(R.id.navigation_map);
        tourBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(TourActivity.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(TourActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    return true;
                }else if (id == R.id.navigation_translate) {
                    intent = new Intent(TourActivity.this, MapsActivity2.class);
                }
                else if (id == R.id.navigation_profile) {
                    intent = new Intent(TourActivity.this, AccountActivity.class);
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