package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.travel.Adapter.DetailDestinationAdapter;
import com.travel.Adapter.TourAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class DetailDestinationActivity extends AppCompatActivity {
    ActivityDetailDestinationBinding detailDestinationBinding;
    DestinationDAO destinationDAO=new DestinationDAO();
    HotelDAO hotelDAO=new HotelDAO();
    RestaurantDAO restaurantDAO=new RestaurantDAO();
    TourDAO tourDAO=new TourDAO();
    DetailDestinationAdapter<Object> detailDestinationAdapter;
    List<Object> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailDestinationBinding=ActivityDetailDestinationBinding.inflate(getLayoutInflater());
        setContentView(detailDestinationBinding.getRoot());
        //int destinationId=getIntent().getIntExtra("destination_id",0);
        int destinationId=21;
        DestinationModel destination=destinationDAO.getDestinationById(destinationId);
        setDestination(destination);

        LinearLayoutManager layoutManagerTour = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewTour.setLayoutManager(layoutManagerTour);
        List<TourModel> tourModels = tourDAO.getAllTours(destinationId);
        DetailDestinationAdapter<TourModel> tourAdapter = new DetailDestinationAdapter<>(tourModels, this);
        detailDestinationBinding.recyclerViewTour.setAdapter(tourAdapter);

        LinearLayoutManager layoutManagerRestaurant = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewRestaurant.setLayoutManager(layoutManagerRestaurant);
        List<RestaurantModel> restaurants = restaurantDAO.getAllRestaurant(destinationId);
        System.out.println("Hotel: "+restaurants.size());
        DetailDestinationAdapter<RestaurantModel> restaurantAdapter = new DetailDestinationAdapter<>(restaurants, this);
        detailDestinationBinding.recyclerViewRestaurant.setAdapter(restaurantAdapter);

        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewHotel.setLayoutManager(layoutManagerHotel);
        List<HotelModel> hotels = hotelDAO.getByDestinationId(destinationId);
        System.out.println("Hotel: "+hotels.size());
        DetailDestinationAdapter<HotelModel> hotelAdapter = new DetailDestinationAdapter<>(hotels, this);
        detailDestinationBinding.recyclerViewHotel.setAdapter(hotelAdapter);
}
    public void setDestination(DestinationModel destination){
        detailDestinationBinding.txtDestinationName.setText(destination.getName());
        Glide.with(this).load(destination.getImage()).into(detailDestinationBinding.imageDestination);
    }
}