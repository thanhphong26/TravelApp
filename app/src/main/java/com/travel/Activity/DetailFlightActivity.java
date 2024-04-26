package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;
import com.travel.Database.AirportDAO;
import com.travel.Database.FlightDAO;
import com.travel.Model.FlightModel;
import com.travel.R;
import com.travel.databinding.ActivityDetailFlightBinding;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class DetailFlightActivity extends AppCompatActivity {
    ActivityDetailFlightBinding detailFlightBinding;
    FlightModel flightModel=new FlightModel();
    FlightDAO flightDAO;
    AirportDAO airportDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailFlightBinding=ActivityDetailFlightBinding.inflate(getLayoutInflater());
        setContentView(detailFlightBinding.getRoot());
       // int flightId = getIntent().getIntExtra("flightId", 0);
        int flightId=1;
        flightDAO=new FlightDAO();
        airportDAO=new AirportDAO();
        flightModel=flightDAO.getFlightById(flightId);
        AppBarLayout appBarLayout = findViewById(com.travel.R.id.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView backIcon = findViewById(R.id.imgBack);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (Math.abs(verticalOffset) - appBarLayout1.getTotalScrollRange() == 0) {
                toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
                backIcon.setColorFilter(Color.parseColor("#000000"));
            } else {
                toolbar.setBackgroundColor(Color.parseColor("#00000000"));
                backIcon.setColorFilter(Color.parseColor("#ffffff"));
            }
        });
        detailFlightBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setDetailFlight(flightModel);
    }
    public void setDetailFlight(FlightModel flightModel){
        detailFlightBinding.txtDeparture.setText(airportDAO.getAirportByCode(flightModel.getDepartureAirportCode()).getName());
        detailFlightBinding.txtArrival.setText(airportDAO.getAirportByCode(flightModel.getArrivalAirportCode()).getName());
        detailFlightBinding.txtFlightName.setText((flightModel.getDescription()));
        detailFlightBinding.txtDepartureCode.setText(flightModel.getDepartureAirportCode());
        detailFlightBinding.txtArrivalCode.setText(flightModel.getArrivalAirportCode());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        detailFlightBinding.txtDepartureTime.setText(timeFormat.format(flightModel.getDepartureTime()));
        detailFlightBinding.txtArrivalTime.setText(timeFormat.format(flightModel.getArrivalTime()));
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedMinValue = decimalFormat.format(flightModel.getPrice());
        detailFlightBinding.txtPrice.setText("đ"+formattedMinValue);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        detailFlightBinding.txtDepartureDate.setText(dateFormat.format(flightModel.getDepartureDate()));
        detailFlightBinding.txtArrivalDate.setText(dateFormat.format(flightModel.getArrivalDate()));
    }
}