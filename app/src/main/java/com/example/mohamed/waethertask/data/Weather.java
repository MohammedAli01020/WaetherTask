package com.example.mohamed.waethertask.data;


public class Weather {
    private double mLon;
    private double mLat;
    private String mCityName;
    private String mCurrentTemp;
    private String mTempMax;
    private String mPressure;
    private String mHumidity;
    private String mWindSpeed;
    private String mDeg;
    private String mWeatherDescription;

    /**
     *
     * @param mLon langitude
     * @param mLat latitude
     * @param mCityName city name
     * @param mCurrentTemp current temp
     * @param mTempMax max temp
     * @param mPressure pressure
     * @param mHumidity umditiy
     * @param mWindSpeed wind speed
     * @param mDeg desgree
     * @param mWeatherDescription description
     */
    public Weather(double mLon, double mLat, String mCityName, String mCurrentTemp, String mTempMax, String mPressure, String mHumidity, String mWindSpeed, String mDeg, String mWeatherDescription) {
        this.mLon = mLon;
        this.mLat = mLat;
        this.mCityName = mCityName;
        this.mCurrentTemp = mCurrentTemp;
        this.mTempMax = mTempMax;
        this.mPressure = mPressure;
        this.mHumidity = mHumidity;
        this.mWindSpeed = mWindSpeed;
        this.mDeg = mDeg;
        this.mWeatherDescription = mWeatherDescription;
    }

    public double getmLon() {
        return mLon;
    }

    public double getmLat() {
        return mLat;
    }

    public String getmCityName() {
        return mCityName;
    }

    public String getmCurrentTemp() {
        return mCurrentTemp;
    }

    public String getmTempMax() {
        return mTempMax;
    }

    public String getmPressure() {
        return mPressure;
    }

    public String getmHumidity() {
        return mHumidity;
    }

    public String getmWindSpeed() {
        return mWindSpeed;
    }

    public String getmDeg() {
        return mDeg;
    }

    public String getmWeatherDescription() {
        return mWeatherDescription;
    }
}
