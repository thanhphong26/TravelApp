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
import com.squareup.picasso.Picasso;
import com.travel.Activity.DetailDestinationActivity;
import com.travel.Database.WishlistDAO;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.NumberHelper;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.Utils.SnackBarHelper;

import java.util.ArrayList;
import java.util.List;

public class DestinationFavoriteAdapter extends RecyclerView.Adapter<DestinationFavoriteAdapter.DestinationFavoriteViewHolder> {
    private List<DestinationDetailModel> listItem;
    private Context context;
    WishlistDAO wishlistDAO;
    UserModel currentUser = SharePreferencesHelper.getInstance().get(Constants.USER_SHARE_PREFERENCES, UserModel.class);

    public DestinationFavoriteAdapter(ArrayList listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
        this.wishlistDAO = new WishlistDAO(context);
    }

    @NonNull
    @Override
    public DestinationFavoriteAdapter.DestinationFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.destination_favorite_card, parent, false);
        return new DestinationFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationFavoriteAdapter.DestinationFavoriteViewHolder holder, int position) {
        DestinationDetailModel item = listItem.get(position);
        bindDestinationModel(holder, item);
    }

    private void bindDestinationModel(DestinationFavoriteViewHolder holder, DestinationDetailModel item) {
        holder.name.setText(item.getName());
        Picasso.get().load(item.getImage()).error(R.drawable.default_image).into(holder.image);
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.description.setText(item.getDescription());
        setHeartColor(holder.imgLove, wishlistDAO.checkFavoriteDestination(item.getDestinationId(), currentUser.getUserId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDestinationActivity.class);
                intent.putExtra("destinationId", item.getDestinationId());
                context.startActivity(intent);
            }
        });

        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCurrentlyFavorite = wishlistDAO.checkFavoriteDestination(item.getDestinationId(), currentUser.getUserId());
                boolean newFavoriteState = !isCurrentlyFavorite;
                setHeartColor(holder.imgLove, newFavoriteState);
                if (newFavoriteState) {
                    wishlistDAO.insertDestinationWhishlist(currentUser.getUserId(), item.getDestinationId());
                    SnackBarHelper.showSnackbar(v, "Đã thêm vào danh sách yêu thích");
                } else {
                    wishlistDAO.removeWishListDestinationId(currentUser.getUserId(),item.getDestinationId());
                    SnackBarHelper.showSnackbar(v, "Đã xóa khỏi danh sách yêu thích");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class DestinationFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView image, imgLove;
        TextView name, rating, description;

        public DestinationFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLove = itemView.findViewById(R.id.imgLove);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            description = itemView.findViewById(R.id.description);
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
