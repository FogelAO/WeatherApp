package com.example.weatherapp.data.repository;

import androidx.annotation.Nullable;

import com.example.weatherapp.data.http.ForecastApiService;
import com.example.weatherapp.model.forecast.DayForecastResponse;
import com.example.weatherapp.model.forecast.ForecastResponse;
import com.google.gson.Gson;

public final class ForecastRepository {
    private final Gson gson = new Gson();

    private final ForecastApiService apiService = new ForecastApiService();

    @Nullable
    public ForecastResponse getNowForecast(final double lat, final double lon) {
        final String json = apiService.getNowForecast(lat, lon);
        return gson.fromJson(json, ForecastResponse.class);
    }

    @Nullable
    public DayForecastResponse getDayForecast(final double lat, final double lon) {
        final String json = apiService.getDayForecast(lat, lon);
        return gson.fromJson(json, DayForecastResponse.class);
    }
}
