package com.travel.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.travel.Activity.DetailHotelActivity;
import com.travel.Activity.DetailRestaurantActivity;
import com.travel.Activity.DetailTourActivity;
import com.travel.Database.WishlistDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.LoyaltyPointModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.Model.VoucherModel;
import com.travel.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class VoucherAdapter<T> extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder>{
    private List<T> listItem;
    private Context context;
    public VoucherAdapter(List<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }
    @NonNull
    @Override
    public VoucherAdapter.VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.voucher,parent,false);
        return new VoucherViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.VoucherViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof VoucherModel){
            bindVoucherModel(holder, (VoucherModel) item);
        }
    }
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder{
        TextView voucherCode, voucherDescription;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            voucherCode = itemView.findViewById(R.id.voucher_Code_Txt);
            voucherDescription = itemView.findViewById(R.id.voucher_Description_Txt);
        }
    }
    public void bindVoucherModel(VoucherViewHolder holder, VoucherModel voucherModel){
        holder.voucherCode.setText("Mã giảm giá " +voucherModel.getVoucherCode());
        holder.voucherDescription.setText(voucherModel.getVoucherDescription());
    }
}
