package com.example.Controllers;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.Containers.WeatherCard;
import com.example.StaticMethods;
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
import static com.example.API_KEYS.LATITUDE_KEY;
import static com.example.API_KEYS.LOCATION_INFO_KEY;
import static com.example.API_KEYS.LONGITUDE_KEY;
import static com.example.API_KEYS.MAIN_INFO_KEY;
import static com.example.API_KEYS.TEMPERATURE_KEY;
import static com.example.StaticMethods.getApiUrlByCoordinates;
import static com.example.StaticMethods.getFeelsLike;
import static com.example.StaticMethods.getHumidityString;
import static com.example.StaticMethods.getIconUrl;
import static com.example.StaticMethods.getJsonStringUpperCase;
import static com.example.StaticMethods.getLocationNameString;
import static com.example.StaticMethods.getLocationString;
import static com.example.StaticMethods.getPressureString;
import static com.example.StaticMethods.getWeatherInCel;
import static com.example.StaticMethods.getWindSpeedString;
import static com.example.StaticMethods.hideKeyboard;

public class CityForecastPage extends AppCompatActivity {
    private TextView title,humidity,wind_speed,pressure,feels_like,temperature,location,description;
    private Button search_confirm;
    private EditText city_search;
    private ProgressBar city_search_progress;
    private ImageView main_img;
    private JSONObject response;
    private LinearLayout city_weather_layout;
    private ConstraintLayout city_info_layout,city_main_layout;
    private ImageButton back_to_main_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.city_weather_page);

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
        city_search_progress = findViewById(R.id.city_search_progress);
        city_weather_layout = findViewById(R.id.city_weather_layout);
        city_info_layout = findViewById(R.id.city_info_layout);
        back_to_main_button = findViewById(R.id.back_to_main_button);
        city_main_layout = findViewById(R.id.city_main_layout);

        city_search_progress.setVisibility(View.INVISIBLE);

        search_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = city_search.getText().toString();
                if (query.length()==0)
                    return;

                city_search_progress.setVisibility(View.VISIBLE);
                city_info_layout.setVisibility(View.INVISIBLE);
                search_confirm.clearFocus();
                search_confirm.setCursorVisible(false);
                CityForecastPage.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                city_main_layout.requestFocus();
                hideKeyboard(CityForecastPage.this);
                new Thread(() -> {

                    GetJsonByCity(StaticMethods.getApiUrlByCity(query));
                }).start();
            }
        });

        back_to_main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetJsonByCity(String url) {
        RequestQueue mQ = Volley.newRequestQueue(CityForecastPage.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        double lon = 0;
                        double lat = 0;
                        try {
                            JSONObject location = response.getJSONObject(LOCATION_INFO_KEY);
                            lon = location.getDouble(LONGITUDE_KEY);
                            lat = location.getDouble(LATITUDE_KEY);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getApiUrlByCoordinates(lat,lon), null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        CityForecastPage.this.response = response;
                                        ParseCityWeatherJson(response);

                                        //JSONArray jsonArray = response.getJSONArray()
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        runOnUiThread(() -> city_search_progress.setVisibility(View.INVISIBLE));

                                        Toast.makeText(CityForecastPage.this, "No such City found", Toast.LENGTH_LONG).show();
                                    }
                                });
                        mQ.add(request);

                        //JSONArray jsonArray = response.getJSONArray()
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        runOnUiThread(() -> city_search_progress.setVisibility(View.INVISIBLE));
                        Toast.makeText(CityForecastPage.this, "No such City found", Toast.LENGTH_LONG).show();

                    }
                });
        mQ.add(request);
    }

    private void ParseCityWeatherJson(JSONObject response) {
        try {
            JSONObject current_weather = response.getJSONObject(CURRENT_WEATHER_KEY);
            JSONArray json_array_main = current_weather.getJSONArray(MAIN_INFO_KEY);
            JSONObject json_main = json_array_main.getJSONObject(0);

            Glide.with(CityForecastPage.this).load(getIconUrl(json_main.getString(ICON_KEY))).into(main_img);
            description.setText(getJsonStringUpperCase(json_main, DESCRIPTION_KEY));

            humidity.setText(getHumidityString(current_weather));
            pressure.setText(getPressureString(current_weather));
            wind_speed.setText(getWindSpeedString(current_weather));
            feels_like.setText(getFeelsLike(current_weather));
            temperature.setText(getWeatherInCel(current_weather, TEMPERATURE_KEY));
            location.setText(getLocationString(response));
            title.setText(getLocationNameString(response));

            JSONArray hourly_forecast = response.getJSONArray(HOURLY_FORECAST_KEY);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 0, 0, 0);
            LinearLayout.LayoutParams layoutParams_first = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams_first.setMargins(30, 0, 0, 0);

            JSONObject forecast_json = hourly_forecast.getJSONObject(0);
            JSONArray forecast_array = forecast_json.getJSONArray(MAIN_INFO_KEY);
            JSONObject forecast_main = forecast_array.getJSONObject(0);

            WeatherCard wc = new WeatherCard(CityForecastPage.this, "Now",
                    forecast_json.getInt(TEMPERATURE_KEY), json_main.getString(ICON_KEY));
            city_weather_layout.addView(wc, layoutParams_first);
            layoutParams.setMargins(10, 0, 0, 0);

            for (int i = 1; i < hourly_forecast.length(); i++) {
                //Log.d(null, hourly_forecast.getJSONObject(i).toString());
                forecast_json = hourly_forecast.getJSONObject(i);
                forecast_array = forecast_json.getJSONArray(MAIN_INFO_KEY);
                forecast_main = forecast_array.getJSONObject(0);

                long dt_mills = forecast_json.getLong(DT_KEY) * 1000;
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date result_date = new Date(dt_mills);

                wc = new WeatherCard(CityForecastPage.this, sdf.format(result_date),
                        forecast_json.getInt(TEMPERATURE_KEY), forecast_main.getString(ICON_KEY));

                city_weather_layout.addView(wc, layoutParams);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        city_search_progress.setVisibility(View.INVISIBLE);
                        city_info_layout.setVisibility(View.VISIBLE);
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
