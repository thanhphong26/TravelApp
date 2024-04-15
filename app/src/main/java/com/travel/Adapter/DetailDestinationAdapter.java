package com.travel.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.R;

import java.util.List;

public class DetailDestinationAdapter<T> extends RecyclerView.Adapter<DetailDestinationAdapter.DetailDestinationViewHolder>{
    private List<T> listItem;
    private Context context;
    private boolean isHeartRed = false;

    public DetailDestinationAdapter(List<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }
    @NonNull
    @Override
    public DetailDestinationAdapter.DetailDestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_detail_item,parent,false);
        return new DetailDestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailDestinationAdapter.DetailDestinationViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof TourModel){
            bindTourModel(holder, (TourModel) item);
        } else if (item instanceof HotelModel){
            bindHotelModel(holder, (HotelModel) item);
        } else if (item instanceof RestaurantModel){
            bindRestaurantModel(holder, (RestaurantModel) item);
        }
    }
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class DetailDestinationViewHolder extends RecyclerView.ViewHolder{
        TextView txtItemName, txtRating;
        ImageView imgTour, imgLove;
        RatingBar ratingBar2;

        public DetailDestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtRating = itemView.findViewById(R.id.txtRating);
            imgTour = itemView.findViewById(R.id.imgItem);
            imgLove = itemView.findViewById(R.id.imgLove);
            ratingBar2 = itemView.findViewById(R.id.ratingBar2);
        }
    }

    public void bindTourModel(DetailDestinationViewHolder holder, TourModel tourModel){
        holder.txtItemName.setText(tourModel.getName());
        Glide.with(context).load(tourModel.getImage()).error(R.drawable.default_image).into(holder.imgTour);
        holder.txtRating.setText(String.valueOf(tourModel.getRating()));
        holder.ratingBar2.setRating(tourModel.getRating());
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeartRed = !isHeartRed;
                if (isHeartRed) {
                    holder.imgLove.setImageResource(R.drawable.ic_heart);
                    //wishlistDAO.insert(tourModel.getId(), sharedPreferences.getInt("userId", 0));
                    showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    holder.imgLove.setImageResource(R.drawable.icon_heart);
                    //wishlistDAO.remove(tourModel.getId(), sharedPreferences.getInt("userId", 0));
                    showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });
    }
    public void bindHotelModel(DetailDestinationViewHolder holder, HotelModel hotelModel){
        //handler hotelModel
        holder.txtItemName.setText(hotelModel.getName());
        Glide.with(context).load(hotelModel.getImage()).error(R.drawable.default_image).into(holder.imgTour);
        holder.txtRating.setText(String.valueOf(hotelModel.getRating()));
        holder.ratingBar2.setRating(hotelModel.getRating());
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeartRed = !isHeartRed;
                if (isHeartRed) {
                    holder.imgLove.setImageResource(R.drawable.ic_heart);
                    //wishlistDAO.insert(tourModel.getId(), sharedPreferences.getInt("userId", 0));
                    showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    holder.imgLove.setImageResource(R.drawable.icon_heart);
                    //wishlistDAO.remove(tourModel.getId(), sharedPreferences.getInt("userId", 0));
                    showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });
    }
    public void bindRestaurantModel(DetailDestinationViewHolder holder, RestaurantModel restaurantModel){
        //bind restaurant model
        holder.txtItemName.setText(restaurantModel.getName());
        Glide.with(context).load(restaurantModel.getImage()).error(R.drawable.default_image).into(holder.imgTour);
        holder.txtRating.setText(String.valueOf(restaurantModel.getRating()));
        holder.ratingBar2.setRating(restaurantModel.getRating());
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeartRed = !isHeartRed;
                if (isHeartRed) {
                    holder.imgLove.setImageResource(R.drawable.ic_heart);
                    //wishlistDAO.insert(tourModel.getId(), sharedPreferences.getInt("userId", 0));
                    showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    holder.imgLove.setImageResource(R.drawable.icon_heart);
                    //wishlistDAO.remove(tourModel.getId(), sharedPreferences.getInt("userId", 0));
                    showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });
    }
    @SuppressLint("ResourceType")
    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundTintList(ColorStateList.valueOf(Color.rgb(204,153,255)));
        snackbar.show();

    }
}
