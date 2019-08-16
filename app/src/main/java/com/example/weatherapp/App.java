package com.example.weatherapp;

import android.app.Application;

import com.example.weatherapp.data.dao.ForecastDatabase;

public final class App extends Application {
    public static ForecastDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        db = new ForecastDatabase(this);
    }
}
