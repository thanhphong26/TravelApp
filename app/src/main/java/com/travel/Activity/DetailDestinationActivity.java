package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.travel.Adapter.DetailDestinationAdapter;
import com.travel.Database.DestinationDAO;
import com.travel.Database.HotelDAO;
import com.travel.Database.RestaurantDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.databinding.ActivityDetailDestinationBinding;

import java.util.List;

public class DetailDestinationActivity extends AppCompatActivity {
    ActivityDetailDestinationBinding detailDestinationBinding;
    DestinationDAO destinationDAO=new DestinationDAO();
    HotelDAO hotelDAO=new HotelDAO();
    RestaurantDAO restaurantDAO=new RestaurantDAO();
    TourDAO tourDAO=new TourDAO();
     DetailDestinationAdapter<TourModel> tourAdapter;
     DetailDestinationAdapter<RestaurantModel> restaurantAdapter;
     DetailDestinationAdapter<HotelModel> hotelAdapter;
     int destinationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailDestinationBinding = ActivityDetailDestinationBinding.inflate(getLayoutInflater());
        setContentView(detailDestinationBinding.getRoot());
        destinationId=getIntent().getIntExtra("destination_id",0);

        DestinationModel destination = destinationDAO.getDestinationById(destinationId);
        setDestination(destination);
        setupLayoutRecyclerView();
        setupTourRecyclerView(destinationId);
        setupRestaurantRecyclerView(destinationId);
        setupHotelRecyclerView(destinationId);
        this.initPage();

    }
    public void setDestination(DestinationModel destination){
        detailDestinationBinding.txtDestinationName.setText(destination.getName());
        detailDestinationBinding.txtDescription.setText(destination.getDescription());
        Glide.with(this).load(destination.getImage()).into(detailDestinationBinding.imageDestination);
    }
    private void setupTourRecyclerView(int destinationId) {

        List<TourModel> tourModels = tourDAO.getTourByDestinationId(destinationId);
        System.out.println("Restaurant: " + tourModels.size());
        DetailDestinationAdapter<TourModel> tourAdapter = new DetailDestinationAdapter<>(tourModels, this);
        detailDestinationBinding.recyclerViewTour.setAdapter(tourAdapter);
    }
    private void setupRestaurantRecyclerView(int destinationId) {

        List<RestaurantModel> restaurants = restaurantDAO.getAllRestaurant(destinationId);
        System.out.println("Restaurant: " + restaurants.size());
        DetailDestinationAdapter<RestaurantModel> restaurantAdapter = new DetailDestinationAdapter<>(restaurants, this);
        detailDestinationBinding.recyclerViewRestaurant.setAdapter(restaurantAdapter);
    }
    private void setupHotelRecyclerView(int destinationId) {

        List<HotelModel> hotels = hotelDAO.getByDestinationId(destinationId);
        System.out.println("Hotel: " + hotels.size());
        DetailDestinationAdapter<HotelModel> hotelAdapter = new DetailDestinationAdapter<>(hotels, this);
        detailDestinationBinding.recyclerViewHotel.setAdapter(hotelAdapter);
    }
    private void setupLayoutRecyclerView(){
        LinearLayoutManager layoutManagerTour = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewTour.setLayoutManager(layoutManagerTour);
        LinearLayoutManager layoutManagerRestaurant = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewRestaurant.setLayoutManager(layoutManagerRestaurant);
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewHotel.setLayoutManager(layoutManagerHotel);
    }
    public void navigateToHotel(int destinationId){
        Intent intent=new Intent(this, HotelActivity.class);
        intent.putExtra("destination_id",destinationId);
        startActivity(intent);
    }
    public void navigateToRestaurant(int destinationId){
        Intent intent=new Intent(this, RestaurantActivity.class);
        intent.putExtra("destination_id",destinationId);
        startActivity(intent);
    }
    public void navigateToTour(int destinationId){
        Intent intent=new Intent(this, TourActivity.class);
        intent.putExtra("destination_id",destinationId);
        startActivity(intent);
    }
    public void navigateToFlight(int destinationId){
        Intent intent=new Intent(this, FlightActivity.class);
        intent.putExtra("destination_id",destinationId);
        startActivity(intent);
    }

    public void initPage() {
        detailDestinationBinding.imgTour.setOnClickListener(v -> navigateToTour(destinationId));
        detailDestinationBinding.imgRestaurant.setOnClickListener(v -> navigateToRestaurant(destinationId));
        detailDestinationBinding.imgHotel.setOnClickListener(v -> navigateToHotel(destinationId));
        detailDestinationBinding.imgFlight.setOnClickListener(v -> navigateToFlight(destinationId));

        detailDestinationBinding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Check scroll position and change background image of back icon accordingly
                if (scrollY >0) {
                    detailDestinationBinding.imgBack.setImageResource(R.drawable.left);
                } else {
                    detailDestinationBinding.imgBack.setImageResource(R.drawable.icon_back);
                }
            }
        });
    }
}