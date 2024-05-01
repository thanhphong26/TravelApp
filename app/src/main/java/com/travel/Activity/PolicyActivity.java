package com.travel.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.travel.databinding.PolicyBinding;

public class PolicyActivity extends AppCompatActivity {
    PolicyBinding policyBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        policyBinding = PolicyBinding.inflate(getLayoutInflater());
        setContentView(policyBinding.getRoot());
        policyBinding.imgBack.setOnClickListener(v -> {
            finish();
        });

    }
}
