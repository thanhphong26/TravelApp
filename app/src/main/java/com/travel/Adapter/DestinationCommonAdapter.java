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

import java.util.ArrayList;
import java.util.List;

public class DestinationCommonAdapter<T> extends RecyclerView.Adapter<DestinationCommonAdapter.DestinationCommonViewHolder> {
    private List<T> listItem;
    private Context context;

    public DestinationCommonAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public DestinationCommonAdapter.DestinationCommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_card, parent, false);
        return new DestinationCommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationCommonAdapter.DestinationCommonViewHolder holder, int position) {
        T item = listItem.get(position);
        if (item instanceof DestinationDetailModel){
            bindDestinationModel(holder, (DestinationDetailModel) item);
        }
    }

    private void bindDestinationModel(DestinationCommonViewHolder holder, DestinationDetailModel item) {
        holder.name.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_hg).into(holder.image);

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

    public class DestinationCommonViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public DestinationCommonViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.roundedCityImage);
            name = itemView.findViewById(R.id.city_name);
        }
    }
}
