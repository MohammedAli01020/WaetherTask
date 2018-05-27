package com.example.mohamed.waethertask.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.waethertask.R;
import com.example.mohamed.waethertask.data.Weather;
import com.example.mohamed.waethertask.uitls.QueryUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        LoaderManager.LoaderCallbacks<List<Weather>> {

    private static final String URL_ADDRESS =
            "http://api.openweathermap.org/data/2.5/box/city?bbox=24.70007,22.0,36.86623,31.58568,10&&appid=e8b5a0596323011ae1c930c7ef3cf01e";

    private static final int LOADER_ID = 22;
    private List<Weather> mWeathers;
    private SupportMapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mWeathers == null) {
            return;
        }

        for (Weather p : mWeathers) {
            Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(p.getmLat(), p.getmLon()))
                    .title(getString(R.string.weather_current_temp, Double.parseDouble(p.getmCurrentTemp()))));

            marker.setSnippet(getString(R.string.weather_city_name, p.getmCityName()) + "\n" +
                    getString(R.string.weather_humidity, p.getmHumidity()) + "\n" +
                    getString(R.string.weather_description, p.getmWeatherDescription()));

            marker.showInfoWindow();
        }

    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<List<Weather>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Weather>>(this) {

            List<Weather> weathers = null;

            @Override
            protected void onStartLoading() {
                if (weathers != null) {
                    deliverResult(weathers);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Weather> loadInBackground() {
                return QueryUtils.fetchWeatherData(URL_ADDRESS);
            }

            @Override
            public void deliverResult(List<Weather> data) {
                weathers = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Weather>> loader, List<Weather> data) {
        if (null != data && data.size() != 0) {
            updateUi(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Weather>> loader) {

    }

    private void updateUi(List<Weather> data) {
        mWeathers = data;
        mMapFragment.getMapAsync(this);
    }
}
