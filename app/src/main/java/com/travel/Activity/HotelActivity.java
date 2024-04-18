package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.Adapter.DetailDestinationAdapter;
import com.travel.Adapter.HotelCommonAdapter;
import com.travel.Adapter.HotelFavoriteAdapter;
import com.travel.Database.HotelDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.R;
import com.travel.databinding.ActivityHotelBinding;

import java.util.ArrayList;

public class HotelActivity extends AppCompatActivity {
    HotelDAO hotelDAO = new HotelDAO();
    ArrayList<HotelModel> commonHotels = new ArrayList<HotelModel>();
    ArrayList<HotelModel> hotels = new ArrayList<HotelModel>();
    ActivityHotelBinding hotelBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hotelBinding = ActivityHotelBinding.inflate(getLayoutInflater());
        setContentView(hotelBinding.getRoot());

        this.initHeaderEvent();



        this.setupLayoutRecyclerView();
        this.initCommonHotel();
        this.handleListHotel();
    }

    private void handleListHotel() {
        hotels = hotelDAO.getAll("", 10, 0);
        HotelFavoriteAdapter<HotelModel> hotelAdapter = new HotelFavoriteAdapter<>(hotels, this);
        hotelBinding.hotelFavouriteRecyclerViewContainer.setAdapter(hotelAdapter);
    }

    public  void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHotelCommon = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hotelBinding.hotelCommonRecyclerViewContainer.setLayoutManager(layoutManagerHotelCommon);
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        hotelBinding.hotelFavouriteRecyclerViewContainer.setLayoutManager(layoutManagerHotel);

    }

    private void initCommonHotel() {
        commonHotels = hotelDAO.getCommon(5);
        System.out.println("Hotel: " + commonHotels.size());
        HotelCommonAdapter<HotelModel> hotelCommonAdapter = new HotelCommonAdapter<>(commonHotels, this);
        hotelBinding.hotelCommonRecyclerViewContainer.setAdapter(hotelCommonAdapter);
    }

    public void initHeaderEvent() {
        ImageView img_back = (ImageView) findViewById(R.id.imgBack);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotelActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}