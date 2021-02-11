package org.osmdroid.tileprovider.util;

import android.util.Log;

public class Counters {
    static final String TAG = "osmCounters";
    public static int countOOM = 0;
    public static int fileCacheHit = 0;
    public static int fileCacheMiss = 0;
    public static int fileCacheOOM = 0;
    public static int fileCacheSaveErrors = 0;
    public static int tileDownloadErrors = 0;

    public Counters() {
    }

    public static void printToLogcat() {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        StringBuilder sb5;
        StringBuilder sb6;
        new StringBuilder();
        int d = Log.d(TAG, sb.append("countOOM ").append(countOOM).toString());
        new StringBuilder();
        int d2 = Log.d(TAG, sb2.append("tileDownloadErrors ").append(tileDownloadErrors).toString());
        new StringBuilder();
        int d3 = Log.d(TAG, sb3.append("fileCacheSaveErrors ").append(fileCacheSaveErrors).toString());
        new StringBuilder();
        int d4 = Log.d(TAG, sb4.append("fileCacheMiss ").append(fileCacheMiss).toString());
        new StringBuilder();
        int d5 = Log.d(TAG, sb5.append("fileCacheOOM ").append(fileCacheOOM).toString());
        new StringBuilder();
        int d6 = Log.d(TAG, sb6.append("fileCacheHit ").append(fileCacheHit).toString());
    }

    public static void reset() {
        countOOM = 0;
        tileDownloadErrors = 0;
        fileCacheSaveErrors = 0;
        fileCacheMiss = 0;
        fileCacheOOM = 0;
        fileCacheHit = 0;
    }
}
