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
import com.travel.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryPointsAdapter<T> extends RecyclerView.Adapter<HistoryPointsAdapter.HistoryPointsViewHolder>{
    private List<T> listItem;
    private Context context;
    public HistoryPointsAdapter(List<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }
    @NonNull
    @Override
    public HistoryPointsAdapter.HistoryPointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.history_points,parent,false);
        return new HistoryPointsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HistoryPointsAdapter.HistoryPointsViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof LoyaltyPointModel){
            bindLoyaltyPointModel(holder, (LoyaltyPointModel) item);
        }
    }
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class HistoryPointsViewHolder extends RecyclerView.ViewHolder{
        TextView historyPoint, historyDate;

        public HistoryPointsViewHolder(@NonNull View itemView) {
            super(itemView);
            historyPoint = itemView.findViewById(R.id.historyPoint_txt);
            historyDate = itemView.findViewById(R.id.historyDate_txt);
        }
    }
    public void bindLoyaltyPointModel(HistoryPointsViewHolder holder, LoyaltyPointModel loyaltyPointModel){
        holder.historyPoint.setText(loyaltyPointModel.getPoints() + " điểm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String historyDate = dateFormat.format(loyaltyPointModel.getCreatedAt());
        holder.historyDate.setText(historyDate);
    }
}
