package com.travel.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.R;
import com.travel.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {
    ActivityAccountBinding accountBinding;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBinding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(accountBinding.getRoot());
        this.handleBottomNavigation();
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
                    return true;
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(AccountActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(AccountActivity.this, DestinationActivity.class);
                }else if (id == R.id.navigation_translate) {
//                    intent = new Intent(HomeActivity.this, A.class);
                }
                else if (id == R.id.navigation_profile) {
                    intent = new Intent(AccountActivity.this, AccountActivity.class);
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
