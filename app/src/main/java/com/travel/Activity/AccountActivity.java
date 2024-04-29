package com.travel.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {
    ActivityAccountBinding accountBinding;
    BottomNavigationView bottomNavigationView;
    UserModel userModel = new UserModel();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBinding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(accountBinding.getRoot());
        this.handleBottomNavigation();
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        accountBinding.txtUserName.setText(userModel.getUsername());
        Glide.with(this).load(userModel.getAvatar()).error(R.drawable.avatar).into(accountBinding.imgUser);
        accountBinding.layoutInfor.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, PersonalInfoActivity.class);
            startActivity(intent);
        });
        accountBinding.layoutAccount.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, PersonalInfoActivity.class);
            startActivity(intent);
        });
        accountBinding.layoutDiscount.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, VoucherActivity.class);
            startActivity(intent);
        });
        accountBinding.layoutLoyaltyPoint.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, LoyaltyPointActivity.class);
            startActivity(intent);
        });
        accountBinding.layoutHistoryOder.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
        accountBinding.layoutRating.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, RatingHistoryActivity.class);
            startActivity(intent);
        });
        accountBinding.layoutLogout.setOnClickListener(v -> {
            logout();
        });
    }
    public void logout() {
        final Dialog dialog = new Dialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.account_signout);

        Button btnLogout = dialog.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                dialog.dismiss();
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            }
        });
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void handleBottomNavigation() {
        accountBinding.navigation.setItemIconTintList(null);
        accountBinding.navigation.setSelectedItemId(R.id.navigation_home);
        accountBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(AccountActivity.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(AccountActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(AccountActivity.this, DestinationActivity.class);
                }else if (id == R.id.navigation_translate) {
                    intent = new Intent(AccountActivity.this, MapsActivity2.class);
                }
                else if (id == R.id.navigation_profile) {
                    return true;
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
