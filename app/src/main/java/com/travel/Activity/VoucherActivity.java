package com.travel.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.Adapter.HistoryPointsAdapter;
import com.travel.Adapter.VoucherAdapter;
import com.travel.Database.VoucherDAO;
import com.travel.Model.LoyaltyPointModel;
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
        this.setupLayoutRecyclerView();
        this.displayVoucher();
    }

    public void displayVoucher() {
        List<VoucherModel> voucherModels = voucherDAO.getAllVouchers();
        VoucherAdapter<VoucherModel> voucherAdapter = new VoucherAdapter<>(voucherModels, this);
        binding.recyclerViewVoucher.setAdapter(voucherAdapter);
    }
    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerVoucher = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewVoucher.setLayoutManager(layoutManagerVoucher);
    }
}
