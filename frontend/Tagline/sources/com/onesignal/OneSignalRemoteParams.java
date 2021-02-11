package com.onesignal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalRestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class OneSignalRemoteParams {
    static final int DEFAULT_INDIRECT_ATTRIBUTION_WINDOW = 1440;
    static final int DEFAULT_NOTIFICATION_LIMIT = 10;
    private static final String DIRECT_PARAM = "direct";
    private static final String ENABLED_PARAM = "enabled";
    private static final String FCM_API_KEY = "api_key";
    private static final String FCM_APP_ID = "app_id";
    private static final String FCM_PARENT_PARAM = "fcm";
    private static final String FCM_PROJECT_ID = "project_id";
    private static final int INCREASE_BETWEEN_RETRIES = 10000;
    private static final String INDIRECT_PARAM = "indirect";
    private static final int MAX_WAIT_BETWEEN_RETRIES = 90000;
    private static final int MIN_WAIT_BETWEEN_RETRIES = 30000;
    private static final String NOTIFICATION_ATTRIBUTION_PARAM = "notification_attribution";
    private static final String OUTCOME_PARAM = "outcomes";
    private static final String UNATTRIBUTED_PARAM = "unattributed";
    /* access modifiers changed from: private */
    public static int androidParamsRetries = 0;

    interface CallBack {
        void complete(Params params);
    }

    OneSignalRemoteParams() {
    }

    static /* synthetic */ int access$008() {
        int i = androidParamsRetries;
        int i2 = i;
        androidParamsRetries = i + 1;
        return i2;
    }

    static class FCMParams {
        @Nullable
        String apiKey;
        @Nullable
        String appId;
        @Nullable
        String projectId;

        FCMParams() {
        }
    }

    static class OutcomesParams {
        boolean directEnabled = false;
        int indirectAttributionWindow = OneSignalRemoteParams.DEFAULT_INDIRECT_ATTRIBUTION_WINDOW;
        boolean indirectEnabled = false;
        int notificationLimit = 10;
        boolean unattributedEnabled = false;

        OutcomesParams() {
        }
    }

    static class Params {
        boolean clearGroupOnSummaryClick;
        boolean enterprise;
        FCMParams fcmParams;
        boolean firebaseAnalytics;
        String googleProjectNumber;
        JSONArray notificationChannels;
        OutcomesParams outcomesParams;
        boolean receiveReceiptEnabled;
        boolean restoreTTLFilter;
        boolean useEmailAuth;

        Params() {
        }
    }

    static void makeAndroidParamsRequest(@NonNull CallBack callBack) {
        OneSignalRestClient.ResponseHandler responseHandler;
        StringBuilder sb;
        StringBuilder sb2;
        final CallBack callBack2 = callBack;
        new OneSignalRestClient.ResponseHandler() {
            /* access modifiers changed from: package-private */
            public void onFailure(int statusCode, String str, Throwable th) {
                Thread thread;
                Runnable runnable;
                String str2 = str;
                Throwable th2 = th;
                if (statusCode == 403) {
                    OneSignal.Log(OneSignal.LOG_LEVEL.FATAL, "403 error getting OneSignal params, omitting further retries!");
                    return;
                }
                new Runnable(this) {
                    final /* synthetic */ C14681 this$0;

                    {
                        this.this$0 = this$0;
                    }

                    public void run() {
                        StringBuilder sb;
                        int sleepTime = OneSignalRemoteParams.MIN_WAIT_BETWEEN_RETRIES + (OneSignalRemoteParams.androidParamsRetries * 10000);
                        if (sleepTime > OneSignalRemoteParams.MAX_WAIT_BETWEEN_RETRIES) {
                            sleepTime = OneSignalRemoteParams.MAX_WAIT_BETWEEN_RETRIES;
                        }
                        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.INFO;
                        new StringBuilder();
                        OneSignal.Log(log_level, sb.append("Failed to get Android parameters, trying again in ").append(sleepTime / 1000).append(" seconds.").toString());
                        OSUtils.sleep(sleepTime);
                        int access$008 = OneSignalRemoteParams.access$008();
                        OneSignalRemoteParams.makeAndroidParamsRequest(callBack2);
                    }
                };
                new Thread(runnable, "OS_PARAMS_REQUEST");
                thread.start();
            }

            /* access modifiers changed from: package-private */
            public void onSuccess(String response) {
                OneSignalRemoteParams.processJson(response, callBack2);
            }
        };
        OneSignalRestClient.ResponseHandler responseHandler2 = responseHandler;
        new StringBuilder();
        String params_url = sb.append("apps/").append(OneSignal.appId).append("/android_params.js").toString();
        String userId = OneSignal.getUserId();
        if (userId != null) {
            new StringBuilder();
            params_url = sb2.append(params_url).append("?player_id=").append(userId).toString();
        }
        OneSignal.Log(OneSignal.LOG_LEVEL.DEBUG, "Starting request to get Android parameters.");
        OneSignalRestClient.get(params_url, responseHandler2, "CACHE_KEY_REMOTE_PARAMS");
    }

    /* access modifiers changed from: private */
    public static void processJson(String str, @NonNull CallBack callBack) {
        StringBuilder sb;
        JSONObject jSONObject;
        Params params;
        String json = str;
        CallBack callBack2 = callBack;
        try {
            JSONObject responseJson = jSONObject;
            new JSONObject(json);
            final JSONObject jSONObject2 = responseJson;
            new Params() {
                {
                    OutcomesParams outcomesParams;
                    FCMParams fCMParams;
                    this.enterprise = jSONObject2.optBoolean("enterp", false);
                    this.useEmailAuth = jSONObject2.optBoolean("use_email_auth", false);
                    this.notificationChannels = jSONObject2.optJSONArray("chnl_lst");
                    this.firebaseAnalytics = jSONObject2.optBoolean("fba", false);
                    this.restoreTTLFilter = jSONObject2.optBoolean("restore_ttl_filter", true);
                    this.googleProjectNumber = jSONObject2.optString("android_sender_id", (String) null);
                    this.clearGroupOnSummaryClick = jSONObject2.optBoolean("clear_group_on_summary_click", true);
                    this.receiveReceiptEnabled = jSONObject2.optBoolean("receive_receipts_enable", false);
                    new OutcomesParams();
                    this.outcomesParams = outcomesParams;
                    if (jSONObject2.has(OneSignalRemoteParams.OUTCOME_PARAM)) {
                        JSONObject outcomes = jSONObject2.optJSONObject(OneSignalRemoteParams.OUTCOME_PARAM);
                        if (outcomes.has(OneSignalRemoteParams.DIRECT_PARAM)) {
                            JSONObject direct = outcomes.optJSONObject(OneSignalRemoteParams.DIRECT_PARAM);
                            this.outcomesParams.directEnabled = direct.optBoolean("enabled");
                        }
                        if (outcomes.has(OneSignalRemoteParams.INDIRECT_PARAM)) {
                            JSONObject indirect = outcomes.optJSONObject(OneSignalRemoteParams.INDIRECT_PARAM);
                            this.outcomesParams.indirectEnabled = indirect.optBoolean("enabled");
                            if (indirect.has(OneSignalRemoteParams.NOTIFICATION_ATTRIBUTION_PARAM)) {
                                JSONObject indirectNotificationAttribution = indirect.optJSONObject(OneSignalRemoteParams.NOTIFICATION_ATTRIBUTION_PARAM);
                                this.outcomesParams.indirectAttributionWindow = indirectNotificationAttribution.optInt("minutes_since_displayed", OneSignalRemoteParams.DEFAULT_INDIRECT_ATTRIBUTION_WINDOW);
                                this.outcomesParams.notificationLimit = indirectNotificationAttribution.optInt("limit", 10);
                            }
                        }
                        if (outcomes.has(OneSignalRemoteParams.UNATTRIBUTED_PARAM)) {
                            JSONObject unattributed = outcomes.optJSONObject(OneSignalRemoteParams.UNATTRIBUTED_PARAM);
                            this.outcomesParams.unattributedEnabled = unattributed.optBoolean("enabled");
                        }
                    }
                    new FCMParams();
                    this.fcmParams = fCMParams;
                    if (jSONObject2.has(OneSignalRemoteParams.FCM_PARENT_PARAM)) {
                        JSONObject fcm = jSONObject2.optJSONObject(OneSignalRemoteParams.FCM_PARENT_PARAM);
                        this.fcmParams.apiKey = fcm.optString(OneSignalRemoteParams.FCM_API_KEY, (String) null);
                        this.fcmParams.appId = fcm.optString(OneSignalRemoteParams.FCM_APP_ID, (String) null);
                        this.fcmParams.projectId = fcm.optString(OneSignalRemoteParams.FCM_PROJECT_ID, (String) null);
                    }
                }
            };
            callBack2.complete(params);
        } catch (NullPointerException | JSONException e) {
            OneSignal.Log(OneSignal.LOG_LEVEL.FATAL, "Error parsing android_params!: ", e);
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.FATAL;
            new StringBuilder();
            OneSignal.Log(log_level, sb.append("Response that errored from android_params!: ").append(json).toString());
        }
    }
}
