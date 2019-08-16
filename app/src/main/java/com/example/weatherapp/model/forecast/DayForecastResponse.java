package com.example.weatherapp.model.forecast;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public final class DayForecastResponse {
    @SerializedName("cod")
    public final int code;
    @SerializedName("cnt")
    public final int count;
    @NonNull
    @SerializedName("list")
    public final List<ForecastResponse> responseList;

    public DayForecastResponse(final int code, final int count, final @NonNull List<ForecastResponse> responseList) {
        this.code = code;
        this.count = count;
        this.responseList = responseList;
    }
}
