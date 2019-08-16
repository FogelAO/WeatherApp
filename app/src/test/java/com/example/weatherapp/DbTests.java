package com.example.weatherapp;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.weatherapp.data.dao.ForecastDao;
import com.example.weatherapp.data.dao.ForecastDatabase;
import com.example.weatherapp.entity.Forecast;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class DbTests {
    private final Context context = ApplicationProvider.getApplicationContext();
    private final ForecastDao db = new ForecastDatabase(context);
    private final Forecast testForecast = new Forecast(
            "20.0",
            null,
            null,
            "Rain",
            "12",
            "22",
            "London",
            "",
            "12:00",
            "13 Feb",
            "17.08"
    );

    private long testForecastId = 0L;

    @Before
    public void beforeTest() {
        testForecastId = db.save(testForecast);
    }

    @After
    public void afterTest() {
        db.clear();
    }

    @Test
    public void deleteByIdTest() {
        db.delete(testForecastId);
        final Forecast dbForecast = db.getById(testForecastId);
        assertThat("", dbForecast == null);
    }

    @Test
    public void getAllTest() {
        final List<Forecast> testList = new ArrayList<>();
        testList.add(testForecast);

        final List<Forecast> dbList = db.getAll();
        assertThat("", dbList.get(0).equals(testList.get(0)));
    }

    @Test
    public void clearTest() {
        final long beforeTestCount = db.getSize();
        db.clear();
        final long afterTestCount = db.getSize();

        assertThat("", afterTestCount < beforeTestCount && afterTestCount == 0);
    }

    @Test
    public void getByIdTest() {
        final Forecast dbForecast = db.getById(testForecastId);
        assert dbForecast != null;
        assertThat("", dbForecast.equals(testForecast));
    }

    @Test
    public void addTest() {
        final long beforeTestCount = db.getSize();
        db.save(testForecast);
        final long afterTestCount = db.getSize();

        assertThat("", afterTestCount > beforeTestCount);
    }

    @Test
    public void getLastTest() {
        final Forecast dbForecast = db.getLast();
        assert dbForecast != null;
        assertThat("", dbForecast.equals(testForecast));
    }
}
