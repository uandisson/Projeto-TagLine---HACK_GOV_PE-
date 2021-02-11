package com.onesignal;

import com.onesignal.OneSignal;
import com.onesignal.OneSignalRestClient;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class OutcomeEventsRepository {
    private static final String APP_ID = "app_id";
    private static final String DEVICE_TYPE = "device_type";
    private static final String DIRECT = "direct";
    private final OneSignalDbHelper dbHelper;
    private final OutcomeEventsService outcomeEventsService;

    OutcomeEventsRepository(OneSignalDbHelper dbHelper2) {
        OutcomeEventsService outcomeEventsService2;
        new OutcomeEventsService();
        this.outcomeEventsService = outcomeEventsService2;
        this.dbHelper = dbHelper2;
    }

    OutcomeEventsRepository(OutcomeEventsService outcomeEventsService2, OneSignalDbHelper dbHelper2) {
        this.outcomeEventsService = outcomeEventsService2;
        this.dbHelper = dbHelper2;
    }

    /* access modifiers changed from: package-private */
    public List<OutcomeEvent> getSavedOutcomeEvents() {
        return OutcomeEventsCache.getAllEventsToSend(this.dbHelper);
    }

    /* access modifiers changed from: package-private */
    public void saveOutcomeEvent(OutcomeEvent event) {
        OutcomeEventsCache.saveOutcomeEvent(event, this.dbHelper);
    }

    /* access modifiers changed from: package-private */
    public void removeEvent(OutcomeEvent outcomeEvent) {
        OutcomeEventsCache.deleteOldOutcomeEvent(outcomeEvent, this.dbHelper);
    }

    /* access modifiers changed from: package-private */
    public void requestMeasureDirectOutcomeEvent(String appId, int i, OutcomeEvent event, OneSignalRestClient.ResponseHandler responseHandler) {
        int deviceType = i;
        OneSignalRestClient.ResponseHandler responseHandler2 = responseHandler;
        JSONObject jsonObject = event.toJSONObjectForMeasure();
        try {
            JSONObject put = jsonObject.put(APP_ID, appId);
            JSONObject put2 = jsonObject.put(DEVICE_TYPE, deviceType);
            JSONObject put3 = jsonObject.put(DIRECT, true);
            this.outcomeEventsService.sendOutcomeEvent(jsonObject, responseHandler2);
        } catch (JSONException e) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating direct outcome:JSON Failed.", e);
        }
    }

    /* access modifiers changed from: package-private */
    public void requestMeasureIndirectOutcomeEvent(String appId, int i, OutcomeEvent event, OneSignalRestClient.ResponseHandler responseHandler) {
        int deviceType = i;
        OneSignalRestClient.ResponseHandler responseHandler2 = responseHandler;
        JSONObject jsonObject = event.toJSONObjectForMeasure();
        try {
            JSONObject put = jsonObject.put(APP_ID, appId);
            JSONObject put2 = jsonObject.put(DEVICE_TYPE, deviceType);
            JSONObject put3 = jsonObject.put(DIRECT, false);
            this.outcomeEventsService.sendOutcomeEvent(jsonObject, responseHandler2);
        } catch (JSONException e) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating indirect outcome:JSON Failed.", e);
        }
    }

    /* access modifiers changed from: package-private */
    public void requestMeasureUnattributedOutcomeEvent(String appId, int i, OutcomeEvent event, OneSignalRestClient.ResponseHandler responseHandler) {
        int deviceType = i;
        OneSignalRestClient.ResponseHandler responseHandler2 = responseHandler;
        JSONObject jsonObject = event.toJSONObjectForMeasure();
        try {
            JSONObject put = jsonObject.put(APP_ID, appId);
            JSONObject put2 = jsonObject.put(DEVICE_TYPE, deviceType);
            this.outcomeEventsService.sendOutcomeEvent(jsonObject, responseHandler2);
        } catch (JSONException e) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating unattributed outcome:JSON Failed.", e);
        }
    }

    /* access modifiers changed from: package-private */
    public void saveUniqueOutcomeNotifications(JSONArray notificationIds, String name) {
        OutcomeEventsCache.saveUniqueOutcomeNotifications(notificationIds, name, this.dbHelper);
    }

    /* access modifiers changed from: package-private */
    public JSONArray getNotCachedUniqueOutcomeNotifications(String name, JSONArray notificationIds) {
        return OutcomeEventsCache.getNotCachedUniqueOutcomeNotifications(name, notificationIds, this.dbHelper);
    }
}
