package com.travel.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.travel.Database.BookFlightDAO;
import com.travel.Database.BookRestaurantDAO;
import com.travel.Model.UserModel;
import com.travel.databinding.ActivityBookRestaurantBinding;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class BookRestaurantActivity extends AppCompatActivity {
    ActivityBookRestaurantBinding bookRestaurantBinding;
    BookRestaurantDAO bookRestaurantDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookRestaurantBinding = ActivityBookRestaurantBinding.inflate(getLayoutInflater());
        setContentView(bookRestaurantBinding.getRoot());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        bookRestaurantBinding.tvNgayDat.setText(tomorrowAsString);
        bookRestaurantBinding.btnDecreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookRestaurantBinding.tvQuantityAdults.getText().toString());
                if (adults > 1) {
                    adults--;
                    bookRestaurantBinding.tvQuantityAdults.setText(String.valueOf(adults));
                }
            }
        });
        bookRestaurantBinding.btnPlusAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookRestaurantBinding.tvQuantityAdults.getText().toString());
                adults++;
                bookRestaurantBinding.tvQuantityAdults.setText(String.valueOf(adults));
            }
        });
        bookRestaurantBinding.btnDecreaseChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childs = Integer.parseInt(bookRestaurantBinding.tvQuantityChilds.getText().toString());
                if (childs > 0) {
                    childs--;
                    bookRestaurantBinding.tvQuantityChilds.setText(String.valueOf(childs));
                }
            }
        });
        bookRestaurantBinding.btnPlusChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childs = Integer.parseInt(bookRestaurantBinding.tvQuantityChilds.getText().toString());
                childs++;
                bookRestaurantBinding.tvQuantityChilds.setText(String.valueOf(childs));
            }
        });
    }
    public void loadUser(int userId)
    {
        bookRestaurantDAO = new BookRestaurantDAO();
        UserModel userModel = bookRestaurantDAO.getUser(userId);
        bookRestaurantBinding.edtHoTen.setText(userModel.getUsername());
        bookRestaurantBinding.edtEmail.setText(userModel.getEmail());
        bookRestaurantBinding.edtSoDienThoai.setText(userModel.getPhoneNumber());
    }
    public void loadInfor(int restaurantId)
    {
        bookRestaurantDAO = new BookRestaurantDAO();
        bookRestaurantBinding.tvTenRestaurant.setText(bookRestaurantDAO.getInfor(restaurantId).getName());
        bookRestaurantBinding.tvMoTa.setText(bookRestaurantDAO.getInfor(restaurantId).getDescription());
    }
}
