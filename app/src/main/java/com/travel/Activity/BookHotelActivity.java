package com.travel.Activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Database.BookHotelDAO;
import com.travel.Database.VoucherDAO;
import com.travel.Model.UserModel;
import com.travel.Model.VoucherModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityBookHotelBinding;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookHotelActivity extends AppCompatActivity {
    ActivityBookHotelBinding bookHotelBinding;

    BookHotelDAO bookHotelDAO;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookHotelBinding = ActivityBookHotelBinding.inflate(getLayoutInflater());
        setContentView(bookHotelBinding.getRoot());
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);
        bookHotelBinding.tvNgaynhan.setText(todayAsString);
        bookHotelBinding.tvNgaytra.setText(tomorrowAsString);
        int hotelId =getIntent().getIntExtra("hotelId",0);
        int userId = userModel.getUserId();
        loadInfor(hotelId);
        loadUser(userId);
        String img=bookHotelDAO.getInFor(hotelId).getImage();
        Glide.with(this).load(img).into(bookHotelBinding.imgHotel);
        bookHotelBinding.btnThanhToan.setEnabled(false);
        bookHotelBinding.btnDecreaseRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int room = Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
                if (room > 0) {
                    room--;
                    bookHotelBinding.tvQuantityRoom.setText(String.valueOf(room));
                }
                float disc = getDisc();
                thanhtien(hotelId,disc);
            }
        });
        bookHotelBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bookHotelBinding.btnPlusRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int room = Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
                room++;
                bookHotelBinding.tvQuantityRoom.setText(String.valueOf(room));
                float disc = getDisc();
                thanhtien(hotelId,disc);
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


            }
        });
        bookHotelBinding.btnPlusAdults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adults = Integer.parseInt(bookHotelBinding.tvQuantityAdults.getText().toString());
                adults++;
                int slnl=Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
                if(adults>slnl*2)
                {
                    adults=slnl*2;
                }

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
                int slte=Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
                if(childs>slte)
                {
                    childs=slte;
                }
                bookHotelBinding.tvQuantityChilds.setText(String.valueOf(childs));

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
                bundle.putLong("total",Long.parseLong(bookHotelBinding.tvThanhTien.getText().toString()));
                bundle.putString("hoTen",bookHotelBinding.edtHoTen.getText().toString());
                bundle.putString("email",bookHotelBinding.edtEmail.getText().toString());
                bundle.putString("soDienThoai",bookHotelBinding.edtSoDienThoai.getText().toString());
                bundle.putString("ten",bookHotelBinding.tvTenHotel.getText().toString());
                bundle.putString("moTa",bookHotelBinding.tvMoTa.getText().toString());
                bundle.putString("img",img);
                bundle.putString("txtgia","Thành tiền");
                bundle.putString("txtgiamgia",bookHotelBinding.tvGiaDuocGiam.getText().toString());
                bundle.putInt("id",2);
                intent.putExtra("package",bundle);
                startActivity(intent);
            }
        });
        bookHotelBinding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoucherDAO voucherDAO = new VoucherDAO();
                List<VoucherModel> voucherModels = voucherDAO.getAllVouchers();
                String code = bookHotelBinding.edtMaGiamGia.getText().toString();
                float discount = 0;
                for (VoucherModel voucherModel : voucherModels) {
                    if (voucherModel.getVoucherCode().equals(code)) {
                        discount = voucherModel.getVoucherDiscount();
                        long giam= (long) (discount*100);
                        bookHotelBinding.tvGiaDuocGiam.setText("Giảm"+" "+giam+"%"+" do áp dụng mã giảm giá");
                        break;
                    }
                }
                if(discount==0){
                    Toast.makeText(BookHotelActivity.this, "Mã giảm giá không hợp lệ", Toast.LENGTH_SHORT).show();
                    bookHotelBinding.tvGiaDuocGiam.setText("");
                }
                thanhtien(hotelId,discount);

            }
        });
    }
    public void loadInfor(int hotelId){
        bookHotelDAO = new BookHotelDAO();
        bookHotelBinding.tvTenHotel.setText(bookHotelDAO.getInFor(hotelId).getName());
        bookHotelBinding.tvMoTa.setText(bookHotelDAO.getInFor(hotelId).getDescription());
    }
    public void loadUser(int userId){
        bookHotelDAO = new BookHotelDAO();
        bookHotelBinding.edtHoTen.setText(bookHotelDAO.getInforUser(userId).getUsername());
        bookHotelBinding.edtSoDienThoai.setText(bookHotelDAO.getInforUser(userId).getPhoneNumber());
        bookHotelBinding.edtEmail.setText(bookHotelDAO.getInforUser(userId).getEmail());
    }
    public void thanhtien(int hotelId, float disc)
    {
        int sl_treEm=Integer.parseInt(bookHotelBinding.tvQuantityChilds.getText().toString());
        int sl_nguoiLon=Integer.parseInt(bookHotelBinding.tvQuantityAdults.getText().toString());
        int sl_phong=Integer.parseInt(bookHotelBinding.tvQuantityRoom.getText().toString());
        long gia=(long) bookHotelDAO.getInFor(hotelId).getPrice();
        long tongtien=(long)((sl_phong*gia)-((sl_phong*gia)*disc));
        bookHotelBinding.tvThanhTien.setText(String.valueOf(tongtien));
        if(checkPrice(Long.parseLong(bookHotelBinding.tvThanhTien.getText().toString())))
        {
            bookHotelBinding.btnThanhToan.setEnabled(true);
        }
        else{
            bookHotelBinding.btnThanhToan.setEnabled(false);
        }
    }
    public boolean checkPrice(float price){
        if(price>0){
            return true;
        }
        return false;
    }
    public void reset(){
        bookHotelBinding.edtMaGiamGia.setText("");
        bookHotelBinding.tvGiaDuocGiam.setText("");
    }
    public float getDisc(){
        VoucherDAO voucherDAO = new VoucherDAO();
        float disc=0;
        List<VoucherModel> voucherModels = voucherDAO.getAllVouchers();
        for (VoucherModel voucherModel : voucherModels) {
            if (bookHotelBinding.edtMaGiamGia.getText().toString().equals(voucherModel.getVoucherCode())) {
                disc = voucherModel.getVoucherDiscount();
            }
        }
        return disc;
    }

}
