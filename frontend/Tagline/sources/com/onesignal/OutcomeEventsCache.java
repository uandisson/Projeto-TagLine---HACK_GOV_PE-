package com.onesignal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.WorkerThread;
import com.onesignal.OSSessionManager;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalDbContract;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

class OutcomeEventsCache {
    OutcomeEventsCache() {
    }

    @WorkerThread
    static synchronized void deleteOldOutcomeEvent(OutcomeEvent outcomeEvent, OneSignalDbHelper oneSignalDbHelper) {
        OutcomeEvent event = outcomeEvent;
        OneSignalDbHelper dbHelper = oneSignalDbHelper;
        synchronized (OutcomeEventsCache.class) {
            SQLiteDatabase writableDb = dbHelper.getWritableDbWithRetries();
            try {
                writableDb.beginTransaction();
                int delete = writableDb.delete(OneSignalDbContract.OutcomeEventsTable.TABLE_NAME, "timestamp = ?", new String[]{String.valueOf(event.getTimestamp())});
                writableDb.setTransactionSuccessful();
                if (writableDb != null) {
                    try {
                        writableDb.endTransaction();
                    } catch (SQLiteException e) {
                        OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error closing transaction! ", e);
                    }
                }
            } catch (SQLiteException e2) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error deleting old outcome event records! ", e2);
                if (writableDb != null) {
                    try {
                        writableDb.endTransaction();
                    } catch (SQLiteException e3) {
                        OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error closing transaction! ", e3);
                    }
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                if (writableDb != null) {
                    try {
                        writableDb.endTransaction();
                    } catch (SQLiteException e4) {
                        OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error closing transaction! ", e4);
                    }
                }
                throw th2;
            }
        }
        return;
    }

    @WorkerThread
    static synchronized void saveOutcomeEvent(OutcomeEvent outcomeEvent, OneSignalDbHelper oneSignalDbHelper) {
        ContentValues contentValues;
        OutcomeEvent event = outcomeEvent;
        OneSignalDbHelper dbHelper = oneSignalDbHelper;
        synchronized (OutcomeEventsCache.class) {
            SQLiteDatabase writableDb = dbHelper.getWritableDbWithRetries();
            String notificationIds = event.getNotificationIds() != null ? event.getNotificationIds().toString() : "[]";
            new ContentValues();
            ContentValues values = contentValues;
            values.put(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_NOTIFICATION_IDS, notificationIds);
            values.put(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_SESSION, event.getSession().toString().toLowerCase());
            values.put("name", event.getName());
            values.put(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_TIMESTAMP, Long.valueOf(event.getTimestamp()));
            values.put(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_WEIGHT, Float.valueOf(event.getWeight()));
            long insert = writableDb.insert(OneSignalDbContract.OutcomeEventsTable.TABLE_NAME, (String) null, values);
        }
    }

