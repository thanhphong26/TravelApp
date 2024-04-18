package com.travel.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.travel.Database.LoyaltyPointDAO;
import com.travel.Model.LoyaltyPointModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.databinding.ActivityLoyaltyPointBinding;
import com.travel.databinding.ActivityPersonalInfoBinding;
import com.travel.databinding.HistoryPointsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LoyaltyPointActivity extends AppCompatActivity {
    ActivityLoyaltyPointBinding binding;
    LoyaltyPointDAO loyaltyPointDAO=new LoyaltyPointDAO();
    LoyaltyPointModel loyaltyPoint=new LoyaltyPointModel();
    int totalPoint;
    List<LoyaltyPointModel> loyaltyPointModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoyaltyPointBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int userId=getIntent().getIntExtra("userId", 0);
        //int userId=2;
        loyaltyPoint= loyaltyPointDAO.getUserPoints(userId);
        if (loyaltyPoint != null) {
            int userPoints = loyaltyPoint.getPoints();
            TextView pointTextView = findViewById(R.id.point_txt);
            pointTextView.setText(String.valueOf(userPoints));
        }
        totalPoint=getTotalPoint(userId);
        binding.pointTxt.setText(totalPoint+ " điểm");
        binding.recentPointTxt.setText(String.valueOf(totalPoint));
        binding.progressBar.setProgress((totalPoint));
        this.displayHistoryPoint();
        this.displayRank();
    }
    public int getTotalPoint(int userId){
        loyaltyPointModels=loyaltyPointDAO.getAllPoint(userId);
        return loyaltyPointModels.stream().mapToInt(LoyaltyPointModel::getPoints).sum();
    }

    public void displayHistoryPoint () {

        if (loyaltyPointModels.size() <= 0) {
            binding.loyaltyContainer.setVisibility(View.GONE);
        } else {
            binding.loyaltyContainer.setVisibility(View.VISIBLE);
            for (LoyaltyPointModel loyaltyPoint : loyaltyPointModels) {
                HistoryPointsBinding historyPointsBinding = HistoryPointsBinding.inflate(getLayoutInflater());
                historyPointsBinding.historyPointTxt.setText(loyaltyPoint.getPoints()+" điểm");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String historyDate = dateFormat.format(loyaltyPoint.getCreatedAt());
                historyPointsBinding.historyDateTxt.setText(historyDate);
                binding.loyaltyContainer.addView(historyPointsBinding.getRoot());
            }
        }

    }
    public void displayRank(){
        if (totalPoint <= 500) {
            binding.rankTxt.setText("Đồng");
            binding.rankImg.setImageResource(R.drawable.bronze);
            binding.awardImg.setImageResource(R.drawable.bronze);
        } else if (totalPoint > 500 && totalPoint <= 1000) {
            binding.rankTxt.setText("Bạc");
            binding.rankImg.setImageResource(R.drawable.silver);
            binding.awardImg.setImageResource(R.drawable.silver);
        } else if (totalPoint >1000 && totalPoint <= 2400) {
            binding.rankTxt.setText("Vàng");
            binding.rankImg.setImageResource(R.drawable.gold);
            binding.awardImg.setImageResource(R.drawable.gold);
        }else {
            binding.rankTxt.setText("Kim cương");
            binding.rankImg.setImageResource(R.drawable.diamon);
            binding.awardImg.setImageResource(R.drawable.diamon);
        }
    }
}
