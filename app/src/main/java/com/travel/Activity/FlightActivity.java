package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.travel.Database.DestinationDAO;
import com.travel.Model.DestinationDetailModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.databinding.ActivityFlightBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightActivity extends AppCompatActivity {
    ActivityFlightBinding flightBinding;
    DestinationDAO destinationDAO = new DestinationDAO();
    ArrayList<DestinationDetailModel> destinationDetails = new ArrayList<DestinationDetailModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flightBinding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(flightBinding.getRoot());


        this.initPage();
    }

    private void initPage() {
        this.initHeaderEvent();

        destinationDetails = destinationDAO.getAll("", Constants.MAX_RECORD, 0);
        List<String> countries = destinationDetails.stream()
                .map(DestinationDetailModel::getName)
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        flightBinding.edtCurrentPlace.setAdapter(adapter);
    }

    private void initHeaderEvent() {
        flightBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlightActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        flightBinding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Check scroll position and change background image of back icon accordingly
                if (scrollY > 0) {
                    flightBinding.imgBack.setImageResource(R.drawable.left);
                    flightBinding.title.setTextColor(getResources().getColor(R.color.black));
                } else {
                    flightBinding.imgBack.setImageResource(R.drawable.icon_back);
                    flightBinding.title.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }
}