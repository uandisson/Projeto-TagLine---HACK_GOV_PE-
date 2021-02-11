package com.onesignal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.WorkerThread;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalDbContract;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;

class OSInAppMessageRepository {
    private static final long OS_IAM_MAX_CACHE_TIME = 15552000;
    private final OneSignalDbHelper dbHelper;

    OSInAppMessageRepository(OneSignalDbHelper dbHelper2) {
        this.dbHelper = dbHelper2;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public synchronized void deleteOldRedisplayedInAppMessages() {
        synchronized (this) {
            int delete = this.dbHelper.getWritableDbWithRetries().delete(OneSignalDbContract.InAppMessageTable.TABLE_NAME, "last_display< ?", new String[]{String.valueOf((System.currentTimeMillis() / 1000) - OS_IAM_MAX_CACHE_TIME)});
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public synchronized void saveInAppMessage(OSInAppMessage oSInAppMessage) {
        ContentValues contentValues;
        OSInAppMessage inAppMessage = oSInAppMessage;
        synchronized (this) {
            SQLiteDatabase writableDb = this.dbHelper.getWritableDbWithRetries();
            new ContentValues();
            ContentValues values = contentValues;
            values.put(OneSignalDbContract.InAppMessageTable.COLUMN_NAME_MESSAGE_ID, inAppMessage.messageId);
            values.put(OneSignalDbContract.InAppMessageTable.COLUMN_NAME_DISPLAY_QUANTITY, Integer.valueOf(inAppMessage.getDisplayStats().getDisplayQuantity()));
            values.put(OneSignalDbContract.InAppMessageTable.COLUMN_NAME_LAST_DISPLAY, Long.valueOf(inAppMessage.getDisplayStats().getLastDisplayTime()));
            values.put(OneSignalDbContract.InAppMessageTable.COLUMN_CLICK_IDS, inAppMessage.getClickedClickIds().toString());
            values.put(OneSignalDbContract.InAppMessageTable.COLUMN_DISPLAYED_IN_SESSION, Boolean.valueOf(inAppMessage.isDisplayedInSession()));
            if (writableDb.update(OneSignalDbContract.InAppMessageTable.TABLE_NAME, values, "message_id = ?", new String[]{inAppMessage.messageId}) == 0) {
                long insert = writableDb.insert(OneSignalDbContract.InAppMessageTable.TABLE_NAME, (String) null, values);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public synchronized List<OSInAppMessage> getRedisplayedInAppMessages() {
        List<OSInAppMessage> list;
        List<OSInAppMessage> list2;
        JSONArray jSONArray;
        Set set;
        Object obj;
        OSInAppMessageDisplayStats oSInAppMessageDisplayStats;
        synchronized (this) {
            List<OSInAppMessage> list3 = list;
            new ArrayList();
            List<OSInAppMessage> iams = list3;
            try {
                Cursor cursor = this.dbHelper.getReadableDbWithRetries().query(OneSignalDbContract.InAppMessageTable.TABLE_NAME, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, (String) null);
                if (cursor.moveToFirst()) {
                    do {
                        String messageId = cursor.getString(cursor.getColumnIndex(OneSignalDbContract.InAppMessageTable.COLUMN_NAME_MESSAGE_ID));
                        String clickIds = cursor.getString(cursor.getColumnIndex(OneSignalDbContract.InAppMessageTable.COLUMN_CLICK_IDS));
                        int displayQuantity = cursor.getInt(cursor.getColumnIndex(OneSignalDbContract.InAppMessageTable.COLUMN_NAME_DISPLAY_QUANTITY));
                        long lastDisplay = cursor.getLong(cursor.getColumnIndex(OneSignalDbContract.InAppMessageTable.COLUMN_NAME_LAST_DISPLAY));
                        boolean displayed = cursor.getInt(cursor.getColumnIndex(OneSignalDbContract.InAppMessageTable.COLUMN_DISPLAYED_IN_SESSION)) == 1;
                        new JSONArray(clickIds);
                        JSONArray clickIdsArray = jSONArray;
                        new HashSet();
                        Set set2 = set;
                        for (int i = 0; i < clickIdsArray.length(); i++) {
                            boolean add = set2.add(clickIdsArray.getString(i));
                        }
                        new OSInAppMessageDisplayStats(displayQuantity, lastDisplay);
                        new OSInAppMessage(messageId, set2, displayed, oSInAppMessageDisplayStats);
                        boolean add2 = iams.add(obj);
                    } while (cursor.moveToNext());
                }
                if (cursor != null) {
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } catch (JSONException e) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating JSONArray from iam click ids:JSON Failed.", e);
                if (0 != 0) {
                    Cursor cursor2 = null;
                    if (!cursor2.isClosed()) {
                        Cursor cursor3 = null;
                        cursor3.close();
                    }
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                if (0 != 0) {
                    Cursor cursor4 = null;
                    if (!cursor4.isClosed()) {
                        Cursor cursor5 = null;
                        cursor5.close();
                    }
                }
                throw th2;
            }
            list2 = iams;
        }
        return list2;
    }
}
