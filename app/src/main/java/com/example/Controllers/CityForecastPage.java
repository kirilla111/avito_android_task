package com.example.Controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.myweather.R;

import org.json.JSONObject;

public class CityForecastPage extends AppCompatActivity {
    private TextView title,humidity,wind_speed,pressure,feels_like,temperature,location,description;
    private JSONObject response;
    private Button search_confirm;
    private SearchView city_search;
    private ImageView main_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = findViewById(R.id.city_title);
        description = findViewById(R.id.city_weather_description);
        humidity = findViewById(R.id.city_humidity);
        wind_speed = findViewById(R.id.city_wind_speed);
        pressure = findViewById(R.id.city_pressure);
        feels_like = findViewById(R.id.city_feels_like);
        temperature = findViewById(R.id.city_temperature);
        location = findViewById(R.id.city_location);
        main_img = findViewById(R.id.city_main_img);
        search_confirm = findViewById(R.id.search_confirm);
        city_search = findViewById(R.id.city_search);

        search_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = city_search.getQuery().toString();


            }
        });

    }
}
