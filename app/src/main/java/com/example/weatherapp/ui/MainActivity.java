package com.example.weatherapp.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.weatherapp.R;
import com.example.weatherapp.base.BaseActivity;
import com.example.weatherapp.data.interactor.ForecastInteractor;
import com.example.weatherapp.entity.Forecast;
import com.example.weatherapp.location.LocationProvider;
import com.example.weatherapp.presentation.main.MainPresenter;
import com.example.weatherapp.presentation.main.MainView;
import com.example.weatherapp.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public final class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
    private static final int PERMISSION_CODE = 1337;

    private final DayForecastAdapter adapter = new DayForecastAdapter();

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView temperatureTextView;
    private TextView cityTextView;
    private TextView timeTextView;
    private TextView dateTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    private TextView temperatureMinTextView;
    private TextView temperatureMaxTextView;
    private List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);

        temperatureTextView = findViewById(R.id.temperatureTextView);
        cityTextView = findViewById(R.id.cityTextView);
        timeTextView = findViewById(R.id.timeTextView);
        dateTextView = findViewById(R.id.dateTextView);
        imageView = findViewById(R.id.imageView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        temperatureMinTextView = findViewById(R.id.temperatureMinTextView);
        temperatureMaxTextView = findViewById(R.id.temperatureMaxTextView);

        final RecyclerView recyclerView = findViewById(R.id.dayForecastRecyclerView);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().updateNowForecast();
            }
        });

        views.add(temperatureTextView);
        views.add(cityTextView);
        views.add(timeTextView);
        views.add(dateTextView);
        views.add(imageView);
        views.add(descriptionTextView);
        views.add(temperatureMinTextView);
        views.add(temperatureMaxTextView);
        views.add(recyclerView);

        setRefreshing(true);
        attachPresenter();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPresenter().updateNowForecast();
            } else {
                requestLocationPermission();
            }
        }
    }

    @NonNull
    @Override
    protected MainPresenter providePresenter() {
        return new MainPresenter(
                new ForecastInteractor(getResources().getConfiguration().locale),
                new LocationProvider(getApplicationContext())
        );
    }

    @Override
    public void showForecastData(@NonNull final Forecast data) {
        temperatureTextView.setText(getString(R.string.temperature_value, data.temperatureValue));
        cityTextView.setText(data.city);
        timeTextView.setText(data.time);
        dateTextView.setText(data.date);
        imageView.setImageBitmap(ImageLoader.loadImage(data.imageUrl));
        descriptionTextView.setText(data.description);
        temperatureMinTextView.setText(getString(R.string.temperature_value, data.tempMin));
        temperatureMaxTextView.setText(getString(R.string.temperature_value, data.tempMax));
    }

    @Override
    public void showSavedDataInfo() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.app_is_offline)
                .setMessage(R.string.data_is_loaded_from_local_storage)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        dialog.show();
    }

    @Override
    public void showDayForecastData(@NonNull final List<Forecast> list) {
        adapter.setList(list);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_LONG).show();
    }

    @Override
    public void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, R.string.app_does_not_work, Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        } else {
            displayLocationWarningDialog();
        }
    }

    @Override
    public void setRefreshing(final boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);

        for (View view : views) {
            view.setVisibility(refreshing ? View.GONE : View.VISIBLE);
        }
    }

    private void displayLocationWarningDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.location_access)
                .setMessage(R.string.location_access_instruction)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().updateNowForecast();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, R.string.app_does_not_work, Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .show();
    }
}