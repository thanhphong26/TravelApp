package com.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.Activity.DetailHotelActivity;
import com.travel.Activity.DetailRestaurantActivity;
import com.travel.Model.RestaurantModel;
import com.travel.R;
import com.travel.Utils.NumberHelper;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFavoriteAdapter<T> extends RecyclerView.Adapter<RestaurantFavoriteAdapter.RestaurantFavoriteViewHolder> {
    private List<T> listItem;
    private Context context;

    public RestaurantFavoriteAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantFavoriteAdapter.RestaurantFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_favorite_card, parent, false);
        return new RestaurantFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantFavoriteAdapter.RestaurantFavoriteViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof RestaurantModel){
            bindRestaurantModel(holder, (RestaurantModel) item);
        }
    }

    private void bindRestaurantModel(RestaurantFavoriteViewHolder holder, RestaurantModel item) {
        holder.name.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_restaurant_favorite).into(holder.image);
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.price.setText(NumberHelper.getFormattedPrice(item.getPrice()) + " đ");
        holder.address.setText(item.getDestination().getName());
        holder.ratingBar.setRating(item.getRating());

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

    public class RestaurantFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, rating, price, address, description;
        RatingBar ratingBar;

        public RestaurantFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.restaurant_roundedCityImage);
            name = itemView.findViewById(R.id.tv_restaurant_name);
            rating = itemView.findViewById(R.id.restaurant_favorite_rating);
            price = itemView.findViewById(R.id.restaurant_favorite_price);
            address = itemView.findViewById(R.id.restaurant_favorite_address);
            ratingBar = itemView.findViewById(R.id.restaurant_favorite_ratingBar);
        }
    }
}
