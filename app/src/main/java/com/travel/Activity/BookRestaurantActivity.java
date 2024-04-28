package com.travel.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.travel.Database.BookRestaurantDAO;
import com.travel.Model.UserModel;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityBookRestaurantBinding;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class BookRestaurantActivity extends AppCompatActivity {
    ActivityBookRestaurantBinding bookRestaurantBinding;
    BookRestaurantDAO bookRestaurantDAO;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookRestaurantBinding = ActivityBookRestaurantBinding.inflate(getLayoutInflater());
        setContentView(bookRestaurantBinding.getRoot());
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tomorrowAsString = dateFormat.format(tomorrow);
        bookRestaurantBinding.tvNgayDat.setText(tomorrowAsString);
        int restaurantId = getIntent().getIntExtra("restaurantId",0);
        int userId= userModel.getUserId();
        loadUser(userId);
        loadInfor(restaurantId);
        String img=bookRestaurantDAO.getInfor(restaurantId).getImage();
        Glide.with(this).load(img).into(bookRestaurantBinding.imgRestaurant);

        bookRestaurantBinding.btnDecreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookRestaurantBinding.tvQuantityAdults.getText().toString());
                if (adults > 0) {
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
        bookRestaurantBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookRestaurantActivity.this, CheckoutActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("restaurantId",restaurantId);
                bundle.putInt("userId",userId);
                bundle.putInt("quantityAdults",Integer.parseInt(bookRestaurantBinding.tvQuantityAdults.getText().toString()));
                bundle.putInt("quantityChilds",Integer.parseInt(bookRestaurantBinding.tvQuantityChilds.getText().toString()));
                bundle.putLong("total",Long.parseLong(bookRestaurantBinding.tvThanhTien.getText().toString()));
                bundle.putString("hoTen",bookRestaurantBinding.edtHoTen.getText().toString());
                bundle.putString("email",bookRestaurantBinding.edtEmail.getText().toString());
                bundle.putString("soDienThoai",bookRestaurantBinding.edtSoDienThoai.getText().toString());
                bundle.putString("ten",bookRestaurantBinding.tvTenRestaurant.getText().toString());
                bundle.putString("moTa",bookRestaurantBinding.tvMoTa.getText().toString());
                bundle.putString("img",img);
                bundle.putString("txtgia","Giá từ");
                bundle.putInt("id",3);
                bundle.putLong("total",Long.parseLong(bookRestaurantBinding.tvThanhTien.getText().toString()));
                intent.putExtra("package",bundle);
                startActivity(intent);
            }
        });
        bookRestaurantBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        long price = (long) bookRestaurantDAO.getInfor(restaurantId).getPrice();
        bookRestaurantBinding.tvThanhTien.setText(String.valueOf(price));
    }
    public boolean checkPrice(float price){
        if(price>0.0){
            return true;
        }
        return false;
    }

}
