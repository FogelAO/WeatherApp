package com.example.weatherapp.presentation.main;

import androidx.annotation.NonNull;

import com.example.weatherapp.base.BaseView;
import com.example.weatherapp.entity.Forecast;

import java.util.List;

public interface MainView extends BaseView {
    void showForecastData(@NonNull final Forecast data);

    void showSavedDataInfo();

    void showDayForecastData(final @NonNull List<Forecast> list);

    void showError();

    void requestLocationPermission();

    void setRefreshing(final boolean refreshing);
}
