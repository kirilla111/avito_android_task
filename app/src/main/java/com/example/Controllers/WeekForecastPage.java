package com.example.Controllers;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Containers.WeatherCard;
import com.example.Containers.WeekActivityCard;
import com.example.myweather.R;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.API_KEYS.DAILY_FORECAST_KEY;

public class WeekForecastPage extends AppCompatActivity {
    private ImageButton back_button;
    private LinearLayout week_card_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.week_forecast_page);

        back_button = findViewById(R.id.back_button);
        week_card_layout = findViewById(R.id.week_card_layout);

        if (getIntent().hasExtra("JSONObject")) {
            try {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 30);

                JSONObject response = new JSONObject(getIntent().getStringExtra("JSONObject"));
                JSONArray week_forecast_array = response.getJSONArray(DAILY_FORECAST_KEY);

                for (int i = 0; i < week_forecast_array.length() - 1; i++) {
                    week_card_layout.addView(new WeekActivityCard(WeekForecastPage.this,  week_forecast_array.getJSONObject(i)), layoutParams);
                }
                LinearLayout.LayoutParams layoutParams_last = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams_last.setMargins(0, 0, 0, 250);
                week_card_layout.addView(new WeekActivityCard(WeekForecastPage.this,  week_forecast_array.getJSONObject(week_forecast_array.length()-1)), layoutParams_last);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
