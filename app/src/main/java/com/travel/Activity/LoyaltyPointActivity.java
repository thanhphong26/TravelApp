package com.travel.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.travel.Adapter.DetailDestinationAdapter;
import com.travel.Adapter.HistoryPointsAdapter;
import com.travel.Database.LoyaltyPointDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.LoyaltyPointModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityLoyaltyPointBinding;
import com.travel.databinding.ActivityPersonalInfoBinding;
import com.travel.databinding.HistoryPointsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoyaltyPointActivity extends AppCompatActivity {
    ActivityLoyaltyPointBinding binding;
    LoyaltyPointDAO loyaltyPointDAO = new LoyaltyPointDAO();
    LoyaltyPointModel loyaltyPoint = new LoyaltyPointModel();
    int totalPoint;
    List<LoyaltyPointModel> loyaltyPointModels;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoyaltyPointBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        int userId = userModel.getUserId();
        this.displayTotalPoint(userId);
        this.setupLayoutRecyclerView();
        this.displayHistoryPoints(userId);
        this.displayRank();
    }

    private void displayTotalPoint(int userId) {
        loyaltyPoint = loyaltyPointDAO.getUserPoints(userId);
        if (loyaltyPoint != null) {
            int userPoints = loyaltyPoint.getPoints();
            TextView pointTextView = findViewById(R.id.point_txt);
            pointTextView.setText(String.valueOf(userPoints));
        }
        totalPoint = getTotalPoint(userId);
        binding.pointTxt.setText(totalPoint + " điểm");
        binding.recentPointTxt.setText(String.valueOf(totalPoint));
        binding.progressBar.setProgress((totalPoint));
    }

    private void displayHistoryPoints(int userId) {
        List<LoyaltyPointModel> loyaltyPointModels = loyaltyPointDAO.getAllPoint(userId);
        HistoryPointsAdapter<LoyaltyPointModel> historyPointsAdapter = new HistoryPointsAdapter<>(loyaltyPointModels, this);
        binding.recyclerViewLoyaltyPoint.setAdapter(historyPointsAdapter);
    }

    private void setupLayoutRecyclerView() {
        LinearLayoutManager layoutManagerLoyaltyPoints = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewLoyaltyPoint.setLayoutManager(layoutManagerLoyaltyPoints);
    }

    public int getTotalPoint(int userId) {
        loyaltyPointModels = loyaltyPointDAO.getAllPoint(userId);
        return loyaltyPointModels.stream().mapToInt(LoyaltyPointModel::getPoints).sum();
    }

    public void displayRank() {
        if (totalPoint <= 500) {
            binding.rankTxt.setText("Đồng");
            binding.rankImg.setImageResource(R.drawable.bronze);
            binding.awardImg.setImageResource(R.drawable.bronze);
        } else if (totalPoint > 500 && totalPoint <= 1000) {
            binding.rankTxt.setText("Bạc");
            binding.rankImg.setImageResource(R.drawable.silver);
            binding.awardImg.setImageResource(R.drawable.silver);
        } else if (totalPoint > 1000 && totalPoint <= 2400) {
            binding.rankTxt.setText("Vàng");
            binding.rankImg.setImageResource(R.drawable.gold);
            binding.awardImg.setImageResource(R.drawable.gold);
        } else {
            binding.rankTxt.setText("Kim cương");
            binding.rankImg.setImageResource(R.drawable.diamon);
            binding.awardImg.setImageResource(R.drawable.diamon);
        }
    }
}
