package com.example.weatherapp.Weather;

import com.example.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 高静 on 2017/3/2.
 */

public class Current {

    private String mTimeZone;
    private String mIcon;
    private String mSummary;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipProbaility;
    private long mTime;




    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public long getTime() {
        return mTime;
    }

    public String getFormatedTime(){

        /**因为API 得到的 时间是 unix 1970 开始的时间
         * SimpleDateFormat**/
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date date = new Date(getTime()*1000);
        String time = formatter.format(date);
        return time;
    }

    public void setTime(long time) {
        mTime = time;
    }


    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId(){
        return Forcast.getImageIcon(mIcon);
    }

    public double getPrecipProbaility() {

        return (int)(mPrecipProbaility*100+0.5);
    }

    public void setPrecipProbaility(double precipProbaility) {
        mPrecipProbaility = precipProbaility;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getFahrenheit() {
        return (int)(mTemperature+0.5);
    }
    public int getCelsius(){
        int temperature = (int)((mTemperature-32)*5/9);
        return temperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }


}
