package com.example.mohamed.waethertask.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mohamed.waethertask.R;
import com.example.mohamed.waethertask.data.Weather;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherAdapter extends ArrayAdapter<Weather> {

    public WeatherAdapter(@NonNull Context context, @NonNull List cities) {
        super(context, 0, cities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Context context = getContext();
        Weather currentWeather = getItem(position);
        ViewHolder holder;

        View rootView = convertView;
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext())
                    .inflate(R.layout.city_item_list, parent, false);

            holder = new ViewHolder(rootView);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }

        holder.cityName.setText(context.getString(R.string.weather_city_name,
                currentWeather.getmCityName()));
        holder.currentTemp.setText(context.getString(R.string.weather_current_temp,
                currentWeather.getmCurrentTemp()));
        holder.humidity.setText(context.getString(R.string.weather_humidity,
                currentWeather.getmHumidity()));
        holder.windSpeed.setText(context.getString(R.string.weather_wind_speed,
                currentWeather.getmWindSpeed()));
        holder.windDeg.setText(context.getString(R.string.weather_wind_degree,
                currentWeather.getmDeg()));

        return rootView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_city_name) TextView cityName;
        @BindView(R.id.tv_city_current_temp) TextView currentTemp;
        @BindView(R.id.tv_city_humidity) TextView humidity;
        @BindView(R.id.tv_city_windSpeed) TextView windSpeed;
        @BindView(R.id.tv_city_degree) TextView windDeg;

        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);

        }
    }
}
