package org.osmdroid.tileprovider.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkAvailabliltyCheck implements INetworkAvailablityCheck {
    private final ConnectivityManager mConnectionManager;
    private final boolean mHasNetworkStatePermission;
    private final boolean mIsX86 = "Android-x86".equalsIgnoreCase(Build.BRAND);

    public NetworkAvailabliltyCheck(Context context) {
        Context aContext = context;
        this.mConnectionManager = (ConnectivityManager) aContext.getSystemService("connectivity");
        this.mHasNetworkStatePermission = aContext.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", aContext.getPackageName()) == 0;
    }

    public boolean getNetworkAvailable() {
        if (!this.mHasNetworkStatePermission) {
            return true;
        }
        NetworkInfo networkInfo = this.mConnectionManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        if (networkInfo.isAvailable()) {
            return true;
        }
        return this.mIsX86 && networkInfo.getType() == 9;
    }

    public boolean getWiFiNetworkAvailable() {
        if (!this.mHasNetworkStatePermission) {
            return true;
        }
        NetworkInfo wifi = this.mConnectionManager.getNetworkInfo(1);
        return wifi != null && wifi.isAvailable();
    }

    public boolean getCellularDataNetworkAvailable() {
        if (!this.mHasNetworkStatePermission) {
            return true;
        }
        NetworkInfo mobile = this.mConnectionManager.getNetworkInfo(0);
        return mobile != null && mobile.isAvailable();
    }

    public boolean getRouteToPathExists(int i) {
        int hostAddress = i;
        return this.mConnectionManager.requestRouteToHost(1, hostAddress) || this.mConnectionManager.requestRouteToHost(0, hostAddress);
    }
}
