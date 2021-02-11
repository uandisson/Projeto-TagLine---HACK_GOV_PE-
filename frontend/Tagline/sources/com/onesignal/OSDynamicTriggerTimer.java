package com.onesignal;

import java.util.Timer;
import java.util.TimerTask;

class OSDynamicTriggerTimer {
    OSDynamicTriggerTimer() {
    }

    static void scheduleTrigger(TimerTask task, String triggerId, long delay) {
        Timer timer;
        StringBuilder sb;
        new StringBuilder();
        new Timer(sb.append("trigger_timer:").append(triggerId).toString());
        timer.schedule(task, delay);
    }
}
