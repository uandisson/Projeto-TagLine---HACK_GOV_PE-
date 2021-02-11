package com.onesignal;

import android.support.annotation.Nullable;
import com.onesignal.OneSignalRestClient;
import com.onesignal.OneSignalStateSynchronizer;
import com.onesignal.UserStateSynchronizer;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class UserStatePushSynchronizer extends UserStateSynchronizer {
    private static boolean serverSuccess;

    static /* synthetic */ boolean access$002(boolean x0) {
        boolean x02 = x0;
        serverSuccess = x02;
        return x02;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UserStatePushSynchronizer() {
        super(OneSignalStateSynchronizer.UserStateSynchronizerType.PUSH);
    }

    /* access modifiers changed from: protected */
    public UserState newUserState(String inPersistKey, boolean load) {
        UserState userState;
        new UserStatePush(inPersistKey, load);
        return userState;
    }

    /* access modifiers changed from: package-private */
    public boolean getSubscribed() {
        return getToSyncUserState().isSubscribed();
    }

    /* access modifiers changed from: package-private */
    public UserStateSynchronizer.GetTagsResult getTags(boolean fromServer) {
        UserStateSynchronizer.GetTagsResult getTagsResult;
        StringBuilder sb;
        OneSignalRestClient.ResponseHandler responseHandler;
        if (fromServer) {
            String userId = OneSignal.getUserId();
            String appId = OneSignal.getSavedAppId();
            new StringBuilder();
            new OneSignalRestClient.ResponseHandler(this) {
                final /* synthetic */ UserStatePushSynchronizer this$0;

                {
                    this.this$0 = this$0;
                }

                /* JADX INFO: finally extract failed */
                /* access modifiers changed from: package-private */
                public void onSuccess(String str) {
                    JSONObject jSONObject;
                    Object obj;
                    String responseStr = str;
                    boolean access$002 = UserStatePushSynchronizer.access$002(true);
                    if (responseStr == null || responseStr.isEmpty()) {
                        responseStr = "{}";
                    }
                    try {
                        new JSONObject(responseStr);
                        JSONObject lastGetTagsResponse = jSONObject;
                        if (lastGetTagsResponse.has("tags")) {
                            Object obj2 = this.this$0.syncLock;
                            obj = obj2;
                            synchronized (obj2) {
                                JSONObject dependDiff = this.this$0.generateJsonDiff(this.this$0.currentUserState.syncValues.optJSONObject("tags"), this.this$0.getToSyncUserState().syncValues.optJSONObject("tags"), (JSONObject) null, (Set<String>) null);
                                JSONObject put = this.this$0.currentUserState.syncValues.put("tags", lastGetTagsResponse.optJSONObject("tags"));
                                this.this$0.currentUserState.persistState();
                                this.this$0.getToSyncUserState().mergeTags(lastGetTagsResponse, dependDiff);
                                this.this$0.getToSyncUserState().persistState();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        Object obj3 = obj;
                        throw th2;
                    }
                }
            };
            OneSignalRestClient.getSync(sb.append("players/").append(userId).append("?app_id=").append(appId).toString(), responseHandler, "CACHE_KEY_GET_TAGS");
        }
        Object obj = this.syncLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                UserStateSynchronizer.GetTagsResult getTagsResult2 = getTagsResult;
                new UserStateSynchronizer.GetTagsResult(serverSuccess, JSONUtils.getJSONObjectWithoutBlankValues(this.toSyncUserState.syncValues, "tags"));
                return getTagsResult2;
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getExternalId(boolean z) {
        boolean z2 = z;
        Object obj = this.syncLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                String optString = this.toSyncUserState.syncValues.optString("external_user_id", (String) null);
                return optString;
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void scheduleSyncToServer() {
        getNetworkHandlerThread(0).runNewJobDelayed();
    }

    /* access modifiers changed from: package-private */
    public void updateState(JSONObject jSONObject) {
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        JSONObject pushState = jSONObject;
        try {
            new JSONObject();
            JSONObject syncUpdate = jSONObject3;
            JSONObject putOpt = syncUpdate.putOpt("identifier", pushState.optString("identifier", (String) null));
            if (pushState.has("device_type")) {
                JSONObject put = syncUpdate.put("device_type", pushState.optInt("device_type"));
            }
            JSONObject putOpt2 = syncUpdate.putOpt("parent_player_id", pushState.optString("parent_player_id", (String) null));
            JSONObject toSync = getUserStateForModification().syncValues;
            JSONObject generateJsonDiff = generateJsonDiff(toSync, syncUpdate, toSync, (Set<String>) null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            new JSONObject();
            JSONObject dependUpdate = jSONObject2;
            if (pushState.has("subscribableStatus")) {
                JSONObject put2 = dependUpdate.put("subscribableStatus", pushState.optInt("subscribableStatus"));
            }
            if (pushState.has("androidPermission")) {
                JSONObject put3 = dependUpdate.put("androidPermission", pushState.optBoolean("androidPermission"));
            }
            JSONObject dependValues = getUserStateForModification().dependValues;
            JSONObject generateJsonDiff2 = generateJsonDiff(dependValues, dependUpdate, dependValues, (Set<String>) null);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void setEmail(String str, String str2) {
        JSONObject jSONObject;
        String email = str;
        String emailAuthHash = str2;
        try {
            UserState userState = getUserStateForModification();
            JSONObject put = userState.dependValues.put("email_auth_hash", emailAuthHash);
            JSONObject syncValues = userState.syncValues;
            new JSONObject();
            JSONObject generateJsonDiff = generateJsonDiff(syncValues, jSONObject.put("email", email), syncValues, (Set<String>) null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void setSubscription(boolean enable) {
        try {
            JSONObject put = getUserStateForModification().dependValues.put("userSubscribePref", enable);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean getUserSubscribePreference() {
        return getToSyncUserState().dependValues.optBoolean("userSubscribePref", true);
    }

    public void setPermission(boolean enable) {
        try {
            JSONObject put = getUserStateForModification().dependValues.put("androidPermission", enable);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public String getId() {
        return OneSignal.getUserId();
    }

    /* access modifiers changed from: package-private */
    public void updateIdDependents(String id) {
        OneSignal.updateUserIdDependents(id);
    }

    /* access modifiers changed from: protected */
    public void addOnSessionOrCreateExtras(JSONObject jsonBody) {
    }

    /* access modifiers changed from: package-private */
    public void logoutEmail() {
        try {
            JSONObject put = getUserStateForModification().dependValues.put("logoutEmail", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void fireEventsForUpdateFailure(JSONObject jsonFields) {
        if (jsonFields.has("email")) {
            OneSignal.fireEmailUpdateFailure();
        }
    }

    /* access modifiers changed from: protected */
    public void onSuccessfulSync(JSONObject jSONObject) {
        JSONObject jsonFields = jSONObject;
        if (jsonFields.has("email")) {
            OneSignal.fireEmailUpdateSuccess();
        }
        if (jsonFields.has("identifier")) {
            OneSignal.fireIdsAvailableCallback();
        }
    }
}
