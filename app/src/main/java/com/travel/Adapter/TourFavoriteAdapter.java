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
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.Utils.NumberHelper;

import java.util.ArrayList;
import java.util.List;

public class TourFavoriteAdapter<T> extends RecyclerView.Adapter<TourFavoriteAdapter.TourFavoriteViewHolder> {
    private List<T> listItem;
    private Context context;

    public TourFavoriteAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public TourFavoriteAdapter.TourFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tour_favorite_card, parent, false);
        return new TourFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourFavoriteAdapter.TourFavoriteViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof TourModel){
            bindTourModel(holder, (TourModel) item);
        }
    }

    private void bindTourModel(TourFavoriteViewHolder holder, TourModel item) {
        System.out.println("Tour Nameeeeeeeeeeeeeee: " + item.getName());
        holder.name.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_card_favorite).into(holder.image);
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.price.setText(NumberHelper.getFormattedPrice(item.getPrice()) + " đ");
        holder.address.setText(item.getDestination().getName());
        holder.ratingBar.setRating(item.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHotelActivity.class);
                intent.putExtra("tourId", item.getTourId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class TourFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, rating, price, address, description;
        RatingBar ratingBar;

        public TourFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.tour_roundedCityImage);
            name = itemView.findViewById(R.id.tv_tour_name);
            rating = itemView.findViewById(R.id.tour_favorite_rating);
            price = itemView.findViewById(R.id.tour_favorite_price);
            address = itemView.findViewById(R.id.tour_favorite_address);
            ratingBar = itemView.findViewById(R.id.tour_favorite_ratingBar);
        }
    }
}
