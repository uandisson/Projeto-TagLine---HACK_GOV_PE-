package com.onesignal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.p000v4.app.NotificationManagerCompat;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalDbContract;
import org.json.JSONArray;
import org.json.JSONObject;

class NotificationOpenedProcessor {
    private static final String TAG = NotificationOpenedProcessor.class.getCanonicalName();

    NotificationOpenedProcessor() {
    }

    static void processFromContext(Context context, Intent intent) {
        Context context2 = context;
        Intent intent2 = intent;
        if (isOneSignalIntent(intent2)) {
            OneSignal.setAppContext(context2);
            handleDismissFromActionButtonPress(context2, intent2);
            processIntent(context2, intent2);
        }
    }

    private static boolean isOneSignalIntent(Intent intent) {
        Intent intent2 = intent;
        return intent2.hasExtra("onesignal_data") || intent2.hasExtra("summary") || intent2.hasExtra("notificationId");
    }

    private static void handleDismissFromActionButtonPress(Context context, Intent intent) {
        Intent intent2;
        Context context2 = context;
        Intent intent3 = intent;
        if (intent3.getBooleanExtra("action_button", false)) {
            NotificationManagerCompat.from(context2).cancel(intent3.getIntExtra("notificationId", 0));
            new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            context2.sendBroadcast(intent2);
        }
    }

