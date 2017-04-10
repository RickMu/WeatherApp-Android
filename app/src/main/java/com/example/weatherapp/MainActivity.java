package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.Weather.Current;
import com.example.weatherapp.Weather.Day;
import com.example.weatherapp.Weather.Forcast;
import com.example.weatherapp.Weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORCAST = "DAYILY_FORCAST";
    Forcast mForcast;


    @BindView(R.id.humidityMeasureId) TextView mHumidity;
    @BindView(R.id.precepValue) TextView mPrecepValue;
    @BindView(R.id.precepLabel) TextView mPrecepLabel;
    @BindView(R.id.temperatureId) TextView mTemperature;
    @BindView(R.id.timeID) TextView mTime;
    @BindView(R.id.weatherDescription) TextView mSummary;
    @BindView (R.id.weatherIconId) ImageView mIconImageView;
    @BindView (R.id.refreshImage) ImageView mRefreshImg;
    @BindView(R.id.refreshProgress) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mProgressBar.setVisibility(View.INVISIBLE);

        final double latitude= -37.814, longitude=144.96332;
        mRefreshImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForcast(latitude,longitude);
            }
        });


        getForcast(latitude, longitude);
    }

    private void getForcast(double latitude, double longitude) {
        String secretKey= "3be6c051769f52fc39f671377b21b49b";
        String forcastUrl="https://api.darksky.net/forecast/"+secretKey+ "/"+ latitude+ ","+longitude;

        if (isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forcastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Failure to connect: ", e);

                }
                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        String jsonData= response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            try {
                                mForcast = parseForcastDetails(jsonData);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        toggleRefresh();
                                        updateForcast();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }

                }
            });
        }else{
            Toast.makeText(this, "Network Connection Error", Toast.LENGTH_LONG).show();
        }
    }

    private void updateForcast() {
        Current mCurrentWeather= mForcast.getCurrent();
        mTemperature.setText(mCurrentWeather.getCelsius()+"");
        mTime.setText("At time "+ mCurrentWeather.getFormatedTime()+" it will be");
        mHumidity.setText(mCurrentWeather.getHumidity()+"");
        mPrecepValue.setText(mCurrentWeather.getPrecipProbaility()+"%");
        mSummary.setText(mCurrentWeather.getSummary());

        Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }
    private void toggleRefresh(){
        if(mProgressBar.getVisibility()== View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImg.setVisibility(View.INVISIBLE);
        }else{
            mRefreshImg.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

    }
    private Forcast parseForcastDetails(String jsonData) throws JSONException {
        Forcast forcast = new Forcast();

        forcast.setCurrent(getCurrentWeather(jsonData));
        forcast.setDailyForcast(getDailyForcast(jsonData));
        forcast.setHourlyForcast(getHourlyForcast(jsonData));
        return forcast;
    }

    private Day[] getDailyForcast(String jsonData) throws JSONException {
        JSONObject weatherData= new JSONObject(jsonData);
        String timeZone = weatherData.getString("timezone");
        JSONObject dailyDataJSON= weatherData.getJSONObject("daily");
        JSONArray dailyDataJSONArray = dailyDataJSON.getJSONArray("data");
        Day[] dailyData= new Day[dailyDataJSONArray.length()];

        for( int i = 0 ;i<dailyDataJSONArray.length();i++){
            JSONObject daily = dailyDataJSONArray.getJSONObject(i);
            Day day = new Day();
            day.setTime(    daily.getLong("time")   );
            day.setIcon(    daily.getString("icon") );
            day.setSummary( daily.getString("summary"));
            day.setTemperatureMax(  daily.getDouble("temperatureMax"));
            day.setTimeZone(timeZone);

            dailyData[i]=day;

        }

        return dailyData;
    }


    private Hour[] getHourlyForcast(String jsonData) throws JSONException {

        JSONObject weatherData= new JSONObject(jsonData);
        String timeZone = weatherData.getString("timezone");
        JSONObject hourlyDataJSON= weatherData.getJSONObject("hourly");
        JSONArray hourlyDataJSONArray = hourlyDataJSON.getJSONArray("data");
        Hour[] hourlyData= new Hour[hourlyDataJSONArray.length()];

        for( int i = 0 ;i<hourlyDataJSONArray.length();i++){
            JSONObject hourly = hourlyDataJSONArray.getJSONObject(i);
            Hour hour = new Hour();
            hour.setTime(    hourly.getLong("time")   );
            hour.setIcon(    hourly.getString("icon") );
            hour.setSummary( hourly.getString("summary"));
            hour.setTemperorary(  hourly.getDouble("temperature"));
            hour.setTimeZone(timeZone);
            hourlyData[i]=hour;

        }
        return hourlyData;
    }

    private Current getCurrentWeather(String jsonData) throws JSONException {
        JSONObject weatherData = new JSONObject(jsonData);
        String timeZone = weatherData.getString("timezone");
        JSONObject currently = weatherData.getJSONObject("currently");

        Current currentWeather = new Current();

        currentWeather.setHumidity( currently.getDouble("humidity"));
        currentWeather.setPrecipProbaility( currently.getDouble("precipProbability"));
        currentWeather.setTemperature( currently.getDouble("temperature"));
        currentWeather.setIcon( currently.getString("icon"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTimeZone(timeZone);
        currentWeather.setTime(currently.getLong("time"));

        Log.d(TAG, currentWeather.getTime()+"");
        return currentWeather;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        boolean isAvalilable= false;
        if(info!=null && info.isConnected()){
            isAvalilable=true;
        }
        return isAvalilable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment alert = new AlertDialogFragment();
        alert.show(getFragmentManager(),"error_dialog");
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent = new Intent(this,DailyForcastActivity.class);
        intent.putExtra(DAILY_FORCAST, mForcast.getDailyForcast());
        startActivity(intent);
    }
}
