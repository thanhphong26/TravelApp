package com.travel.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.travel.App;
import com.travel.Database.BookFlightDAO;
import com.travel.Database.DatabaseHelper;
import com.travel.Model.BookFlightModel;
import com.travel.Model.FlightModel;
import com.travel.Model.TypeOfFlightModel;
import com.travel.Model.UserModel;
import com.travel.databinding.ActivityCheckoutBinding;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    ActivityCheckoutBinding checkoutBinding;
    BookFlightDAO bookFlightDAO;
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
        int userId = bundle.getInt("userId");
        checkoutBinding.tvTen.setText(bundle.getString("tenFlight"));
        checkoutBinding.tvMoTa.setText(bundle.getString("moTa"));
        checkoutBinding.tvQuantityAdult.setText(String.valueOf(bundle.getInt("quantityAdults")));
        checkoutBinding.tvQuantityChild.setText(String.valueOf(bundle.getInt("quantityChilds")));
        checkoutBinding.tvThanhTien.setText(String.valueOf(bundle.getFloat("total")));
        checkoutBinding.edtHoTen.setText(bundle.getString("hoTen"));
        checkoutBinding.edtEmail.setText(bundle.getString("email"));
        checkoutBinding.edtSoDienThoai.setText(bundle.getString("soDienThoai"));
        checkoutBinding.tvTongTien.setText(String.valueOf(bundle.getFloat("total")));
        checkoutBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<BookFlightModel> list = new ArrayList<>();
                BookFlightModel bookFlightModel = new BookFlightModel();

                bookFlightModel.setFlightId(flightId);
                bookFlightModel.setTypeId(flightId);
                bookFlightModel.setNumberOfAdults(bundle.getInt("quantityAdults"));
                bookFlightModel.setNumberOfChilds(bundle.getInt("quantityChilds"));
                bookFlightModel.setTotalPrice(bundle.getFloat("total"));
                list.add(bookFlightModel);

//                database = databaseHelper.openDatabase();
//                ContentValues values = new ContentValues();
//                values.put("user_id", userId);
//                values.put("flight_id", flightId);
//                values.put("type_id", flightId);
//                values.put("number_of_adults", bundle.getInt("quantityAdults"));
//                values.put("number_of_childs", bundle.getInt("quantityChilds"));
//                values.put("total_price", bundle.getFloat("total"));
//                database.insert("flight_bookings", null, values);
            }
        });

    }
}
