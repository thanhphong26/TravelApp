package com.travel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Adapter.FlightCardAdapter;
import com.travel.Database.AirportDAO;
import com.travel.Database.DestinationDAO;
import com.travel.Database.FlightDAO;
import com.travel.Model.AirportModel;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.FlightModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.DatePickerHelper;
import com.travel.Utils.SnackBarHelper;
import com.travel.Utils.TextWatcherHelper;
import com.travel.databinding.ActivityFlightBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FlightActivity extends AppCompatActivity {
    ActivityFlightBinding flightBinding;
    DestinationDAO destinationDAO = new DestinationDAO();
    ArrayList<DestinationDetailModel> destinationDetails = new ArrayList<DestinationDetailModel>();
    ArrayList<AirportModel> airportModels = new ArrayList<AirportModel>();
    ArrayList<FlightModel> flights = new ArrayList<FlightModel>();

    AirportDAO airportDAO = new AirportDAO();
    FlightDAO flightDAO = new FlightDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flightBinding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(flightBinding.getRoot());

        this.setupLayoutRecyclerView();
        this.setDefaultData();
        this.initPage();
    }

    private  void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerHotelCommon = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        flightBinding.rcvFlight.setLayoutManager(layoutManagerHotelCommon);
    }

    private void setDefaultData() {
        destinationDetails = destinationDAO.getAll("", Constants.MAX_RECORD, 0);
        airportModels = airportDAO.getAll("", Constants.MAX_RECORD, 0);
    }

    private void initPage() {
        this.initHeaderEvent();
        this.initDatePicker();
        this.initAutoComplete();
        this.handleSearchFlight();
    }

    private void handleSearchFlight() {

        flightBinding.btnSearchFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String currentPlace = flightBinding.spnCurrentPlace.getSelectedItem().toString().split(" - ")[0];
                    String destination = flightBinding.spnDestination.getSelectedItem().toString().split(" - ")[0];
                    String departTime = flightBinding.edtDepartTime.getText().toString();
                    String arrivalTime = flightBinding.edtArrivalTime.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                    if (departTime.isEmpty() || arrivalTime.isEmpty()) {
                        SnackBarHelper.showSnackbar(flightBinding.getRoot(), "Vui lòng nhập đầy đủ thông tin để tìm kiếm chuyến bay");
                        return;
                    }
                    //*TODO: Update database later
//                    else if (sdf.parse(departTime).before(Calendar.getInstance().getTime())) {
//                        SnackBarHelper.showSnackbar(flightBinding.getRoot(), "Thời gian khởi hành không thể là quá khứ");
//                        return;
//                    }
//                    else if (sdf.parse(arrivalTime).before(Calendar.getInstance().getTime())) {
//                        SnackBarHelper.showSnackbar(flightBinding.getRoot(), "Thời gian đến không thể là quá khứ");
//                        return;
//                    }
                    else if (currentPlace.equals(destination)) {
                        SnackBarHelper.showSnackbar(flightBinding.getRoot(), "Điểm đến không được trùng với điểm khởi hành");
                        return;
                    }
                    flights = flightDAO.search(destination, currentPlace, arrivalTime, departTime);
                    handleListFlight();
                }catch (Exception e){
                    SnackBarHelper.showSnackbar(flightBinding.getRoot(), "Vui lòng nhập đầy đủ thông tin để tìm kiếm chuyến bay");
                    Log.e("Error", e.getMessage());
                }
            }
        });
    }

    private void initAutoComplete() {
        List<String> airportCodes = airportModels.stream()
                .map(airport -> airport.getAirportCode() + " - " + airport.getDestination().getName())
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, airportCodes);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flightBinding.spnCurrentPlace.setAdapter(adapter1);
        flightBinding.spnDestination.setAdapter(adapter1);
    }

    private void initDatePicker() {
        // ́INFO: ontext change data time must is format dd/MM/yyyy
        TextWatcherHelper departTimeWatcher = new TextWatcherHelper();
        departTimeWatcher.setEditText(flightBinding.edtDepartTime);
        flightBinding.edtDepartTime.addTextChangedListener(departTimeWatcher);

        TextWatcherHelper arrivalTimeWatcher = new TextWatcherHelper();
        arrivalTimeWatcher.setEditText(flightBinding.edtArrivalTime);
        flightBinding.edtArrivalTime.addTextChangedListener(arrivalTimeWatcher);

        //́INFO: Set event date picker for image
        DatePickerHelper datePicker = new DatePickerHelper();
        datePicker.setEditText(flightBinding.edtDepartTime);
        datePicker.setView(flightBinding.dpkDepartTime);
        datePicker.initDatePicker();

        DatePickerHelper datePicker2 = new DatePickerHelper();
        datePicker2.setEditText(flightBinding.edtArrivalTime);
        datePicker2.setView(flightBinding.dpkArrivalTime);
        datePicker2.initDatePicker();
    }

    private void initHeaderEvent() {
        flightBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
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


    private void handleListFlight() {
        flightBinding.rcvFlight.setAdapter(new FlightCardAdapter(flights, this, airportModels));
    }
}


