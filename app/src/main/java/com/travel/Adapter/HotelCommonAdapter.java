package com.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.travel.Activity.DetailHotelActivity;
import com.travel.Model.HotelModel;
import com.travel.R;

import java.util.ArrayList;
import java.util.List;

public class HotelCommonAdapter<T> extends RecyclerView.Adapter<HotelCommonAdapter.HotelCommonViewHolder> {
    private List<T> listItem;
    private Context context;

    public HotelCommonAdapter(ArrayList<T> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelCommonAdapter.HotelCommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotel_common_card, parent, false);
        return new HotelCommonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelCommonAdapter.HotelCommonViewHolder holder, int position) {
       System.out.println("BIDINGGGGGGGGGGGGGGGGGGGG");
        T item = listItem.get(position);
        System.out.println("Itemmmmmmmmmmmmmmmmmmmmmmmm" +  item.toString() + "  " + item.getClass().getName() + "");
        if (item instanceof HotelModel){
            bindHotelModel(holder, (HotelModel) item);
        }
    }

    private void bindHotelModel(HotelCommonViewHolder holder, HotelModel item) {
        System.out.println("Hotelllllllllllllllllll: " + item.getName());
        holder.hotelName.setText(item.getName());
        Glide.with(context).load(item.getImage()).error(R.drawable.bg_test_hotel_common_card).into(holder.hotelImage);

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailHotelActivity.class);
//                intent.putExtra("hotelId", item.getHotelId());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HotelCommonViewHolder extends RecyclerView.ViewHolder {
        ImageView hotelImage;
        TextView hotelName;

        CardView cardView;

        public HotelCommonViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.hotel_common_image);
            hotelName = itemView.findViewById(R.id.hotel_common_name);
            cardView = itemView.findViewById(R.id.hotelCommonCardContainer);
        }
    }
}
