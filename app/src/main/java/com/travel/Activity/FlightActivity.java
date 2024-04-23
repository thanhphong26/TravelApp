package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.travel.Database.DestinationDAO;
import com.travel.Model.DestinationDetailModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.TextWatcherHelper;
import com.travel.databinding.ActivityFlightBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


        this.setDefaultData();
        this.initPage();
    }

    private void setDefaultData() {
        destinationDetails = destinationDAO.getAll("", Constants.MAX_RECORD, 0);
    }

    private void initPage() {
        this.initHeaderEvent();
        this.initDatePicker();
        this.initAutoComplete();
    }

    private void initAutoComplete() {
        List<String> countries = destinationDetails.stream()
                .map(DestinationDetailModel::getName)
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        flightBinding.edtCurrentPlace.setAdapter(adapter);
        flightBinding.edtDestination.setAdapter(adapter);
    }

    private void initDatePicker() {
        TextWatcherHelper departTimeWatcher = new TextWatcherHelper();
        departTimeWatcher.setEditText(flightBinding.edtDepartTime);
        flightBinding.edtDepartTime.addTextChangedListener(departTimeWatcher);

        TextWatcherHelper arrivalTimeWatcher = new TextWatcherHelper();
        arrivalTimeWatcher.setEditText(flightBinding.edtArrivalTime);
        flightBinding.edtArrivalTime.addTextChangedListener(arrivalTimeWatcher);
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


