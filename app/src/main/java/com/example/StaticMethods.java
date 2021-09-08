package com.example;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.API_KEYS.*;

public class StaticMethods {
    public static final double CEL_CONST  = 273.15;
    public static boolean hasConnection(final Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions((AppCompatActivity) context,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 44);

        }
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions((AppCompatActivity) context,
                    new String[]{Manifest.permission.INTERNET}, 44);

        }

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static Boolean isLocationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is a new method provided in API 28
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
            // This was deprecated in API 28
            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return (mode != Settings.Secure.LOCATION_MODE_OFF);
        }
    }
    /* V 1.0 */
//    public static String getApiUrlByCoordinates(double lat, double lon) {
//        String attrs = String.format(API_URL_ATTRS_COORDINATES, lat, lon);
//        return API_URL + attrs + API_DIVIDER + APP_ID;
//    }
    /* V 2.0 */
    public static String getApiUrlByCoordinates(double lat, double lon) {
        String attrs = String.format(ALTERNATIVE_API_URL, lat, lon);
//        Log.d(null, attrs);
        return attrs;
    }

    public static String getIconUrl(String icon_code) {
//        Log.d(null, String.format(ICON_URL, icon_code));
        return String.format(ICON_URL, icon_code);
    }

    public static String getJsonStringUpperCase(JSONObject json, String key) throws JSONException {
        String start = json.getString(key).substring(0, 1);
        String end = json.getString(key).substring(1);
        return start.toUpperCase() + end;
    }

    public static String getWeatherInCel(JSONObject json, String key) throws JSONException {
        double temp = json.getDouble(key);
        temp = temp - CEL_CONST;
        return String.format("%o °C", (int) Math.floor(temp));

    }

    public static String getHumidityString(JSONObject json) throws JSONException {
        String humidity = json.getString(HUMIDITY_KEY);
        return humidity + " %";
    }

    public static String getFeelsLike(JSONObject json) throws JSONException {
        return String.format("Feels like %s", getWeatherInCel(json, TEMPERATURE_KEY));
    }

    public static String getPressureString(JSONObject json) throws JSONException {
        double pressure = json.getDouble(PRESSURE_KEY);
        pressure = pressure / 1.333;
        return String.format("%.3f mmHg", pressure);
    }

    public static String getWindSpeedString(JSONObject json) throws JSONException {
        return String.format("%.2f m/s", json.getDouble(WIND_SPEED_KEY));
    }

    public static String getLocationString(JSONObject json) throws JSONException {
        return String.format("lon: %.2f / lat: %.2f", json.getDouble(LONGITUDE_KEY), json.getDouble(LATITUDE_KEY));
    }

    public static String getLocationNameString(JSONObject json) throws JSONException {
        return String.format("Weather in %s", json.getString(LOCATION_NAME_KEY));
    }

    public static String getApiUrlByCity(String query) {
        String attrs = String.format(API_URL_ATTRS_CITY, query);
        return API_URL+attrs+API_DIVIDER+APP_ID;
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
