package com.travel.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.travel.Adapter.HistoryRatingAdapter;
import com.travel.Database.HistoryRatingDAO;
import com.travel.Model.HistoryRatingModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityRatingHistoryBinding;

import java.util.ArrayList;

public class RatingHistoryActivity extends AppCompatActivity {
    ActivityRatingHistoryBinding ratingHistoryBinding;

    ArrayList<HistoryRatingModel> list;
    ArrayList<HistoryRatingModel> listHotel;
    ArrayList<HistoryRatingModel> listResTaurant;
    ArrayList<HistoryRatingModel> listTour;
    HistoryRatingAdapter adapter;
    HistoryRatingDAO historyRatingDAO;
    UserModel userModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ratingHistoryBinding = ActivityRatingHistoryBinding.inflate(getLayoutInflater());
        setContentView(ratingHistoryBinding.getRoot());
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        int userId = userModel.getUserId();
        historyRatingDAO = new HistoryRatingDAO();
        listHotel = historyRatingDAO.getReviewHotel(userId);
        listResTaurant = historyRatingDAO.getReviewRestaurant(userId);
        listTour = historyRatingDAO.getReviewTour(userId);
        list = new ArrayList<>();
        list.addAll(listHotel);
        list.addAll(listResTaurant);
        list.addAll(listTour);
        adapter = new HistoryRatingAdapter(this, R.layout.item_rating, list);
        ratingHistoryBinding.lvRatingHistory.setAdapter(adapter);
        ratingHistoryBinding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}