    @WorkerThread
    static synchronized List<OutcomeEvent> getAllEventsToSend(OneSignalDbHelper oneSignalDbHelper) {
        List<OutcomeEvent> list;
        List<OutcomeEvent> list2;
        Object obj;
        JSONArray jSONArray;
        OneSignalDbHelper dbHelper = oneSignalDbHelper;
        synchronized (OutcomeEventsCache.class) {
            List<OutcomeEvent> list3 = list;
            new ArrayList();
            List<OutcomeEvent> events = list3;
            Cursor cursor = null;
            try {
                cursor = dbHelper.getReadableDbWithRetries().query(OneSignalDbContract.OutcomeEventsTable.TABLE_NAME, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, (String) null);
                if (cursor.moveToFirst()) {
                    do {
                        OSSessionManager.Session session = OSSessionManager.Session.fromString(cursor.getString(cursor.getColumnIndex(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_SESSION)));
                        String notificationIds = cursor.getString(cursor.getColumnIndex(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_NOTIFICATION_IDS));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        long timestamp = cursor.getLong(cursor.getColumnIndex(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_TIMESTAMP));
                        float weight = cursor.getFloat(cursor.getColumnIndex(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_WEIGHT));
                        new JSONArray(notificationIds);
                        new OutcomeEvent(session, jSONArray, name, timestamp, weight);
                        boolean add = events.add(obj);
                    } while (cursor.moveToNext());
                }
            } catch (JSONException e) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating JSONArray from notifications ids outcome:JSON Failed.", e);
            } catch (Throwable th) {
                Throwable th2 = th;
                if (cursor != null) {
                    if (!cursor.isClosed()) {
                        cursor.close();
                    }
                }
                throw th2;
            }
            if (cursor != null) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
            list2 = events;
        }
        return list2;
    }

    @WorkerThread
    static synchronized void saveUniqueOutcomeNotifications(JSONArray jSONArray, String str, OneSignalDbHelper oneSignalDbHelper) {
        ContentValues contentValues;
        JSONArray notificationIds = jSONArray;
        String outcomeName = str;
        OneSignalDbHelper dbHelper = oneSignalDbHelper;
        synchronized (OutcomeEventsCache.class) {
            if (notificationIds != null) {
                SQLiteDatabase writableDb = dbHelper.getWritableDbWithRetries();
                int i = 0;
                while (i < notificationIds.length()) {
                    try {
                        new ContentValues();
                        ContentValues values = contentValues;
                        values.put("notification_id", notificationIds.getString(i));
                        values.put("name", outcomeName);
                        long insert = writableDb.insert(OneSignalDbContract.CachedUniqueOutcomeNotificationTable.TABLE_NAME, (String) null, values);
                        i++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return;
    }

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.inline(CodeShrinkVisitor.java:146)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:71)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    @android.support.annotation.WorkerThread
    static synchronized org.json.JSONArray getNotCachedUniqueOutcomeNotifications(java.lang.String r24, org.json.JSONArray r25, com.onesignal.OneSignalDbHelper r26) {
        /*
            r0 = r24
            r1 = r25
            r2 = r26
            java.lang.Class<com.onesignal.OutcomeEventsCache> r22 = com.onesignal.OutcomeEventsCache.class
            monitor-enter(r22)
            org.json.JSONArray r13 = new org.json.JSONArray     // Catch:{ all -> 0x00c7 }
            r23 = r13
            r13 = r23
            r14 = r23
            r14.<init>()     // Catch:{ all -> 0x00c7 }
            r3 = r13
            r13 = r2
            android.database.sqlite.SQLiteDatabase r13 = r13.getReadableDbWithRetries()     // Catch:{ all -> 0x00c7 }
            r4 = r13
            r13 = 0
            r5 = r13
            r13 = 0
            r6 = r13
        L_0x001f:
            r13 = r6
            r14 = r1
            int r14 = r14.length()     // Catch:{ JSONException -> 0x00a0 }
            if (r13 >= r14) goto L_0x008e
            r13 = r1
            r14 = r6
            java.lang.String r13 = r13.getString(r14)     // Catch:{ JSONException -> 0x00a0 }
            r7 = r13
            com.onesignal.CachedUniqueOutcomeNotification r13 = new com.onesignal.CachedUniqueOutcomeNotification     // Catch:{ JSONException -> 0x00a0 }
            r23 = r13
            r13 = r23
            r14 = r23
            r15 = r7
            r16 = r0
            r14.<init>(r15, r16)     // Catch:{ JSONException -> 0x00a0 }
            r8 = r13
            r13 = 0
            java.lang.String[] r13 = new java.lang.String[r13]     // Catch:{ JSONException -> 0x00a0 }
            r9 = r13
            java.lang.String r13 = "notification_id = ? AND name = ?"
            r10 = r13
            r13 = 2
            java.lang.String[] r13 = new java.lang.String[r13]     // Catch:{ JSONException -> 0x00a0 }
            r23 = r13
            r13 = r23
            r14 = r23
            r15 = 0
            r16 = r8
            java.lang.String r16 = r16.getNotificationId()     // Catch:{ JSONException -> 0x00a0 }
            r14[r15] = r16     // Catch:{ JSONException -> 0x00a0 }
            r23 = r13
            r13 = r23
            r14 = r23
            r15 = 1
            r16 = r8
            java.lang.String r16 = r16.getName()     // Catch:{ JSONException -> 0x00a0 }
            r14[r15] = r16     // Catch:{ JSONException -> 0x00a0 }
            r11 = r13
            r13 = r4
            java.lang.String r14 = "cached_unique_outcome_notification"
            r15 = r9
            r16 = r10
            r17 = r11
            r18 = 0
            r19 = 0
            r20 = 0
            java.lang.String r21 = "1"
            android.database.Cursor r13 = r13.query(r14, r15, r16, r17, r18, r19, r20, r21)     // Catch:{ JSONException -> 0x00a0 }
            r5 = r13
            r13 = r5
            int r13 = r13.getCount()     // Catch:{ JSONException -> 0x00a0 }
            if (r13 != 0) goto L_0x008b
            r13 = r3
            r14 = r7
            org.json.JSONArray r13 = r13.put(r14)     // Catch:{ JSONException -> 0x00a0 }
        L_0x008b:
            int r6 = r6 + 1
            goto L_0x001f
        L_0x008e:
            r13 = r5
            if (r13 == 0) goto L_0x009c
            r13 = r5
            boolean r13 = r13.isClosed()     // Catch:{ all -> 0x00c7 }
            if (r13 != 0) goto L_0x009c
            r13 = r5
            r13.close()     // Catch:{ all -> 0x00c7 }
        L_0x009c:
            r13 = r3
            r0 = r13
            monitor-exit(r22)
            return r0
        L_0x00a0:
            r13 = move-exception
            r6 = r13
            r13 = r6
            r13.printStackTrace()     // Catch:{ all -> 0x00b5 }
            r13 = r5
            if (r13 == 0) goto L_0x009c
            r13 = r5
            boolean r13 = r13.isClosed()     // Catch:{ all -> 0x00c7 }
            if (r13 != 0) goto L_0x009c
            r13 = r5
            r13.close()     // Catch:{ all -> 0x00c7 }
            goto L_0x009c
        L_0x00b5:
            r13 = move-exception
            r12 = r13
            r13 = r5
            if (r13 == 0) goto L_0x00c5
            r13 = r5
            boolean r13 = r13.isClosed()     // Catch:{ all -> 0x00c7 }
            if (r13 != 0) goto L_0x00c5
            r13 = r5
            r13.close()     // Catch:{ all -> 0x00c7 }
        L_0x00c5:
            r13 = r12
            throw r13     // Catch:{ all -> 0x00c7 }
        L_0x00c7:
            r0 = move-exception
            monitor-exit(r22)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OutcomeEventsCache.getNotCachedUniqueOutcomeNotifications(java.lang.String, org.json.JSONArray, com.onesignal.OneSignalDbHelper):org.json.JSONArray");
    }
}
