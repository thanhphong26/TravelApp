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
import com.travel.Database.WishlistDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.NumberHelper;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.Utils.SnackBarHelper;

import java.util.ArrayList;
import java.util.List;

public class HotelFavoriteAdapter<T> extends RecyclerView.Adapter<HotelFavoriteAdapter.HotelCommonViewHolder> {
    private List<T> listItem;
    private Context context;
    WishlistDAO wishlistDAO;
    UserModel currentUser = SharePreferencesHelper.getInstance().get(Constants.USER_SHARE_PREFERENCES, UserModel.class);

    public HotelFavoriteAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
        this.wishlistDAO = new WishlistDAO(context);
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
        setHeartColor(holder.imgLove, wishlistDAO.checkFavoriteHotel(item.getHotelId(), currentUser.getUserId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailHotelActivity.class);
                intent.putExtra("hotelId", item.getHotelId());
                context.startActivity(intent);
            }
        });

        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCurrentlyFavorite = wishlistDAO.checkFavoriteHotel(item.getHotelId(), currentUser.getUserId());
                boolean newFavoriteState = !isCurrentlyFavorite;
                setHeartColor(holder.imgLove, newFavoriteState);
                if (newFavoriteState) {
                    wishlistDAO.insertHotelWhishlist(currentUser.getUserId(), item.getHotelId());
                    SnackBarHelper.showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    wishlistDAO.removeWhishlistHotelId(currentUser.getUserId(),item.getHotelId());
                    SnackBarHelper.showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class HotelCommonViewHolder extends RecyclerView.ViewHolder {
        ImageView hotelImage, imgLove;
        TextView hotelName, rating, price, address, description;
        RatingBar ratingBar;

        public HotelCommonViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLove = itemView.findViewById(R.id.imgLove);
            hotelImage = itemView.findViewById(R.id.roundedCityImage);
            hotelName = itemView.findViewById(R.id.tv_hotel_name);
            rating = itemView.findViewById(R.id.tour_favorite_rating);
            price = itemView.findViewById(R.id.tv_price);
            address = itemView.findViewById(R.id.tour_favorite_address);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    private void setHeartColor(ImageView imageView, boolean isHeartRed) {
        if (isHeartRed) {
            imageView.setImageResource(R.drawable.ic_heart);
        } else {
            imageView.setImageResource(R.drawable.icon_heart);
        }
    }
}
