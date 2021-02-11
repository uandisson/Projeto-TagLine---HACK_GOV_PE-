package com.onesignal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.onesignal.AndroidSupportV4Compat;
import com.onesignal.OneSignal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

class LocationGMS {
    private static final long BACKGROUND_UPDATE_TIME_MS = 570000;
    private static final long FOREGROUND_UPDATE_TIME_MS = 270000;
    private static final long TIME_BACKGROUND_SEC = 600;
    private static final long TIME_FOREGROUND_SEC = 300;
    /* access modifiers changed from: private */
    public static Context classContext;
    private static Thread fallbackFailThread;
    private static boolean locationCoarse;
    private static LocationHandlerThread locationHandlerThread;
    private static ConcurrentHashMap<PermissionType, LocationHandler> locationHandlers;
    static LocationUpdateListener locationUpdateListener;
    /* access modifiers changed from: private */
    public static GoogleApiClientCompatProxy mGoogleApiClient;
    /* access modifiers changed from: private */
    public static Location mLastLocation;
    private static final List<LocationPromptCompletionHandler> promptHandlers;
    static String requestPermission;
    protected static final Object syncLock;

    interface LocationHandler {
        void complete(LocationPoint locationPoint);

        PermissionType getType();
    }

    enum PermissionType {
    }

    LocationGMS() {
    }

    static /* synthetic */ Location access$402(Location x0) {
        Location x02 = x0;
        mLastLocation = x02;
        return x02;
    }

    static class LocationPoint {
        Float accuracy;

        /* renamed from: bg */
        Boolean f304bg;
        Double lat;
        Double log;
        Long timeStamp;
        Integer type;

        LocationPoint() {
        }
    }

    static {
        Object obj;
        ConcurrentHashMap<PermissionType, LocationHandler> concurrentHashMap;
        List<LocationPromptCompletionHandler> list;
        new Object() {
        };
        syncLock = obj;
        new ConcurrentHashMap<>();
        locationHandlers = concurrentHashMap;
        new ArrayList();
        promptHandlers = list;
    }

    static abstract class LocationPromptCompletionHandler implements LocationHandler {
        LocationPromptCompletionHandler() {
        }

        /* access modifiers changed from: package-private */
        public void onAnswered(boolean accepted) {
        }
    }

    static boolean scheduleUpdate(Context context) {
        Context context2 = context;
        if (!hasLocationPermission(context2) || !OneSignal.shareLocation) {
            return false;
        }
        OneSignalSyncServiceUtils.scheduleLocationUpdateTask(context2, (1000 * (OneSignal.isForeground() ? TIME_FOREGROUND_SEC : TIME_BACKGROUND_SEC)) - (System.currentTimeMillis() - getLastLocationTime()));
        return true;
    }

    private static void setLastLocationTime(long time) {
        OneSignalPrefs.saveLong(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_LOCATION_TIME, time);
    }

    private static long getLastLocationTime() {
        return OneSignalPrefs.getLong(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_LOCATION_TIME, -600000);
    }

