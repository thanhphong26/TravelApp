package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.travel.R;

public class DetailTourActivity extends AppCompatActivity {
    FloatingActionButton btnLove;
    private boolean isHeartRed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);
        btnLove=(FloatingActionButton) findViewById(R.id.fabLove);
        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isHeartRed = !isHeartRed;
                if (isHeartRed) {
                    btnLove.setImageResource(R.drawable.red_heart);
                } else {
                    btnLove.setImageResource(R.drawable.heart);
                }
            }
        });
    }
}