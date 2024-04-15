package com.travel.Adapter;

import static android.graphics.Color.BLUE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.travel.Database.WishlistDAO;
import com.travel.Model.TourModel;
import com.travel.R;

import java.util.ArrayList;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.TourViewHolder>{
    private boolean isHeartRed = false;
    private List<TourModel> tourModels;
    private Context context;
    private WishlistDAO wishlistDAO;
    private SharedPreferences sharedPreferences;
    private View.OnClickListener onClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public TourAdapter(List<TourModel> tourModels, Context context) {
        this.tourModels = tourModels;
        this.context = context;
    }
    @NonNull
    @Override
    public TourAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_detail_item,parent,false);
        return new TourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.TourViewHolder holder, int position) {
        TourModel tourModel=tourModels.get(position);
        holder.txtTourName.setText(tourModel.getName());
        Glide.with(context).load(tourModel.getImage()).error(R.drawable.default_image).into(holder.imgTour);
        holder.txtTourRating.setText(String.valueOf(tourModel.getRating()));
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
    @Override
    public int getItemCount() {
        return tourModels.size();
    }
    public class TourViewHolder extends RecyclerView.ViewHolder {
        TextView txtTourName,  txtTourRating, txtDestinationName;
        ImageView imgTour, imgLove;
        RatingBar ratingBar2;
        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTourName=itemView.findViewById(R.id.txtItemName);
            txtTourRating=itemView.findViewById(R.id.txtRating);
            imgTour=itemView.findViewById(R.id.imgItem);
            imgLove=itemView.findViewById(R.id.imgLove);
            ratingBar2=itemView.findViewById(R.id.ratingBar2);
        }
    }
    @SuppressLint("ResourceType")
    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundTintList(ColorStateList.valueOf(Color.rgb(204,153,255)));
        snackbar.show();

    }
}
