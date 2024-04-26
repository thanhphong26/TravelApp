package com.travel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.travel.R;
import com.travel.Utils.BottomNavigationBehavior;
import com.travel.databinding.BottomNavigationBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView);
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                redirectToLoginOrMain();
            }
        });
        animationView.playAnimation();
        animationView.loop(false);
        BottomNavigationBinding bottomNavigationBinding = BottomNavigationBinding.inflate(getLayoutInflater());
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationBinding.getRoot().getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());
//        Menu menu = bottomNavigationBinding.navigation.getMenu();
//
//        // Set a listener for item selections
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.navigation_home) {
//                    // Handle navigation for Home activity
//                    return true;
//                } else if (item.getItemId() == R.id.navigation_map) {
//                    // Handle navigation for Map activity
//                    return true;
//                } else if (item.getItemId() == R.id.navigation_favorite) {
//                    // Handle navigation for Favorite activity
//                    return true;
//                } else if (item.getItemId() == R.id.navigation_translate) {
//                    // Handle navigation for Translate activity
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }
    private void redirectToLoginOrMain() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        finish();
    }
}
