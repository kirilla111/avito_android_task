package com.example.Controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.StaticMethods;
import com.example.myweather.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LoadPage extends AppCompatActivity {
    private ProgressBar load_progress_bar;
    private TextView connection_warn;


    public Address address;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.load_page);
        this.load_progress_bar = findViewById(R.id.load_progress_bar);
        this.connection_warn = findViewById(R.id.connection_warn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(() -> {
            while (
                    ActivityCompat.checkSelfPermission(LoadPage.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(LoadPage.this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (!StaticMethods.hasConnection(LoadPage.this) || !StaticMethods.isLocationEnabled(LoadPage.this)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setVisable(connection_warn);
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setInVisable(connection_warn);
                }
            });


            /* Get User address */
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LoadPage.this);
            getLocation();


        }).start();
    }

    private void setInVisable(View v) {
        try {
            v.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private synchronized void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                try {
                    Location location = task.getResult();

                    /* if GPS still don't work*/
                    if (location == null) {
                        try {
                            Thread.sleep(1000);
                            getLocation();
                            return;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    Geocoder geocoder = new Geocoder(LoadPage.this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1
                    );
                    address = addresses.get(0);
                    load_progress_bar.setProgress(1);
                    GetJsonByCoordinates(StaticMethods.getApiUrlByCoordinates(address.getLatitude(), address.getLongitude()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void GetJsonByCoordinates(String url) {
        RequestQueue mQ = Volley.newRequestQueue(LoadPage.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        load_progress_bar.setProgress(2);
                        Intent intent = new Intent(LoadPage.this, MainPage.class);
                        intent.putExtra("JSONObject", response.toString());
                        startActivity(intent);
                        //JSONArray jsonArray = response.getJSONArray()
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        GetJsonByCoordinates(url);
                    }
                });
        mQ.add(request);
    }

    private synchronized void setVisable(View v) {
        try {
            v.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}
