package com.onesignal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.onesignal.OSDynamicTriggerController;
import com.onesignal.OSTrigger;
import com.onesignal.OneSignal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class OSTriggerController {
    OSDynamicTriggerController dynamicTriggerController;
    private final ConcurrentHashMap<String, Object> triggers;

    OSTriggerController(OSDynamicTriggerController.OSDynamicTriggerControllerObserver dynamicTriggerObserver) {
        ConcurrentHashMap<String, Object> concurrentHashMap;
        OSDynamicTriggerController oSDynamicTriggerController;
        new ConcurrentHashMap<>();
        this.triggers = concurrentHashMap;
        new OSDynamicTriggerController(dynamicTriggerObserver);
        this.dynamicTriggerController = oSDynamicTriggerController;
    }

    /* access modifiers changed from: package-private */
    public boolean evaluateMessageTriggers(@NonNull OSInAppMessage oSInAppMessage) {
        OSInAppMessage message = oSInAppMessage;
        if (message.triggers.size() == 0) {
            return true;
        }
        Iterator<ArrayList<OSTrigger>> it = message.triggers.iterator();
        while (it.hasNext()) {
            if (evaluateAndTriggers(it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluateAndTriggers(@NonNull ArrayList<OSTrigger> andConditions) {
        Iterator<OSTrigger> it = andConditions.iterator();
        while (it.hasNext()) {
            if (!evaluateTrigger(it.next())) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluateTrigger(@NonNull OSTrigger oSTrigger) {
        OSTrigger trigger = oSTrigger;
        if (trigger.kind == OSTrigger.OSTriggerKind.UNKNOWN) {
            return false;
        }
        if (trigger.kind != OSTrigger.OSTriggerKind.CUSTOM) {
            return this.dynamicTriggerController.dynamicTriggerShouldFire(trigger);
        }
        OSTrigger.OSTriggerOperator operatorType = trigger.operatorType;
        Object deviceValue = this.triggers.get(trigger.property);
        if (deviceValue == null) {
            if (operatorType == OSTrigger.OSTriggerOperator.NOT_EXISTS) {
                return true;
            }
            return operatorType == OSTrigger.OSTriggerOperator.NOT_EQUAL_TO && trigger.value != null;
        } else if (operatorType == OSTrigger.OSTriggerOperator.EXISTS) {
            return true;
        } else {
            if (operatorType == OSTrigger.OSTriggerOperator.NOT_EXISTS) {
                return false;
            }
            if (operatorType == OSTrigger.OSTriggerOperator.CONTAINS) {
                return (deviceValue instanceof Collection) && ((Collection) deviceValue).contains(trigger.value);
            } else if ((deviceValue instanceof String) && (trigger.value instanceof String) && triggerMatchesStringValue((String) trigger.value, (String) deviceValue, operatorType)) {
                return true;
            } else {
                if ((trigger.value instanceof Number) && (deviceValue instanceof Number) && triggerMatchesNumericValue((Number) trigger.value, (Number) deviceValue, operatorType)) {
                    return true;
                }
                if (triggerMatchesFlex(trigger.value, deviceValue, operatorType)) {
                    return true;
                }
                return false;
            }
        }
    }

    private boolean triggerMatchesStringValue(@NonNull String str, @NonNull String str2, @NonNull OSTrigger.OSTriggerOperator oSTriggerOperator) {
        StringBuilder sb;
        String triggerValue = str;
        String deviceValue = str2;
        OSTrigger.OSTriggerOperator operator = oSTriggerOperator;
        switch (operator) {
            case EQUAL_TO:
                return triggerValue.equals(deviceValue);
            case NOT_EQUAL_TO:
                return !triggerValue.equals(deviceValue);
            default:
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.ERROR;
                new StringBuilder();
                OneSignal.onesignalLog(log_level, sb.append("Attempted to use an invalid operator for a string trigger comparison: ").append(operator.toString()).toString());
                return false;
        }
    }

    private boolean triggerMatchesFlex(@Nullable Object obj, @NonNull Object obj2, @NonNull OSTrigger.OSTriggerOperator oSTriggerOperator) {
        Object triggerValue = obj;
        Object deviceValue = obj2;
        OSTrigger.OSTriggerOperator operator = oSTriggerOperator;
        if (triggerValue == null) {
            return false;
        }
        if (operator.checksEquality()) {
            return triggerMatchesStringValue(triggerValue.toString(), deviceValue.toString(), operator);
        }
        if (!(deviceValue instanceof String) || !(triggerValue instanceof Number)) {
            return false;
        }
        return triggerMatchesNumericValueFlex((Number) triggerValue, (String) deviceValue, operator);
    }

    private boolean triggerMatchesNumericValueFlex(@NonNull Number triggerValue, @NonNull String deviceValue, @NonNull OSTrigger.OSTriggerOperator operator) {
        try {
            return triggerMatchesNumericValue(Double.valueOf(triggerValue.doubleValue()), Double.valueOf(Double.parseDouble(deviceValue)), operator);
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = e;
            return false;
        }
    }

    private boolean triggerMatchesNumericValue(@NonNull Number triggerValue, @NonNull Number deviceValue, @NonNull OSTrigger.OSTriggerOperator oSTriggerOperator) {
        StringBuilder sb;
        OSTrigger.OSTriggerOperator operator = oSTriggerOperator;
        double triggerDoubleValue = triggerValue.doubleValue();
        double deviceDoubleValue = deviceValue.doubleValue();
        switch (operator) {
            case EQUAL_TO:
                return deviceDoubleValue == triggerDoubleValue;
            case NOT_EQUAL_TO:
                return deviceDoubleValue != triggerDoubleValue;
            case EXISTS:
            case CONTAINS:
            case NOT_EXISTS:
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.ERROR;
                new StringBuilder();
                OneSignal.onesignalLog(log_level, sb.append("Attempted to use an invalid operator with a numeric value: ").append(operator.toString()).toString());
                return false;
            case LESS_THAN:
                return deviceDoubleValue < triggerDoubleValue;
            case GREATER_THAN:
                return deviceDoubleValue > triggerDoubleValue;
            case LESS_THAN_OR_EQUAL_TO:
                return deviceDoubleValue < triggerDoubleValue || deviceDoubleValue == triggerDoubleValue;
            case GREATER_THAN_OR_EQUAL_TO:
                return deviceDoubleValue > triggerDoubleValue || deviceDoubleValue == triggerDoubleValue;
            default:
                return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isTriggerOnMessage(OSInAppMessage oSInAppMessage, Collection<String> newTriggersKeys) {
        OSInAppMessage message = oSInAppMessage;
        for (String triggerKey : newTriggersKeys) {
            Iterator<ArrayList<OSTrigger>> it = message.triggers.iterator();
            while (true) {
                if (it.hasNext()) {
                    Iterator<OSTrigger> it2 = it.next().iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (triggerKey.equals(it2.next().property)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public void addTriggers(Map<String, Object> map) {
        Map<String, Object> newTriggers = map;
        ConcurrentHashMap<String, Object> concurrentHashMap = this.triggers;
        ConcurrentHashMap<String, Object> concurrentHashMap2 = concurrentHashMap;
        synchronized (concurrentHashMap) {
            try {
                for (String key : newTriggers.keySet()) {
                    Object put = this.triggers.put(key, newTriggers.get(key));
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                ConcurrentHashMap<String, Object> concurrentHashMap3 = concurrentHashMap2;
                throw th2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public void removeTriggersForKeys(Collection<String> collection) {
        Collection<String> keys = collection;
        ConcurrentHashMap<String, Object> concurrentHashMap = this.triggers;
        ConcurrentHashMap<String, Object> concurrentHashMap2 = concurrentHashMap;
        synchronized (concurrentHashMap) {
            try {
                for (String key : keys) {
                    Object remove = this.triggers.remove(key);
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                ConcurrentHashMap<String, Object> concurrentHashMap3 = concurrentHashMap2;
                throw th2;
            }
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Object getTriggerValue(String str) {
        String key = str;
        ConcurrentHashMap<String, Object> concurrentHashMap = this.triggers;
        ConcurrentHashMap<String, Object> concurrentHashMap2 = concurrentHashMap;
        synchronized (concurrentHashMap) {
            try {
                if (this.triggers.containsKey(key)) {
                    Object obj = this.triggers.get(key);
                    return obj;
                }
                return null;
            } catch (Throwable th) {
                Throwable th2 = th;
                ConcurrentHashMap<String, Object> concurrentHashMap3 = concurrentHashMap2;
                throw th2;
            }
        }
    }
}
