package com.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.Activity.DetailTourActivity;
import com.travel.Model.TourModel;
import com.travel.R;

import java.util.ArrayList;
import java.util.List;

public class TourCommonAdapter<T> extends RecyclerView.Adapter<TourCommonAdapter.TourCommonViewHolder> {
    private List<T> listItem;
    private Context context;

    public TourCommonAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public TourCommonAdapter.TourCommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tour_common_card, parent, false);
        return new TourCommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourCommonAdapter.TourCommonViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof TourModel){
            bindTourModel(holder, (TourModel) item);
        }
    }

    private void bindTourModel(TourCommonViewHolder holder, TourModel item) {
        holder.name.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_hg).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTourActivity.class);
                intent.putExtra("tourId", item.getTourId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class TourCommonViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public TourCommonViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.roundedCityImage);
            name = itemView.findViewById(R.id.city_name);
        }
    }
}
