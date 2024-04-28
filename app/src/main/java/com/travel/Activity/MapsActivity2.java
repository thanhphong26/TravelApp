package com.travel.Activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.travel.Database.HotelDAO;
import com.travel.Database.RestaurantDAO;
import com.travel.Database.TourDAO;
import com.travel.Database.TourLineDAO;
import com.travel.Model.HotelModel;
import com.travel.Model.RestaurantModel;
import com.travel.Model.TourLineModel;
import com.travel.Model.TourModel;
import com.travel.R;
import com.travel.databinding.ActivityMaps2Binding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    TourLineDAO tourLineDAO;
    List<TourLineModel> listTourLineModel;
    List<HotelModel> listHotel;
    List<RestaurantModel> listRestaurant;
    HotelModel hotelModel=new HotelModel();
    RestaurantModel restaurantModel=new RestaurantModel();
    HotelDAO hotelDAO=new HotelDAO();
    RestaurantDAO restaurantDAO=new RestaurantDAO();
    TourModel tourModel=new TourModel();
    TourDAO tourDAO=new TourDAO();
    private Map<String, Marker> tourLineMarkers = new HashMap<>();
    private List<Snackbar> snackbarList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleBottomNavigation();
        tourLineDAO=new TourLineDAO();
        listTourLineModel=tourLineDAO.getAllTourLine();
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        binding.fabCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationSettings();
            }
        });
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity2.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapsActivity2.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity2.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_LOCATION);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(MapsActivity2.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.pin);
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng).icon(icon)
                            .title("Vị trí hiện tại"));

                    for (TourLineModel tourLine : listTourLineModel) {
                        LatLng tourLocation = new LatLng(tourLine.getLatitude(), tourLine.getLongitude());
                        Marker marker = mMap.addMarker(new MarkerOptions().position(tourLocation).title(tourLine.getLocationName()));
                        marker.setTag(tourLine);
                    }
                    for (HotelModel hotelModel:hotelDAO.getAllHotel()){
                        LatLng hotelLocation=new LatLng(hotelModel.getLatitude(),hotelModel.getLongitude());
                        BitmapDescriptor iconHotel = BitmapDescriptorFactory.fromResource(R.drawable.hotel1);
                        Marker marker=mMap.addMarker(new MarkerOptions().position(hotelLocation).icon(iconHotel).title(hotelModel.getName()));
                        marker.setTag(hotelModel);
                    }
                    for (RestaurantModel restaurantModel:restaurantDAO.getAllRestaurant()){
                        LatLng restaurantLocation=new LatLng(restaurantModel.getLatitude(),restaurantModel.getLongitude());
                        BitmapDescriptor iconRestaurant = BitmapDescriptorFactory.fromResource(R.drawable.restaurant1);
                        Marker marker=mMap.addMarker(new MarkerOptions().position(restaurantLocation).icon(iconRestaurant).title(restaurantModel.getName()));
                        marker.setTag(restaurantModel);
                    }
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            Object tag = marker.getTag();
                            if (tag instanceof TourLineModel) {
                                TourLineModel tourLine = (TourLineModel) tag;
                                showTourLineDetailSnackbar(tourLine);
                                return true;
                            }
                            if (tag instanceof HotelModel){
                                HotelModel hotelModel=(HotelModel) tag;
                                showHotelDetailSnackbar(hotelModel);
                                return true;
                            }
                            if (tag instanceof RestaurantModel){
                                RestaurantModel restaurantModel=(RestaurantModel) tag;
                                showRestaurantSnackbar(restaurantModel);
                                return true;
                            }
                            return false;
                        }
                    });

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                } else {
                    Toast.makeText(MapsActivity2.this, "Không thể xác định vị trí hiện tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleBottomNavigation() {
        binding.navigation.setItemIconTintList(null);
        binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    intent = new Intent(MapsActivity2.this, HomeActivity.class);
                } else if (id == R.id.navigation_favorite) {
                    intent = new Intent(MapsActivity2.this, FavoriteActivity.class);
                } else if (id == R.id.navigation_map) {
                    intent = new Intent(MapsActivity2.this, DestinationActivity.class);
                } else if (id == R.id.navigation_translate) {
                    return true;
                } else if (id == R.id.navigation_profile) {
                    intent = new Intent(MapsActivity2.this, AccountActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
    }

    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    getLastLocation();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(
                                        MapsActivity2.this,
                                        LocationRequest.PRIORITY_HIGH_ACCURACY);
                            } catch (IntentSender.SendIntentException | ClassCastException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Thêm marker cho mỗi tourline và đặt tag là đối tượng TourLineModel
        for (TourLineModel tourLine : listTourLineModel) {
            LatLng tourLocation = new LatLng(tourLine.getLatitude(), tourLine.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(tourLocation).title(tourLine.getLocationName()));
            marker.setTag(tourLine);
        }
        for (HotelModel hotelModel:hotelDAO.getAllHotel()){
            LatLng hotelLocation=new LatLng(hotelModel.getLatitude(),hotelModel.getLongitude());
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.hotel1);
            Marker marker=mMap.addMarker(new MarkerOptions().position(hotelLocation).icon(icon).title(hotelModel.getName()));
            marker.setTag(hotelModel);
        }
        for (RestaurantModel restaurantModel:restaurantDAO.getAllRestaurant()){
            LatLng restaurantLocation=new LatLng(restaurantModel.getLatitude(),restaurantModel.getLongitude());
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.restaurant1);
            Marker marker=mMap.addMarker(new MarkerOptions().position(restaurantLocation).icon(icon).title(restaurantModel.getName()));
            marker.setTag(restaurantModel);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Object tag = marker.getTag();
                if (tag instanceof TourLineModel) {
                    TourLineModel tourLine = (TourLineModel) tag;
                    showTourLineDetailSnackbar(tourLine);
                    return true;
                }
                if (tag instanceof HotelModel){
                    HotelModel hotelModel=(HotelModel) tag;
                    showHotelDetailSnackbar(hotelModel);
                    return true;
                }
                if (tag instanceof RestaurantModel){
                    RestaurantModel restaurantModel=(RestaurantModel) tag;
                    showRestaurantSnackbar(restaurantModel);
                    return true;
                }
                return false;
            }
        });

        // Đặt giới hạn camera dựa trên các marker
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (TourLineModel tourLine : listTourLineModel) {
            LatLng tourLocation = new LatLng(tourLine.getLatitude(), tourLine.getLongitude());
            builder.include(tourLocation);
        }
        LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showTourLineDetailSnackbar(TourLineModel tourLine) {
        View customSnackbarView = getLayoutInflater().inflate(R.layout.snackbar_tourline_detail, null);

        ImageView imageView = customSnackbarView.findViewById(R.id.imgItem);
        TextView locationName = customSnackbarView.findViewById(R.id.locationName);
        TextView tourName = customSnackbarView.findViewById(R.id.tourName);
        tourModel=tourDAO.getTourById(tourLine.getTourId());
        locationName.setText(tourLine.getLocationName());
        tourName.setText(tourModel.getName());
        Glide.with(this).load(tourLine.getImage()).into(imageView);

        Snackbar customSnackbar = Snackbar.make(binding.getRoot(), "", Snackbar.LENGTH_LONG);
        customSnackbar.getView().setBackgroundResource(android.R.color.transparent);

        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) customSnackbar.getView();
        snackbarLayout.removeAllViews();
        snackbarLayout.addView(customSnackbarView, 0);

        customSnackbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity2.this, DetailTourActivity.class);
                intent.putExtra("tourId", tourLine.getTourId());
                startActivity(intent);
            }
        });

        customSnackbar.show();
    }
    private void showHotelDetailSnackbar(HotelModel hotelModel){
        View customSnackbarView = getLayoutInflater().inflate(R.layout.snackbar_tourline_detail, null);

        ImageView imageView = customSnackbarView.findViewById(R.id.imgItem);
        TextView hotelName = customSnackbarView.findViewById(R.id.locationName);
        TextView description = customSnackbarView.findViewById(R.id.tourName);
        hotelName.setText(hotelModel.getName());
        description.setText(hotelModel.getDescription());
        Glide.with(this).load(hotelModel.getImage()).into(imageView);

        Snackbar customSnackbar = Snackbar.make(binding.getRoot(), "", Snackbar.LENGTH_LONG);
        customSnackbar.getView().setBackgroundResource(android.R.color.transparent);

        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) customSnackbar.getView();
        snackbarLayout.removeAllViews();
        snackbarLayout.addView(customSnackbarView, 0);

        customSnackbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity2.this, DetailHotelActivity.class);
                intent.putExtra("hotelId", hotelModel.getHotelId());
                startActivity(intent);
            }
        });

        customSnackbar.show();
    }
    private void showRestaurantSnackbar(RestaurantModel restaurantModel){
        View customSnackbarView = getLayoutInflater().inflate(R.layout.snackbar_tourline_detail, null);

        ImageView imageView = customSnackbarView.findViewById(R.id.imgItem);
        TextView restaurantName = customSnackbarView.findViewById(R.id.locationName);
        TextView description = customSnackbarView.findViewById(R.id.tourName);
        restaurantName.setText(restaurantModel.getName());
        description.setText(restaurantModel.getDescription());
        Glide.with(this).load(restaurantModel.getImage()).into(imageView);

        Snackbar customSnackbar = Snackbar.make(binding.getRoot(), "", Snackbar.LENGTH_LONG);
        customSnackbar.getView().setBackgroundResource(android.R.color.transparent);

        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) customSnackbar.getView();
        snackbarLayout.removeAllViews();
        snackbarLayout.addView(customSnackbarView, 0);

        customSnackbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity2.this, DetailRestaurantActivity.class);
                intent.putExtra("restaurantId", restaurantModel.getRestaurantId());
                startActivity(intent);
            }
        });

        customSnackbar.show();
    }
    private TourLineModel getTourLineById(String id) {
        for (TourLineModel tourLine : listTourLineModel) {
            if (String.valueOf(tourLine.getItineraryId()).equals(id)) {
                return tourLine;
            }
        }
        return null;
    }

}
