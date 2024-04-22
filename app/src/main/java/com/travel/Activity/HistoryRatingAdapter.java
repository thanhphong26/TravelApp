package com.travel.Activity;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.travel.Model.HistoryRatingModel;
import com.travel.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoryRatingAdapter extends ArrayAdapter<HistoryRatingModel> {
    Activity context;

    int idLayout;
    ArrayList<HistoryRatingModel> list;

    public HistoryRatingAdapter( Activity context, int idLayout, ArrayList<HistoryRatingModel> list) {
        super(context, idLayout, list);
        this.context = context;
        this.idLayout = idLayout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(idLayout, null);
        HistoryRatingModel historyRatingModel = list.get(position);
        ImageView img = convertView.findViewById(R.id.imgRating);
        TextView txtName = convertView.findViewById(R.id.tvName);
        TextView txtMota= convertView.findViewById(R.id.tvRatingMoTa);
        TextView txtNoiDung = convertView.findViewById(R.id.tvRatingNoiDung);
        RatingBar ratingBar = convertView.findViewById(R.id.itemratingBar);
        TextView time=convertView.findViewById(R.id.tvNgayDanhGia);
        txtName.setText(historyRatingModel.getName());
        txtMota.setText(historyRatingModel.getDescription());
        txtNoiDung.setText(historyRatingModel.getReview());
        ratingBar.setRating(historyRatingModel.getRating());
        time.setText(historyRatingModel.getDate());
        Glide.with(convertView.getContext()).load(historyRatingModel.getImage()).into(img);
        return convertView;
    }
}
