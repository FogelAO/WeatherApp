package com.example.weatherapp.data.dao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapp.entity.Forecast;

import java.util.List;

public interface ForecastDao {

    long save(@NonNull final Forecast forecast);

    @Nullable
    Forecast getById(final long id);

    @Nullable
    Forecast getLast();

    void delete(final long id);

    List<Forecast> getAll();

    long getSize();

    void clear();
}
