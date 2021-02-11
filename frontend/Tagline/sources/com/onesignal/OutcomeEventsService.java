package com.onesignal;

import com.onesignal.OneSignalRestClient;
import org.json.JSONObject;

class OutcomeEventsService {
    OutcomeEventsService() {
    }

    /* access modifiers changed from: package-private */
    public void sendOutcomeEvent(JSONObject object, OneSignalRestClient.ResponseHandler responseHandler) {
        OneSignalRestClient.post("outcomes/measure", object, responseHandler);
    }
}
