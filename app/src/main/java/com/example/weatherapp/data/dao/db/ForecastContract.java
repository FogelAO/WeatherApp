package com.example.weatherapp.data.dao.db;

import android.provider.BaseColumns;

public final class ForecastContract {
    private ForecastContract() {
    }

    public static class ForecastEntry implements BaseColumns {
        private ForecastEntry() {
        }

        public static final String TABLE_NAME = "forecast";
        public static final String COLUMN_TEMPERATURE = "temperature";
        public static final String COLUMN_TEMPERATURE_MIN = "temperature_min";
        public static final String COLUMN_TEMPERATURE_MAX = "temperature_max";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_SHORT_DATE = "short_date";
    }
}
