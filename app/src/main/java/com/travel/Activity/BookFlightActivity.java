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

import com.bumptech.glide.Glide;
import com.travel.Database.BookFlightDAO;
import com.travel.Database.DatabaseHelper;
import com.travel.Database.VoucherDAO;
import com.travel.Model.BookFlightModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.FlightModel;
import com.travel.Model.TypeOfFlightModel;
import com.travel.Model.UserModel;
import com.travel.Model.VoucherModel;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityBookFlightBinding;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BookFlightActivity extends AppCompatActivity {
    ActivityBookFlightBinding bookFlightBinding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    BookFlightDAO bookFlightDAO;
    UserModel userModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookFlightBinding = ActivityBookFlightBinding.inflate(getLayoutInflater());
        setContentView(bookFlightBinding.getRoot());
        String img="https://storage.googleapis.com/app-bucket1/Image/Flight/flight.jpg";
        Glide.with(this).load(img).into(bookFlightBinding.imgFlight);
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        int flightId=getIntent().getIntExtra("flightId",0);
        int userId=userModel.getUserId();
        loadInfor(flightId);
        loadUser(userId);
        bookFlightBinding.btnThanhToan.setEnabled(false);
        bookFlightDAO=new BookFlightDAO();

        bookFlightBinding.btnDecreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityA = Integer.parseInt(bookFlightBinding.tvQuantityAdults.getText().toString());
                if (quantityA > 0) {
                    quantityA--;
                    bookFlightBinding.tvQuantityAdults.setText(String.valueOf(quantityA));
                }
                float disc = getDisc();
                thanhtien(flightId,disc);
            }
        });
        bookFlightBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bookFlightBinding.btnPlusAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityA = Integer.parseInt(bookFlightBinding.tvQuantityAdults.getText().toString());
                quantityA++;
                bookFlightBinding.tvQuantityAdults.setText(String.valueOf(quantityA));
                float disc = getDisc();
                thanhtien(flightId,disc);
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
                float disc = getDisc();
                thanhtien(flightId,disc);
            }
        });
        bookFlightBinding.btnPlusChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityC = Integer.parseInt(bookFlightBinding.tvQuantityChilds.getText().toString());
                quantityC++;
                bookFlightBinding.tvQuantityChilds.setText(String.valueOf(quantityC));
                float disc = getDisc();
                thanhtien(flightId,disc);
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
                bundle.putLong("total",Long.parseLong(bookFlightBinding.tvThanhTien.getText().toString()));
                bundle.putString("hoTen",bookFlightBinding.edtHoTen.getText().toString());
                bundle.putString("email",bookFlightBinding.edtEmail.getText().toString());
                bundle.putString("soDienThoai",bookFlightBinding.edtSoDienThoai.getText().toString());
                bundle.putString("ten",bookFlightBinding.tvTenFlight.getText().toString());
                bundle.putString("moTa",bookFlightBinding.tvMoTa.getText().toString());
                bundle.putString("txtgia","Thành tiền");
                bundle.putString("txtgiamgia",bookFlightBinding.tvGiaDuocGiam.getText().toString());
                bundle.putString("img",img);
                bundle.putInt("id",1);
                intent.putExtra("package",bundle);
                startActivity(intent);
            }
        });
        bookFlightBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bookFlightBinding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoucherDAO voucherDAO = new VoucherDAO();
                List<VoucherModel> voucherModels = voucherDAO.getAllVouchers();
                String code = bookFlightBinding.edtMaGiamGia.getText().toString();
                float discount = 0;
                for (VoucherModel voucherModel : voucherModels) {
                    if (voucherModel.getVoucherCode().equals(code)) {
                        discount = voucherModel.getVoucherDiscount();
                        long giam= (long) (discount*100);
                        bookFlightBinding.tvGiaDuocGiam.setText("Giảm"+" "+giam+"%"+" do áp dụng mã giảm giá");
                        break;
                    }
                }
                if(discount==0){
                    Toast.makeText(BookFlightActivity.this, "Mã giảm giá không hợp lệ", Toast.LENGTH_SHORT).show();
                    bookFlightBinding.tvGiaDuocGiam.setText("");
                }
                thanhtien(flightId,discount);


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
    public void thanhtien(int flightId,float disc)
    {

        int sl_treEm = Integer.parseInt(bookFlightBinding.tvQuantityChilds.getText().toString());
        int sl_nguoiLon = Integer.parseInt(bookFlightBinding.tvQuantityAdults.getText().toString());
        long gia = (long) bookFlightDAO.getInfor(flightId).getPrice();
        long tongtien = (long)((sl_nguoiLon * gia + sl_treEm * gia)-((sl_nguoiLon * gia + sl_treEm * gia)*disc));
        bookFlightBinding.tvThanhTien.setText(String.valueOf(tongtien));
        if(checkPrice(Float.parseFloat(bookFlightBinding.tvThanhTien.getText().toString())))
        {
            bookFlightBinding.btnThanhToan.setEnabled(true);
        }
        else{
            bookFlightBinding.btnThanhToan.setEnabled(false);
        }
    }
    public boolean checkPrice(float price){
        if(price>0.0){
            return true;
        }
        return false;
    }
    public float getDisc(){
        VoucherDAO voucherDAO = new VoucherDAO();
        float disc=0;
        List<VoucherModel> voucherModels = voucherDAO.getAllVouchers();
        for (VoucherModel voucherModel : voucherModels) {
            if (bookFlightBinding.edtMaGiamGia.getText().toString().equals(voucherModel.getVoucherCode())) {
                disc = voucherModel.getVoucherDiscount();
            }
        }
        return disc;
    }
}