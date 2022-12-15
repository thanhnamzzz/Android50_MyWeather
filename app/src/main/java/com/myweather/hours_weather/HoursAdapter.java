package com.myweather.hours_weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myweather.Global;
import com.myweather.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HoursViewHolder> {
    private ArrayList<WeatherHours> mListWeather;
    private Context mContext;

    public HoursAdapter(ArrayList<WeatherHours> mListWeather) {
        this.mListWeather = mListWeather;
    }

    @NonNull
    @Override
    public HoursAdapter.HoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext =parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_next_hours, parent, false);
        return new HoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursViewHolder holder, int position) {
        WeatherHours weatherHours = mListWeather.get(position);
//        holder.tvTimeHours.setText(weatherHours.getDtTxt());
        holder.tvTimeHours.setText(getHours(weatherHours.getDtTxt())+":00");
        // get object weather trong list weather của weatherHours chứ không phải get theo mlistWeather
        // Glide.with(mContext).load(Global.getImgForecast(weatherHours.getWeather().get(position).getIcon())).into(holder.imgTempHours);
        // Phải get tại position = 0 vì list weather của weatherHours chỉ có một phần tử
        Glide.with(mContext).load(Global.getImgForecast(weatherHours.getWeather().get(0).getIcon())).into(holder.imgTempHours);
        holder.tvTempHours.setText(Global.convertKtoC(weatherHours.getMain().getTemp()));
    }

    @Override
    public int getItemCount() {
        return mListWeather.size();
    }

    public class HoursViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimeHours, tvTempHours;
        ImageView imgTempHours;

        public HoursViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeHours = itemView.findViewById(R.id.tvTimeHours);
            tvTempHours = itemView.findViewById(R.id.tvTempHours);
            imgTempHours = itemView.findViewById(R.id.imgTempHours);
        }
    }
    private static int getHours(String time) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(time));
        return cal.get(Calendar.HOUR_OF_DAY);
    }
}
