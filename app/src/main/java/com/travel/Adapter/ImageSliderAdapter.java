package com.travel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.travel.Model.ImageTourModel;
import com.travel.R;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private List<String> imageList;
    private ViewPager2 viewPager2;

    public ImageSliderAdapter(List<String> imageList, ViewPager2 viewPager2){
        this.imageList = imageList;
        this.viewPager2 = viewPager2;
    }
    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.rounded_card, parent, false);
        return new ImageSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        String image = imageList.get(position);
        Glide.with(viewPager2)
                .load(image)
                .error(R.drawable.default_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageSliderViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView imageView;
        public ImageSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            imageView.setShapeAppearanceModel(imageView.getShapeAppearanceModel()
                    .toBuilder()
                    .setTopLeftCornerSize(30)
                    .setTopRightCornerSize(30)
                    .setBottomLeftCornerSize(30)
                    .setBottomRightCornerSize(30)
                    .build());

        }
    }
}