    static void processIntent(Context context, Intent intent) {
        String group;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        Context context2 = context;
        Intent intent2 = intent;
        String summaryGroup = intent2.getStringExtra("summary");
        boolean dismissed = intent2.getBooleanExtra(OneSignalDbContract.NotificationTable.COLUMN_NAME_DISMISSED, false);
        JSONArray dataArray = null;
        JSONObject jsonData = null;
        if (!dismissed) {
            try {
                new JSONObject(intent2.getStringExtra("onesignal_data"));
                jsonData = jSONObject;
                if (!handleIAMPreviewOpen(context2, jsonData)) {
                    JSONObject put = jsonData.put("notificationId", intent2.getIntExtra("notificationId", 0));
                    Intent putExtra = intent2.putExtra("onesignal_data", jsonData.toString());
                    new JSONObject(intent2.getStringExtra("onesignal_data"));
                    dataArray = NotificationBundleProcessor.newJsonArray(jSONObject2);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        try {
            SQLiteDatabase writableDb = OneSignalDbHelper.getInstance(context2).getWritableDbWithRetries();
            writableDb.beginTransaction();
            if (!dismissed && summaryGroup != null) {
                addChildNotifications(dataArray, summaryGroup, writableDb);
            }
            markNotificationsConsumed(context2, intent2, writableDb, dismissed);
            if (summaryGroup == null && (group = intent2.getStringExtra("grp")) != null) {
                NotificationSummaryManager.updateSummaryNotificationAfterChildRemoved(context2, writableDb, group, dismissed);
            }
            writableDb.setTransactionSuccessful();
            if (writableDb != null) {
                try {
                    writableDb.endTransaction();
                } catch (Throwable th2) {
                    OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error closing transaction! ", th2);
                }
            }
        } catch (Exception e) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error processing notification open or dismiss record! ", e);
            if (0 != 0) {
                SQLiteDatabase sQLiteDatabase = null;
                sQLiteDatabase.endTransaction();
            }
        } catch (Throwable th3) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error closing transaction! ", th3);
        }
        if (!dismissed) {
            OneSignal.handleNotificationOpen(context2, dataArray, intent2.getBooleanExtra("from_alert", false), OneSignal.getNotificationIdFromGCMJson(jsonData));
        }
    }

    private static boolean handleIAMPreviewOpen(@NonNull Context context, @NonNull JSONObject jsonData) {
        Context context2 = context;
        String previewUUID = NotificationBundleProcessor.inAppPreviewPushUUID(jsonData);
        if (previewUUID == null) {
            return false;
        }
        boolean startOrResumeApp = OneSignal.startOrResumeApp(context2);
        OSInAppMessageController.getController().displayPreviewMessage(previewUUID);
        return true;
    }

    private static void addChildNotifications(JSONArray jSONArray, String str, SQLiteDatabase writableDb) {
        StringBuilder sb;
        Object obj;
        JSONArray dataArray = jSONArray;
        String summaryGroup = str;
        Cursor cursor = writableDb.query(OneSignalDbContract.NotificationTable.TABLE_NAME, new String[]{OneSignalDbContract.NotificationTable.COLUMN_NAME_FULL_DATA}, "group_id = ? AND dismissed = 0 AND opened = 0 AND is_summary = 0", new String[]{summaryGroup}, (String) null, (String) null, (String) null);
        if (cursor.getCount() > 1) {
            boolean moveToFirst = cursor.moveToFirst();
            do {
                try {
                    new JSONObject(cursor.getString(cursor.getColumnIndex(OneSignalDbContract.NotificationTable.COLUMN_NAME_FULL_DATA)));
                    JSONArray put = dataArray.put(obj);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.ERROR;
                    new StringBuilder();
                    OneSignal.Log(log_level, sb.append("Could not parse JSON of sub notification in group: ").append(summaryGroup).toString());
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private static void markNotificationsConsumed(Context context, Intent intent, SQLiteDatabase sQLiteDatabase, boolean z) {
        StringBuilder sb;
        String whereStr;
        StringBuilder sb2;
        String[] strArr;
        Context context2 = context;
        Intent intent2 = intent;
        SQLiteDatabase writableDb = sQLiteDatabase;
        boolean dismissed = z;
        String summaryGroup = intent2.getStringExtra("summary");
        String[] whereArgs = null;
        if (summaryGroup != null) {
            boolean isGroupless = summaryGroup.equals(OneSignalNotificationManager.getGrouplessSummaryKey());
            if (isGroupless) {
                whereStr = "group_id IS NULL";
            } else {
                whereStr = "group_id = ?";
                whereArgs = new String[]{summaryGroup};
            }
            if (!dismissed && !OneSignal.getClearGroupSummaryClick()) {
                String mostRecentId = String.valueOf(OneSignalNotificationManager.getMostRecentNotifIdFromGroup(writableDb, summaryGroup, isGroupless));
                new StringBuilder();
                whereStr = sb2.append(whereStr).append(" AND android_notification_id = ?").toString();
                if (isGroupless) {
                    String[] strArr2 = new String[1];
                    strArr = strArr2;
                    strArr2[0] = mostRecentId;
                } else {
                    String[] strArr3 = new String[2];
                    strArr3[0] = summaryGroup;
                    String[] strArr4 = strArr3;
                    strArr = strArr4;
                    strArr4[1] = mostRecentId;
                }
                whereArgs = strArr;
            }
        } else {
            new StringBuilder();
            whereStr = sb.append("android_notification_id = ").append(intent2.getIntExtra("notificationId", 0)).toString();
        }
        clearStatusBarNotifications(context2, writableDb, summaryGroup);
        int update = writableDb.update(OneSignalDbContract.NotificationTable.TABLE_NAME, newContentValuesWithConsumed(intent2), whereStr, whereArgs);
        BadgeCountUpdater.update(writableDb, context2);
    }

    private static void clearStatusBarNotifications(Context context, SQLiteDatabase sQLiteDatabase, String str) {
        Context context2 = context;
        SQLiteDatabase writableDb = sQLiteDatabase;
        String summaryGroup = str;
        if (summaryGroup != null) {
            NotificationSummaryManager.clearNotificationOnSummaryClick(context2, writableDb, summaryGroup);
        } else if (Build.VERSION.SDK_INT >= 23 && OneSignalNotificationManager.getGrouplessNotifsCount(context2).intValue() < 1) {
            OneSignalNotificationManager.getNotificationManager(context2).cancel(OneSignalNotificationManager.getGrouplessSummaryId());
        }
    }

    private static ContentValues newContentValuesWithConsumed(Intent intent) {
        ContentValues contentValues;
        new ContentValues();
        ContentValues values = contentValues;
        if (intent.getBooleanExtra(OneSignalDbContract.NotificationTable.COLUMN_NAME_DISMISSED, false)) {
            values.put(OneSignalDbContract.NotificationTable.COLUMN_NAME_DISMISSED, 1);
        } else {
            values.put(OneSignalDbContract.NotificationTable.COLUMN_NAME_OPENED, 1);
        }
        return values;
    }
}
