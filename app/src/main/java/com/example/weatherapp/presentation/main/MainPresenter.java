package com.example.weatherapp.presentation.main;

import android.location.Location;

import androidx.annotation.NonNull;

import com.example.weatherapp.base.BasePresenter;
import com.example.weatherapp.data.interactor.ForecastInteractor;
import com.example.weatherapp.entity.Forecast;
import com.example.weatherapp.location.LocationProvider;

import java.util.List;

public final class MainPresenter extends BasePresenter<MainView> {
    private final ForecastInteractor interactor;
    private final LocationProvider locationProvider;

    public MainPresenter(@NonNull final ForecastInteractor interactor, @NonNull final LocationProvider locationProvider) {
        this.interactor = interactor;
        this.locationProvider = locationProvider;
    }

    @Override
    protected void onAttach() {
        updateNowForecast();
    }

    public void updateNowForecast() {
        final Location location = locationProvider.getLastBestLocation();
        final MainView view = getView();
        if (location != null) {
            final Forecast data = interactor.getNowForecast(location.getLatitude(), location.getLongitude());
            final List<Forecast> dayForecastData = interactor.getDayForecast(location.getLatitude(), location.getLongitude());

            if (view != null) {
                if (data == null) {
                    view.setRefreshing(false);
                    view.showError();
                } else {
                    view.setRefreshing(false);
                    view.showForecastData(data);
                    view.showDayForecastData(dayForecastData);
                }
            }
        } else {
            final Forecast savedDate = interactor.getSavedForecast();
            if (view != null) {
                view.setRefreshing(false);
                if (savedDate != null) {
                    view.showForecastData(savedDate);
                    view.showSavedDataInfo();
                } else {
                    view.requestLocationPermission();
                }
            }
        }
    }
}
