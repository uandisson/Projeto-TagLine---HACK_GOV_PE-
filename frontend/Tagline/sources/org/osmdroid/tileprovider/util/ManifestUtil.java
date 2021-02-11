package org.osmdroid.tileprovider.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import org.osmdroid.api.IMapView;

public class ManifestUtil {
    public ManifestUtil() {
    }

    public static String retrieveKey(Context context, String str) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        Context aContext = context;
        String aKey = str;
        try {
            ApplicationInfo info = aContext.getPackageManager().getApplicationInfo(aContext.getPackageName(), 128);
            if (info.metaData == null) {
                new StringBuilder();
                int i = Log.i(IMapView.LOGTAG, sb3.append("Key %s not found in manifest").append(aKey).toString());
            } else {
                String value = info.metaData.getString(aKey);
                if (value != null) {
                    return value.trim();
                }
                new StringBuilder();
                int i2 = Log.i(IMapView.LOGTAG, sb2.append("Key %s not found in manifest").append(aKey).toString());
            }
        } catch (PackageManager.NameNotFoundException e) {
            PackageManager.NameNotFoundException nameNotFoundException = e;
            new StringBuilder();
            int i3 = Log.i(IMapView.LOGTAG, sb.append("Key %s not found in manifest").append(aKey).toString());
        }
        return "";
    }
}
