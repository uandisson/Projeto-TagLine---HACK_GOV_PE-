package org.osmdroid.views.overlay.mylocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import java.util.HashSet;
import java.util.Set;
import org.osmdroid.api.IMapView;
import org.osmdroid.util.NetworkLocationIgnorer;

public class GpsMyLocationProvider implements IMyLocationProvider, LocationListener {
    private final Set<String> locationSources;
    private NetworkLocationIgnorer mIgnorer;
    private Location mLocation;
    private LocationManager mLocationManager;
    private float mLocationUpdateMinDistance = 0.0f;
    private long mLocationUpdateMinTime = 0;
    private IMyLocationConsumer mMyLocationConsumer;

    public GpsMyLocationProvider(Context context) {
        NetworkLocationIgnorer networkLocationIgnorer;
        Set<String> set;
        new NetworkLocationIgnorer();
        this.mIgnorer = networkLocationIgnorer;
        new HashSet();
        this.locationSources = set;
        this.mLocationManager = (LocationManager) context.getSystemService("location");
        boolean add = this.locationSources.add("gps");
    }

    public void clearLocationSources() {
        this.locationSources.clear();
    }

    public void addLocationSource(String source) {
        boolean add = this.locationSources.add(source);
    }

    public Set<String> getLocationSources() {
        return this.locationSources;
    }

    public long getLocationUpdateMinTime() {
        return this.mLocationUpdateMinTime;
    }

    public void setLocationUpdateMinTime(long milliSeconds) {
        long j = milliSeconds;
        this.mLocationUpdateMinTime = j;
    }

    public float getLocationUpdateMinDistance() {
        return this.mLocationUpdateMinDistance;
    }

    public void setLocationUpdateMinDistance(float meters) {
        float f = meters;
        this.mLocationUpdateMinDistance = f;
    }

    public boolean startLocationProvider(IMyLocationConsumer myLocationConsumer) {
        this.mMyLocationConsumer = myLocationConsumer;
        boolean result = false;
        for (String provider : this.mLocationManager.getProviders(true)) {
            if (this.locationSources.contains(provider)) {
                result = true;
                this.mLocationManager.requestLocationUpdates(provider, this.mLocationUpdateMinTime, this.mLocationUpdateMinDistance, this);
            }
        }
        return result;
    }

    public void stopLocationProvider() {
        this.mMyLocationConsumer = null;
        if (this.mLocationManager != null) {
            this.mLocationManager.removeUpdates(this);
        }
    }

    public Location getLastKnownLocation() {
        return this.mLocation;
    }

    public void destroy() {
        stopLocationProvider();
        this.mLocation = null;
        this.mLocationManager = null;
        this.mMyLocationConsumer = null;
        this.mIgnorer = null;
    }

    public void onLocationChanged(Location location) {
        Location location2 = location;
        if (this.mIgnorer == null) {
            int w = Log.w(IMapView.LOGTAG, "GpsMyLocation proivider, mIgnore is null, unexpected. Location update will be ignored");
        } else if (location2 != null && location2.getProvider() != null && !this.mIgnorer.shouldIgnore(location2.getProvider(), System.currentTimeMillis())) {
            this.mLocation = location2;
            if (this.mMyLocationConsumer != null && this.mLocation != null) {
                this.mMyLocationConsumer.onLocationChanged(this.mLocation, this);
            }
        }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
