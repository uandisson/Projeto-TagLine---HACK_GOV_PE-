package org.osmdroid;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationListenerProxy implements LocationListener {
    private LocationListener mListener = null;
    private final LocationManager mLocationManager;

    public LocationListenerProxy(LocationManager pLocationManager) {
        this.mLocationManager = pLocationManager;
    }

    public boolean startListening(LocationListener pListener, long j, float f) {
        long pUpdateTime = j;
        float pUpdateDistance = f;
        boolean result = false;
        this.mListener = pListener;
        for (String provider : this.mLocationManager.getProviders(true)) {
            if ("gps".equals(provider) || "network".equals(provider)) {
                result = true;
                this.mLocationManager.requestLocationUpdates(provider, pUpdateTime, pUpdateDistance, this);
            }
        }
        return result;
    }

    public void stopListening() {
        this.mListener = null;
        this.mLocationManager.removeUpdates(this);
    }

    public void onLocationChanged(Location location) {
        Location arg0 = location;
        if (this.mListener != null) {
            this.mListener.onLocationChanged(arg0);
        }
    }

    public void onProviderDisabled(String str) {
        String arg0 = str;
        if (this.mListener != null) {
            this.mListener.onProviderDisabled(arg0);
        }
    }

    public void onProviderEnabled(String str) {
        String arg0 = str;
        if (this.mListener != null) {
            this.mListener.onProviderEnabled(arg0);
        }
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
        String arg0 = str;
        int arg1 = i;
        Bundle arg2 = bundle;
        if (this.mListener != null) {
            this.mListener.onStatusChanged(arg0, arg1, arg2);
        }
    }
}
