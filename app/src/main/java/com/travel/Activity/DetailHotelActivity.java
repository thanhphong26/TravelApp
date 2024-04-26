package com.travel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.travel.Adapter.DetailDestinationAdapter;
import com.travel.Adapter.ReviewAdapter;
import com.travel.Database.HotelDAO;
import com.travel.Database.ReviewDAO;
import com.travel.Database.WishlistDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.ReviewModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityDetailHotelBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailHotelActivity extends AppCompatActivity {
    ActivityDetailHotelBinding detailHotelBinding;
    HotelDAO hotelDAO = new HotelDAO();
    HotelModel hotelModel=new HotelModel();
    ReviewDAO reviewDAO=new ReviewDAO();
    List<ReviewModel> reviewList=new ArrayList<ReviewModel>();
    ReviewAdapter reviewAdapter;
    WishlistDAO wishlistDAO;
    private boolean isFavorite;
    int destinationId;
    UserModel userModel;
    private int REQUEST_CODE_DETAIL_HOTEL=Constants.REQUEST_CODE_DETAIL_HOTEL;
    int REQUEST_CODE_HOTEL=Constants.REQUEST_CODE_HOTEL;
    int REQUEST_CODE_DETAIL_DESTINATION=Constants.REQUEST_CODE_DETAIL_DESTINATION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailHotelBinding = ActivityDetailHotelBinding.inflate(getLayoutInflater());
        setContentView(detailHotelBinding.getRoot());
        int requestCode = getIntent().getIntExtra("requestCode", 0);
        int hotelId = getIntent().getIntExtra("hotelId", 0);
        destinationId=getIntent().getIntExtra("destinationId",0);
        AppBarLayout appBarLayout = findViewById(com.travel.R.id.app_bar_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView backIcon = findViewById(R.id.imgBack);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            if (Math.abs(verticalOffset) - appBarLayout1.getTotalScrollRange() == 0) {
                toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
                backIcon.setColorFilter(Color.parseColor("#000000"));
            } else {
                toolbar.setBackgroundColor(Color.parseColor("#00000000"));
                backIcon.setColorFilter(Color.parseColor("#ffffff"));
            }
        });
        wishlistDAO=new WishlistDAO(this);
        hotelModel = hotelDAO.getHotelById(hotelId);
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        reviewList = reviewDAO.getReviewsForHotel(hotelId);
        setHotelModel(hotelModel);
        setRating((int) ratingAverage(reviewList));
        setupHotelRecyclerView(hotelModel);
        setUpRecyclerView(hotelModel);
        detailHotelBinding.lyMap.setOnClickListener(v -> navigateToLocation(hotelModel));
        detailHotelBinding.button.setOnClickListener(v -> navigateToBooking(hotelModel));
        isFavorite = wishlistDAO.checkFavoriteHotel(hotelModel.getHotelId(), userModel.getUserId());
        setHeartColor(detailHotelBinding.fab, isFavorite);
        detailHotelBinding.fab.setOnClickListener(v -> addToWhislist(hotelModel, userModel));

        if(requestCode==REQUEST_CODE_HOTEL){
            onClickBackToHoTel();
        }else if(requestCode==REQUEST_CODE_DETAIL_DESTINATION){
            onClickBackToDetailDestination();
        }
    }
    public void onClickBackToHoTel(){
        detailHotelBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailHotelActivity.this,HotelActivity.class);
                intent.putExtra("destinationId",destinationId);
                startActivity(intent);
                //onBackPressed();
            }
        });
    }
    public void onClickBackToDetailDestination(){
        detailHotelBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailHotelActivity.this,DetailDestinationActivity.class);
                intent.putExtra("destinationId",destinationId);
                startActivity(intent);
                //onBackPressed();
            }
        });
    }
    private void addToWhislist(HotelModel hotelModel, UserModel userModel) {
        isFavorite = !isFavorite;
        if (isFavorite) {
            wishlistDAO.insertHotelWhishlist(userModel.getUserId(), hotelModel.getHotelId());
            showSnackbar("Đã thêm vào danh sách yêu thích");
        } else {
            wishlistDAO.removeWhishlistHotelId(userModel.getUserId(), hotelModel.getHotelId());
            showSnackbar("Đã xóa khỏi danh sách yêu thích");
        }
        setHeartColor(detailHotelBinding.fab, isFavorite);
    }

    private void navigateToBooking(HotelModel hotelModel) {
        Intent intent = new Intent(this, BookHotelActivity.class);
        intent.putExtra("hotel_id", hotelModel.getHotelId());
        startActivity(intent);
    }
    private void navigateToLocation(HotelModel hotelModel) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", hotelModel.getLatitude());
        intent.putExtra("longitude", hotelModel.getLongitude());
        intent.putExtra("locationName", hotelModel.getName());
        startActivity(intent);
    }
    public void setHotelModel(HotelModel hotelModel){
        detailHotelBinding.txtHotelName.setText(hotelModel.getName());
        detailHotelBinding.txtDescription.setText(hotelModel.getDescription());
       detailHotelBinding.txtAddress.setText(hotelModel.getAddress());
        detailHotelBinding.txtRating.setText(String.valueOf(hotelModel.getRating()));
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedMinValue = decimalFormat.format(hotelModel.getPrice());
        detailHotelBinding.txtPrice.setText("đ "+formattedMinValue);
    }
    public void setRating(int ratingAverage){
        detailHotelBinding.txtRatingAverage.setText(String.valueOf(ratingAverage));
        detailHotelBinding.ratingBar2.setRating(ratingAverage);
        //detailHotelBinding.txtRating.setText(String.valueOf(ratingAverage(reviewList)));
        detailHotelBinding.txtCount.setText("("+reviewList.size()+" đánh giá)");
        String review=reviewList.isEmpty()?"Chưa có đánh giá": "Từ " + reviewList.size() + " đánh giá";
        detailHotelBinding.txtCountRating.setText(review);
        Glide.with(this).load(hotelModel.getImage()).into(detailHotelBinding.imageView);
    }
    public float ratingAverage(List<ReviewModel> reviewList){
        float totalRating=0;
        if (reviewList.isEmpty()) {
            return 0;
        }
        for (ReviewModel reviewModel:reviewList){
            totalRating+=reviewModel.getRating();
        }
        return totalRating/reviewList.size();
    }
    private void setupHotelRecyclerView(HotelModel hotelModel) {
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailHotelBinding.recyclerViewHotelNearby.setLayoutManager(layoutManagerHotel);
        List<HotelModel> hotels = hotelDAO.getNearDestinationExcludingCurrent(hotelModel.getDestination().getDestinationId(), hotelModel.getHotelId());
        DetailDestinationAdapter<HotelModel> hotelAdapter = new DetailDestinationAdapter<>(hotels, this);
        detailHotelBinding.recyclerViewHotelNearby.setAdapter(hotelAdapter);
    }
    public void setUpRecyclerView(HotelModel hotelModel){
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailHotelBinding.recyclerViewHotelNearby.setLayoutManager(layoutManagerHotel);
        reviewList=reviewDAO.getReviewsForHotel(hotelModel.getHotelId());
        reviewAdapter=new ReviewAdapter(this,reviewList);
        detailHotelBinding.recyclerViewDanhGia.setAdapter(reviewAdapter);
    }
    private void setHeartColor(ImageView imageView, boolean isHeartRed) {
        if (isHeartRed) {
            imageView.setImageResource(R.drawable.red_heart);
        } else {
            imageView.setImageResource(R.drawable.heart);
        }
    }
    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(204, 153, 255)));
        snackbar.show();
    }
}