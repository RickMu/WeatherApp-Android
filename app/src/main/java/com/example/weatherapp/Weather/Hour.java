package com.example.weatherapp.Weather;

/**
 * Created by 高静 on 2017/3/8.
 */

public class Hour {
    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public double getTemperorary() {
        return mTemperorary;
    }

    public void setTemperorary(double temperorary) {
        mTemperorary = temperorary;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    private long mTime;
    private String mSummary;
    private double mTemperorary;
    private String mIcon;
    private String mTimeZone;
}
