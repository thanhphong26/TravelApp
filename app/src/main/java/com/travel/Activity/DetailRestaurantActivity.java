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
import com.travel.Database.RestaurantDAO;
import com.travel.Database.ReviewDAO;
import com.travel.Database.WishlistDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.ReviewModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityDetailRestaurantBinding;
import com.travel.databinding.ActivityRestaurantBinding;

import java.text.DecimalFormat;
import java.util.List;

public class DetailRestaurantActivity extends AppCompatActivity {
    ActivityDetailRestaurantBinding restaurantBinding;
    ReviewAdapter reviewAdapter;
    RestaurantModel restaurantModel=new RestaurantModel();
    RestaurantDAO restaurantDAO;
    WishlistDAO wishlistDAO;
    ReviewDAO reviewDAO=new ReviewDAO();
    private boolean isFavorite;
    List<ReviewModel> reviewList;
    int destinationId;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantBinding = ActivityDetailRestaurantBinding.inflate(getLayoutInflater());
        setContentView(restaurantBinding.getRoot());
        destinationId=getIntent().getIntExtra("destinationId",0);
        int restaurantId = getIntent().getIntExtra("restaurantId", 0);
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
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
        restaurantDAO=new RestaurantDAO();
        wishlistDAO=new WishlistDAO(this);
        restaurantModel = restaurantDAO.getRestaurantById(restaurantId);
        reviewList = reviewDAO.getReviewsForHotel(restaurantId);
        setRestaurant(restaurantModel);
        setRating((int) ratingAverage(reviewList));
        setupRestaurantRecyclerView(restaurantModel);
        setUpRecyclerView(restaurantModel);
        restaurantBinding.lyMap.setOnClickListener(v -> navigateToLocation(restaurantModel));
        restaurantBinding.button.setOnClickListener(v -> navigateToBooking(restaurantModel));
        isFavorite = wishlistDAO.checkFavoriteRestaurant(restaurantModel.getRestaurantId(), userModel.getUserId());
        setHeartColor(restaurantBinding.fab, isFavorite);
        restaurantBinding.fab.setOnClickListener(v -> addToWhislist(restaurantModel,userModel));
        restaurantBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(DetailRestaurantActivity.this,DetailDestinationActivity.class);
                intent.putExtra("destinationId",destinationId);
                startActivity(intent);*/
                onBackPressed();
            }
        });
    }
    private void addToWhislist(RestaurantModel restaurantModel, UserModel userModel) {
        isFavorite = !isFavorite;
        if (isFavorite) {
            wishlistDAO.insertRestaurantWhishlist(userModel.getUserId(), restaurantModel.getRestaurantId());
            showSnackbar("Đã thêm vào danh sách yêu thích");
        } else {
            wishlistDAO.removeWhishlistRestaurantId(userModel.getUserId(), restaurantModel.getRestaurantId());
            showSnackbar("Đã xóa khỏi danh sách yêu thích");
        }
        setHeartColor(restaurantBinding.fab, isFavorite);
    }
    private void navigateToBooking(RestaurantModel restaurantModel) {
        Intent intent = new Intent(this, BookRestaurantActivity.class);
        intent.putExtra("restaurantId", restaurantModel.getRestaurantId());
        startActivity(intent);
    }
    private void navigateToLocation(RestaurantModel restaurantModel) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", restaurantModel.getLatitude());
        intent.putExtra("longitude", restaurantModel.getLongitude());
        intent.putExtra("locationName", restaurantModel.getName());
        startActivity(intent);
    }
    public void setRestaurant(RestaurantModel restaurantModel){
        restaurantBinding.txtRestaurantName.setText(restaurantModel.getName());
        restaurantBinding.txtDescription.setText(restaurantModel.getDescription());
        restaurantBinding.txtAddress.setText(restaurantModel.getAddress());
        restaurantBinding.txtRating.setText(String.valueOf(restaurantModel.getRating()));
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedMinValue = decimalFormat.format(restaurantModel.getPrice());
        restaurantBinding.txtPrice.setText("đ "+formattedMinValue);
    }
    public void setRating(int ratingAverage){
        restaurantBinding.txtRatingAverage.setText(String.valueOf(ratingAverage));
        restaurantBinding.ratingBar2.setRating(ratingAverage);
        //restaurantBinding.txtRating.setText(String.valueOf(ratingAverage(reviewList)));
        restaurantBinding.txtCount.setText("("+reviewList.size()+" đánh giá)");
        String review=reviewList.isEmpty()?"Chưa có đánh giá": "Từ " + reviewList.size() + " đánh giá";
        restaurantBinding.txtCountRating.setText(review);
        Glide.with(this).load(restaurantModel.getImage()).into(restaurantBinding.imageView);
    }
    public float ratingAverage(List<ReviewModel> reviewList){
        float totalRating=0;
        if(reviewList.size()==0){
            return 0;
        }
        for (ReviewModel reviewModel:reviewList){
            totalRating+=reviewModel.getRating();
        }
        return totalRating/reviewList.size();
    }
    private void setupRestaurantRecyclerView(RestaurantModel restaurantModel) {
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        restaurantBinding.recyclerViewRestaurantNearby.setLayoutManager(layoutManagerHotel);
        List<RestaurantModel> restaurants = restaurantDAO.getNearDestinationExcludingCurrent(restaurantModel.getDestination().getDestinationId(), restaurantModel.getRestaurantId());
        DetailDestinationAdapter<RestaurantModel> restaurantAdapter = new DetailDestinationAdapter<>(restaurants, this);
        restaurantBinding.recyclerViewRestaurantNearby.setAdapter(restaurantAdapter);
    }
    public void setUpRecyclerView(RestaurantModel restaurantModel){
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        restaurantBinding.recyclerViewRestaurantNearby.setLayoutManager(layoutManagerHotel);
        reviewList=reviewDAO.getReviewsForHotel(restaurantModel.getRestaurantId());
        reviewAdapter=new ReviewAdapter(this,reviewList);
        restaurantBinding.recyclerViewDanhGia.setAdapter(reviewAdapter);
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