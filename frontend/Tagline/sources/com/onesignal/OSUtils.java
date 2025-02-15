package com.onesignal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.app.JobIntentService;
import android.support.p000v4.app.NotificationManagerCompat;
import android.support.p000v4.content.WakefulBroadcastReceiver;
import android.telephony.TelephonyManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.firebase.messaging.FirebaseMessaging;
import com.microsoft.appcenter.ingestion.models.CommonProperties;
import com.onesignal.OneSignal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class OSUtils {
    public static int MAX_NETWORK_REQUEST_ATTEMPT_COUNT = 3;
    static final int[] NO_RETRY_NETWROK_REQUEST_STATUS_CODES = {ErrorMessages.ERROR_NXT_BLUETOOTH_NOT_SET, ErrorMessages.ERROR_NXT_NOT_CONNECTED_TO_ROBOT, ErrorMessages.ERROR_NXT_INVALID_RETURN_PACKAGE, 404, ErrorMessages.ERROR_NXT_MESSAGE_TOO_LONG};
    static final int UNINITIALIZABLE_STATUS = -999;

    OSUtils() {
    }

    public enum SchemaType {
        ;
        
        private final String text;

        private SchemaType(String text2) {
            String str = r8;
            int i = r9;
            this.text = text2;
        }

        public static SchemaType fromString(String str) {
            String text2 = str;
            SchemaType[] values = values();
            int length = values.length;
            for (int i = 0; i < length; i++) {
                SchemaType type = values[i];
                if (type.text.equalsIgnoreCase(text2)) {
                    return type;
                }
            }
            return null;
        }
    }

    public static boolean shouldRetryNetworkRequest(int i) {
        int statusCode = i;
        int[] iArr = NO_RETRY_NETWROK_REQUEST_STATUS_CODES;
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (statusCode == iArr[i2]) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public int initializationChecker(Context context, String str) {
        Integer pushErrorType;
        Context context2 = context;
        String oneSignalAppId = str;
        int subscribableStatus = 1;
        int deviceType = getDeviceType();
        try {
            UUID fromString = UUID.fromString(oneSignalAppId);
            if ("b2f7f966-d8cc-11e4-bed1-df8f05be55ba".equals(oneSignalAppId) || "5eb5a37e-b458-11e3-ac11-000c2940e62c".equals(oneSignalAppId)) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "OneSignal Example AppID detected, please update to your app's id found on OneSignal.com");
            }
            if (deviceType == 1 && (pushErrorType = checkForGooglePushLibrary()) != null) {
                subscribableStatus = pushErrorType.intValue();
            }
            Integer supportErrorType = checkAndroidSupportLibrary(context2);
            if (supportErrorType != null) {
                subscribableStatus = supportErrorType.intValue();
            }
            return subscribableStatus;
        } catch (Throwable th) {
            OneSignal.Log(OneSignal.LOG_LEVEL.FATAL, "OneSignal AppId format is invalid.\nExample: 'b2f7f966-d8cc-11e4-bed1-df8f05be55ba'\n", th);
            return UNINITIALIZABLE_STATUS;
        }
    }

    static boolean hasFCMLibrary() {
        return FirebaseMessaging.class != null;
    }

    private static boolean hasGCMLibrary() {
        return GoogleCloudMessaging.class != null;
    }

    /* access modifiers changed from: package-private */
    public Integer checkForGooglePushLibrary() {
        boolean hasFCMLibrary = hasFCMLibrary();
        boolean hasGCMLibrary = hasGCMLibrary();
        if (hasFCMLibrary || hasGCMLibrary) {
            if (hasGCMLibrary && !hasFCMLibrary) {
                OneSignal.Log(OneSignal.LOG_LEVEL.WARN, "GCM Library detected, please upgrade to Firebase FCM library as GCM is deprecated!");
            }
            if (hasGCMLibrary && hasFCMLibrary) {
                OneSignal.Log(OneSignal.LOG_LEVEL.WARN, "Both GCM & FCM Libraries detected! Please remove the deprecated GCM library.");
            }
            return null;
        }
        OneSignal.Log(OneSignal.LOG_LEVEL.FATAL, "The Firebase FCM library is missing! Please make sure to include it in your project.");
        return -4;
    }

    private static boolean hasWakefulBroadcastReceiver() {
        return WakefulBroadcastReceiver.class != null;
    }

    private static boolean hasNotificationManagerCompat() {
        return NotificationManagerCompat.class != null;
    }

    private static boolean hasJobIntentService() {
        return JobIntentService.class != null;
    }

    private Integer checkAndroidSupportLibrary(Context context) {
        Context context2 = context;
        boolean hasWakefulBroadcastReceiver = hasWakefulBroadcastReceiver();
        boolean hasNotificationManagerCompat = hasNotificationManagerCompat();
        if (!hasWakefulBroadcastReceiver && !hasNotificationManagerCompat) {
            OneSignal.Log(OneSignal.LOG_LEVEL.FATAL, "Could not find the Android Support Library. Please make sure it has been correctly added to your project.");
            return -3;
        } else if (!hasWakefulBroadcastReceiver || !hasNotificationManagerCompat) {
            OneSignal.Log(OneSignal.LOG_LEVEL.FATAL, "The included Android Support Library is to old or incomplete. Please update to the 26.0.0 revision or newer.");
            return -5;
        } else if (Build.VERSION.SDK_INT < 26 || getTargetSdkVersion(context2) < 26 || hasJobIntentService()) {
            return null;
        } else {
            OneSignal.Log(OneSignal.LOG_LEVEL.FATAL, "The included Android Support Library is to old or incomplete. Please update to the 26.0.0 revision or newer.");
            return -5;
        }
    }

    /* access modifiers changed from: package-private */
    public int getDeviceType() {
        try {
            Class<?> cls = Class.forName("com.amazon.device.messaging.ADM");
            return 2;
        } catch (ClassNotFoundException e) {
            ClassNotFoundException classNotFoundException = e;
            return 1;
        }
    }

    /* access modifiers changed from: package-private */
    public Integer getNetType() {
        NetworkInfo netInfo = ((ConnectivityManager) OneSignal.appContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (netInfo == null) {
            return null;
        }
        int networkType = netInfo.getType();
        if (networkType == 1 || networkType == 9) {
            return null;
        }
        return 1;
    }

    /* access modifiers changed from: package-private */
    public String getCarrierName() {
        try {
            String carrierName = ((TelephonyManager) OneSignal.appContext.getSystemService("phone")).getNetworkOperatorName();
            return "".equals(carrierName) ? null : carrierName;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    static String getManifestMeta(Context context, String metaName) {
        Context context2 = context;
        try {
            return context2.getPackageManager().getApplicationInfo(context2.getPackageName(), 128).metaData.getString(metaName);
        } catch (Throwable th) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "", th);
            return null;
        }
    }

    static String getResourceString(Context context, String key, String str) {
        Context context2 = context;
        String defaultStr = str;
        Resources resources = context2.getResources();
        int bodyResId = resources.getIdentifier(key, "string", context2.getPackageName());
        if (bodyResId != 0) {
            return resources.getString(bodyResId);
        }
        return defaultStr;
    }

    static String getCorrectedLanguage() {
        StringBuilder sb;
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("iw")) {
            return "he";
        }
        if (lang.equals("in")) {
            return CommonProperties.f295ID;
        }
        if (lang.equals("ji")) {
            return "yi";
        }
        if (!lang.equals("zh")) {
            return lang;
        }
        new StringBuilder();
        return sb.append(lang).append("-").append(Locale.getDefault().getCountry()).toString();
    }

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.inline(CodeShrinkVisitor.java:146)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:71)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    static boolean isValidEmail(java.lang.String r5) {
        /*
            r0 = r5
            r3 = r0
            if (r3 != 0) goto L_0x0007
            r3 = 0
            r0 = r3
        L_0x0006:
            return r0
        L_0x0007:
            java.lang.String r3 = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
            r1 = r3
            r3 = r1
            java.util.regex.Pattern r3 = java.util.regex.Pattern.compile(r3)
            r2 = r3
            r3 = r2
            r4 = r0
            java.util.regex.Matcher r3 = r3.matcher(r4)
            boolean r3 = r3.matches()
            r0 = r3
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OSUtils.isValidEmail(java.lang.String):boolean");
    }

    static boolean areNotificationsEnabled(Context context) {
        Context context2 = context;
        try {
            return NotificationManagerCompat.from(OneSignal.appContext).areNotificationsEnabled();
        } catch (Throwable th) {
            Throwable th2 = th;
            return true;
        }
    }

    static void runOnMainUIThread(Runnable runnable) {
        Handler handler;
        Runnable runnable2 = runnable;
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable2.run();
            return;
        }
        new Handler(Looper.getMainLooper());
        boolean post = handler.post(runnable2);
    }

    static void runOnMainThreadDelayed(Runnable runnable, int delay) {
        Handler handler;
        new Handler(Looper.getMainLooper());
        boolean postDelayed = handler.postDelayed(runnable, (long) delay);
    }

    static int getTargetSdkVersion(Context context) {
        Context context2 = context;
        try {
            return context2.getPackageManager().getApplicationInfo(context2.getPackageName(), 0).targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 15;
        }
    }

    static boolean isValidResourceName(String str) {
        String name = str;
        return name != null && !name.matches("^[0-9]");
    }

    static Uri getSoundUri(Context context, String str) {
        StringBuilder sb;
        int soundId;
        StringBuilder sb2;
        Context context2 = context;
        String sound = str;
        Resources resources = context2.getResources();
        String packageName = context2.getPackageName();
        if (!isValidResourceName(sound) || (soundId = resources.getIdentifier(sound, "raw", packageName)) == 0) {
            int soundId2 = resources.getIdentifier("onesignal_default_sound", "raw", packageName);
            if (soundId2 == 0) {
                return null;
            }
            new StringBuilder();
            return Uri.parse(sb.append("android.resource://").append(packageName).append("/").append(soundId2).toString());
        }
        new StringBuilder();
        return Uri.parse(sb2.append("android.resource://").append(packageName).append("/").append(soundId).toString());
    }

    static long[] parseVibrationPattern(JSONObject gcmBundle) {
        JSONArray jsonVibArray;
        JSONArray jSONArray;
        try {
            Object patternObj = gcmBundle.opt("vib_pt");
            if (patternObj instanceof String) {
                new JSONArray((String) patternObj);
                jsonVibArray = jSONArray;
            } else {
                jsonVibArray = (JSONArray) patternObj;
            }
            long[] longArray = new long[jsonVibArray.length()];
            for (int i = 0; i < jsonVibArray.length(); i++) {
                longArray[i] = jsonVibArray.optLong(i);
            }
            return longArray;
        } catch (JSONException e) {
            JSONException jSONException = e;
            return null;
        }
    }

    static String hexDigest(String str, String digestInstance) throws Throwable {
        StringBuilder sb;
        String h;
        StringBuilder sb2;
        MessageDigest digest = MessageDigest.getInstance(digestInstance);
        digest.update(str.getBytes("UTF-8"));
        byte[] messageDigest = digest.digest();
        new StringBuilder();
        StringBuilder hexString = sb;
        byte[] bArr = messageDigest;
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            String hexString2 = Integer.toHexString(255 & bArr[i]);
            while (true) {
                h = hexString2;
                if (h.length() >= 2) {
                    break;
                }
                new StringBuilder();
                hexString2 = sb2.append("0").append(h).toString();
            }
            StringBuilder append = hexString.append(h);
        }
        return hexString.toString();
    }

    static void sleep(int ms) {
        try {
            Thread.sleep((long) ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void openURLInBrowser(@NonNull String url) {
        openURLInBrowser(Uri.parse(url.trim()));
    }

    private static void openURLInBrowser(@NonNull Uri uri) {
        Intent intent;
        Intent intent2;
        StringBuilder sb;
        Uri uri2 = uri;
        SchemaType type = uri2.getScheme() != null ? SchemaType.fromString(uri2.getScheme()) : null;
        if (type == null) {
            type = SchemaType.HTTP;
            if (!uri2.toString().contains("://")) {
                new StringBuilder();
                uri2 = Uri.parse(sb.append("http://").append(uri2.toString()).toString());
            }
        }
        switch (type) {
            case DATA:
                intent = Intent.makeMainSelectorActivity("android.intent.action.MAIN", "android.intent.category.APP_BROWSER");
                Intent data = intent.setData(uri2);
                break;
            default:
                new Intent("android.intent.action.VIEW", uri2);
                intent = intent2;
                break;
        }
        Intent addFlags = intent.addFlags(1476919296);
        OneSignal.appContext.startActivity(intent);
    }

    static <T> Set<T> newConcurrentSet() {
        Map map;
        new ConcurrentHashMap();
        return Collections.newSetFromMap(map);
    }

    static boolean hasConfigChangeFlag(Activity activity, int configChangeFlag) {
        Activity activity2 = activity;
        boolean hasFlag = false;
        try {
            hasFlag = (activity2.getPackageManager().getActivityInfo(activity2.getComponentName(), 0).configChanges & configChangeFlag) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return hasFlag;
    }

    @NonNull
    static Collection<String> extractStringsFromCollection(@Nullable Collection<Object> collection) {
        Collection<Object> collection2;
        Collection<Object> collection3 = collection;
        new ArrayList<>();
        Collection<Object> result = collection2;
        if (collection3 == null) {
            return result;
        }
        for (Object value : collection3) {
            if (value instanceof String) {
                boolean add = result.add((String) value);
            }
        }
        return result;
    }

    static boolean shouldLogMissingAppIdError(@Nullable String appId) {
        if (appId != null) {
            return false;
        }
        OneSignal.Log(OneSignal.LOG_LEVEL.INFO, "OneSignal was not initialized, ensure to always initialize OneSignal from the onCreate of your Application class.");
        return true;
    }
}
