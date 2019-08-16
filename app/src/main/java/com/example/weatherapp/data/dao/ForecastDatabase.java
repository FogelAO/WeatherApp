package com.example.weatherapp.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapp.data.dao.db.DBHelper;
import com.example.weatherapp.data.dao.db.ForecastContract.ForecastEntry;
import com.example.weatherapp.entity.Forecast;
import com.example.weatherapp.utils.qualifiers.AppCtx;

import java.util.ArrayList;
import java.util.List;

public final class ForecastDatabase implements ForecastDao {
    private static final String TAG = ForecastDatabase.class.getSimpleName();

    private final DBHelper dbHelper;
    private final String[] projection = {
            BaseColumns._ID,
            ForecastEntry.COLUMN_TEMPERATURE,
            ForecastEntry.COLUMN_TEMPERATURE_MIN,
            ForecastEntry.COLUMN_TEMPERATURE_MAX,
            ForecastEntry.COLUMN_CITY,
            ForecastEntry.COLUMN_TIME,
            ForecastEntry.COLUMN_DATE,
            ForecastEntry.COLUMN_DESCRIPTION,
            ForecastEntry.COLUMN_SHORT_DATE
    };

    public ForecastDatabase(@AppCtx @Nullable final Context context) {
        this.dbHelper = new DBHelper(context);
    }

    @Override
    public long save(@NonNull Forecast forecast) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        final ContentValues values = new ContentValues();
        values.put(ForecastEntry.COLUMN_TEMPERATURE, forecast.temperatureValue);
        values.put(ForecastEntry.COLUMN_TEMPERATURE_MIN, forecast.tempMin);
        values.put(ForecastEntry.COLUMN_TEMPERATURE_MAX, forecast.tempMax);
        values.put(ForecastEntry.COLUMN_CITY, forecast.city);
        values.put(ForecastEntry.COLUMN_TIME, forecast.time);
        values.put(ForecastEntry.COLUMN_DATE, forecast.date);
        values.put(ForecastEntry.COLUMN_DESCRIPTION, forecast.description);
        values.put(ForecastEntry.COLUMN_SHORT_DATE, forecast.shortDate);

        return db.insert(ForecastEntry.TABLE_NAME, null, values);
    }

    @Nullable
    @Override
    public Forecast getById(final long id) {
        if (getSize() == 0)
            return null;

        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        final Cursor cursor = db.rawQuery(
                "SELECT * FROM " + ForecastEntry.TABLE_NAME + " WHERE " + ForecastEntry._ID + " = " + id, null
        );
        cursor.moveToFirst();

        try {
            final String temperature = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE));
            final String temperatureMin = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE_MIN));
            final String temperatureMax = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE_MAX));
            final String city = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_CITY));
            final String time = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TIME));
            final String date = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_DATE));
            final String description = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_DESCRIPTION));
            final String shortDate = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_SHORT_DATE));

            cursor.close();

            return new Forecast(
                    temperature,
                    null,
                    null,
                    description,
                    temperatureMin,
                    temperatureMax,
                    city,
                    "",
                    time,
                    date,
                    shortDate
            );
        } catch (Exception e) {
            Log.e(TAG, "SQL error occurred", e);
        }
        return null;
    }

    @Nullable
    @Override
    public Forecast getLast() {
        if (getSize() == 0)
            return null;

        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        final Cursor cursor = db.query(
                ForecastEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                BaseColumns._ID + " DESC",
                "1"
        );
        cursor.moveToFirst();

        try {
            final String temperature = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE));
            final String temperatureMin = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE_MIN));
            final String temperatureMax = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE_MAX));
            final String city = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_CITY));
            final String time = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TIME));
            final String date = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_DATE));
            final String description = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_DESCRIPTION));
            final String shortDate = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_SHORT_DATE));

            cursor.close();

            return new Forecast(
                    temperature,
                    null,
                    null,
                    description,
                    temperatureMin,
                    temperatureMax,
                    city,
                    "",
                    time,
                    date,
                    shortDate
            );
        } catch (Exception e) {
            Log.e(TAG, "SQL error occurred", e);
        }
        return null;
    }

    @Override
    public void delete(final long id) {
        dbHelper.getWritableDatabase().delete(ForecastEntry.TABLE_NAME, ForecastEntry._ID + "=" + id, null);
    }

    @Override
    public List<Forecast> getAll() {
        if (getSize() == 0)
            return new ArrayList<>();

        List<Forecast> items = new ArrayList<>();
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        final Cursor cursor = db.query(
                ForecastEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            try {
                final String temperature = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE));
                final String temperatureMin = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE_MIN));
                final String temperatureMax = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TEMPERATURE_MAX));
                final String city = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_CITY));
                final String time = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_TIME));
                final String date = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_DATE));
                final String description = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_DESCRIPTION));
                final String shortDate = cursor.getString(cursor.getColumnIndexOrThrow(ForecastEntry.COLUMN_SHORT_DATE));

                final Forecast forecast = new Forecast(
                        temperature,
                        null,
                        null,
                        description,
                        temperatureMin,
                        temperatureMax,
                        city,
                        "",
                        time,
                        date,
                        shortDate
                );

                items.add(forecast);
            } catch (Exception e) {
                Log.e(TAG, "SQL error occurred", e);
            }
        }
        cursor.close();
        return items;
    }

    @Override
    public long getSize() {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, ForecastEntry.TABLE_NAME);
    }

    @Override
    public void clear() {
        dbHelper.getWritableDatabase().delete(ForecastEntry.TABLE_NAME, null, null);
    }
}
