package com.example.weatherapp.data.http;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapp.BuildConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ForecastApiService {
    private static final String TAG = ForecastApiService.class.getSimpleName();

    @NonNull
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Nullable
    public String getNowForecast(final double lat, final double lon) {
        final Future<String> call = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                final URL url;
                HttpURLConnection urlConnection = null;
                String line;
                try {
                    //Inject endpoint and API key
                    url = new URL(BuildConfig.ENDPOINT + "data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + BuildConfig.API_KEY);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    final InputStream inputStream = urlConnection.getInputStream();
                    final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    line = bufferedReader.readLine();
                    return line;
                } catch (Exception e) {
                    Log.e(TAG, "Requesting error", e);
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
                return null;
            }
        });

        try {
            return call.get();
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return null;
    }

    @Nullable
    public String getDayForecast(final double lat, final double lon) {
        final Future<String> call = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                final URL url;
                HttpURLConnection urlConnection = null;
                String line;
                try {
                    //Inject endpoint and API key
                    url = new URL(BuildConfig.ENDPOINT + "data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + BuildConfig.API_KEY);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");

                    final InputStream inputStream = urlConnection.getInputStream();
                    final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    line = bufferedReader.readLine();
                    return line;
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
                return null;
            }
        });

        try {
            return call.get();
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        return null;
    }
}
