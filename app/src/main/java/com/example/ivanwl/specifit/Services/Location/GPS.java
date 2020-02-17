package com.example.ivanwl.specifit.Services.Location;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.ivanwl.specifit.Interfaces.RestaurantCallback;
import com.example.ivanwl.specifit.Interfaces.RestaurantsCallback;

import static com.example.ivanwl.specifit.Utils.Utils.print;

//GPS Methods for reference
//https://developer.android.com/reference/android/location/LocationManager#public-methods_1
public class GPS {
    private LocationManager locationManager;
    private long minTime = 5000; //change in ms to update location
    private float minDistance = 5000; //change in meters to update location
    private boolean isGPSEnabled;
    private double longitude;
    private double latitude;
    private RestaurantsCallback callback;

    public GPS(Context context, RestaurantsCallback callback) {
        this.callback = callback;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public double getLongitude() {
        if (!isGPSEnabled) {
            print("GPS not allowed");
            return 0;
        }
        return longitude;
    }

    public double getLatitude() {
        if (!isGPSEnabled) {
            print("GPS not allowed");
            return 0;
        }
        return latitude;
    }

    final private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // A new location update is received.  Do something useful with it.  In this case,
            // we're sending the update to a handler which then updates the UI with the new
            // location.
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            callback.getRestaurants(location.getLatitude(), location.getLongitude());
        }
        @Override
        public void onProviderDisabled(String str) {}

        @Override
        public void onProviderEnabled(String str) {}

        @Override
        public void onStatusChanged(String str, int num, Bundle bundle) {}
    };
}
