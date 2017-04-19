package com.example.weatherapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.weatherapp.Adapter.DayAdapter;
import com.example.weatherapp.Weather.Day;

import java.util.Arrays;

public class DailyForcastActivity extends ListActivity {
    private Day[] mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daily_forcast);

        Intent intent = getIntent();
        Parcelable[] parcelables= intent.getParcelableArrayExtra(MainActivity.DAILY_FORCAST);
        mDays= Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter = new DayAdapter(mDays, this);
        setListAdapter(adapter);



    }
}
