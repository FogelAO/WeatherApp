package com.example.weatherapp.data.dao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherapp.data.dao.db.ForecastContract.ForecastEntry;

public final class DBHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + ForecastEntry.TABLE_NAME + " (" +
            ForecastEntry._ID + " INTEGER PRIMARY KEY, " +
            ForecastEntry.COLUMN_TEMPERATURE + " TEXT, " +
            ForecastEntry.COLUMN_CITY + " TEXT, " +
            ForecastEntry.COLUMN_TIME + " TEXT, " +
            ForecastEntry.COLUMN_DATE + " TEXT, " +
            ForecastEntry.COLUMN_DESCRIPTION + " TEXT, " +
            ForecastEntry.COLUMN_TEMPERATURE_MAX + " TEXT, " +
            ForecastEntry.COLUMN_TEMPERATURE_MIN + " TEXT, " +
            ForecastEntry.COLUMN_SHORT_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ForecastEntry.TABLE_NAME;


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "forecast.db";

    public DBHelper(@Nullable final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(@NonNull final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
