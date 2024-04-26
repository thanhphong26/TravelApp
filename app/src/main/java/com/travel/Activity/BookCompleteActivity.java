package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.travel.databinding.ActivityBookingCompleteBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookCompleteActivity extends AppCompatActivity {
    ActivityBookingCompleteBinding bookingCompleteBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookingCompleteBinding = ActivityBookingCompleteBinding.inflate(getLayoutInflater());
        setContentView(bookingCompleteBinding.getRoot());
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayAsString = dateFormat.format(today);
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getBundleExtra("value");
        bookingCompleteBinding.tvTen.setText(bundle.getString("ten"));
        bookingCompleteBinding.tvMoTa.setText(bundle.getString("moTa"));
        bookingCompleteBinding.edtHoTen.setText(bundle.getString("hoTen"));
        bookingCompleteBinding.edtEmail.setText(bundle.getString("email"));
        bookingCompleteBinding.edtSoDienThoai.setText(bundle.getString("soDienThoai"));
        String img = bundle.getString("img");
        Glide.with(this).load(img).into(bookingCompleteBinding.img);
        bookingCompleteBinding.tvThanhTien.setText(bundle.getString("thanhtien"));
        bookingCompleteBinding.tvNgayDat.setText(todayAsString);
        bookingCompleteBinding.btnQuayVeTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(BookCompleteActivity.this, HomeActivity.class);
                startActivity(intent2);
            }
        });
    }
}
