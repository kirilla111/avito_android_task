package com.example.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.Containers.WeatherCard;
import com.example.myweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.API_KEYS.CURRENT_WEATHER_KEY;
import static com.example.API_KEYS.DESCRIPTION_KEY;
import static com.example.API_KEYS.DT_KEY;
import static com.example.API_KEYS.HOURLY_FORECAST_KEY;
import static com.example.API_KEYS.ICON_KEY;
import static com.example.API_KEYS.MAIN_INFO_KEY;
import static com.example.API_KEYS.TEMPERATURE_KEY;
import static com.example.StaticMethods.getFeelsLike;
import static com.example.StaticMethods.getHumidityString;
import static com.example.StaticMethods.getIconUrl;
import static com.example.StaticMethods.getJsonStringUpperCase;
import static com.example.StaticMethods.getLocationNameString;
import static com.example.StaticMethods.getLocationString;
import static com.example.StaticMethods.getPressureString;
import static com.example.StaticMethods.getWeatherInCel;
import static com.example.StaticMethods.getWindSpeedString;

public class MainPage extends AppCompatActivity {
    private TextView weather_description, humidity, wind_speed, pressure, feels_like, temperature, location, main_title;
    private JSONObject response;
    private Button go_to_city_button, go_to_week_forecast_button;
    private ImageView main_img;
    private LinearLayout card_layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_page);
        weather_description = findViewById(R.id.city_weather_description);
        main_img = findViewById(R.id.city_main_img);
        humidity = findViewById(R.id.humidity);
        wind_speed = findViewById(R.id.wind_speed);
        pressure = findViewById(R.id.pressure);
        feels_like = findViewById(R.id.city_feels_like);
        temperature = findViewById(R.id.city_temperature);
        location = findViewById(R.id.location);
        main_title = findViewById(R.id.main_title);
        go_to_city_button = findViewById(R.id.go_to_city_button);
        go_to_week_forecast_button = findViewById(R.id.go_to_week_forecast);
        card_layout = findViewById(R.id.card_layout);

        if (getIntent().hasExtra("JSONObject")) {
            try {
                response = new JSONObject(getIntent().getStringExtra("JSONObject"));
                JSONObject current_weather = response.getJSONObject(CURRENT_WEATHER_KEY);
//                Log.d(null,response.toString());
//                JSONArray jsonArray = current_weather.getJSONArray(CURRENT_WEATHER_KEY);
//                JSONObject json_weather = jsonArray.getJSONObject(0);
                JSONArray json_array_main = current_weather.getJSONArray(MAIN_INFO_KEY);
                JSONObject json_main = json_array_main.getJSONObject(0);

                Glide.with(MainPage.this).load(getIconUrl(json_main.getString(ICON_KEY))).into(main_img);
                weather_description.setText(getJsonStringUpperCase(json_main, DESCRIPTION_KEY));

                humidity.setText(getHumidityString(current_weather));
                pressure.setText(getPressureString(current_weather));
                wind_speed.setText(getWindSpeedString(current_weather));
                feels_like.setText(getFeelsLike(current_weather));
                temperature.setText(getWeatherInCel(current_weather, TEMPERATURE_KEY));
                location.setText(getLocationString(response));
                main_title.setText(getLocationNameString(response));

                JSONArray hourly_forecast = response.getJSONArray(HOURLY_FORECAST_KEY);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 0, 0, 0);
                LinearLayout.LayoutParams layoutParams_first = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams_first.setMargins(30, 0, 0, 0);

                JSONObject forecast_json = hourly_forecast.getJSONObject(0);
                JSONArray forecast_array = forecast_json.getJSONArray(MAIN_INFO_KEY);
                JSONObject forecast_main = forecast_array.getJSONObject(0);

                WeatherCard wc = new WeatherCard(MainPage.this, "Now",
                        forecast_json.getInt(TEMPERATURE_KEY), json_main.getString(ICON_KEY));
                card_layout.addView(wc, layoutParams_first);
                layoutParams.setMargins(10, 0, 0, 0);

                for (int i = 1; i < hourly_forecast.length(); i++) {
                    //Log.d(null, hourly_forecast.getJSONObject(i).toString());
                    forecast_json = hourly_forecast.getJSONObject(i);
                    forecast_array = forecast_json.getJSONArray(MAIN_INFO_KEY);
                    forecast_main = forecast_array.getJSONObject(0);

                    long dt_mills = forecast_json.getLong(DT_KEY) * 1000;
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date result_date = new Date(dt_mills);

                    wc = new WeatherCard(MainPage.this, sdf.format(result_date),
                                                    forecast_json.getInt(TEMPERATURE_KEY), forecast_main.getString(ICON_KEY));

                    card_layout.addView(wc, layoutParams);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        go_to_week_forecast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, WeekForecastPage.class);
                intent.putExtra("JSONObject", response.toString());
                startActivity(intent);
            }
        });

        go_to_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, CityForecastPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }


}
