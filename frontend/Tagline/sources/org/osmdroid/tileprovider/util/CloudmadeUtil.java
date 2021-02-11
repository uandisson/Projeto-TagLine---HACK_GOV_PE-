package org.osmdroid.tileprovider.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import com.microsoft.appcenter.http.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;

public class CloudmadeUtil {
    private static final String CLOUDMADE_ID = "CLOUDMADE_ID";
    private static final String CLOUDMADE_KEY = "CLOUDMADE_KEY";
    private static final String CLOUDMADE_TOKEN = "CLOUDMADE_TOKEN";
    public static boolean DEBUGMODE = false;
    private static String mAndroidId = "android_id";
    private static String mKey = "";
    private static SharedPreferences.Editor mPreferenceEditor;
    private static String mToken = "";

    public CloudmadeUtil() {
    }

    public static void retrieveCloudmadeKey(Context context) {
        Context aContext = context;
        mAndroidId = Settings.Secure.getString(aContext.getContentResolver(), "android_id");
        mKey = ManifestUtil.retrieveKey(aContext, CLOUDMADE_KEY);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(aContext);
        mPreferenceEditor = pref.edit();
        if (pref.getString(CLOUDMADE_ID, "").equals(mAndroidId)) {
            mToken = pref.getString(CLOUDMADE_TOKEN, "");
            if (mToken.length() > 0) {
                mPreferenceEditor = null;
                return;
            }
            return;
        }
        SharedPreferences.Editor putString = mPreferenceEditor.putString(CLOUDMADE_ID, mAndroidId);
        boolean commit = mPreferenceEditor.commit();
    }

    public static String getCloudmadeKey() {
        return mKey;
    }

    public static void setCloudmadeKey(String key) {
        mKey = key;
    }

    /* JADX INFO: finally extract failed */
    public static String getCloudmadeToken() {
        StringBuilder sb;
        StringBuilder sb2;
        URL urlToRequest;
        BufferedReader br;
        Reader reader;
        StringBuilder sb3;
        StringBuilder sb4;
        if (mToken.length() == 0) {
            String str = mToken;
            String str2 = str;
            synchronized (str) {
                try {
                    if (mToken.length() == 0) {
                        new StringBuilder();
                        HttpURLConnection urlConnection = null;
                        try {
                            new URL(sb.append("http://auth.cloudmade.com/token/").append(mKey).append("?userid=").append(mAndroidId).toString());
                            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
                            urlConnection.setDoOutput(true);
                            urlConnection.setRequestMethod(DefaultHttpClient.METHOD_POST);
                            urlConnection.setRequestProperty(DefaultHttpClient.CONTENT_TYPE_KEY, "application/x-www-form-urlencoded");
                            urlConnection.setRequestProperty(Configuration.getInstance().getUserAgentHttpHeader(), Configuration.getInstance().getUserAgentValue());
                            for (Map.Entry<String, String> entry : Configuration.getInstance().getAdditionalHttpRequestProperties().entrySet()) {
                                urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
                            }
                            urlConnection.connect();
                            if (DEBUGMODE) {
                                new StringBuilder();
                                int d = Log.d(IMapView.LOGTAG, sb4.append("Response from Cloudmade auth: ").append(urlConnection.getResponseMessage()).toString());
                            }
                            if (urlConnection.getResponseCode() == 200) {
                                new InputStreamReader(urlConnection.getInputStream());
                                new BufferedReader(reader, 8192);
                                String line = br.readLine();
                                if (DEBUGMODE) {
                                    new StringBuilder();
                                    int d2 = Log.d(IMapView.LOGTAG, sb3.append("First line from Cloudmade auth: ").append(line).toString());
                                }
                                mToken = line.trim();
                                if (mToken.length() > 0) {
                                    SharedPreferences.Editor putString = mPreferenceEditor.putString(CLOUDMADE_TOKEN, mToken);
                                    boolean commit = mPreferenceEditor.commit();
                                    mPreferenceEditor = null;
                                } else {
                                    int e = Log.e(IMapView.LOGTAG, "No authorization token received from Cloudmade");
                                }
                            }
                            if (urlConnection != null) {
                                try {
                                    urlConnection.disconnect();
                                } catch (Exception e2) {
                                    Exception exc = e2;
                                }
                            }
                        } catch (IOException e3) {
                            IOException e4 = e3;
                            new StringBuilder();
                            int e5 = Log.e(IMapView.LOGTAG, sb2.append("No authorization token received from Cloudmade: ").append(e4).toString());
                            if (urlConnection != null) {
                                try {
                                    urlConnection.disconnect();
                                } catch (Exception e6) {
                                    Exception exc2 = e6;
                                }
                            }
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            if (urlConnection != null) {
                                try {
                                    urlConnection.disconnect();
                                } catch (Exception e7) {
                                    Exception exc3 = e7;
                                }
                            }
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    String str3 = str2;
                    throw th4;
                }
            }
        }
        return mToken;
    }
}
