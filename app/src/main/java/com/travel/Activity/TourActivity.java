package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.travel.Adapter.DestinationCommonRatingAdapter;
import com.travel.Adapter.TourCommonAdapter;
import com.travel.Adapter.TourFavoriteAdapter;
import com.travel.Database.TourDAO;
import com.travel.Model.DestinationDetailModel;
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
                tours = tourDAO.getAll(newText, 10, 0);
                handleListTour();
                return false;
            }
        });
    }

    public void handleListCommonTour() {
        TourCommonAdapter<TourModel> adapter = new TourCommonAdapter<>(commonTours, this);
        tourBinding.rcvCommonTour.setAdapter(adapter);
    }

    public void handleListTour() {
        TourFavoriteAdapter<TourModel> adapter = new TourFavoriteAdapter<>(tours, this);
        tourBinding.rcvTour.setAdapter(adapter);
    }
}