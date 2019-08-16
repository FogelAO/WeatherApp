package com.example.weatherapp.data.interactor;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapp.App;
import com.example.weatherapp.BuildConfig;
import com.example.weatherapp.data.repository.ForecastRepository;
import com.example.weatherapp.entity.Forecast;
import com.example.weatherapp.model.forecast.DayForecastResponse;
import com.example.weatherapp.model.forecast.ForecastResponse;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ForecastInteractor {
    private static final String TAG = ForecastInteractor.class.getSimpleName();

    @NonNull
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @NonNull
    private final ForecastRepository repository = new ForecastRepository();
    @NonNull
    private final NumberFormat numberFormat = NumberFormat.getNumberInstance();
    @NonNull
    private final DateFormat timeFormat;
    @NonNull
    private final DateFormat dateFormat;
    @NonNull
    private final DateFormat shortDateFormat;

    public ForecastInteractor(@NonNull final Locale locale) {
        timeFormat = new SimpleDateFormat("HH:mm", locale);
        dateFormat = new SimpleDateFormat("EEEE dd MMMM", locale);
        shortDateFormat = new SimpleDateFormat("dd.MM", locale);

        numberFormat.setMaximumFractionDigits(0);
    }

    @Nullable
    public Forecast getNowForecast(final double lat, final double lon) {
        final Future<Forecast> call = executorService.submit(new Callable<Forecast>() {
            @Override
            public Forecast call() {
                final ForecastResponse response = repository.getNowForecast(lat, lon);
                if (response == null) {
                    return App.db.getLast();
                }

                final Forecast forecast = mapToForecastData(response);

                App.db.save(forecast);

                return forecast;
            }
        });

        try {
            return call.get();
        } catch (Exception e) {
            Log.e(TAG, "Error calling request", e);
        }
        return null;
    }

    @Nullable
    public Forecast getSavedForecast() {
        final Future<Forecast> call = executorService.submit(new Callable<Forecast>() {
            @Override
            public Forecast call() {
                return App.db.getLast();
            }
        });

        try {
            return call.get();
        } catch (Exception e) {
            Log.e(TAG, "Error calling request", e);
        }
        return null;
    }

    @NonNull
    public List<Forecast> getDayForecast(final double lat, final double lon) {
        final Future<List<Forecast>> call = executorService.submit(new Callable<List<Forecast>>() {
            @Override
            public List<Forecast> call() {
                final List<Forecast> forecastList = new ArrayList<>();
                final DayForecastResponse response = repository.getDayForecast(lat, lon);
                if (response == null) return forecastList;

                for (ForecastResponse forecastResponse : response.responseList) {
                    forecastList.add(mapToForecastData(forecastResponse));
                }

                return forecastList;
            }
        });

        try {
            return call.get();
        } catch (Exception e) {
            Log.e(TAG, "Error calling request", e);
        }
        return new ArrayList<>();
    }

    private Forecast mapToForecastData(@NonNull final ForecastResponse response) {
        final ForecastResponse.MainData mainData = response.main;

        final String imageUrl = BuildConfig.IMAGE_ENDPOINT + "img/wn/" + response.weather.get(0).icon + "@2x.png";

        final Date date = new Date(Long.parseLong(response.date) * 1000);
        final String timeString = timeFormat.format(date);
        final String dateString = dateFormat.format(date);
        final String shortDateString = shortDateFormat.format(date);

        return new Forecast(
                numberFormat.format(mainData.temperature),
                mainData.humidity,
                response.wind,
                response.weather.get(0).description,
                numberFormat.format(mainData.tempMin),
                numberFormat.format(mainData.tempMax),
                response.name,
                imageUrl,
                dateString,
                timeString,
                shortDateString
        );
    }
}
