package com.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.travel.Activity.MapsActivity;
import com.travel.Database.DestinationDAO;
import com.travel.Database.TourDAO;
import com.travel.Model.DestinationModel;
import com.travel.Model.OnClickListener;
import com.travel.Model.TourLineModel;
import com.travel.Model.TourModel;
import com.travel.R;
import java.util.ArrayList;

public class TourLineAdapter extends RecyclerView.Adapter<TourLineAdapter.TourLineViewHolder>{
    private ArrayList<TourLineModel> tourLineList;
    private Context context;
    TourDAO tourDAO;
    private View.OnClickListener onClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public TourLineAdapter(ArrayList<TourLineModel> tourLineList, Context context) {
        this.tourLineList = tourLineList;
        this.context = context;
        this.tourDAO=new TourDAO(context);
    }
    @NonNull
    @Override
    public TourLineAdapter.TourLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.timeline_sightseeing,parent,false);
        return new TourLineViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TourLineAdapter.TourLineViewHolder holder, int position) {
        TourLineModel tourLineModel = tourLineList.get(position);
        TourModel tourModel=new TourModel();
        tourModel=tourDAO.getDestinationId(tourLineModel.getTourId());
        SpannableString locationName = new SpannableString(tourLineModel.getLocationName());
        locationName.setSpan(new UnderlineSpan(), 0, locationName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.txtDestinationName.setText(locationName);
        holder.txtTime.setText(tourLineModel.getTime()+"-"+tourLineModel.getEndTime());
        Glide.with(context).load(tourLineModel.getImage()).error(R.drawable.default_image).into(holder.imgDestination);
        TourModel finalTourModel = tourModel;
        holder.txtDestinationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("destinationId", finalTourModel.getDestination().getDestinationId());
                intent.putExtra("tourId", tourLineModel.getTourId());
                intent.putExtra("latitude", tourLineModel.getLatitude());
                intent.putExtra("longitude", tourLineModel.getLongitude());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return tourLineList.size();
    }

    public class TourLineViewHolder  extends RecyclerView.ViewHolder{
        TextView txtTime, txtDestinationName;
        RoundedImageView imgDestination;
        public TourLineViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDestinationName = itemView.findViewById(R.id.txtDestinationName);
            imgDestination = itemView.findViewById(R.id.imgTimeDestination);
        }
    }
}
