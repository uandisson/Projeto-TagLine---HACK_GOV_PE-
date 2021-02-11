package com.onesignal;

import com.onesignal.OSTrigger;
import com.onesignal.OneSignal;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

class OSDynamicTriggerController {
    private static final long DEFAULT_LAST_IN_APP_TIME_AGO = 999999;
    private static final double REQUIRED_ACCURACY = 0.3d;
    static Date sessionLaunchTime;
    /* access modifiers changed from: private */
    public final OSDynamicTriggerControllerObserver observer;
    /* access modifiers changed from: private */
    public final ArrayList<String> scheduledMessages;

    interface OSDynamicTriggerControllerObserver {
        void messageTriggerConditionChanged();
    }

    static {
        Date date;
        new Date();
        sessionLaunchTime = date;
    }

    OSDynamicTriggerController(OSDynamicTriggerControllerObserver triggerObserver) {
        ArrayList<String> arrayList;
        new ArrayList<>();
        this.scheduledMessages = arrayList;
        this.observer = triggerObserver;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public boolean dynamicTriggerShouldFire(OSTrigger oSTrigger) {
        Date date;
        Date date2;
        TimerTask timerTask;
        OSTrigger trigger = oSTrigger;
        if (trigger.value == null) {
            return false;
        }
        ArrayList<String> arrayList = this.scheduledMessages;
        ArrayList<String> arrayList2 = arrayList;
        synchronized (arrayList) {
            try {
                if (!(trigger.value instanceof Number)) {
                    return false;
                }
                long currentTimeInterval = 0;
                switch (trigger.kind) {
                    case SESSION_TIME:
                        new Date();
                        currentTimeInterval = date2.getTime() - sessionLaunchTime.getTime();
                        break;
                    case TIME_SINCE_LAST_IN_APP:
                        if (!OSInAppMessageController.getController().isInAppMessageShowing()) {
                            Date lastTimeAppDismissed = OSInAppMessageController.getController().lastTimeInAppDismissed;
                            if (lastTimeAppDismissed != null) {
                                new Date();
                                currentTimeInterval = date.getTime() - lastTimeAppDismissed.getTime();
                                break;
                            } else {
                                currentTimeInterval = 999999;
                                break;
                            }
                        } else {
                            return false;
                        }
                }
                long requiredTimeInterval = (long) (((Number) trigger.value).doubleValue() * 1000.0d);
                if (evaluateTimeIntervalWithOperator((double) requiredTimeInterval, (double) currentTimeInterval, trigger.operatorType)) {
                    return true;
                }
                long offset = requiredTimeInterval - currentTimeInterval;
                if (offset <= 0) {
                    return false;
                } else if (this.scheduledMessages.contains(trigger.triggerId)) {
                    return false;
                } else {
                    final OSTrigger oSTrigger2 = trigger;
                    new TimerTask(this) {
                        final /* synthetic */ OSDynamicTriggerController this$0;

                        {
                            this.this$0 = this$0;
                        }

                        public void run() {
                            boolean remove = this.this$0.scheduledMessages.remove(oSTrigger2.triggerId);
                            this.this$0.observer.messageTriggerConditionChanged();
                        }
                    };
                    OSDynamicTriggerTimer.scheduleTrigger(timerTask, trigger.triggerId, offset);
                    boolean add = this.scheduledMessages.add(trigger.triggerId);
                    return false;
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                ArrayList<String> arrayList3 = arrayList2;
                throw th2;
            }
        }
    }

    private static boolean evaluateTimeIntervalWithOperator(double d, double d2, OSTrigger.OSTriggerOperator oSTriggerOperator) {
        StringBuilder sb;
        double timeInterval = d;
        double currentTimeInterval = d2;
        OSTrigger.OSTriggerOperator operator = oSTriggerOperator;
        switch (operator) {
            case LESS_THAN:
                return currentTimeInterval < timeInterval;
            case LESS_THAN_OR_EQUAL_TO:
                return currentTimeInterval <= timeInterval || roughlyEqual(timeInterval, currentTimeInterval);
            case GREATER_THAN:
                return currentTimeInterval > timeInterval;
            case GREATER_THAN_OR_EQUAL_TO:
                return currentTimeInterval >= timeInterval || roughlyEqual(timeInterval, currentTimeInterval);
            case EQUAL_TO:
                return roughlyEqual(timeInterval, currentTimeInterval);
            case NOT_EQUAL_TO:
                return !roughlyEqual(timeInterval, currentTimeInterval);
            default:
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.ERROR;
                new StringBuilder();
                OneSignal.onesignalLog(log_level, sb.append("Attempted to apply an invalid operator on a time-based in-app-message trigger: ").append(operator.toString()).toString());
                return false;
        }
    }

    private static boolean roughlyEqual(double left, double right) {
        return Math.abs(left - right) < REQUIRED_ACCURACY;
    }
}
