package org.osmdroid.util;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.constants.UtilConstants;

public class NetworkLocationIgnorer implements UtilConstants {
    private long mLastGps = 0;

    public NetworkLocationIgnorer() {
    }

    public boolean shouldIgnore(String pProvider, long j) {
        long pTime = j;
        if ("gps".equals(pProvider)) {
            this.mLastGps = pTime;
        } else if (pTime < this.mLastGps + Configuration.getInstance().getGpsWaitTime()) {
            return true;
        }
        return false;
    }
}
