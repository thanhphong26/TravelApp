package com.travel.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import com.travel.R;
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
        this.handleBottomNavigation();
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
        checkoutBinding.GiaTu.setText(bundle.getString("txtgia"));
        checkoutBinding.tvGiaDuocGiam.setText(bundle.getString("txtgiamgia"));
        checkoutBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookFlightDAO = new BookFlightDAO();
                bookHotelDAO = new BookHotelDAO();
                bookRestaurantDAO = new BookRestaurantDAO();
                bookTourDAO = new BookTourDAO();

                if(bundle.getInt("id")==1){
                    bookFlightDAO.addBookFlight(userId, flightId, typeId , bundle.getInt("quantityAdults"), bundle.getInt("quantityChilds"), bundle.getLong("total"));
                }
                if(bundle.getInt("id")==2){
                    bookHotelDAO.addBookHotel(userId, bundle.getInt("hotelId"), bundle.getInt("quantityRoom"), bundle.getInt("quantityAdults"), bundle.getInt("quantityChilds"), bundle.getLong("total"));

                }
                if(bundle.getInt("id")==3){
                    bookRestaurantDAO.addBookRestaurant(userId, bundle.getInt("restaurantId"), bundle.getInt("quantityAdults"), bundle.getInt("quantityChilds"), bundle.getLong("total"));
                }
                if(bundle.getInt("id")==4){
                    bookTourDAO.addBookTour(userId, bundle.getInt("tourId"),bundle.getString("ngayDat"), bundle.getInt("quantityAdults"), bundle.getInt("quantityChilds"), bundle.getLong("total"),bundle.getString("createdAt"));
                }
                toComplete(img);
            }
        });



    }
    public void toComplete(String img){
        Intent intent1 =new Intent(CheckoutActivity.this, BookCompleteActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("ten",checkoutBinding.tvTen.getText().toString());
        bundle.putString("moTa",checkoutBinding.tvMoTa.getText().toString());
        bundle.putString("hoTen",checkoutBinding.edtHoTen.getText().toString());
        bundle.putString("email",checkoutBinding.edtEmail.getText().toString());
        bundle.putString("soDienThoai",checkoutBinding.edtSoDienThoai.getText().toString());
        bundle.putString("img",img);
        bundle.putString("thanhtien",checkoutBinding.tvThanhTien.getText().toString());
        intent1.putExtra("value",bundle);
        startActivity(intent1);
    }
    private void handleBottomNavigation() {
        checkoutBinding.navigation.setItemIconTintList(null);
        checkoutBinding.navigation.setSelectedItemId(R.id.navigation_home);
        checkoutBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
               if (id == R.id.navigation_home) {
                   return true;
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(CheckoutActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(CheckoutActivity.this, DestinationActivity.class);
                }else if (id == R.id.navigation_translate) {
                    intent = new Intent(CheckoutActivity.this, MapsActivity2.class);
                }
                else if (id == R.id.navigation_profile) {
                    intent = new Intent(CheckoutActivity.this, AccountActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
    }
}
