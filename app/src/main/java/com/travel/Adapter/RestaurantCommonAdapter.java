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
import com.travel.Activity.DetailRestaurantActivity;
import com.travel.Model.RestaurantModel;
import com.travel.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantCommonAdapter<T> extends RecyclerView.Adapter<RestaurantCommonAdapter.RestaurantCommonViewHolder> {
    private List<T> listItem;
    private Context context;

    public RestaurantCommonAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantCommonAdapter.RestaurantCommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_common_card, parent, false);
        return new RestaurantCommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantCommonAdapter.RestaurantCommonViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof RestaurantModel){
            bindRestaurantModel(holder, (RestaurantModel) item);
        }
    }

    private void bindRestaurantModel(RestaurantCommonViewHolder holder, RestaurantModel item) {
        holder.name.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_restaurant_common).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailRestaurantActivity.class);
                intent.putExtra("restaurantId", item.getRestaurantId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class RestaurantCommonViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public RestaurantCommonViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }
    }
}
