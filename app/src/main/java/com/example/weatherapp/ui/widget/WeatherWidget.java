package com.example.weatherapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapp.R;
import com.example.weatherapp.data.interactor.ForecastInteractor;
import com.example.weatherapp.entity.Forecast;

/**
 * Implementation of App Widget functionality.
 */
public final class WeatherWidget extends AppWidgetProvider {
    @Nullable
    private ForecastInteractor forecastInteractor;

    void updateAppWidget(
            @NonNull final Context context,
            @NonNull final AppWidgetManager appWidgetManager,
            final int appWidgetId
    ) {
        if (forecastInteractor == null)
            forecastInteractor = new ForecastInteractor(context.getResources().getConfiguration().locale);
        final Forecast forecast = forecastInteractor.getSavedForecast();

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        views.setTextViewText(R.id.temperatureTextView, forecast == null ? null : context.getString(R.string.temperature_value, forecast.temperatureValue));
        views.setTextViewText(R.id.cityTextView, forecast == null ? null : forecast.city);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(@NonNull final Context context, @NonNull final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(@NonNull final Context context) {
    }

    @Override
    public void onDisabled(@NonNull final Context context) {
    }
}

