package com.example.mohamed.waethertask.uitls;

import android.text.TextUtils;
import android.util.Log;

import com.example.mohamed.waethertask.data.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<Weather> fetchWeatherData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean flag = true;
        if (url.toString().contains("api")) {
            flag = false;
        }
        return extractWeather(jsonResponse, flag);
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder jsonResponse = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            String line = reader.readLine();
            while (line != null) {
                jsonResponse.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse.toString();
    }

    private static List<Weather> extractWeather(String jsonResponse, boolean flag) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<Weather> weathers = new ArrayList<>();
        if (flag) {
            try {
                JSONObject baseResponse = new JSONObject(jsonResponse);
                JSONArray listCities = baseResponse.getJSONArray("list");

                for (int i = 0; i < listCities.length(); i++) {
                    JSONObject currentCity = listCities.getJSONObject(i);
                    String cityName = currentCity.getString("name");

                    JSONObject coord = currentCity.getJSONObject("coord");
                    double lon = coord.getDouble("lon");
                    double lat = coord.getDouble("lat");

                    JSONObject main = currentCity.getJSONObject("main");

                    String currentTemp = main.getString("temp");
                    String tempMax = main.getString("temp_max");
                    String pressure = main.getString("pressure");
                    String humidity = main.getString("humidity");

                    JSONObject wind = currentCity.getJSONObject("wind");
                    String windSpeed = wind.getString("speed");
                    String deg = wind.getString("deg");

                    JSONArray weather = currentCity.getJSONArray("weather");
                    JSONObject firstObject = weather.getJSONObject(0);
                    String weatherDescription = firstObject.getString("description");
                    String weatherIcon = firstObject.getString("icon");
                    Weather place = new Weather(lon, lat, cityName, currentTemp,
                            tempMax, pressure, humidity, windSpeed, deg, weatherDescription, weatherIcon);

                    weathers.add(place);
                }

                return weathers;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONObject baseResponse = new JSONObject(jsonResponse);

                String cityName = baseResponse.getString("name");

                JSONObject coord = baseResponse.getJSONObject("coord");
                double lon = coord.getDouble("lon");
                double lat = coord.getDouble("lat");

                JSONObject main = baseResponse.getJSONObject("main");

                String currentTemp = main.getString("temp");
                String tempMax = main.getString("temp_max");
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                JSONObject wind = baseResponse.getJSONObject("wind");
                String windSpeed = wind.getString("speed");
                String deg = wind.getString("deg");

                JSONArray weather = baseResponse.getJSONArray("weather");
                JSONObject firstObject = weather.getJSONObject(0);
                String weatherDescription = firstObject.getString("description");
                String weatherIcon = firstObject.getString("icon");

                Weather place = new Weather(lon, lat, cityName, currentTemp,
                        tempMax, pressure, humidity, windSpeed, deg, weatherDescription, weatherIcon);

                weathers.add(place);

                return weathers;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
