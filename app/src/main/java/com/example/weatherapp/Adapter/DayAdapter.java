package com.example.weatherapp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.Weather.Day;

/**
 * Created by 高静 on 2017/3/9.
 */

public class DayAdapter extends BaseAdapter {


    Day[] mDays;
    Context mContext;
    public DayAdapter(Day[] days, Context context) {
        this.mDays = days;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.daily_weather_list,null);
            holder= new ViewHolder();
            holder.iconImageView = (ImageView)convertView.findViewById(R.id.weatherIcon);
            holder.temperatureLabel= (TextView) convertView.findViewById(R.id.degreeLabel);
            holder.dayLabel= (TextView) convertView.findViewById(R.id.dayLabel);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Day day = mDays[position];
        int d = day.getIconId();
        holder.iconImageView.setImageResource(d);
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");


        if (position == 0) {
            holder.dayLabel.setText("Today");
        }
        else {
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }

        return convertView;
    }
    private static class ViewHolder{
        ImageView iconImageView; // public by default
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
