package com.travel.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.travel.Database.VoucherDAO;
import com.travel.Model.VoucherModel;
import com.travel.databinding.ActivityMyDiscountBinding;
import com.travel.databinding.VoucherBinding;

import java.util.List;

public class VoucherActivity extends AppCompatActivity {
    ActivityMyDiscountBinding binding;
    VoucherDAO voucherDAO = new VoucherDAO();
    VoucherModel voucher = new VoucherModel();
    List<VoucherModel> voucherModels;

    // write onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyDiscountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //int voucherId = getIntent().getIntExtra("voucherId", 0);
        int voucherId=1;
        voucherModels = voucherDAO.getAllVouchers(voucherId);
        for (VoucherModel voucher : voucherModels) {
            System.out.println("voucher" + voucher.getVoucherCode() + voucher.getVoucherDescription());
        }
        if (voucher != null) {

            binding.voucherContainer.setVisibility(View.VISIBLE);
            this.displayVoucher();
        }
    }

    public void displayVoucher() {
        if (voucherModels.size() <= 0) {
            binding.voucherContainer.setVisibility(View.GONE);
        } else {
            binding.voucherContainer.setVisibility(View.VISIBLE);
            for (VoucherModel voucher : voucherModels) {
                VoucherBinding voucherBinding = VoucherBinding.inflate(getLayoutInflater());
                voucherBinding.voucherCodeTxt.setText(voucher.getVoucherCode());
                voucherBinding.voucherDescriptionTxt.setText(voucher.getVoucherDescription());
            }
        }
    }
}
