package com.travel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.travel.Model.ImageTourModel;
import com.travel.R;
import android.os.Handler;

import java.util.List;
import java.util.logging.LogRecord;

public class ImageTourDetailAdapter extends RecyclerView.Adapter<ImageTourDetailAdapter.ImageViewHolder> {
    private List<ImageTourModel> imageList;
    private ViewPager2 viewPager2;

    public ImageTourDetailAdapter(List<ImageTourModel> imageList, ViewPager2 viewPager2){
        this.imageList = imageList;
        this.viewPager2 = viewPager2;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.image_container, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageTourModel imageTour = imageList.get(position);
        Glide.with(viewPager2)
                .load(imageTour.getImage())
                .error(R.drawable.default_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
