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
import com.travel.Database.DestinationDAO;
import com.travel.Database.WishlistDAO;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.Model.WishlistModel;
import com.travel.R;

import java.text.BreakIterator;
import java.util.List;

public class DetailDestinationAdapter<T> extends RecyclerView.Adapter<DetailDestinationAdapter.DetailDestinationViewHolder>{
    private List<T> listItem;
    private Context context;
    private WishlistDAO wishlistDAO;
    private boolean isHeartRed = false;
    public DetailDestinationAdapter(List<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
        this.wishlistDAO = new WishlistDAO(context);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item instanceof TourModel){
                    TourModel tourModel = (TourModel) item;
                    Intent intent = new Intent(context, DetailTourActivity.class);
                    intent.putExtra("destinationId",tourModel.getDestination().getDestinationId());
                    intent.putExtra("tourId", tourModel.getTourId());
                    context.startActivity(intent);
                } else if (item instanceof HotelModel){
                    HotelModel hotelModel = (HotelModel) item;
                    Intent intent = new Intent(context, DetailHotelActivity.class);
                    intent.putExtra("destinationId",hotelModel.getDestination().getDestinationId());
                    intent.putExtra("hotel_id", hotelModel.getHotelId());
                    context.startActivity(intent);
                } else if (item instanceof RestaurantModel){
                    RestaurantModel restaurantModel = (RestaurantModel) item;
                    Intent intent = new Intent(context, DetailRestaurantActivity.class);
                    intent.putExtra("destinationId",restaurantModel.getDestination().getDestinationId());
                    intent.putExtra("restaurant_id", restaurantModel.getRestaurantId());
                    context.startActivity(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class DetailDestinationViewHolder extends RecyclerView.ViewHolder{
        TextView txtItemName, txtRating,txtDestinationName;
        ImageView imgTour, imgLove;
        RatingBar ratingBar2;

        public DetailDestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtDestinationName= itemView.findViewById(R.id.txtDestination);
            imgTour = itemView.findViewById(R.id.imgItem);
            imgLove = itemView.findViewById(R.id.imgLove);
            ratingBar2 = itemView.findViewById(R.id.ratingBar2);
        }
    }
    public void bindTourModel(DetailDestinationViewHolder holder, TourModel tourModel){
        holder.txtItemName.setText(tourModel.getName());
        holder.txtDestinationName.setText(tourModel.getDestination().getName());
        Glide.with(context).load(tourModel.getImage()).error(R.drawable.default_image).into(holder.imgTour);
        holder.txtRating.setText(String.valueOf(tourModel.getRating()));
        holder.ratingBar2.setRating(tourModel.getRating());
        setHeartColor(holder.imgLove, wishlistDAO.checkFavoriteTour(tourModel.getTourId(), 1));
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCurrentlyFavorite = wishlistDAO.checkFavoriteTour(tourModel.getTourId(), 1);
                boolean newFavoriteState = !isCurrentlyFavorite;
                setHeartColor(holder.imgLove, newFavoriteState);
                if (newFavoriteState) {
                    wishlistDAO.insertTourWhishlist(1, tourModel.getTourId());
                    showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    wishlistDAO.removeWhishlistTourId(1,tourModel.getTourId());
                    showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });
    }
    public void bindHotelModel(DetailDestinationViewHolder holder, HotelModel hotelModel){
        //handler hotelModel
        holder.txtItemName.setText(hotelModel.getName());
        holder.txtDestinationName.setText(hotelModel.getDestination().getName());
        Glide.with(context).load(hotelModel.getImage()).error(R.drawable.default_image).into(holder.imgTour);
        holder.txtRating.setText(String.valueOf(hotelModel.getRating()));
        holder.ratingBar2.setRating(hotelModel.getRating());
        setHeartColor(holder.imgLove, wishlistDAO.checkFavoriteHotel(hotelModel.getHotelId(), 1));
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCurrentlyFavorite = wishlistDAO.checkFavoriteHotel(hotelModel.getHotelId(), 1);
                boolean newFavoriteState = !isCurrentlyFavorite;
                setHeartColor(holder.imgLove, newFavoriteState);
                if (newFavoriteState) {
                    wishlistDAO.insertHotelWhishlist(1, hotelModel.getHotelId());
                    showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    wishlistDAO.removeWhishlistHotelId(1,hotelModel.getHotelId());
                    showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });
    }
    public void bindRestaurantModel(DetailDestinationViewHolder holder, RestaurantModel restaurantModel){
        //bind restaurant model
        holder.txtItemName.setText(restaurantModel.getName());
        holder.txtDestinationName.setText(restaurantModel.getDestination().getName());
        Glide.with(context).load(restaurantModel.getImage()).error(R.drawable.default_image).into(holder.imgTour);
        holder.txtRating.setText(String.valueOf(restaurantModel.getRating()));
        holder.ratingBar2.setRating(restaurantModel.getRating());
        setHeartColor(holder.imgLove, wishlistDAO.checkFavoriteRestaurant(restaurantModel.getRestaurantId(), 1));
        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCurrentlyFavorite = wishlistDAO.checkFavoriteRestaurant(restaurantModel.getRestaurantId(), 1);
                boolean newFavoriteState = !isCurrentlyFavorite;
                setHeartColor(holder.imgLove, newFavoriteState);
                if (newFavoriteState) {
                    wishlistDAO.insertRestaurantWhishlist(1, restaurantModel.getRestaurantId());
                    showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    wishlistDAO.removeWhishlistRestaurantId(1,restaurantModel.getRestaurantId());
                    showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });
    }
    private void setHeartColor(ImageView imageView, boolean isHeartRed) {
        if (isHeartRed) {
            imageView.setImageResource(R.drawable.ic_heart);
        } else {
            imageView.setImageResource(R.drawable.icon_heart);
        }
    }
    @SuppressLint("ResourceType")
    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundTintList(ColorStateList.valueOf(Color.rgb(204,153,255)));
        snackbar.show();
    }
}
