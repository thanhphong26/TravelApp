package com.travel.Activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
        int hotelId = 4;
        int userId = 1;
        loadInfor(hotelId);
        loadUser(userId);
        String img=bookHotelDAO.getInFor(hotelId).getImage();
        Glide.with(this).load(img).into(bookHotelBinding.imgHotel);
        bookHotelBinding.btnDecreaseRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int room = Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
                if (room > 0) {
                    room--;
                    bookHotelBinding.tvQuantityRoom.setText(String.valueOf(room));
                }
                thanhtien(hotelId);
            }
        });
        bookHotelBinding.btnPlusRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int room = Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
                room++;
                bookHotelBinding.tvQuantityRoom.setText(String.valueOf(room));
                thanhtien(hotelId);
            }
        });
        bookHotelBinding.btnDecreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookHotelBinding.tvQuantityAdults.getText().toString());
                if (adults > 0) {
                    adults--;
                    bookHotelBinding.tvQuantityAdults.setText(String.valueOf(adults));
                }
                thanhtien(hotelId);
            }
        });
        bookHotelBinding.btnPlusAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookHotelBinding.tvQuantityAdults.getText().toString());
                adults++;
                bookHotelBinding.tvQuantityAdults.setText(String.valueOf(adults));
                thanhtien(hotelId);
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
                thanhtien(hotelId);
            }
        });
        bookHotelBinding.btnPlusChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childs = Integer.parseInt(bookHotelBinding.tvQuantityChilds.getText().toString());
                childs++;
                bookHotelBinding.tvQuantityChilds.setText(String.valueOf(childs));
                thanhtien(hotelId);
            }
        });
        bookHotelBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookHotelActivity.this, CheckoutActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("hotelId",hotelId);
                bundle.putInt("userId",userId);
                bundle.putInt("quantityRoom",Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString()));
                bundle.putInt("quantityAdults",Integer.parseInt(bookHotelBinding.tvQuantityAdults.getText().toString()));
                bundle.putInt("quantityChilds",Integer.parseInt(bookHotelBinding.tvQuantityChilds.getText().toString()));
                bundle.putFloat("total",Float.parseFloat(bookHotelBinding.tvThanhTien.getText().toString()));
                bundle.putString("hoTen",bookHotelBinding.edtHoTen.getText().toString());
                bundle.putString("email",bookHotelBinding.edtEmail.getText().toString());
                bundle.putString("soDienThoai",bookHotelBinding.edtSoDienThoai.getText().toString());
                bundle.putString("ten",bookHotelBinding.tvTenHotel.getText().toString());
                bundle.putString("moTa",bookHotelBinding.tvMoTa.getText().toString());
                bundle.putString("img",img);
                intent.putExtra("package",bundle);
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
    public void thanhtien(int hotelId)
    {
        int sl_treEm=Integer.parseInt(bookHotelBinding.tvQuantityChilds.getText().toString());
        int sl_nguoiLon=Integer.parseInt(bookHotelBinding.tvQuantityAdults.getText().toString());
        int sl_phong=Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
        float gia=bookHotelDAO.getInFor(hotelId).getPrice();
        float tongtien=sl_phong*gia+sl_nguoiLon*gia+sl_treEm*gia/2;
        bookHotelBinding.tvThanhTien.setText(String.valueOf(tongtien));
    }

}
