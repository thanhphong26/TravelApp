package com.travel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import com.bumptech.glide.Glide;
import com.travel.Model.TourBookingModel;
import com.travel.R;

public class HistoryCardAdapter<T> extends RecyclerView.Adapter<HistoryCardAdapter.HistoryCardViewHolder> {
    private List<T> listItem;
    private Context context;
    public HistoryCardAdapter(List<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }
    @NonNull
    @Override
    public HistoryCardAdapter.HistoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_card, parent, false);
        return new HistoryCardViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HistoryCardAdapter.HistoryCardViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof TourBookingModel) {
            bindTourBookingModel(holder, (TourBookingModel) item);
        }
    }
    @Override
    public int getItemCount() {
        return listItem.size();
    }
    public class HistoryCardViewHolder extends RecyclerView.ViewHolder {
        TextView tourName, tourDescription, createdAt;
        View tourImage;
        public HistoryCardViewHolder(@NonNull View itemView) {
            super(itemView);
            tourName = itemView.findViewById(R.id.tour_name);
            tourImage = itemView.findViewById(R.id.tour_img);
            tourDescription = itemView.findViewById(R.id.tour_description);
            createdAt = itemView.findViewById(R.id.date);
        }
    }
    private void bindTourBookingModel(HistoryCardViewHolder holder, TourBookingModel tourBookingModel) {
        View view = holder.itemView;
        holder.tourName.setText(tourBookingModel.getTour().getName());
        Glide.with(context).load(tourBookingModel.getTour().getImage()).into(((ImageView)view.findViewById(R.id.tour_img)));
        // shortcut description
        String description = tourBookingModel.getTour().getDescription();
        if (description.length() > 150) {
            description = description.substring(0, 150) + "...";
        }
        holder.tourDescription.setText(description);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(tourBookingModel.getCreatedAt());
        holder.createdAt.setText("Đã đặt ngày " + date);
    }
}
