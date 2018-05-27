package com.example.mohamed.waethertask.ui;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mohamed.waethertask.R;
import com.example.mohamed.waethertask.adapter.WeatherAdapter;
import com.example.mohamed.waethertask.data.Weather;
import com.example.mohamed.waethertask.uitls.QueryUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Weather>> {

    private static final int LOADER_ID = 102;
    private static final String URL_KEY = "url_key";
    private static final String URL_FOR_QUERY =
            "http://api.openweathermap.org/data/2.5/weather?appid=f242acfd7bb9431ca04e38448b4d58ef";
    private static final String URL_ADDRESS =
            "http://api.openweathermap.org/data/2.5/box/city?bbox=24.70007,22.0,36.86623,31.58568,10&&appid=e8b5a0596323011ae1c930c7ef3cf01e";

    @BindView(R.id.cities_list)
    ListView mCitiesListView;
    WeatherAdapter mAdapter;

    @BindView(R.id.pb_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_empty_list)
    TextView mEmptyTextView;
    private static final String QUERY_PARAM = "q";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        ButterKnife.bind(this);

        mAdapter = new WeatherAdapter(this, new ArrayList());
        mCitiesListView.setAdapter(mAdapter);

        mCitiesListView.setEmptyView(mEmptyTextView);
        mCitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CitiesActivity.this, DetailsActivity.class);

                Weather currentWeather = mAdapter.getItem(i);

                Bundle data = new Bundle();
                data.putString("name", currentWeather.getmCityName());
                data.putString("currentTemp", currentWeather.getmCurrentTemp());
                data.putString("maxTemp", currentWeather.getmTempMax());
                data.putString("humidity", currentWeather.getmHumidity());
                data.putString("pressure", currentWeather.getmPressure());
                data.putString("windSpeed", currentWeather.getmWindSpeed());
                data.putString("windDeg", currentWeather.getmDeg());
                data.putString("weatherDescription", currentWeather.getmWeatherDescription());
                data.putString("weatherIcon", currentWeather.getmWeatherIcon());

                intent.putExtras(data);

                startActivity(intent);

            }
        });
        mProgressBar.setVisibility(View.VISIBLE);

        Bundle data = new Bundle();
        data.putString(URL_KEY, URL_ADDRESS);
        getSupportLoaderManager().initLoader(LOADER_ID, data, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_cities, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Uri builtUri = Uri.parse(URL_FOR_QUERY).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, s).build();

                Bundle bundle = new Bundle();
                bundle.putString(URL_KEY, builtUri.toString());

                getSupportLoaderManager().restartLoader(LOADER_ID, bundle, CitiesActivity.this);
                return true;
            }
        });
        return true;
    }

    /**
     * uupdate the ui with data
     *
     * @param weathers data list
     */
    private void updateUi(List<Weather> weathers) {
        mAdapter.clear();
        mAdapter.addAll(weathers);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * load data in the background
     *
     * @param id   the id of the loader
     * @param args null
     * @return loader
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<List<Weather>> onCreateLoader(int id, final Bundle args) {
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
                if (args == null) return null;
                return QueryUtils.fetchWeatherData(args.getString(URL_KEY));
            }

            @Override
            public void deliverResult(List<Weather> data) {
                weathers = data;
                super.deliverResult(data);
            }
        };
    }

    /**
     * @param loader loader
     * @param data   the data returned from the loader
     */
    @Override
    public void onLoadFinished(Loader<List<Weather>> loader, List<Weather> data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (null != data && data.size() != 0)
            updateUi(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Weather>> loader) {
        mAdapter.clear();
    }
}
