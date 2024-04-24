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
import com.travel.Activity.DetailDestinationActivity;
import com.travel.Model.DestinationDetailModel;
import com.travel.Model.DestinationModel;
import com.travel.R;
import com.travel.Utils.NumberHelper;

import java.util.ArrayList;
import java.util.List;

public class DestinationFavoriteAdapter<T> extends RecyclerView.Adapter<DestinationFavoriteAdapter.DestinationFavoriteViewHolder> {
    private List<T> listItem;
    private Context context;

    public DestinationFavoriteAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public DestinationFavoriteAdapter.DestinationFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.destination_favorite_card, parent, false);
        return new DestinationFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationFavoriteAdapter.DestinationFavoriteViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof DestinationDetailModel){
            bindDestinationModel(holder, (DestinationDetailModel) item);
        }
    }

    private void bindDestinationModel(DestinationFavoriteViewHolder holder, DestinationDetailModel item) {
        holder.name.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_hn).into(holder.image);
        holder.rating.setText(String.valueOf(item.getRating()));
        holder.description.setText(item.getDescription());

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

    public class DestinationFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, rating, description;

        public DestinationFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            description = itemView.findViewById(R.id.description);
        }
    }
}
