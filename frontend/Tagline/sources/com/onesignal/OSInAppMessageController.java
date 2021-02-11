package com.onesignal;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.onesignal.OSDynamicTriggerController;
import com.onesignal.OSInAppMessageAction;
import com.onesignal.OSSystemConditionController;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalRestClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class OSInAppMessageController implements OSDynamicTriggerController.OSDynamicTriggerControllerObserver, OSSystemConditionController.OSSystemConditionObserver {
    public static final String IN_APP_MESSAGES_JSON_KEY = "in_app_messages";
    private static final String OS_DELETE_IN_APP_MESSAGE = "OS_DELETE_IN_APP_MESSAGE";
    private static final String OS_SAVE_IN_APP_MESSAGE = "OS_SAVE_IN_APP_MESSAGE";
    private static ArrayList<String> PREFERRED_VARIANT_ORDER;
    @Nullable
    private static OSInAppMessageController sharedInstance;
    /* access modifiers changed from: private */
    @NonNull
    public final Set<String> clickedClickIds;
    private OSInAppMessagePrompt currentPrompt = null;
    @NonNull
    private final Set<String> dismissedMessages;
    /* access modifiers changed from: private */
    public int htmlNetworkRequestAttemptCount = 0;
    /* access modifiers changed from: private */
    @NonNull
    public final Set<String> impressionedMessages;
    /* access modifiers changed from: private */
    public OSInAppMessageRepository inAppMessageRepository;
    private boolean inAppMessageShowing = false;
    private boolean inAppMessagingEnabled = true;
    @Nullable
    Date lastTimeInAppDismissed;
    @NonNull
    private final ArrayList<OSInAppMessage> messageDisplayQueue;
    @NonNull
    private ArrayList<OSInAppMessage> messages;
    @NonNull
    private List<OSInAppMessage> redisplayedInAppMessages;
    private OSSystemConditionController systemConditionController;
    OSTriggerController triggerController;

    static /* synthetic */ OSInAppMessagePrompt access$402(OSInAppMessageController x0, OSInAppMessagePrompt x1) {
        OSInAppMessagePrompt oSInAppMessagePrompt = x1;
        OSInAppMessagePrompt oSInAppMessagePrompt2 = oSInAppMessagePrompt;
        x0.currentPrompt = oSInAppMessagePrompt2;
        return oSInAppMessagePrompt;
    }

    static /* synthetic */ boolean access$702(OSInAppMessageController x0, boolean x1) {
        boolean z = x1;
        boolean z2 = z;
        x0.inAppMessageShowing = z2;
        return z;
    }

    static /* synthetic */ int access$802(OSInAppMessageController x0, int x1) {
        int i = x1;
        int i2 = i;
        x0.htmlNetworkRequestAttemptCount = i2;
        return i;
    }

    static /* synthetic */ int access$808(OSInAppMessageController x0) {
        OSInAppMessageController oSInAppMessageController = x0;
        int i = oSInAppMessageController.htmlNetworkRequestAttemptCount;
        int i2 = i + 1;
        oSInAppMessageController.htmlNetworkRequestAttemptCount = i2;
        return i;
    }

    static {
        ArrayList<String> arrayList;
        new ArrayList<String>() {
            {
                boolean add = add("android");
                boolean add2 = add("app");
                boolean add3 = add("all");
            }
        };
        PREFERRED_VARIANT_ORDER = arrayList;
    }

    public static OSInAppMessageController getController() {
        OSInAppMessageController oSInAppMessageController;
        OSInAppMessageController oSInAppMessageController2;
        OneSignalDbHelper dbHelper = OneSignal.getDBHelperInstance();
        if (Build.VERSION.SDK_INT <= 18) {
            new OSInAppMessageDummyController((OneSignalDbHelper) null);
            sharedInstance = oSInAppMessageController2;
        }
        if (sharedInstance == null) {
            new OSInAppMessageController(dbHelper);
            sharedInstance = oSInAppMessageController;
        }
        return sharedInstance;
    }

    protected OSInAppMessageController(OneSignalDbHelper oneSignalDbHelper) {
        ArrayList<OSInAppMessage> arrayList;
        ArrayList<OSInAppMessage> arrayList2;
        OSTriggerController oSTriggerController;
        OSSystemConditionController oSSystemConditionController;
        OneSignalDbHelper dbHelper = oneSignalDbHelper;
        new ArrayList<>();
        this.messages = arrayList;
        this.dismissedMessages = OSUtils.newConcurrentSet();
        new ArrayList<>();
        this.messageDisplayQueue = arrayList2;
        this.impressionedMessages = OSUtils.newConcurrentSet();
        this.clickedClickIds = OSUtils.newConcurrentSet();
        new OSTriggerController(this);
        this.triggerController = oSTriggerController;
        new OSSystemConditionController(this);
        this.systemConditionController = oSSystemConditionController;
        Set<String> tempDismissedSet = OneSignalPrefs.getStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_DISMISSED_IAMS, (Set<String>) null);
        if (tempDismissedSet != null) {
            boolean addAll = this.dismissedMessages.addAll(tempDismissedSet);
        }
        Set<String> tempImpressionsSet = OneSignalPrefs.getStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_IMPRESSIONED_IAMS, (Set<String>) null);
        if (tempImpressionsSet != null) {
            boolean addAll2 = this.impressionedMessages.addAll(tempImpressionsSet);
        }
        Set<String> tempClickedMessageIdsSet = OneSignalPrefs.getStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_CLICKED_CLICK_IDS_IAMS, (Set<String>) null);
        if (tempClickedMessageIdsSet != null) {
            boolean addAll3 = this.clickedClickIds.addAll(tempClickedMessageIdsSet);
        }
        initRedisplayData(dbHelper);
    }

    /* access modifiers changed from: protected */
    public void initRedisplayData(OneSignalDbHelper dbHelper) {
        OSInAppMessageRepository oSInAppMessageRepository;
        StringBuilder sb;
        new OSInAppMessageRepository(dbHelper);
        this.inAppMessageRepository = oSInAppMessageRepository;
        this.redisplayedInAppMessages = this.inAppMessageRepository.getRedisplayedInAppMessages();
        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
        new StringBuilder();
        OneSignal.Log(log_level, sb.append("redisplayedInAppMessages: ").append(this.redisplayedInAppMessages.toString()).toString());
    }

    /* access modifiers changed from: package-private */
    public void initWithCachedInAppMessages() {
        StringBuilder sb;
        JSONArray jSONArray;
        if (this.messages.isEmpty()) {
            String cachedIamsStr = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_CACHED_IAMS, (String) null);
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.Log(log_level, sb.append("initWithCachedInAppMessages: ").append(cachedIamsStr).toString());
            if (cachedIamsStr != null) {
                try {
                    new JSONArray(cachedIamsStr);
                    processInAppMessageJson(jSONArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void receivedInAppMessageJson(@NonNull JSONArray jSONArray) throws JSONException {
        JSONArray json = jSONArray;
        OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_CACHED_IAMS, json.toString());
        resetRedisplayMessagesBySession();
        processInAppMessageJson(json);
        deleteOldRedisplayedInAppMessages();
    }

    private void resetRedisplayMessagesBySession() {
        for (OSInAppMessage redisplayInAppMessage : this.redisplayedInAppMessages) {
            redisplayInAppMessage.setDisplayedInSession(false);
        }
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processInAppMessageJson(@android.support.annotation.NonNull org.json.JSONArray r11) throws org.json.JSONException {
        /*
            r10 = this;
            r0 = r10
            r1 = r11
            java.util.ArrayList r6 = new java.util.ArrayList
            r9 = r6
            r6 = r9
            r7 = r9
            r7.<init>()
            r2 = r6
            r6 = 0
            r3 = r6
        L_0x000d:
            r6 = r3
            r7 = r1
            int r7 = r7.length()
            if (r6 >= r7) goto L_0x002f
            r6 = r1
            r7 = r3
            org.json.JSONObject r6 = r6.getJSONObject(r7)
            r4 = r6
            com.onesignal.OSInAppMessage r6 = new com.onesignal.OSInAppMessage
            r9 = r6
            r6 = r9
            r7 = r9
            r8 = r4
            r7.<init>((org.json.JSONObject) r8)
            r5 = r6
            r6 = r2
            r7 = r5
            boolean r6 = r6.add(r7)
            int r3 = r3 + 1
            goto L_0x000d
        L_0x002f:
            r6 = r0
            r7 = r2
            r6.messages = r7
            r6 = r0
            r6.evaluateInAppMessages()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OSInAppMessageController.processInAppMessageJson(org.json.JSONArray):void");
    }

    private void deleteOldRedisplayedInAppMessages() {
        Thread thread;
        Runnable runnable;
        new Runnable(this) {
            final /* synthetic */ OSInAppMessageController this$0;

            {
                this.this$0 = this$0;
            }

            public void run() {
                Thread.currentThread().setPriority(10);
                this.this$0.inAppMessageRepository.deleteOldRedisplayedInAppMessages();
            }
        };
        new Thread(runnable, OS_DELETE_IN_APP_MESSAGE);
        thread.start();
    }

    private void evaluateInAppMessages() {
        Iterator<OSInAppMessage> it = this.messages.iterator();
        while (it.hasNext()) {
            OSInAppMessage message = it.next();
            setDataForRedisplay(message);
            if (!this.dismissedMessages.contains(message.messageId) && this.triggerController.evaluateMessageTriggers(message)) {
                queueMessageForDisplay(message);
            }
        }
    }

    @Nullable
    private static String variantIdForMessage(@NonNull OSInAppMessage oSInAppMessage) {
        OSInAppMessage message = oSInAppMessage;
        String languageIdentifier = OSUtils.getCorrectedLanguage();
        Iterator<String> it = PREFERRED_VARIANT_ORDER.iterator();
        while (it.hasNext()) {
            String variant = it.next();
            if (message.variants.containsKey(variant)) {
                HashMap<String, String> variantMap = message.variants.get(variant);
                if (variantMap.containsKey(languageIdentifier)) {
                    return variantMap.get(languageIdentifier);
                }
                return variantMap.get("default");
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static void printHttpSuccessForInAppMessageRequest(String requestType, String response) {
        StringBuilder sb;
        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
        new StringBuilder();
        OneSignal.onesignalLog(log_level, sb.append("Successful post for in-app message ").append(requestType).append(" request: ").append(response).toString());
    }

    /* access modifiers changed from: private */
    public static void printHttpErrorForInAppMessageRequest(String requestType, int statusCode, String response) {
        StringBuilder sb;
        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.ERROR;
        new StringBuilder();
        OneSignal.onesignalLog(log_level, sb.append("Encountered a ").append(statusCode).append(" error while attempting in-app message ").append(requestType).append(" request: ").append(response).toString());
    }

    /* access modifiers changed from: package-private */
    public void onMessageWasShown(@NonNull OSInAppMessage oSInAppMessage) {
        JSONObject json;
        StringBuilder sb;
        OneSignalRestClient.ResponseHandler responseHandler;
        OSInAppMessage message = oSInAppMessage;
        if (!message.isPreview && !this.impressionedMessages.contains(message.messageId)) {
            boolean add = this.impressionedMessages.add(message.messageId);
            String variantId = variantIdForMessage(message);
            if (variantId != null) {
                try {
                    final String str = variantId;
                    new JSONObject(this) {
                        final /* synthetic */ OSInAppMessageController this$0;

                        {
                            OSUtils oSUtils;
                            this.this$0 = this$0;
                            JSONObject put = put("app_id", OneSignal.appId);
                            JSONObject put2 = put("player_id", OneSignal.getUserId());
                            JSONObject put3 = put("variant_id", str);
                            new OSUtils();
                            JSONObject put4 = put("device_type", oSUtils.getDeviceType());
                            JSONObject put5 = put("first_impression", true);
                        }
                    };
                    new StringBuilder();
                    final OSInAppMessage oSInAppMessage2 = message;
                    new OneSignalRestClient.ResponseHandler(this) {
                        final /* synthetic */ OSInAppMessageController this$0;

                        {
                            this.this$0 = this$0;
                        }

                        /* access modifiers changed from: package-private */
                        public void onSuccess(String response) {
                            OSInAppMessageController.printHttpSuccessForInAppMessageRequest("impression", response);
                            OneSignalPrefs.saveStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_IMPRESSIONED_IAMS, this.this$0.impressionedMessages);
                        }

                        /* access modifiers changed from: package-private */
                        public void onFailure(int statusCode, String response, Throwable th) {
                            Throwable th2 = th;
                            OSInAppMessageController.printHttpErrorForInAppMessageRequest("impression", statusCode, response);
                            boolean remove = this.this$0.impressionedMessages.remove(oSInAppMessage2.messageId);
                        }
                    };
                    OneSignalRestClient.post(sb.append("in_app_messages/").append(message.messageId).append("/impression").toString(), json, responseHandler);
                } catch (JSONException e) {
                    e.printStackTrace();
                    OneSignal.onesignalLog(OneSignal.LOG_LEVEL.ERROR, "Unable to execute in-app message impression HTTP request due to invalid JSON");
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onMessageActionOccurredOnMessage(@NonNull OSInAppMessage oSInAppMessage, @NonNull JSONObject actionJson) throws JSONException {
        OSInAppMessageAction oSInAppMessageAction;
        OSInAppMessage message = oSInAppMessage;
        new OSInAppMessageAction(actionJson);
        OSInAppMessageAction action = oSInAppMessageAction;
        action.firstClick = message.takeActionAsUnique();
        firePublicClickHandler(action);
        beginProcessingPrompts(message, action.prompts);
        fireClickAction(action);
        fireRESTCallForClick(message, action);
        fireTagCallForClick(action);
        fireOutcomesForClick(action.outcomes);
    }

    /* access modifiers changed from: package-private */
    public void onMessageActionOccurredOnPreview(@NonNull OSInAppMessage oSInAppMessage, @NonNull JSONObject actionJson) throws JSONException {
        OSInAppMessageAction oSInAppMessageAction;
        OSInAppMessage message = oSInAppMessage;
        new OSInAppMessageAction(actionJson);
        OSInAppMessageAction action = oSInAppMessageAction;
        action.firstClick = message.takeActionAsUnique();
        firePublicClickHandler(action);
        beginProcessingPrompts(message, action.prompts);
        fireClickAction(action);
        logInAppMessagePreviewActions(action);
    }

    private void logInAppMessagePreviewActions(OSInAppMessageAction oSInAppMessageAction) {
        StringBuilder sb;
        StringBuilder sb2;
        OSInAppMessageAction action = oSInAppMessageAction;
        if (action.tags != null) {
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.onesignalLog(log_level, sb2.append("Tags detected inside of the action click payload, ignoring because action came from IAM preview:: ").append(action.tags.toString()).toString());
        }
        if (action.outcomes.size() > 0) {
            OneSignal.LOG_LEVEL log_level2 = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.onesignalLog(log_level2, sb.append("Outcomes detected inside of the action click payload, ignoring because action came from IAM preview: ").append(action.outcomes.toString()).toString());
        }
    }

    private void beginProcessingPrompts(OSInAppMessage oSInAppMessage, List<OSInAppMessagePrompt> list) {
        StringBuilder sb;
        OSInAppMessage message = oSInAppMessage;
        List<OSInAppMessagePrompt> prompts = list;
        if (prompts.size() > 0) {
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.onesignalLog(log_level, sb.append("IAM showing prompts from IAM: ").append(message.toString()).toString());
            WebViewManager.dismissCurrentInAppMessage();
            showMultiplePrompts(message, prompts);
        }
    }

    /* access modifiers changed from: private */
    public void showMultiplePrompts(OSInAppMessage oSInAppMessage, List<OSInAppMessagePrompt> list) {
        StringBuilder sb;
        StringBuilder sb2;
        OneSignal.OSPromptActionCompletionCallback oSPromptActionCompletionCallback;
        OSInAppMessage message = oSInAppMessage;
        List<OSInAppMessagePrompt> prompts = list;
        Iterator<OSInAppMessagePrompt> it = prompts.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            OSInAppMessagePrompt prompt = it.next();
            if (!prompt.hasPrompted()) {
                this.currentPrompt = prompt;
                break;
            }
        }
        if (this.currentPrompt != null) {
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.onesignalLog(log_level, sb2.append("IAM prompt to handle: ").append(this.currentPrompt.toString()).toString());
            this.currentPrompt.setPrompted(true);
            final OSInAppMessage oSInAppMessage2 = message;
            final List<OSInAppMessagePrompt> list2 = prompts;
            new OneSignal.OSPromptActionCompletionCallback(this) {
                final /* synthetic */ OSInAppMessageController this$0;

                {
                    this.this$0 = this$0;
                }

                public void completed(boolean accepted) {
                    StringBuilder sb;
                    OSInAppMessagePrompt access$402 = OSInAppMessageController.access$402(this.this$0, (OSInAppMessagePrompt) null);
                    OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                    new StringBuilder();
                    OneSignal.onesignalLog(log_level, sb.append("IAM prompt to handle finished accepted: ").append(accepted).toString());
                    this.this$0.showMultiplePrompts(oSInAppMessage2, list2);
                }
            };
            this.currentPrompt.handlePrompt(oSPromptActionCompletionCallback);
            return;
        }
        OneSignal.LOG_LEVEL log_level2 = OneSignal.LOG_LEVEL.DEBUG;
        new StringBuilder();
        OneSignal.onesignalLog(log_level2, sb.append("No IAM prompt to handle, dismiss message: ").append(message.messageId).toString());
        messageWasDismissed(message);
    }

    private void fireOutcomesForClick(@NonNull List<OSInAppMessageOutcome> outcomes) {
        for (OSInAppMessageOutcome outcome : outcomes) {
            String name = outcome.getName();
            if (outcome.isUnique()) {
                OneSignal.sendClickActionUniqueOutcome(name);
            } else if (outcome.getWeight() > 0.0f) {
                OneSignal.sendClickActionOutcomeWithValue(name, outcome.getWeight());
            } else {
                OneSignal.sendClickActionOutcome(name);
            }
        }
    }

    private void fireTagCallForClick(@NonNull OSInAppMessageAction oSInAppMessageAction) {
        OSInAppMessageAction action = oSInAppMessageAction;
        if (action.tags != null) {
            OSInAppMessageTag tags = action.tags;
            if (tags.getTagsToAdd() != null) {
                OneSignal.sendTags(tags.getTagsToAdd());
            }
            if (tags.getTagsToRemove() != null) {
                OneSignal.deleteTags(tags.getTagsToRemove(), (OneSignal.ChangeTagsUpdateHandler) null);
            }
        }
    }

    private void firePublicClickHandler(@NonNull OSInAppMessageAction oSInAppMessageAction) {
        Runnable runnable;
        OSInAppMessageAction action = oSInAppMessageAction;
        if (OneSignal.mInitBuilder.mInAppMessageClickHandler != null) {
            final OSInAppMessageAction oSInAppMessageAction2 = action;
            new Runnable(this) {
                final /* synthetic */ OSInAppMessageController this$0;

                {
                    this.this$0 = this$0;
                }

                public void run() {
                    OneSignal.mInitBuilder.mInAppMessageClickHandler.inAppMessageClicked(oSInAppMessageAction2);
                }
            };
            OSUtils.runOnMainUIThread(runnable);
        }
    }

    private void fireClickAction(@NonNull OSInAppMessageAction oSInAppMessageAction) {
        OSInAppMessageAction action = oSInAppMessageAction;
        if (action.clickUrl != null && !action.clickUrl.isEmpty()) {
            if (action.urlTarget == OSInAppMessageAction.OSInAppMessageActionUrlType.BROWSER) {
                OSUtils.openURLInBrowser(action.clickUrl);
            } else if (action.urlTarget == OSInAppMessageAction.OSInAppMessageActionUrlType.IN_APP_WEBVIEW) {
                boolean open = OneSignalChromeTab.open(action.clickUrl, true);
            }
        }
    }

    private void fireRESTCallForClick(@NonNull OSInAppMessage oSInAppMessage, @NonNull OSInAppMessageAction oSInAppMessageAction) {
        JSONObject json;
        StringBuilder sb;
        OneSignalRestClient.ResponseHandler responseHandler;
        OSInAppMessage message = oSInAppMessage;
        OSInAppMessageAction action = oSInAppMessageAction;
        String variantId = variantIdForMessage(message);
        if (variantId != null) {
            String clickId = action.clickId;
            if ((message.getDisplayStats().isRedisplayEnabled() && message.isClickAvailable(clickId)) || !this.clickedClickIds.contains(clickId)) {
                boolean add = this.clickedClickIds.add(clickId);
                message.addClickId(clickId);
                try {
                    final String str = clickId;
                    final String str2 = variantId;
                    final OSInAppMessageAction oSInAppMessageAction2 = action;
                    new JSONObject(this) {
                        final /* synthetic */ OSInAppMessageController this$0;

                        {
                            OSUtils oSUtils;
                            this.this$0 = this$0;
                            JSONObject put = put("app_id", OneSignal.getSavedAppId());
                            new OSUtils();
                            JSONObject put2 = put("device_type", oSUtils.getDeviceType());
                            JSONObject put3 = put("player_id", OneSignal.getUserId());
                            JSONObject put4 = put("click_id", str);
                            JSONObject put5 = put("variant_id", str2);
                            if (oSInAppMessageAction2.firstClick) {
                                JSONObject put6 = put("first_click", true);
                            }
                        }
                    };
                    new StringBuilder();
                    final OSInAppMessageAction oSInAppMessageAction3 = action;
                    new OneSignalRestClient.ResponseHandler(this) {
                        final /* synthetic */ OSInAppMessageController this$0;

                        {
                            this.this$0 = this$0;
                        }

                        /* access modifiers changed from: package-private */
                        public void onSuccess(String response) {
                            OSInAppMessageController.printHttpSuccessForInAppMessageRequest("engagement", response);
                            OneSignalPrefs.saveStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_CLICKED_CLICK_IDS_IAMS, this.this$0.clickedClickIds);
                        }

                        /* access modifiers changed from: package-private */
                        public void onFailure(int statusCode, String response, Throwable th) {
                            Throwable th2 = th;
                            OSInAppMessageController.printHttpErrorForInAppMessageRequest("engagement", statusCode, response);
                            boolean remove = this.this$0.clickedClickIds.remove(oSInAppMessageAction3.clickId);
                        }
                    };
                    OneSignalRestClient.post(sb.append("in_app_messages/").append(message.messageId).append("/click").toString(), json, responseHandler);
                } catch (JSONException e) {
                    e.printStackTrace();
                    OneSignal.onesignalLog(OneSignal.LOG_LEVEL.ERROR, "Unable to execute in-app message action HTTP request due to invalid JSON");
                }
            }
        }
    }

    private void setDataForRedisplay(OSInAppMessage oSInAppMessage) {
        StringBuilder sb;
        OSInAppMessage message = oSInAppMessage;
        if (message.getDisplayStats().isRedisplayEnabled()) {
            boolean messageDismissed = this.dismissedMessages.contains(message.messageId);
            int index = this.redisplayedInAppMessages.indexOf(message);
            if (messageDismissed && index != -1) {
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                new StringBuilder();
                OneSignal.onesignalLog(log_level, sb.append("setDataForRedisplay: ").append(message.messageId).toString());
                OSInAppMessage savedIAM = this.redisplayedInAppMessages.get(index);
                message.getDisplayStats().setDisplayStats(savedIAM.getDisplayStats());
                if ((message.isTriggerChanged() || (!savedIAM.isDisplayedInSession() && message.triggers.isEmpty())) && message.getDisplayStats().isDelayTimeSatisfied() && message.getDisplayStats().shouldDisplayAgain()) {
                    boolean remove = this.dismissedMessages.remove(message.messageId);
                    boolean remove2 = this.impressionedMessages.remove(message.messageId);
                    message.clearClickIds();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void queueMessageForDisplay(@NonNull OSInAppMessage oSInAppMessage) {
        StringBuilder sb;
        OSInAppMessage message = oSInAppMessage;
        ArrayList<OSInAppMessage> arrayList = this.messageDisplayQueue;
        ArrayList<OSInAppMessage> arrayList2 = arrayList;
        synchronized (arrayList) {
            try {
                if (!this.messageDisplayQueue.contains(message)) {
                    boolean add = this.messageDisplayQueue.add(message);
                    OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                    new StringBuilder();
                    OneSignal.onesignalLog(log_level, sb.append("In app message with id, ").append(message.messageId).append(", added to the queue").toString());
                }
                attemptToShowInAppMessage();
            } catch (Throwable th) {
                Throwable th2 = th;
                ArrayList<OSInAppMessage> arrayList3 = arrayList2;
                throw th2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    private void attemptToShowInAppMessage() {
        StringBuilder sb;
        ArrayList<OSInAppMessage> arrayList = this.messageDisplayQueue;
        ArrayList<OSInAppMessage> arrayList2 = arrayList;
        synchronized (arrayList) {
            try {
                if (!this.systemConditionController.systemConditionsAvailable()) {
                    OneSignal.onesignalLog(OneSignal.LOG_LEVEL.WARN, "In app message not showing due to system condition not correct");
                    return;
                }
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                new StringBuilder();
                OneSignal.onesignalLog(log_level, sb.append("displayFirstIAMOnQueue: ").append(this.messageDisplayQueue).toString());
                if (this.messageDisplayQueue.size() <= 0 || isInAppMessageShowing()) {
                    OneSignal.onesignalLog(OneSignal.LOG_LEVEL.DEBUG, "In app message is currently showing or there are no IAMs left in the queue!");
                    return;
                }
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.DEBUG, "No IAM showing currently, showing first item in the queue!");
                displayMessage(this.messageDisplayQueue.get(0));
            } catch (Throwable th) {
                Throwable th2 = th;
                ArrayList<OSInAppMessage> arrayList3 = arrayList2;
                throw th2;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInAppMessageShowing() {
        return this.inAppMessageShowing;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public OSInAppMessage getCurrentDisplayedInAppMessage() {
        return this.inAppMessageShowing ? this.messageDisplayQueue.get(0) : null;
    }

    /* access modifiers changed from: package-private */
    public void messageWasDismissed(@NonNull OSInAppMessage oSInAppMessage) {
        Date date;
        StringBuilder sb;
        OSInAppMessage message = oSInAppMessage;
        if (!message.isPreview) {
            boolean add = this.dismissedMessages.add(message.messageId);
            OneSignalPrefs.saveStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_DISMISSED_IAMS, this.dismissedMessages);
            new Date();
            this.lastTimeInAppDismissed = date;
            persistInAppMessageForRedisplay(message);
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.onesignalLog(log_level, sb.append("OSInAppMessageController messageWasDismissed dismissedMessages: ").append(this.dismissedMessages.toString()).toString());
        }
        dismissCurrentMessage(message);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public void dismissCurrentMessage(@Nullable OSInAppMessage oSInAppMessage) {
        StringBuilder sb;
        StringBuilder sb2;
        OSInAppMessage message = oSInAppMessage;
        if (this.currentPrompt != null) {
            OneSignal.onesignalLog(OneSignal.LOG_LEVEL.DEBUG, "Stop evaluateMessageDisplayQueue because prompt is currently displayed");
            return;
        }
        this.inAppMessageShowing = false;
        ArrayList<OSInAppMessage> arrayList = this.messageDisplayQueue;
        ArrayList<OSInAppMessage> arrayList2 = arrayList;
        synchronized (arrayList) {
            try {
                if (this.messageDisplayQueue.size() > 0) {
                    if (message == null || this.messageDisplayQueue.contains(message)) {
                        String removedMessageId = this.messageDisplayQueue.remove(0).messageId;
                        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                        new StringBuilder();
                        OneSignal.onesignalLog(log_level, sb2.append("In app message with id, ").append(removedMessageId).append(", dismissed (removed) from the queue!").toString());
                    } else {
                        OneSignal.onesignalLog(OneSignal.LOG_LEVEL.DEBUG, "Message already removed from the queue!");
                        return;
                    }
                }
                if (this.messageDisplayQueue.size() > 0) {
                    OneSignal.LOG_LEVEL log_level2 = OneSignal.LOG_LEVEL.DEBUG;
                    new StringBuilder();
                    OneSignal.onesignalLog(log_level2, sb.append("In app message on queue available: ").append(this.messageDisplayQueue.get(0).messageId).toString());
                    displayMessage(this.messageDisplayQueue.get(0));
                } else {
                    OneSignal.onesignalLog(OneSignal.LOG_LEVEL.DEBUG, "In app message dismissed evaluating messages");
                    evaluateInAppMessages();
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                ArrayList<OSInAppMessage> arrayList3 = arrayList2;
                throw th2;
            }
        }
    }

    private void persistInAppMessageForRedisplay(OSInAppMessage oSInAppMessage) {
        Thread thread;
        Runnable runnable;
        StringBuilder sb;
        OSInAppMessage message = oSInAppMessage;
        if (message.getDisplayStats().isRedisplayEnabled()) {
            message.getDisplayStats().setLastDisplayTime(System.currentTimeMillis() / 1000);
            message.getDisplayStats().incrementDisplayQuantity();
            message.setTriggerChanged(false);
            message.setDisplayedInSession(true);
            final OSInAppMessage oSInAppMessage2 = message;
            new Runnable(this) {
                final /* synthetic */ OSInAppMessageController this$0;

                {
                    this.this$0 = this$0;
                }

                public void run() {
                    Thread.currentThread().setPriority(10);
                    this.this$0.inAppMessageRepository.saveInAppMessage(oSInAppMessage2);
                }
            };
            new Thread(runnable, OS_SAVE_IN_APP_MESSAGE);
            thread.start();
            int index = this.redisplayedInAppMessages.indexOf(message);
            if (index != -1) {
                OSInAppMessage oSInAppMessage3 = this.redisplayedInAppMessages.set(index, message);
            } else {
                boolean add = this.redisplayedInAppMessages.add(message);
            }
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.onesignalLog(log_level, sb.append("persistInAppMessageForRedisplay: ").append(message.toString()).append(" with msg array data: ").append(this.redisplayedInAppMessages.toString()).toString());
        }
    }

    @Nullable
    private static String htmlPathForMessage(OSInAppMessage oSInAppMessage) {
        StringBuilder sb;
        StringBuilder sb2;
        OSInAppMessage message = oSInAppMessage;
        String variantId = variantIdForMessage(message);
        if (variantId == null) {
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.ERROR;
            new StringBuilder();
            OneSignal.onesignalLog(log_level, sb2.append("Unable to find a variant for in-app message ").append(message.messageId).toString());
            return null;
        }
        new StringBuilder();
        return sb.append("in_app_messages/").append(message.messageId).append("/variants/").append(variantId).append("/html?app_id=").append(OneSignal.appId).toString();
    }

    private void displayMessage(@NonNull OSInAppMessage oSInAppMessage) {
        OneSignalRestClient.ResponseHandler responseHandler;
        OSInAppMessage message = oSInAppMessage;
        if (!this.inAppMessagingEnabled) {
            OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "In app messaging is currently paused, iam will not be shown!");
            return;
        }
        this.inAppMessageShowing = true;
        final OSInAppMessage oSInAppMessage2 = message;
        new OneSignalRestClient.ResponseHandler(this) {
            final /* synthetic */ OSInAppMessageController this$0;

            {
                this.this$0 = this$0;
            }

            /* access modifiers changed from: package-private */
            public void onFailure(int i, String response, Throwable th) {
                int statusCode = i;
                Throwable th2 = th;
                boolean access$702 = OSInAppMessageController.access$702(this.this$0, false);
                OSInAppMessageController.printHttpErrorForInAppMessageRequest("html", statusCode, response);
                if (!OSUtils.shouldRetryNetworkRequest(statusCode) || this.this$0.htmlNetworkRequestAttemptCount >= OSUtils.MAX_NETWORK_REQUEST_ATTEMPT_COUNT) {
                    int access$802 = OSInAppMessageController.access$802(this.this$0, 0);
                    this.this$0.messageWasDismissed(oSInAppMessage2);
                    return;
                }
                int access$808 = OSInAppMessageController.access$808(this.this$0);
                this.this$0.queueMessageForDisplay(oSInAppMessage2);
            }

            /* access modifiers changed from: package-private */
            public void onSuccess(String str) {
                JSONObject jSONObject;
                String response = str;
                int access$802 = OSInAppMessageController.access$802(this.this$0, 0);
                try {
                    new JSONObject(response);
                    JSONObject jsonResponse = jSONObject;
                    String htmlStr = jsonResponse.getString("html");
                    oSInAppMessage2.setDisplayDuration(jsonResponse.optDouble("display_duration"));
                    WebViewManager.showHTMLString(oSInAppMessage2, htmlStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        OneSignalRestClient.getSync(htmlPathForMessage(message), responseHandler, (String) null);
    }

    /* access modifiers changed from: package-private */
    public void displayPreviewMessage(@NonNull String previewUUID) {
        StringBuilder sb;
        OneSignalRestClient.ResponseHandler responseHandler;
        this.inAppMessageShowing = true;
        new StringBuilder();
        new OneSignalRestClient.ResponseHandler(this) {
            final /* synthetic */ OSInAppMessageController this$0;

            {
                this.this$0 = this$0;
            }

            /* access modifiers changed from: package-private */
            public void onFailure(int statusCode, String response, Throwable th) {
                Throwable th2 = th;
                OSInAppMessageController.printHttpErrorForInAppMessageRequest("html", statusCode, response);
                this.this$0.dismissCurrentMessage((OSInAppMessage) null);
            }

            /* access modifiers changed from: package-private */
            public void onSuccess(String response) {
                JSONObject jSONObject;
                OSInAppMessage oSInAppMessage;
                try {
                    new JSONObject(response);
                    JSONObject jsonResponse = jSONObject;
                    String htmlStr = jsonResponse.getString("html");
                    new OSInAppMessage(true);
                    OSInAppMessage message = oSInAppMessage;
                    message.setDisplayDuration(jsonResponse.optDouble("display_duration"));
                    WebViewManager.showHTMLString(message, htmlStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        OneSignalRestClient.get(sb.append("in_app_messages/device_preview?preview_id=").append(previewUUID).append("&app_id=").append(OneSignal.appId).toString(), responseHandler, (String) null);
    }

    public void messageTriggerConditionChanged() {
        evaluateInAppMessages();
    }

    public void systemConditionChanged() {
        attemptToShowInAppMessage();
    }

    private void makeRedisplayMessagesAvailableWithTriggers(Collection<String> collection) {
        Collection<String> newTriggersKeys = collection;
        Iterator<OSInAppMessage> it = this.messages.iterator();
        while (it.hasNext()) {
            OSInAppMessage message = it.next();
            if (this.redisplayedInAppMessages.contains(message) && this.triggerController.isTriggerOnMessage(message, newTriggersKeys)) {
                message.setTriggerChanged(true);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addTriggers(Map<String, Object> map) {
        Map<String, Object> newTriggers = map;
        this.triggerController.addTriggers(newTriggers);
        makeRedisplayMessagesAvailableWithTriggers(newTriggers.keySet());
        evaluateInAppMessages();
    }

    /* access modifiers changed from: package-private */
    public void removeTriggersForKeys(Collection<String> collection) {
        Collection<String> keys = collection;
        this.triggerController.removeTriggersForKeys(keys);
        makeRedisplayMessagesAvailableWithTriggers(keys);
        evaluateInAppMessages();
    }

    /* access modifiers changed from: package-private */
    public void setInAppMessagingEnabled(boolean z) {
        boolean enabled = z;
        this.inAppMessagingEnabled = enabled;
        if (enabled) {
            evaluateInAppMessages();
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Object getTriggerValue(String key) {
        return this.triggerController.getTriggerValue(key);
    }

    @NonNull
    public ArrayList<OSInAppMessage> getInAppMessageDisplayQueue() {
        return this.messageDisplayQueue;
    }

    @NonNull
    public List<OSInAppMessage> getRedisplayedInAppMessages() {
        return this.redisplayedInAppMessages;
    }
}
