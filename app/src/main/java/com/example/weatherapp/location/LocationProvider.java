package com.example.weatherapp.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.weatherapp.utils.qualifiers.AppCtx;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationProvider {
    private static final String TAG = LocationProvider.class.getSimpleName();

    @NonNull
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @NonNull
    private final LocationManager locationManager;
    @AppCtx
    @NonNull
    private final Context context;
    @NonNull
    private final FusedLocationProviderClient fusedLocationClient;
    @Nullable
    private Location location;

    public LocationProvider(@NonNull @AppCtx final Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        getLastBestLocation();
    }

    @Nullable
    public Location getLastBestLocation() {
        if (isLocationServicesEnabled() &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(executorService, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(@Nullable final Location location) {
                            if (location != null) {
                                Log.i(TAG, "success location = " + location.getLatitude());
                                LocationProvider.this.location = location;
                            } else
                                Log.e(TAG, "location is NULL");
                        }
                    });
        }
        return location;
    }

    private boolean isLocationServicesEnabled() {
        try {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ignored) {
            return false;
        }
    }
}

