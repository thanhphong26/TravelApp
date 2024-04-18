package com.travel.Activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.travel.Database.BookHotelDAO;
import com.travel.R;
import com.travel.databinding.ActivityBookHotelBinding;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookHotelActivity extends AppCompatActivity {
    ActivityBookHotelBinding bookHotelBinding;

    BookHotelDAO bookHotelDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookHotelBinding = ActivityBookHotelBinding.inflate(getLayoutInflater());
        setContentView(bookHotelBinding.getRoot());
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);
        bookHotelBinding.tvNgaynhan.setText(todayAsString);
        bookHotelBinding.tvNgaytra.setText(tomorrowAsString);
        int hotelId = 5;
        int userId = 1;
        loadInfor(hotelId);
        loadUser(userId);
        bookHotelBinding.btnDecreaseRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int room = Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
                if (room > 1) {
                    room--;
                    bookHotelBinding.tvQuantityRoom.setText(String.valueOf(room));
                }
            }
        });
        bookHotelBinding.btnPlusRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int room = Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
                room++;
                bookHotelBinding.tvQuantityRoom.setText(String.valueOf(room));
            }
        });
        bookHotelBinding.btnDecreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookHotelBinding.tvQuantityAdults.getText().toString());
                if (adults > 1) {
                    adults--;
                    bookHotelBinding.tvQuantityAdults.setText(String.valueOf(adults));
                }
            }
        });
        bookHotelBinding.btnPlusAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookHotelBinding.tvQuantityAdults.getText().toString());
                adults++;
                bookHotelBinding.tvQuantityAdults.setText(String.valueOf(adults));
            }
        });
        bookHotelBinding.btnDecreaseChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childs = Integer.parseInt(bookHotelBinding.tvQuantityChilds.getText().toString());
                if (childs > 0) {
                    childs--;
                    bookHotelBinding.tvQuantityChilds.setText(String.valueOf(childs));
                }
            }
        });
        bookHotelBinding.btnPlusChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childs = Integer.parseInt(bookHotelBinding.tvQuantityChilds.getText().toString());
                childs++;
                bookHotelBinding.tvQuantityChilds.setText(String.valueOf(childs));
            }
        });
        bookHotelBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookHotelActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
    public void loadInfor(int hotelId){
        bookHotelDAO = new BookHotelDAO();
        bookHotelBinding.tvTenHotel.setText(bookHotelDAO.getInFor(hotelId).getName());
        bookHotelBinding.tvMoTa.setText(bookHotelDAO.getInFor(hotelId).getDescription());
        bookHotelBinding.tvThanhTien.setText(String.valueOf(bookHotelDAO.getInFor(hotelId).getPrice()));
    }
    public void loadUser(int userId){
        bookHotelDAO = new BookHotelDAO();
        bookHotelBinding.edtHoTen.setText(bookHotelDAO.getInforUser(userId).getUsername());
        bookHotelBinding.edtSoDienThoai.setText(bookHotelDAO.getInforUser(userId).getPhoneNumber());
        bookHotelBinding.edtEmail.setText(bookHotelDAO.getInforUser(userId).getEmail());
    }

}
