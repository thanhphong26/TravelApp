package com.travel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.travel.databinding.ActivityPersonalInfoBinding;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.R;

public class PersonalInforActivity extends AppCompatActivity {
    ActivityPersonalInfoBinding binding;
    ImageView edit_dob_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.initPage();
    }

    private void initPage() {
        edit_dob_img=(ImageView) findViewById(R.id.edit_dob_img);
        edit_dob_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        this.handleBottomNavigation();
    }
    public void showDialog(){
        final Dialog dialog= new Dialog(PersonalInforActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_dob);
        LinearLayout edtLayout=(LinearLayout) dialog.findViewById(R.id.edtLayout);
        edtLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalInforActivity.this, "Chỉnh sửa ngày sinh", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void handleBottomNavigation() {
        binding.navigation.setItemIconTintList(null);
        binding.navigation.setSelectedItemId(R.id.navigation_profile);
        binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(PersonalInforActivity.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(PersonalInforActivity.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(PersonalInforActivity.this, DestinationActivity.class);
                }else if (id == R.id.navigation_translate) {
                    intent = new Intent(PersonalInforActivity.this, MapsActivity2.class);
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