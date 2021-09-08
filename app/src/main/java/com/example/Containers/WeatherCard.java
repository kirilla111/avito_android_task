package com.example.Containers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.Controllers.MainPage;
import com.example.myweather.R;

import static com.example.API_KEYS.ICON_KEY;
import static com.example.StaticMethods.CEL_CONST;
import static com.example.StaticMethods.getIconUrl;
import static com.example.StaticMethods.getWeatherInCel;

public class WeatherCard extends ConstraintLayout{
    private TextView time_tv,temp_tv;
    private ImageView Icon;

    public WeatherCard(Context context, String time, int temp, String icon_name) {
        super(context);
        inflate(context, R.layout.weather_card, this);

        Icon = findViewById(R.id.card_icon);
        this.time_tv = findViewById(R.id.card_time);
        this.temp_tv = findViewById(R.id.card_temp);

        this.time_tv.setText(time);
        this.temp_tv.setText(String.format("%o °C", (int) Math.floor(temp - CEL_CONST)));
        Glide.with(context).load(getIconUrl(icon_name)).into(Icon);



    }
}
