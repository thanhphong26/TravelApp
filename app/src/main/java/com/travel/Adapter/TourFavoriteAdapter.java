package com.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.Activity.BookTourActivity;
import com.travel.Activity.DetailTourActivity;
import com.travel.Activity.MapsActivity;
import com.travel.Database.WishlistDAO;
import com.travel.Model.TourModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.NumberHelper;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.Utils.SnackBarHelper;

import java.util.ArrayList;
import java.util.List;

public class TourFavoriteAdapter<T> extends RecyclerView.Adapter<TourFavoriteAdapter.TourFavoriteViewHolder> {
    private List<T> listItem;
    private Context context;
    WishlistDAO wishlistDAO;
    UserModel currentUser = SharePreferencesHelper.getInstance().get(Constants.USER_SHARE_PREFERENCES, UserModel.class);

    public TourFavoriteAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
        this.wishlistDAO = new WishlistDAO(context);
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
        holder.name.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_card_favorite).into(holder.image);
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.price.setText(NumberHelper.getFormattedPrice(item.getPrice()) + " đ");
        holder.address.setText(item.getDestination().getName());
        holder.ratingBar.setRating(item.getRating());
        setHeartColor(holder.imgLove, wishlistDAO.checkFavoriteTour(item.getTourId(), currentUser.getUserId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTourActivity.class);
                intent.putExtra("tourId", item.getTourId());
                context.startActivity(intent);
            }
        });

        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCurrentlyFavorite = wishlistDAO.checkFavoriteTour(item.getTourId(), currentUser.getUserId());
                boolean newFavoriteState = !isCurrentlyFavorite;
                setHeartColor(holder.imgLove, newFavoriteState);
                if (newFavoriteState) {
                    wishlistDAO.insertTourWhishlist(currentUser.getUserId(), item.getTourId());
                    SnackBarHelper.showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    wishlistDAO.removeWhishlistTourId(currentUser.getUserId(),item.getTourId());
                    SnackBarHelper.showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });

        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookTourActivity.class);
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
        ImageView image, imgLove;
        TextView name, rating, price, address, description;
        RatingBar ratingBar;
        Button btnBook;

        public TourFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLove = itemView.findViewById(R.id.imgLove);
            image = itemView.findViewById(R.id.tour_roundedCityImage);
            name = itemView.findViewById(R.id.tv_tour_name);
            rating = itemView.findViewById(R.id.tour_favorite_rating);
            price = itemView.findViewById(R.id.tour_favorite_price);
            address = itemView.findViewById(R.id.tour_favorite_address);
            ratingBar = itemView.findViewById(R.id.tour_favorite_ratingBar);
            btnBook = itemView.findViewById(R.id.btnBooking);
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
