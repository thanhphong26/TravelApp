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
import com.travel.Model.HotelModel;
import com.travel.R;
import com.travel.Utils.NumberHelper;

import java.util.ArrayList;
import java.util.List;

public class HotelFavoriteAdapter<T> extends RecyclerView.Adapter<HotelFavoriteAdapter.HotelCommonViewHolder> {
    private List<T> listItem;
    private Context context;

    public HotelFavoriteAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelFavoriteAdapter.HotelCommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotel_favorite_card, parent, false);
        return new HotelCommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelFavoriteAdapter.HotelCommonViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof HotelModel){
            bindHotelModel(holder, (HotelModel) item);
        }
    }

    private void bindHotelModel(HotelCommonViewHolder holder, HotelModel item) {
        holder.hotelName.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_hotel_common_card).into(holder.hotelImage);
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.price.setText(NumberHelper.getFormattedPrice(item.getPrice()) + " đ");
        holder.address.setText(item.getAddress());
        holder.ratingBar.setRating(item.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHotelActivity.class);
                intent.putExtra("hotelId", item.getHotelId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class HotelCommonViewHolder extends RecyclerView.ViewHolder {
        ImageView hotelImage;
        TextView hotelName, rating, price, address, description;
        RatingBar ratingBar;

        public HotelCommonViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.roundedCityImage);
            hotelName = itemView.findViewById(R.id.tv_hotel_name);
            rating = itemView.findViewById(R.id.tour_favorite_rating);
            price = itemView.findViewById(R.id.tv_price);
            address = itemView.findViewById(R.id.tour_favorite_address);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
