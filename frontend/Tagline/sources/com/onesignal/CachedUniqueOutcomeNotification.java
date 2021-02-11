package com.onesignal;

class CachedUniqueOutcomeNotification {
    private String name;
    private String notificationId;

    CachedUniqueOutcomeNotification(String notificationId2, String name2) {
        this.notificationId = notificationId2;
        this.name = name2;
    }

    /* access modifiers changed from: package-private */
    public String getNotificationId() {
        return this.notificationId;
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return this.name;
    }
}
