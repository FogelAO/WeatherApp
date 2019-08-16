package com.example.weatherapp.model.forecast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class ForecastResponse {
    @NonNull
    @SerializedName("dt")
    public final String date;

    @Nullable
    @SerializedName("dt_txt")
    public final String dateText;

    @NonNull
    @SerializedName("weather")
    public final List<Weather> weather;

    @NonNull
    @SerializedName("main")
    public final MainData main;

    @SerializedName("visibility")
    int visibility;

    @NonNull
    @SerializedName("wind")
    public final Wind wind;

    @NonNull
    @SerializedName("clouds")
    Object clouds;

    @Nullable
    @SerializedName("name")
    public final String name;

    @SerializedName("cod")
    final int code;

    public ForecastResponse(
            @NonNull final String date,
            @Nullable final String dateText,
            @NonNull final List<Weather> weather,
            @NonNull final MainData main,
            final int visibility,
            @NonNull final Wind wind,
            @NonNull final Object clouds,
            @Nullable final String name,
            final int code
    ) {
        this.date = date;
        this.dateText = dateText;
        this.weather = weather;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.name = name;
        this.code = code;
    }

    public final class MainData {
        @NonNull
        @SerializedName("temp")
        public final Double temperature;

        @NonNull
        @SerializedName("pressure")
        public final String pressure;

        @NonNull
        @SerializedName("humidity")
        public final String humidity;

        @NonNull
        @SerializedName("temp_min")
        public final Double tempMin;

        @NonNull
        @SerializedName("temp_max")
        public final Double tempMax;

        public MainData(
                @NonNull final Double temperature,
                @NonNull final String pressure,
                @NonNull final String humidity,
                @NonNull final Double tempMin,
                @NonNull final Double tempMax
        ) {
            this.temperature = temperature;
            this.pressure = pressure;
            this.humidity = humidity;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
        }
    }

    public final class Weather {
        @NonNull
        @SerializedName("main")
        public final String main;

        @NonNull
        @SerializedName("description")
        public final String description;

        @NonNull
        @SerializedName("icon")
        public final String icon;

        public Weather(
                @NonNull final String main,
                @NonNull final String description,
                @NonNull final String icon
        ) {
            this.main = main;
            this.description = description;
            this.icon = icon;
        }
    }

    public final class Wind {
        @NonNull
        @SerializedName("speed")
        public final String speed;

        @NonNull
        @SerializedName("deg")
        public final String degrees;

        public Wind(@NonNull String speed, @NonNull String degrees) {
            this.speed = speed;
            this.degrees = degrees;
        }
    }
}
