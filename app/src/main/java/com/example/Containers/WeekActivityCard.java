package com.example.Containers;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.Controllers.MainPage;
import com.example.Controllers.WeekForecastPage;
import com.example.myweather.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.API_KEYS.DAY_KEY;
import static com.example.API_KEYS.DT_KEY;
import static com.example.API_KEYS.EVENING_KEY;
import static com.example.API_KEYS.ICON_KEY;
import static com.example.API_KEYS.MAIN_INFO_KEY;
import static com.example.API_KEYS.MAX_VALUE_KEY;
import static com.example.API_KEYS.MIN_VALUE_KEY;
import static com.example.API_KEYS.MORN_KEY;
import static com.example.API_KEYS.NIGHT_KEY;
import static com.example.API_KEYS.PRESSURE_KEY;
import static com.example.API_KEYS.TEMPERATURE_KEY;
import static com.example.StaticMethods.getHumidityString;
import static com.example.StaticMethods.getIconUrl;
import static com.example.StaticMethods.getPressureString;
import static com.example.StaticMethods.getWeatherInCel;
import static com.example.StaticMethods.getWindSpeedString;

public class WeekActivityCard extends ConstraintLayout {
    private TextView day_temp, morn_temp, eve_temp, night_temp, max_temp, min_temp, pressure, humidity, wind_speed, date_time;
    private ImageView icon;

    public WeekActivityCard(Context context, JSONObject json) throws JSONException {
        super(context);
        inflate(context, R.layout.week_card, this);

        this.date_time = findViewById(R.id.week_date_tv);
        this.day_temp = findViewById(R.id.week_day_temp);
        this.morn_temp = findViewById(R.id.week_morn_temp);
        this.eve_temp = findViewById(R.id.week_eve_temp);
        this.night_temp = findViewById(R.id.week_night_temp);
        this.max_temp = findViewById(R.id.week_max_temp);
        this.min_temp = findViewById(R.id.week_min_temp);
        this.pressure = findViewById(R.id.week_pressure);
        this.humidity = findViewById(R.id.week_humidity);
        this.wind_speed = findViewById(R.id.week_wind_speed);
        this.icon = findViewById(R.id.week_forecast_icon);


        long dt_mills = json.getLong(DT_KEY) * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        Date result_date = new Date(dt_mills);

        date_time.setText(sdf.format(result_date));


        JSONObject weather_json = json.getJSONObject(TEMPERATURE_KEY);

        morn_temp.setText(String.format("morn: %s",getWeatherInCel(weather_json, MORN_KEY)));
        eve_temp.setText(String.format("eve: %s", getWeatherInCel(weather_json, EVENING_KEY)));
        night_temp.setText(String.format("night: %s",getWeatherInCel(weather_json, NIGHT_KEY)));
        day_temp.setText(String.format("day: %s",getWeatherInCel(weather_json, DAY_KEY)));
        min_temp.setText(String.format("min: %s",getWeatherInCel(weather_json, MIN_VALUE_KEY)));
        max_temp.setText(String.format("max: %s",getWeatherInCel(weather_json, MAX_VALUE_KEY)));

        pressure.setText(getPressureString(json));
        humidity.setText(getHumidityString(json));
        wind_speed.setText(getWindSpeedString(json));

        Glide.with(context).load(getIconUrl(json.getJSONArray(MAIN_INFO_KEY).getJSONObject(0).getString(ICON_KEY))).into(icon);
    }
}