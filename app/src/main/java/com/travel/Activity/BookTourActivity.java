package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.travel.Database.BookTourDAO;
import com.travel.Model.UserModel;
import com.travel.databinding.ActivityBookTourBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookTourActivity extends AppCompatActivity {
    ActivityBookTourBinding bookTourBinding;
    BookTourDAO bookTourDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookTourBinding = ActivityBookTourBinding.inflate(getLayoutInflater());
        setContentView(bookTourBinding.getRoot());
        bookTourDAO = new BookTourDAO();
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayAsString = dateFormat.format(today);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String tomorrowAsString = dateFormat.format(tomorrow);
        bookTourBinding.tvNgayDi.setText(tomorrowAsString);
        bookTourBinding.tvNgayVe.setText(tomorrowAsString);
        int tourId = 3;
        int userId = 1;
        loadInfor(tourId);
        loadUser(userId);
        String img=bookTourDAO.getInformationTour(tourId).getImage();
        bookTourBinding.btnDecreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookTourBinding.tvQuantityAdults.getText().toString());
                if (adults > 0) {
                    adults--;
                    bookTourBinding.tvQuantityAdults.setText(String.valueOf(adults));
                }
                thanhtien(tourId);
            }
        });
        bookTourBinding.btnIncreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookTourBinding.tvQuantityAdults.getText().toString());
                adults++;
                bookTourBinding.tvQuantityAdults.setText(String.valueOf(adults));
                thanhtien(tourId);
            }
        });
        bookTourBinding.btnDecreaseChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childs = Integer.parseInt(bookTourBinding.tvQuantityChilds.getText().toString());
                if (childs > 0) {
                    childs--;
                    bookTourBinding.tvQuantityChilds.setText(String.valueOf(childs));
                }
                thanhtien(tourId);
            }
        });
        bookTourBinding.btnIncreaseChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childs = Integer.parseInt(bookTourBinding.tvQuantityChilds.getText().toString());
                childs++;
                bookTourBinding.tvQuantityChilds.setText(String.valueOf(childs));
                thanhtien(tourId);
            }
        });
        bookTourBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookTourActivity.this, CheckoutActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("tourId",tourId);
                bundle.putInt("userId",userId);
                bundle.putString("ten",bookTourBinding.tvTenTour.getText().toString());
                bundle.putString("moTa",bookTourBinding.tvMoTa.getText().toString());
                bundle.putInt("quantityAdults",Integer.parseInt(bookTourBinding.tvQuantityAdults.getText().toString()));
                bundle.putInt("quantityChilds",Integer.parseInt(bookTourBinding.tvQuantityChilds.getText().toString()));
                bundle.putLong("total",Long.parseLong(bookTourBinding.tvThanhTien.getText().toString()));
                bundle.putString("hoTen",bookTourBinding.edtHoTen.getText().toString());
                bundle.putString("email",bookTourBinding.edtEmail.getText().toString());
                bundle.putString("soDienThoai",bookTourBinding.edtSoDienThoai.getText().toString());
                bundle.putString("ngayDat",bookTourBinding.tvNgayDi.getText().toString());
                bundle.putString("createdAt",todayAsString);
                bundle.putString("img",img);
                intent.putExtra("package",bundle);
                startActivity(intent);
            }
        });
    }
    public void loadUser(int userId)
    {
        BookTourDAO bookTourDAO = new BookTourDAO();
        UserModel userModel = bookTourDAO.getUser(userId);
        bookTourBinding.edtHoTen.setText(userModel.getUsername());
        bookTourBinding.edtEmail.setText(userModel.getEmail());
        bookTourBinding.edtSoDienThoai.setText(userModel.getPhoneNumber());
    }
    public void loadInfor(int tourId)
    {
        BookTourDAO bookTourDAO = new BookTourDAO();
        bookTourBinding.tvTenTour.setText(bookTourDAO.getInformationTour(tourId).getName());
        bookTourBinding.tvMoTa.setText(bookTourDAO.getInformationTour(tourId).getDescription());
        Glide.with(this).load(bookTourDAO.getInformationTour(tourId).getImage()).into(bookTourBinding.imgTour);
    }
    public void thanhtien(int tourId)
    {
        BookTourDAO bookTourDAO = new BookTourDAO();
        int quantityAdults = Integer.parseInt(bookTourBinding.tvQuantityAdults.getText().toString());
        int quantityChilds = Integer.parseInt(bookTourBinding.tvQuantityChilds.getText().toString());
        float price_adults = bookTourDAO.getInforPrice(tourId).getAdultPrice();
        float price_childs = bookTourDAO.getInforPrice(tourId).getChildPrice();
        long total = (long)(quantityAdults * price_adults + quantityChilds * price_childs);
        bookTourBinding.tvThanhTien.setText(String.valueOf(total));
        if(checkPrice(Float.parseFloat(bookTourBinding.tvThanhTien.getText().toString())))
        {
            bookTourBinding.btnThanhToan.setEnabled(true);
        }
        else{
            bookTourBinding.btnThanhToan.setEnabled(false);
        }
    }
    public boolean checkPrice(float price){
        if(price>0){
            return true;
        }
        return false;
    }


}
