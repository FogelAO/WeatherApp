package com.example.weatherapp.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapp.model.forecast.ForecastResponse;

import java.util.Objects;

public class Forecast {
    @NonNull
    public final String temperatureValue;
    @Nullable
    public final String humidity;
    @Nullable
    public final String windSpeed;
    @Nullable
    public final String windDegrees;
    @NonNull
    public final String description;
    @NonNull
    public final String tempMin;
    @NonNull
    public final String tempMax;
    @Nullable
    public final String city;
    @Nullable
    public final String imageUrl;
    @NonNull
    public final String time;
    @NonNull
    public final String date;
    @NonNull
    public final String shortDate;

    public Forecast(
            @NonNull String temperatureValue,
            @Nullable String humidity,
            @Nullable ForecastResponse.Wind wind,
            @NonNull String description,
            @NonNull String tempMin,
            @NonNull String tempMax,
            @Nullable String city,
            @Nullable String image,
            @NonNull String time,
            @NonNull String date,
            @NonNull String shortDate
    ) {
        this.temperatureValue = temperatureValue;
        this.humidity = humidity;
        this.windSpeed = wind == null ? "" : wind.speed;
        this.windDegrees = wind == null ? "" : wind.degrees;
        this.description = description;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.city = city;
        this.imageUrl = image;
        this.date = date;
        this.time = time;
        this.shortDate = shortDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forecast forecast = (Forecast) o;
        return temperatureValue.equals(forecast.temperatureValue) &&
                description.equals(forecast.description) &&
                tempMin.equals(forecast.tempMin) &&
                tempMax.equals(forecast.tempMax) &&
                Objects.equals(city, forecast.city) &&
                Objects.equals(imageUrl, forecast.imageUrl) &&
                time.equals(forecast.time) &&
                date.equals(forecast.date) &&
                shortDate.equals(forecast.shortDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperatureValue, humidity, windSpeed, windDegrees, description, tempMin, tempMax, city, imageUrl, time, date, shortDate);
    }
}
