package com.example.Controllers;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.API_KEYS.*;
import static com.example.StaticMethods.*;

public class MainPage extends AppCompatActivity {
    private TextView weather_description,humidity,wind_speed,pressure,feels_like,temperature,location,main_title;
    private JSONObject response;
    private ImageView main_img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_page);
        weather_description = findViewById(R.id.weather_description);
        main_img = findViewById(R.id.main_img);
        humidity = findViewById(R.id.humidity);
        wind_speed = findViewById(R.id.wind_speed);
        pressure = findViewById(R.id.pressure);
        feels_like = findViewById(R.id.feels_like);
        temperature = findViewById(R.id.temperature);
        location = findViewById(R.id.location);
        main_title = findViewById(R.id.main_title);


        if(getIntent().hasExtra("JSONObject")) {
            try {
                response = new JSONObject(getIntent().getStringExtra("JSONObject"));
                JSONArray jsonArray = response.getJSONArray(WEATHER_KEY);
                JSONObject json_weather = jsonArray.getJSONObject(0);
                JSONObject json_main = response.getJSONObject(MAIN_INFO_KEY);

                Glide.with(MainPage.this).load(getIconUrl(json_weather.getString("icon"))).into(main_img);
                weather_description.setText(getJsonStringUpperCase(json_weather, DESCRIPTION_KEY));
                humidity.setText(getHumidityString(json_main,HUMIDITY_KEY));
                temperature.setText(getWeatherInCel(json_main,TEMPERATURE_KEY));
                feels_like.setText(getFeelsLike(json_main,FEELS_LIKE_KEY));
                pressure.setText(getPressureString(json_main,PRESSURE_KEY));

                JSONObject json_wind = response.getJSONObject(WIND_INFO_KEY);
                wind_speed.setText(getWindSpeedString(json_wind,WIND_SPEED_KEY));

                JSONObject json_location = response.getJSONObject(LOCATION_INFO_KEY);
                location.setText(getLocationString(json_location));

                main_title.setText(getLocationNameString(response));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        // do nothing
    }


}
