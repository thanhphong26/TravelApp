package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.Adapter.DestinationCommonAdapter;
import com.travel.Adapter.DestinationFavoriteAdapter;
import com.travel.Adapter.HotelCommonAdapter;
import com.travel.Adapter.HotelFavoriteAdapter;
import com.travel.Database.DestinationDAO;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.databinding.ActivityDestinationBinding;

import java.util.ArrayList;

public class DestinationActivity extends AppCompatActivity {
    ActivityDestinationBinding destinationBinding;
    ArrayList<DestinationDetailModel> commonDestinations = new ArrayList<DestinationDetailModel>();
    ArrayList<DestinationDetailModel> destinationDetails = new ArrayList<DestinationDetailModel>();
    DestinationDAO destinationDAO = new DestinationDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinationBinding = ActivityDestinationBinding.inflate(getLayoutInflater());
        setContentView(destinationBinding.getRoot());

        this.setupLayoutRecyclerView();
        this.initHeaderEvent();
        this.initPage();
        this.handleSearchGlobal();
    }

    public void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHotelCommon = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        destinationBinding.rvCity.setLayoutManager(layoutManagerHotelCommon);
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        destinationBinding.rvFavorite.setLayoutManager(layoutManagerHotel);
    }

    public void initPage() {
        commonDestinations = destinationDAO.getDetailCommon(Constants.COMMON_RECORD);
        destinationDetails = destinationDAO.getAll("", Constants.MAX_RECORD, 0);

        this.handleListDestination();
        this.handleListCommonDestination();
    }

    public void initHeaderEvent() {
        ImageView img_back = (ImageView) findViewById(R.id.imgBack);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestinationActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleListDestination() {
        DestinationFavoriteAdapter<DestinationDetailModel> adapter = new DestinationFavoriteAdapter<DestinationDetailModel>(destinationDetails, this);
        destinationBinding.rvFavorite.setAdapter(adapter);
    }


    private void handleListCommonDestination() {
        DestinationCommonAdapter<DestinationDetailModel> adapter = new DestinationCommonAdapter<DestinationDetailModel>(commonDestinations, this);
        destinationBinding.rvCity.setAdapter(adapter);
    }

    public void handleSearchGlobal() {
        destinationBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                destinationDetails = destinationDAO.getAll(query.trim(), Constants.MAX_RECORD, 0);
                handleListDestination();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                destinationDetails = destinationDAO.getAll(newText.trim(), Constants.MAX_RECORD, 0);
                handleListDestination();
                return false;
            }
        });
    }
}