package com.example.mohamed.waethertask.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.waethertask.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    private static final String IMAGE_PATH = "http://openweathermap.org/img/w/";

    @BindView(R.id.tv_name)
    TextView mName;

    @BindView(R.id.tv_current_temp)
    TextView mCurrentTemp;

    @BindView(R.id.tv_max_temp)
    TextView mMaxTemp;

    @BindView(R.id.tv_humidity)
    TextView mHumidity;

    @BindView(R.id.tv_pressure)
    TextView mPressure;

    @BindView(R.id.tv_wind_speed)
    TextView mWindSpeed;

    @BindView(R.id.tv_wind_deg)
    TextView mWindDeg;

    @BindView(R.id.tv_weather_description)
    TextView mWeatherDescription;

    @BindView(R.id.img_details_weather_icon)
    ImageView mWeatherIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) throw new NullPointerException("intent not found!");

        updateUi(bundle);
    }

    /**
     * update the ui with data received from intent
     *
     * @param data the weather data
     */
    private void updateUi(Bundle data) {

        mName.setText(getString(R.string.weather_city_name,
                data.getString("name")));
        mCurrentTemp.setText(getString(R.string.weather_current_temp,
                (data.getString("currentTemp"))));
        mMaxTemp.setText(getString(R.string.weather_max_temp,
                (data.getString("maxTemp"))));
        mHumidity.setText(getString(R.string.weather_humidity,
                data.getString("humidity")));
        mPressure.setText(getString(R.string.weather_pressure,
                data.getString("pressure")));
        mWindSpeed.setText(getString(R.string.weather_wind_speed,
                data.getString("windSpeed")));
        mWindDeg.setText(getString(R.string.weather_wind_degree,
                data.getString("windDeg")));
        mWeatherDescription.setText(getString(R.string.weather_description,
                data.getString("weatherDescription")));

        Picasso.get()
                .load(Uri.parse(IMAGE_PATH + data.getString("weatherIcon") + ".png").toString())
                .into(mWeatherIcon);
    }

}
