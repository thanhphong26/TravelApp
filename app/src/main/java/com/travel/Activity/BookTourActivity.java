package com.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Database.BookTourDAO;
import com.travel.Database.VoucherDAO;
import com.travel.Model.UserModel;
import com.travel.Model.VoucherModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityBookTourBinding;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookTourActivity extends AppCompatActivity {
    ActivityBookTourBinding bookTourBinding;
    BookTourDAO bookTourDAO;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookTourBinding = ActivityBookTourBinding.inflate(getLayoutInflater());
        setContentView(bookTourBinding.getRoot());
        this.handleBottomNavigation();
        bookTourDAO = new BookTourDAO();
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayAsString = dateFormat.format(today);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String tomorrowAsString = dateFormat.format(tomorrow);
        bookTourBinding.tvNgayDi.setText(tomorrowAsString);
        bookTourBinding.tvNgayVe.setText(tomorrowAsString);
        int tourId = getIntent().getIntExtra("tourId", 0);
        loadInfor(tourId);
        loadUser(userModel.getUserId());
        String img=bookTourDAO.getInformationTour(tourId).getImage();
        bookTourBinding.btnThanhToan.setEnabled(false);
        bookTourBinding.btnDecreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookTourBinding.tvQuantityAdults.getText().toString());
                if (adults > 0) {
                    adults--;
                    bookTourBinding.tvQuantityAdults.setText(String.valueOf(adults));
                }
                thanhtien(tourId,0);
            }
        });
        bookTourBinding.btnIncreaseAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookTourBinding.tvQuantityAdults.getText().toString());
                adults++;
                bookTourBinding.tvQuantityAdults.setText(String.valueOf(adults));
                thanhtien(tourId,0);
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
                thanhtien(tourId,0);
            }
        });
        bookTourBinding.btnIncreaseChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childs = Integer.parseInt(bookTourBinding.tvQuantityChilds.getText().toString());
                childs++;
                bookTourBinding.tvQuantityChilds.setText(String.valueOf(childs));
                thanhtien(tourId,0);
            }
        });
        bookTourBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bookTourBinding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookTourActivity.this, CheckoutActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("tourId",tourId);
                bundle.putInt("userId",userModel.getUserId());
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
                bundle.putInt("id",4);
                bundle.putString("txtgia","Thành tiền");
                bundle.putString("txtgiamgia",bookTourBinding.tvGiaDuocGiam.getText().toString());
                intent.putExtra("package",bundle);
                startActivity(intent);
            }
        });
        bookTourBinding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoucherDAO voucherDAO = new VoucherDAO();
                List<VoucherModel> voucherModels = voucherDAO.getAllVouchers();
                String code = bookTourBinding.edtMaGiamGia.getText().toString();
                float discount = 0;
                for (VoucherModel voucherModel : voucherModels) {
                    if (voucherModel.getVoucherCode().equals(code)) {
                        discount = voucherModel.getVoucherDiscount();
                        break;
                    }
                }
                thanhtien(tourId,discount);
                long giam= (long) (discount*100);
                bookTourBinding.tvGiaDuocGiam.setText("Giảm"+" "+giam+"%"+" do áp dụng mã giảm giá");
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
    public void thanhtien(int tourId,float disc)
    {
        BookTourDAO bookTourDAO = new BookTourDAO();
        int quantityAdults = Integer.parseInt(bookTourBinding.tvQuantityAdults.getText().toString());
        int quantityChilds = Integer.parseInt(bookTourBinding.tvQuantityChilds.getText().toString());
        float price_adults = bookTourDAO.getInforPrice(tourId).getAdultPrice();
        float price_childs = bookTourDAO.getInforPrice(tourId).getChildPrice();
        long total = (long)((quantityAdults * price_adults + quantityChilds * price_childs)-((quantityAdults * price_adults + quantityChilds * price_childs)*disc));
        bookTourBinding.tvThanhTien.setText(String.valueOf(total));
        if(checkPrice(Long.parseLong(bookTourBinding.tvThanhTien.getText().toString())))
        {
            bookTourBinding.btnThanhToan.setEnabled(true);
        }
        else{
            bookTourBinding.btnThanhToan.setEnabled(false);
        }
    }
    public boolean checkPrice(float price){
        if(price>0.0){
            return true;
        }
        return false;
    }
    private void handleBottomNavigation() {
        bookTourBinding.navigation.setItemIconTintList(null);
        bookTourBinding.navigation.setSelectedItemId(R.id.navigation_home);
        bookTourBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    return true;
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(BookTourActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(BookTourActivity.this, DestinationActivity.class);
                }else if (id == R.id.navigation_translate) {
//                    intent = new Intent(HomeActivity.this, A.class);
                }
                else if (id == R.id.navigation_profile) {
                    intent = new Intent(BookTourActivity.this, AccountActivity.class);
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
