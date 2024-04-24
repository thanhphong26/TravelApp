package com.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.Activity.DetailDestinationActivity;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.R;
import com.travel.Utils.NumberHelper;

import java.util.ArrayList;
import java.util.List;

public class DestinationCommonRatingAdapter<T> extends RecyclerView.Adapter<DestinationCommonRatingAdapter.DestinationCommonRatingViewHolder> {
    private List<T> listItem;
    private Context context;

    public DestinationCommonRatingAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public DestinationCommonRatingAdapter.DestinationCommonRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tour_card, parent, false);
        return new DestinationCommonRatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationCommonRatingAdapter.DestinationCommonRatingViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof DestinationDetailModel){
            bindDestinationModel(holder, (DestinationDetailModel) item);
        }
    }

    private void bindDestinationModel(DestinationCommonRatingViewHolder holder, DestinationDetailModel item) {
        holder.name.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.img_ha_giang).into(holder.image);
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.price.setText("Từ " + NumberHelper.getFormattedPrice(item.getMinPrice()) + "đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDestinationActivity.class);
                intent.putExtra("destinationId", item.getDestinationId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class DestinationCommonRatingViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, rating, price;

        public DestinationCommonRatingViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgCity);
            name = itemView.findViewById(R.id.txtCity);
            rating = itemView.findViewById(R.id.txtRating);
            price = itemView.findViewById(R.id.tvPrice);
        }
    }
}
