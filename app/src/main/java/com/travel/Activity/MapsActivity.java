package com.travel.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.travel.R;
import com.travel.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private static final int PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Marker originalMarker;
    private Marker currentLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        binding.fabCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });
        binding.fabChooseMapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapTypeSelectionDialog();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.park);
        LatLng sydney = new LatLng(23.36396,105.31621);
        originalMarker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(icon));
        moveCamera(sydney, 15);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_LOCATION);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    if (currentLocationMarker != null) {
                        currentLocationMarker.remove();
                    }
                    currentLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Your current location").draggable(false));
                    moveCamera(latLng, 15);                }
            }
        });
    }
    private void addMarker(LatLng latLng, String title, float color) {
        if (mMap != null) {
            if (currentLocationMarker != null) {
                currentLocationMarker.remove();
            }
            currentLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(title).icon(BitmapDescriptorFactory.defaultMarker(color)));
        }
    }
    private void moveCamera(LatLng latLng, float zoom) {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showMapTypeSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn loại bản đồ");

        String[] mapTypes = {"Bản đồ", "Bản đồ vệ tinh", "Bản đồ hybrid", "Bản đồ dạng mặt đất", "Bản đồ dạng mặt đất với bề mặt"};
        builder.setItems(mapTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0-> mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    case 1 -> mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    case 2 -> mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    case 3-> mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        });
        builder.show();
    }
}