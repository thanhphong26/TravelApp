package com.travel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
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
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
//                    System.out.println("aaa");
//                    // Handle navigation for Home activity
//                    return true;
//                } else if (item.getItemId() == R.id.navigation_map) {
//                    System.out.println("bbb");
//                    // Handle navigation for Map activity
//                    return true;
//                } else if (item.getItemId() == R.id.navigation_favorite) {
//                    System.out.println("ccc");
//                    // Handle navigation for Favorite activity
//                    return true;
//                } else if (item.getItemId() == R.id.navigation_translate) {
//                    System.out.println("ddd");
//                    // Handle navigation for Translate activity
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
    }
}
