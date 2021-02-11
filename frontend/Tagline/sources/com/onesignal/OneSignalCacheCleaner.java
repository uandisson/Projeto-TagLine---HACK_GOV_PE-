package com.onesignal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.onesignal.OneSignalDbContract;

class OneSignalCacheCleaner {
    private static String OS_DELETE_OLD_CACHED_DATA = "OS_DELETE_OLD_CACHED_DATA";

    OneSignalCacheCleaner() {
    }

    static synchronized void cleanOldCachedData(Context context) {
        Thread thread;
        Runnable runnable;
        Context context2 = context;
        synchronized (OneSignalCacheCleaner.class) {
            final Context context3 = context2;
            new Runnable() {
                public void run() {
                    Thread.currentThread().setPriority(10);
                    OneSignalCacheCleaner.cleanInAppMessagingCache();
                    OneSignalCacheCleaner.cleanNotificationCache(OneSignalDbHelper.getInstance(context3).getWritableDbWithRetries());
                }
            };
            new Thread(runnable, OS_DELETE_OLD_CACHED_DATA);
            thread.start();
        }
    }

    static void cleanInAppMessagingCache() {
    }

    static void cleanNotificationCache(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase writableDb = sQLiteDatabase;
        cleanOldNotificationData(writableDb);
        cleanOldUniqueOutcomeEventNotificationsCache(writableDb);
    }

    private static void cleanOldNotificationData(SQLiteDatabase writableDb) {
        StringBuilder sb;
        new StringBuilder();
        int delete = writableDb.delete(OneSignalDbContract.NotificationTable.TABLE_NAME, sb.append("created_time < ").append((System.currentTimeMillis() / 1000) - 604800).toString(), (String[]) null);
    }

    static void cleanOldUniqueOutcomeEventNotificationsCache(SQLiteDatabase writableDb) {
        int delete = writableDb.delete(OneSignalDbContract.CachedUniqueOutcomeNotificationTable.TABLE_NAME, "NOT EXISTS(SELECT NULL FROM notification n WHERE n.notification_id = notification_id)", (String[]) null);
    }
}
