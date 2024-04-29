package com.travel.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.vcn.VcnUnderlyingNetworkTemplate;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.travel.Adapter.DetailDestinationAdapter;
import com.travel.Database.DestinationDAO;
import com.travel.Database.HotelDAO;
import com.travel.Database.RestaurantDAO;
import com.travel.Database.TourDAO;
import com.travel.Database.WishlistDAO;
import com.travel.Model.DestinationModel;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourModel;
import com.travel.Model.UserModel;
import com.travel.R;
import com.travel.Utils.Constants;
import com.travel.Utils.SharePreferencesHelper;
import com.travel.databinding.ActivityDetailDestinationBinding;

import java.util.List;

public class DetailDestinationActivity extends AppCompatActivity {
    ActivityDetailDestinationBinding detailDestinationBinding;
    DestinationDAO destinationDAO=new DestinationDAO();
    HotelDAO hotelDAO=new HotelDAO();
    RestaurantDAO restaurantDAO=new RestaurantDAO();
    TourDAO tourDAO=new TourDAO();
     DetailDestinationAdapter<TourModel> tourAdapter;
     DetailDestinationAdapter<RestaurantModel> restaurantAdapter;
     DetailDestinationAdapter<HotelModel> hotelAdapter;
     WishlistDAO wishlistDAO;
    private boolean isFavorite;
    UserModel userModel;
    int REQUEST_CODE_DETAIL_DESTINATION= Constants.REQUEST_CODE_DETAIL_DESTINATION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailDestinationBinding = ActivityDetailDestinationBinding.inflate(getLayoutInflater());
        setContentView(detailDestinationBinding.getRoot());
        int destinationId=getIntent().getIntExtra("destinationId",0);
        userModel = SharePreferencesHelper.getInstance().get("user", UserModel.class);
        wishlistDAO=new WishlistDAO(this);
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
        DestinationModel destination = destinationDAO.getDestinationById(destinationId);
        isFavorite = wishlistDAO.checkFavoriteDestination(destination.getDestinationId(), userModel.getUserId());
        setHeartColor(detailDestinationBinding.fab, isFavorite);
        detailDestinationBinding.fab.setOnClickListener(v -> addToWhislist(destinationId, userModel));
        setDestination(destination);
        setupLayoutRecyclerView();
        setupTourRecyclerView(destinationId);
        setupRestaurantRecyclerView(destinationId);
        setupHotelRecyclerView(destinationId);

        detailDestinationBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(DetailDestinationActivity.this, DestinationActivity.class);
                startActivity(intent);*/
                onBackPressed();
                finish();
            }
        });

    }
    private void addToWhislist(int destinationId, UserModel userModel) {
        isFavorite = !isFavorite;
        if (isFavorite) {
            wishlistDAO.insertDestinationWhishlist(userModel.getUserId(), destinationId);
            showSnackbar("Đã thêm vào danh sách yêu thích");
        } else {
            wishlistDAO.removeWishListDestinationId(userModel.getUserId(), destinationId);
            showSnackbar("Đã xóa khỏi danh sách yêu thích");
        }
        setHeartColor(detailDestinationBinding.fab, isFavorite);
    }

    public void setDestination(DestinationModel destination){
        detailDestinationBinding.txtDestinationName.setText(destination.getName());
        detailDestinationBinding.txtDescription.setText(destination.getDescription());
        Glide.with(this).load(destination.getImage()).into(detailDestinationBinding.imageDestination);
    }
    private void setupTourRecyclerView(int destinationId) {

        List<TourModel> tourModels = tourDAO.getTourByDestinationId(destinationId);
        DetailDestinationAdapter<TourModel> tourAdapter = new DetailDestinationAdapter<>(tourModels, this);
        detailDestinationBinding.recyclerViewTour.setAdapter(tourAdapter);
    }
    private void setupRestaurantRecyclerView(int destinationId) {

        List<RestaurantModel> restaurants = restaurantDAO.getAllRestaurant(destinationId);
        DetailDestinationAdapter<RestaurantModel> restaurantAdapter = new DetailDestinationAdapter<>(restaurants, this);
        detailDestinationBinding.recyclerViewRestaurant.setAdapter(restaurantAdapter);
    }
    private void setupHotelRecyclerView(int destinationId) {
        List<HotelModel> hotels = hotelDAO.getByDestinationId(destinationId);
        DetailDestinationAdapter<HotelModel> hotelAdapter = new DetailDestinationAdapter<>(hotels, this);
        detailDestinationBinding.recyclerViewHotel.setAdapter(hotelAdapter);
    }
    private void setupLayoutRecyclerView(){
        LinearLayoutManager layoutManagerTour = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewTour.setLayoutManager(layoutManagerTour);
        LinearLayoutManager layoutManagerRestaurant = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewRestaurant.setLayoutManager(layoutManagerRestaurant);
        LinearLayoutManager layoutManagerHotel = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        detailDestinationBinding.recyclerViewHotel.setLayoutManager(layoutManagerHotel);
    }
    public void navigateToHotel(int destinationId){
        Intent intent=new Intent(this, HotelActivity.class);
        intent.putExtra("requestCode",REQUEST_CODE_DETAIL_DESTINATION);
        intent.putExtra("destinationId",destinationId);
        startActivity(intent);
    }
    public void navigateToRestaurant(int destinationId){
        Intent intent=new Intent(this, RestaurantActivity.class);
        intent.putExtra("requestCode",REQUEST_CODE_DETAIL_DESTINATION);
        intent.putExtra("destinationId",destinationId);
        startActivity(intent);
    }
    public void navigateToTour(int destinationId){
        Intent intent=new Intent(this, TourActivity.class);
        intent.putExtra("requestCode",REQUEST_CODE_DETAIL_DESTINATION);
        intent.putExtra("destinationId",destinationId);
        startActivity(intent);
    }
    public void navigateToFlight(int destinationId){
        Intent intent=new Intent(this, FlightActivity.class);
        intent.putExtra("requestCode",REQUEST_CODE_DETAIL_DESTINATION);
        intent.putExtra("destinationId",destinationId);
        startActivity(intent);
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