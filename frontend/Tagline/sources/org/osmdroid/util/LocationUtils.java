package org.osmdroid.util;

import android.location.Location;
import android.location.LocationManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.constants.UtilConstants;

public class LocationUtils implements UtilConstants {
    private LocationUtils() {
    }

    public static Location getLastKnownLocation(LocationManager locationManager) {
        LocationManager pLocationManager = locationManager;
        if (pLocationManager == null) {
            return null;
        }
        Location gpsLocation = getLastKnownLocation(pLocationManager, "gps");
        Location networkLocation = getLastKnownLocation(pLocationManager, "network");
        if (gpsLocation == null) {
            return networkLocation;
        }
        if (networkLocation == null) {
            return gpsLocation;
        }
        if (networkLocation.getTime() > gpsLocation.getTime() + Configuration.getInstance().getGpsWaitTime()) {
            return networkLocation;
        }
        return gpsLocation;
    }

    private static Location getLastKnownLocation(LocationManager locationManager, String str) {
        LocationManager pLocationManager = locationManager;
        String pProvider = str;
        try {
            if (!pLocationManager.isProviderEnabled(pProvider)) {
                return null;
            }
            return pLocationManager.getLastKnownLocation(pProvider);
        } catch (IllegalArgumentException e) {
            IllegalArgumentException illegalArgumentException = e;
            return null;
        }
    }
}
