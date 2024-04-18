package com.travel.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.travel.Database.DatabaseHelper;
import com.travel.Model.ReviewModel;
import com.travel.Model.UserModel;
import com.travel.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    List<ReviewModel> reviewModelList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    Context context;
    public ReviewAdapter(Context context, List<ReviewModel> reviewList) {
        this.context = context;
        this.reviewModelList = reviewList;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.review_item,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewModel reviewModel=reviewModelList.get(position);
        UserModel userModel = getUser(reviewModel.getUserId());
        holder.txtReview.setText(reviewModel.getReview());
        holder.txtRating.setText(String.valueOf(reviewModel.getRating())+"/5");
        holder.txtDate.setText(reviewModel.getReviewDate().toString());
        holder.txtUsername.setText(userModel.getUsername());
        Glide.with(context).load(userModel.getAvatar()) .error(R.drawable.profile_user).into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return reviewModelList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView txtUsername, txtReview, txtDate, txtRating;
        ShapeableImageView imgUser;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtReview = itemView.findViewById(R.id.txtReview);
            txtDate = itemView.findViewById(R.id.txtReviewDate);
            txtRating = itemView.findViewById(R.id.txtRating);
            imgUser = itemView.findViewById(R.id.edit_avt);
        }
    }
    public UserModel getUser(int userId){
        UserModel userModel = new UserModel();
        database = databaseHelper.openDatabase();
        Cursor cursor=null;
        if (database != null) {
            try {
                cursor = database.query("users", null, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    userModel.setUserId(cursor.getInt(0));
                    userModel.setUsername(cursor.getString(1));
                    userModel.setPhoneNumber(cursor.getString(2));
                    userModel.setEmail(cursor.getString(3));
                    userModel.setPassword(cursor.getString(4));
                    userModel.setAvatar(cursor.getString(6));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return userModel;
    }
}
