package com.example.weatherapp.utils;

import android.location.Location;

import androidx.annotation.Nullable;

public interface LocationCallback {

    void onLocation(@Nullable final Location location);
}
