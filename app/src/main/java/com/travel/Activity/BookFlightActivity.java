package com.travel.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.travel.Database.BookFlightDAO;
import com.travel.Database.DatabaseHelper;
import com.travel.Model.BookFlightModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.FlightModel;
import com.travel.Model.TypeOfFlightModel;
import com.travel.Model.UserModel;
import com.travel.databinding.ActivityBookFlightBinding;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class BookFlightActivity extends AppCompatActivity {
    ActivityBookFlightBinding bookFlightBinding;
    private int mYear, mMonth, mDay;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    BookFlightDAO bookFlightDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookFlightBinding = ActivityBookFlightBinding.inflate(getLayoutInflater());
        setContentView(bookFlightBinding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        int flightId=7;
        int userId=1;
        loadInfor(flightId);
        loadUser(userId);
        bookFlightDAO=new BookFlightDAO();
        bookFlightBinding.btnDecreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityA = Integer.parseInt(bookFlightBinding.tvQuantityAdults.getText().toString());
                if (quantityA > 0) {
                    quantityA--;
                    bookFlightBinding.tvQuantityAdults.setText(String.valueOf(quantityA));
                }
                thanhtien(flightId);
            }
        });
        bookFlightBinding.btnPlusAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityA = Integer.parseInt(bookFlightBinding.tvQuantityAdults.getText().toString());
                quantityA++;
                bookFlightBinding.tvQuantityAdults.setText(String.valueOf(quantityA));
                thanhtien(flightId);
            }

        });
        bookFlightBinding.btnDecreaseChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityC = Integer.parseInt(bookFlightBinding.tvQuantityChilds.getText().toString());
                if (quantityC > 0) {
                    quantityC--;
                    bookFlightBinding.tvQuantityChilds.setText(String.valueOf(quantityC));
                }
                thanhtien(flightId);
            }
        });
        bookFlightBinding.btnPlusChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityC = Integer.parseInt(bookFlightBinding.tvQuantityChilds.getText().toString());
                quantityC++;
                bookFlightBinding.tvQuantityChilds.setText(String.valueOf(quantityC));
                thanhtien(flightId);
            }
        });

        bookFlightBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookFlightActivity.this, CheckoutActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("flightId",flightId);
                bundle.putInt("userId",userId);
                bundle.putInt("quantityAdults",Integer.parseInt(bookFlightBinding.tvQuantityAdults.getText().toString()));
                bundle.putInt("quantityChilds",Integer.parseInt(bookFlightBinding.tvQuantityChilds.getText().toString()));
                bundle.putFloat("total",Float.parseFloat(bookFlightBinding.tvThanhTien.getText().toString()));
                bundle.putString("hoTen",bookFlightBinding.edtHoTen.getText().toString());
                bundle.putString("email",bookFlightBinding.edtEmail.getText().toString());
                bundle.putString("soDienThoai",bookFlightBinding.edtSoDienThoai.getText().toString());
                bundle.putString("tenFlight",bookFlightBinding.tvTenFlight.getText().toString());
                bundle.putString("moTa",bookFlightBinding.tvMoTa.getText().toString());
                intent.putExtra("package",bundle);
                startActivity(intent);
            }
        });
    }
    public void loadInfor(int flightId)
    {
        bookFlightDAO = new BookFlightDAO();
        FlightModel flightModel = bookFlightDAO.getInfor(flightId);
        bookFlightBinding.tvTenFlight.setText(flightModel.getDescription());
        bookFlightBinding.tvKhoiHanh.setText(flightModel.getDepartureTime().toString());
        bookFlightBinding.tvDen.setText(flightModel.getArrivalTime().toString());
        TypeOfFlightModel typeOfFlightModel= bookFlightDAO.getType(flightId);
        if(typeOfFlightModel.getType().equals("business"))
        {
            bookFlightBinding.tvMoTa.setText("Vé hạng thương gia");
        }
        if(typeOfFlightModel.getType().equals("economy"))
        {
            bookFlightBinding.tvMoTa.setText("Vé hạng phổ thông");
        }
        if(typeOfFlightModel.getType().equals("first-class"))
        {
            bookFlightBinding.tvMoTa.setText("Vé hạng nhất");
        }
    }
    public void loadUser(int userId)
    {
        bookFlightDAO = new BookFlightDAO();
        UserModel userModel = bookFlightDAO.getUser(userId);
        bookFlightBinding.edtHoTen.setText(userModel.getUsername());
        bookFlightBinding.edtEmail.setText(userModel.getEmail());
        bookFlightBinding.edtSoDienThoai.setText(userModel.getPhoneNumber());
    }
    public void thanhtien(int flightId)
    {
        int sl_treEm=Integer.parseInt(bookFlightBinding.tvQuantityChilds.getText().toString());
        int sl_nguoiLon=Integer.parseInt(bookFlightBinding.tvQuantityAdults.getText().toString());
        float gia=bookFlightDAO.getInfor(flightId).getPrice();
        float tongtien=sl_nguoiLon*gia+sl_treEm*gia/2;
        bookFlightBinding.tvThanhTien.setText(String.valueOf(tongtien));
    }

}