    private static boolean hasLocationPermission(Context context) {
        Context context2 = context;
        return AndroidSupportV4Compat.ContextCompat.checkSelfPermission(context2, "android.permission.ACCESS_FINE_LOCATION") == 0 || AndroidSupportV4Compat.ContextCompat.checkSelfPermission(context2, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    static void addPromptHandlerIfAvailable(LocationHandler locationHandler) {
        LocationHandler handler = locationHandler;
        if (handler instanceof LocationPromptCompletionHandler) {
            List<LocationPromptCompletionHandler> list = promptHandlers;
            List<LocationPromptCompletionHandler> list2 = list;
            synchronized (list) {
                try {
                    boolean add = promptHandlers.add((LocationPromptCompletionHandler) handler);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    List<LocationPromptCompletionHandler> list3 = list2;
                    throw th2;
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    static void sendAndClearPromptHandlers(boolean promptLocation, boolean z) {
        boolean accepted = z;
        if (!promptLocation) {
            OneSignal.onesignalLog(OneSignal.LOG_LEVEL.DEBUG, "LocationGMS sendAndClearPromptHandlers from non prompt flow");
            return;
        }
        List<LocationPromptCompletionHandler> list = promptHandlers;
        List<LocationPromptCompletionHandler> list2 = list;
        synchronized (list) {
            try {
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.DEBUG, "LocationGMS calling prompt handlers");
                for (LocationPromptCompletionHandler promptHandler : promptHandlers) {
                    promptHandler.onAnswered(accepted);
                }
                promptHandlers.clear();
            } catch (Throwable th) {
                Throwable th2 = th;
                List<LocationPromptCompletionHandler> list3 = list2;
                throw th2;
            }
        }
    }

    static void getLocation(Context context, boolean z, LocationHandler locationHandler) {
        Context context2 = context;
        boolean promptLocation = z;
        LocationHandler handler = locationHandler;
        addPromptHandlerIfAvailable(handler);
        classContext = context2;
        LocationHandler put = locationHandlers.put(handler.getType(), handler);
        if (!OneSignal.shareLocation) {
            sendAndClearPromptHandlers(promptLocation, false);
            fireFailedComplete();
            return;
        }
        int locationCoarsePermission = -1;
        int locationFinePermission = AndroidSupportV4Compat.ContextCompat.checkSelfPermission(context2, "android.permission.ACCESS_FINE_LOCATION");
        if (locationFinePermission == -1) {
            locationCoarsePermission = AndroidSupportV4Compat.ContextCompat.checkSelfPermission(context2, "android.permission.ACCESS_COARSE_LOCATION");
            locationCoarse = true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (locationFinePermission == 0 || locationCoarsePermission == 0) {
                sendAndClearPromptHandlers(promptLocation, true);
                startGetLocation();
                return;
            }
            sendAndClearPromptHandlers(promptLocation, false);
            handler.complete((LocationPoint) null);
        } else if (locationFinePermission != 0) {
            try {
                List<String> permissionList = Arrays.asList(context2.getPackageManager().getPackageInfo(context2.getPackageName(), 4096).requestedPermissions);
                if (permissionList.contains("android.permission.ACCESS_FINE_LOCATION")) {
                    requestPermission = "android.permission.ACCESS_FINE_LOCATION";
                } else if (!permissionList.contains("android.permission.ACCESS_COARSE_LOCATION")) {
                    OneSignal.onesignalLog(OneSignal.LOG_LEVEL.INFO, "Location permissions not added on AndroidManifest file");
                } else if (locationCoarsePermission != 0) {
                    requestPermission = "android.permission.ACCESS_COARSE_LOCATION";
                }
                if (requestPermission != null && promptLocation) {
                    PermissionsActivity.startPrompt();
                } else if (locationCoarsePermission == 0) {
                    sendAndClearPromptHandlers(promptLocation, true);
                    startGetLocation();
                } else {
                    sendAndClearPromptHandlers(promptLocation, false);
                    fireFailedComplete();
                }
            } catch (PackageManager.NameNotFoundException e) {
                sendAndClearPromptHandlers(promptLocation, false);
                e.printStackTrace();
            }
        } else {
            sendAndClearPromptHandlers(promptLocation, true);
            startGetLocation();
        }
    }

    /* JADX WARNING: type inference failed for: r5v6, types: [com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void startGetLocation() {
        /*
            java.lang.Thread r4 = fallbackFailThread
            if (r4 == 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            java.lang.Object r4 = syncLock     // Catch:{ Throwable -> 0x0079 }
            r7 = r4
            r4 = r7
            r5 = r7
            r0 = r5
            monitor-enter(r4)     // Catch:{ Throwable -> 0x0079 }
            startFallBackThread()     // Catch:{ all -> 0x0073 }
            com.onesignal.LocationGMS$LocationHandlerThread r4 = locationHandlerThread     // Catch:{ all -> 0x0073 }
            if (r4 != 0) goto L_0x001d
            com.onesignal.LocationGMS$LocationHandlerThread r4 = new com.onesignal.LocationGMS$LocationHandlerThread     // Catch:{ all -> 0x0073 }
            r7 = r4
            r4 = r7
            r5 = r7
            r5.<init>()     // Catch:{ all -> 0x0073 }
            locationHandlerThread = r4     // Catch:{ all -> 0x0073 }
        L_0x001d:
            com.onesignal.GoogleApiClientCompatProxy r4 = mGoogleApiClient     // Catch:{ all -> 0x0073 }
            if (r4 == 0) goto L_0x0025
            android.location.Location r4 = mLastLocation     // Catch:{ all -> 0x0073 }
            if (r4 != 0) goto L_0x0069
        L_0x0025:
            com.onesignal.LocationGMS$GoogleApiClientListener r4 = new com.onesignal.LocationGMS$GoogleApiClientListener     // Catch:{ all -> 0x0073 }
            r7 = r4
            r4 = r7
            r5 = r7
            r6 = 0
            r5.<init>(r6)     // Catch:{ all -> 0x0073 }
            r1 = r4
            com.google.android.gms.common.api.GoogleApiClient$Builder r4 = new com.google.android.gms.common.api.GoogleApiClient$Builder     // Catch:{ all -> 0x0073 }
            r7 = r4
            r4 = r7
            r5 = r7
            android.content.Context r6 = classContext     // Catch:{ all -> 0x0073 }
            r5.<init>(r6)     // Catch:{ all -> 0x0073 }
            com.google.android.gms.common.api.Api<com.google.android.gms.common.api.Api$ApiOptions$NoOptions> r5 = com.google.android.gms.location.LocationServices.API     // Catch:{ all -> 0x0073 }
            com.google.android.gms.common.api.GoogleApiClient$Builder r4 = r4.addApi(r5)     // Catch:{ all -> 0x0073 }
            r5 = r1
            com.google.android.gms.common.api.GoogleApiClient$Builder r4 = r4.addConnectionCallbacks(r5)     // Catch:{ all -> 0x0073 }
            r5 = r1
            com.google.android.gms.common.api.GoogleApiClient$Builder r4 = r4.addOnConnectionFailedListener(r5)     // Catch:{ all -> 0x0073 }
            com.onesignal.LocationGMS$LocationHandlerThread r5 = locationHandlerThread     // Catch:{ all -> 0x0073 }
            android.os.Handler r5 = r5.mHandler     // Catch:{ all -> 0x0073 }
            com.google.android.gms.common.api.GoogleApiClient$Builder r4 = r4.setHandler(r5)     // Catch:{ all -> 0x0073 }
            com.google.android.gms.common.api.GoogleApiClient r4 = r4.build()     // Catch:{ all -> 0x0073 }
            r2 = r4
            com.onesignal.GoogleApiClientCompatProxy r4 = new com.onesignal.GoogleApiClientCompatProxy     // Catch:{ all -> 0x0073 }
            r7 = r4
            r4 = r7
            r5 = r7
            r6 = r2
            r5.<init>(r6)     // Catch:{ all -> 0x0073 }
            mGoogleApiClient = r4     // Catch:{ all -> 0x0073 }
            com.onesignal.GoogleApiClientCompatProxy r4 = mGoogleApiClient     // Catch:{ all -> 0x0073 }
            r4.connect()     // Catch:{ all -> 0x0073 }
        L_0x0066:
            r4 = r0
            monitor-exit(r4)     // Catch:{ all -> 0x0073 }
        L_0x0068:
            goto L_0x0004
        L_0x0069:
            android.location.Location r4 = mLastLocation     // Catch:{ all -> 0x0073 }
            if (r4 == 0) goto L_0x0066
            android.location.Location r4 = mLastLocation     // Catch:{ all -> 0x0073 }
            fireCompleteForLocation(r4)     // Catch:{ all -> 0x0073 }
            goto L_0x0066
        L_0x0073:
            r4 = move-exception
            r3 = r4
            r4 = r0
            monitor-exit(r4)     // Catch:{ all -> 0x0073 }
            r4 = r3
            throw r4     // Catch:{ Throwable -> 0x0079 }
        L_0x0079:
            r4 = move-exception
            r0 = r4
            com.onesignal.OneSignal$LOG_LEVEL r4 = com.onesignal.OneSignal.LOG_LEVEL.WARN
            java.lang.String r5 = "Location permission exists but there was an error initializing: "
            r6 = r0
            com.onesignal.OneSignal.Log(r4, r5, r6)
            fireFailedComplete()
            goto L_0x0068
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.LocationGMS.startGetLocation():void");
    }

    /* access modifiers changed from: private */
    public static int getApiFallbackWait() {
        return 30000;
    }

    private static void startFallBackThread() {
        Thread thread;
        Runnable runnable;
        new Runnable() {
            public void run() {
                try {
                    Thread.sleep((long) LocationGMS.getApiFallbackWait());
                    OneSignal.Log(OneSignal.LOG_LEVEL.WARN, "Location permission exists but GoogleApiClient timed out. Maybe related to mismatch google-play aar versions.");
                    LocationGMS.fireFailedComplete();
                    boolean scheduleUpdate = LocationGMS.scheduleUpdate(LocationGMS.classContext);
                } catch (InterruptedException e) {
                    InterruptedException interruptedException = e;
                }
            }
        };
        new Thread(runnable, "OS_GMS_LOCATION_FALLBACK");
        fallbackFailThread = thread;
        fallbackFailThread.start();
    }

    static void fireFailedComplete() {
        PermissionsActivity.answered = false;
        Object obj = syncLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (mGoogleApiClient != null) {
                    mGoogleApiClient.disconnect();
                }
                mGoogleApiClient = null;
                fireComplete((LocationPoint) null);
            } catch (Throwable th) {
                while (true) {
                    Throwable th2 = th;
                    Object obj3 = obj2;
                    throw th2;
                }
            }
        }
    }

    /* JADX INFO: finally extract failed */
    private static void fireComplete(LocationPoint locationPoint) {
        HashMap hashMap;
        LocationPoint point = locationPoint;
        new HashMap();
        HashMap hashMap2 = hashMap;
        Class<LocationGMS> cls = LocationGMS.class;
        Class<LocationGMS> cls2 = cls;
        synchronized (cls) {
            try {
                hashMap2.putAll(locationHandlers);
                locationHandlers.clear();
                Thread _fallbackFailThread = fallbackFailThread;
                for (PermissionType type : hashMap2.keySet()) {
                    ((LocationHandler) hashMap2.get(type)).complete(point);
                }
                if (_fallbackFailThread != null && !Thread.currentThread().equals(_fallbackFailThread)) {
                    _fallbackFailThread.interrupt();
                }
                if (_fallbackFailThread == fallbackFailThread) {
                    Class<LocationGMS> cls3 = LocationGMS.class;
                    Class<LocationGMS> cls4 = cls3;
                    synchronized (cls3) {
                        try {
                            if (_fallbackFailThread == fallbackFailThread) {
                                fallbackFailThread = null;
                            }
                        } catch (Throwable th) {
                            while (true) {
                                Throwable th2 = th;
                                Class<LocationGMS> cls5 = cls4;
                                throw th2;
                            }
                        }
                    }
                }
                setLastLocationTime(System.currentTimeMillis());
            } catch (Throwable th3) {
                while (true) {
                    Throwable th4 = th3;
                    Class<LocationGMS> cls6 = cls2;
                    throw th4;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void fireCompleteForLocation(Location location) {
        LocationPoint locationPoint;
        BigDecimal bigDecimal;
        BigDecimal bigDecimal2;
        Location location2 = location;
        new LocationPoint();
        LocationPoint point = locationPoint;
        point.accuracy = Float.valueOf(location2.getAccuracy());
        point.f304bg = Boolean.valueOf(!OneSignal.isForeground());
        point.type = Integer.valueOf(locationCoarse ? 0 : 1);
        point.timeStamp = Long.valueOf(location2.getTime());
        if (locationCoarse) {
            new BigDecimal(location2.getLatitude());
            point.lat = Double.valueOf(bigDecimal.setScale(7, RoundingMode.HALF_UP).doubleValue());
            new BigDecimal(location2.getLongitude());
            point.log = Double.valueOf(bigDecimal2.setScale(7, RoundingMode.HALF_UP).doubleValue());
        } else {
            point.lat = Double.valueOf(location2.getLatitude());
            point.log = Double.valueOf(location2.getLongitude());
        }
        fireComplete(point);
        boolean scheduleUpdate = scheduleUpdate(classContext);
    }

    static void onFocusChange() {
        LocationUpdateListener locationUpdateListener2;
        Object obj = syncLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (mGoogleApiClient == null || !mGoogleApiClient.realInstance().isConnected()) {
                    return;
                }
                GoogleApiClient googleApiClient = mGoogleApiClient.realInstance();
                if (locationUpdateListener != null) {
                    PendingResult<Status> removeLocationUpdates = LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (LocationListener) locationUpdateListener);
                }
                new LocationUpdateListener(googleApiClient);
                locationUpdateListener = locationUpdateListener2;
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    private static class GoogleApiClientListener implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        private GoogleApiClientListener() {
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ GoogleApiClientListener(C14131 r4) {
            this();
            C14131 r1 = r4;
        }

        public void onConnected(Bundle bundle) {
            LocationUpdateListener locationUpdateListener;
            Bundle bundle2 = bundle;
            Object obj = LocationGMS.syncLock;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    PermissionsActivity.answered = false;
                    if (LocationGMS.mGoogleApiClient == null || LocationGMS.mGoogleApiClient.realInstance() == null) {
                        return;
                    }
                    if (LocationGMS.mLastLocation == null) {
                        Location access$402 = LocationGMS.access$402(FusedLocationApiWrapper.getLastLocation(LocationGMS.mGoogleApiClient.realInstance()));
                        if (LocationGMS.mLastLocation != null) {
                            LocationGMS.fireCompleteForLocation(LocationGMS.mLastLocation);
                        }
                    }
                    new LocationUpdateListener(LocationGMS.mGoogleApiClient.realInstance());
                    LocationGMS.locationUpdateListener = locationUpdateListener;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    Object obj3 = obj2;
                    throw th2;
                }
            }
        }

        public void onConnectionSuspended(int i) {
            LocationGMS.fireFailedComplete();
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            LocationGMS.fireFailedComplete();
        }
    }

    static class LocationUpdateListener implements LocationListener {
        private GoogleApiClient mGoogleApiClient;

        LocationUpdateListener(GoogleApiClient googleApiClient) {
            this.mGoogleApiClient = googleApiClient;
            long updateInterval = OneSignal.isForeground() ? 270000 : 570000;
            FusedLocationApiWrapper.requestLocationUpdates(this.mGoogleApiClient, LocationRequest.create().setFastestInterval(updateInterval).setInterval(updateInterval).setMaxWaitTime((long) (((double) updateInterval) * 1.5d)).setPriority(102), this);
        }

        public void onLocationChanged(Location location) {
            Location access$402 = LocationGMS.access$402(location);
            OneSignal.Log(OneSignal.LOG_LEVEL.INFO, "Location Change Detected");
        }
    }

    static class FusedLocationApiWrapper {
        FusedLocationApiWrapper() {
        }

        static void requestLocationUpdates(GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
            Object obj;
            GoogleApiClient googleApiClient2 = googleApiClient;
            LocationRequest locationRequest2 = locationRequest;
            LocationListener locationListener2 = locationListener;
            try {
                Object obj2 = LocationGMS.syncLock;
                obj = obj2;
                synchronized (obj2) {
                    if (googleApiClient2.isConnected()) {
                        PendingResult<Status> requestLocationUpdates = LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient2, locationRequest2, locationListener2);
                    }
                }
            } catch (Throwable th) {
                OneSignal.Log(OneSignal.LOG_LEVEL.WARN, "FusedLocationApi.requestLocationUpdates failed!", th);
            }
        }

        static Location getLastLocation(GoogleApiClient googleApiClient) {
            GoogleApiClient googleApiClient2 = googleApiClient;
            Object obj = LocationGMS.syncLock;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    if (googleApiClient2.isConnected()) {
                        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient2);
                        return lastLocation;
                    }
                    return null;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    Object obj3 = obj2;
                    throw th2;
                }
            }
        }
    }

    private static class LocationHandlerThread extends HandlerThread {
        Handler mHandler;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        LocationHandlerThread() {
            super("OSH_LocationHandlerThread");
            Handler handler;
            start();
            new Handler(getLooper());
            this.mHandler = handler;
        }
    }
}
