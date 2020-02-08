package com.example.ivanwl.specifit.Services.Location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;


//GPS Methods for reference
//https://developer.android.com/reference/android/location/LocationManager#public-methods_1
public class GPS {
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private double longitude;
    private double latitude;

    public GPS(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public double getLongitude() {
        if (!isGPSEnabled) {
            Log.i("GPSLocation", "GPS not allowed");
            return 0;
        }

        try {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null);
        } catch (SecurityException err) {
            Log.i("PRINT", err.toString());
        }
        //need to add a delay
        return longitude;
    }

    public double getLatitude() {
        if (!isGPSEnabled) {
            Log.i("GPSLocation", "GPS not allowed");
            return 0;
        }

        try {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null);
        } catch (SecurityException err) {
            Log.i("PRINT", err.toString());
        }
        //need to add a delay
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
        }
        @Override
        public void onProviderDisabled(String str) {}

        @Override
        public void onProviderEnabled(String str) {}

        @Override
        public void onStatusChanged(String str, int num, Bundle bundle) {}
    };
}
