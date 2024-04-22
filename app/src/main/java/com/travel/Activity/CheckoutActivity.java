package com.travel.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.travel.App;
import com.travel.Database.BookFlightDAO;
import com.travel.Database.BookHotelDAO;
import com.travel.Database.BookRestaurantDAO;
import com.travel.Database.BookTourDAO;
import com.travel.Database.DatabaseHelper;
import com.travel.Model.BookFlightModel;
import com.travel.Model.FlightModel;
import com.travel.Model.TypeOfFlightModel;
import com.travel.Model.UserModel;
import com.travel.databinding.ActivityCheckoutBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {
    ActivityCheckoutBinding checkoutBinding;
    BookFlightDAO bookFlightDAO;
    BookHotelDAO bookHotelDAO;
    BookRestaurantDAO bookRestaurantDAO;
    BookTourDAO bookTourDAO;
    DatabaseHelper databaseHelper = databaseHelper = new DatabaseHelper(App.self());;
    SQLiteDatabase database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(checkoutBinding.getRoot());
        checkoutBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("package");
        int flightId = bundle.getInt("flightId");
        int typeId = flightId;
        int userId = bundle.getInt("userId");
        String img= bundle.getString("img");
        Glide.with(this).load(img).into(checkoutBinding.img);
        checkoutBinding.tvTen.setText(bundle.getString("ten"));
        checkoutBinding.tvMoTa.setText(bundle.getString("moTa"));
        checkoutBinding.tvQuantityAdult.setText(String.valueOf(bundle.getInt("quantityAdults")));
        checkoutBinding.tvQuantityChild.setText(String.valueOf(bundle.getInt("quantityChilds")));
        checkoutBinding.tvThanhTien.setText(String.valueOf(bundle.getLong("total")));
        checkoutBinding.edtHoTen.setText(bundle.getString("hoTen"));
        checkoutBinding.edtEmail.setText(bundle.getString("email"));
        checkoutBinding.edtSoDienThoai.setText(bundle.getString("soDienThoai"));
        checkoutBinding.tvTongTien.setText(String.valueOf(bundle.getLong("total")));
        checkoutBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookFlightDAO = new BookFlightDAO();
                bookHotelDAO = new BookHotelDAO();
                bookRestaurantDAO = new BookRestaurantDAO();
                bookTourDAO = new BookTourDAO();
                bookFlightDAO.addBookFlight(userId, flightId, typeId , bundle.getInt("quantityAdults"), bundle.getInt("quantityChilds"), bundle.getLong("total"));
                bookHotelDAO.addBookHotel(userId, bundle.getInt("hotelId"), bundle.getInt("quantityRoom"), bundle.getInt("quantityAdults"), bundle.getInt("quantityChilds"), bundle.getLong("total"));
                bookRestaurantDAO.addBookRestaurant(userId, bundle.getInt("restaurantId"), bundle.getInt("quantityAdults"), bundle.getInt("quantityChilds"), bundle.getLong("total"));
                bookTourDAO.addBookTour(userId, bundle.getInt("tourId"),bundle.getString("ngayDat"), bundle.getInt("quantityAdults"), bundle.getInt("quantityChilds"), bundle.getLong("total"),bundle.getString("createdAt"));
            }
        });


    }

}
