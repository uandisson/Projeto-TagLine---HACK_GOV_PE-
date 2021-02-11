package com.onesignal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalDbContract;
import java.util.ArrayList;
import java.util.List;

public class OneSignalDbHelper extends SQLiteOpenHelper {
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "OneSignal.db";
    static final int DATABASE_VERSION = 7;
    private static final int DB_OPEN_RETRY_BACKOFF = 400;
    private static final int DB_OPEN_RETRY_MAX = 5;
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String INTEGER_PRIMARY_KEY_TYPE = " INTEGER PRIMARY KEY";
    private static final String INT_TYPE = " INTEGER";
    protected static final String SQL_CREATE_ENTRIES = "CREATE TABLE notification (_id INTEGER PRIMARY KEY,notification_id TEXT,android_notification_id INTEGER,group_id TEXT,collapse_id TEXT,is_summary INTEGER DEFAULT 0,opened INTEGER DEFAULT 0,dismissed INTEGER DEFAULT 0,title TEXT,message TEXT,full_data TEXT,created_time TIMESTAMP DEFAULT (strftime('%s', 'now')),expire_time TIMESTAMP);";
    private static final String SQL_CREATE_IN_APP_MESSAGE_ENTRIES = "CREATE TABLE in_app_message (_id INTEGER PRIMARY KEY,display_quantity INTEGER,last_display INTEGER,message_id TEXT,displayed_in_session INTEGER,click_ids TEXT);";
    private static final String SQL_CREATE_OUTCOME_ENTRIES = "CREATE TABLE outcome (_id INTEGER PRIMARY KEY,session TEXT,notification_ids TEXT,name TEXT,timestamp TIMESTAMP,weight FLOAT);";
    private static final String SQL_CREATE_UNIQUE_OUTCOME_NOTIFICATION_ENTRIES = "CREATE TABLE cached_unique_outcome_notification (_id INTEGER PRIMARY KEY,notification_id TEXT,name TEXT);";
    protected static final String[] SQL_INDEX_ENTRIES;
    private static final String TEXT_TYPE = " TEXT";
    private static final String TIMESTAMP_TYPE = " TIMESTAMP";
    private static OneSignalDbHelper sInstance;

    static {
        String[] strArr = new String[6];
        strArr[0] = OneSignalDbContract.NotificationTable.INDEX_CREATE_NOTIFICATION_ID;
        String[] strArr2 = strArr;
        strArr2[1] = OneSignalDbContract.NotificationTable.INDEX_CREATE_ANDROID_NOTIFICATION_ID;
        String[] strArr3 = strArr2;
        strArr3[2] = OneSignalDbContract.NotificationTable.INDEX_CREATE_GROUP_ID;
        String[] strArr4 = strArr3;
        strArr4[3] = OneSignalDbContract.NotificationTable.INDEX_CREATE_COLLAPSE_ID;
        String[] strArr5 = strArr4;
        strArr5[4] = OneSignalDbContract.NotificationTable.INDEX_CREATE_CREATED_TIME;
        String[] strArr6 = strArr5;
        strArr6[5] = OneSignalDbContract.NotificationTable.INDEX_CREATE_EXPIRE_TIME;
        SQL_INDEX_ENTRIES = strArr6;
    }

    private static int getDbVersion() {
        return 7;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    OneSignalDbHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, getDbVersion());
    }

    public static synchronized OneSignalDbHelper getInstance(Context context) {
        OneSignalDbHelper oneSignalDbHelper;
        OneSignalDbHelper oneSignalDbHelper2;
        Context context2 = context;
        synchronized (OneSignalDbHelper.class) {
            if (sInstance == null) {
                new OneSignalDbHelper(context2.getApplicationContext());
                sInstance = oneSignalDbHelper2;
            }
            oneSignalDbHelper = sInstance;
        }
        return oneSignalDbHelper;
    }

    /* access modifiers changed from: package-private */
    public synchronized SQLiteDatabase getWritableDbWithRetries() {
        SQLiteDatabase writableDatabase;
        synchronized (this) {
            int count = 0;
            while (true) {
                try {
                    writableDatabase = getWritableDatabase();
                } catch (SQLiteCantOpenDatabaseException e) {
                    SQLiteCantOpenDatabaseException e2 = e;
                    count++;
                    if (count >= 5) {
                        throw e2;
                    }
                    SystemClock.sleep((long) (count * 400));
                }
            }
        }
        return writableDatabase;
    }

    /* access modifiers changed from: package-private */
    public synchronized SQLiteDatabase getReadableDbWithRetries() {
        SQLiteDatabase readableDatabase;
        synchronized (this) {
            int count = 0;
            while (true) {
                try {
                    readableDatabase = getReadableDatabase();
                } catch (SQLiteCantOpenDatabaseException e) {
                    SQLiteCantOpenDatabaseException e2 = e;
                    count++;
                    if (count >= 5) {
                        throw e2;
                    }
                    SystemClock.sleep((long) (count * 400));
                }
            }
        }
        return readableDatabase;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase db = sQLiteDatabase;
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_OUTCOME_ENTRIES);
        db.execSQL(SQL_CREATE_UNIQUE_OUTCOME_NOTIFICATION_ENTRIES);
        db.execSQL(SQL_CREATE_IN_APP_MESSAGE_ENTRIES);
        String[] strArr = SQL_INDEX_ENTRIES;
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            db.execSQL(strArr[i]);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int i) {
        int i2 = i;
        try {
            internalOnUpgrade(db, oldVersion);
        } catch (SQLiteException e) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error in upgrade, migration may have already run! Skipping!", e);
        }
    }

    private static void internalOnUpgrade(SQLiteDatabase sQLiteDatabase, int i) {
        SQLiteDatabase db = sQLiteDatabase;
        int oldVersion = i;
        if (oldVersion < 2) {
            upgradeToV2(db);
        }
        if (oldVersion < 3) {
            upgradeToV3(db);
        }
        if (oldVersion < 4) {
            upgradeToV4(db);
        }
        if (oldVersion < 5) {
            upgradeToV5(db);
        }
        if (oldVersion == 5) {
            upgradeFromV5ToV6(db);
        }
        if (oldVersion < 7) {
            upgradeToV7(db);
        }
    }

    private static void upgradeToV2(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase db = sQLiteDatabase;
        safeExecSQL(db, "ALTER TABLE notification ADD COLUMN collapse_id TEXT;");
        safeExecSQL(db, OneSignalDbContract.NotificationTable.INDEX_CREATE_GROUP_ID);
    }

    private static void upgradeToV3(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase db = sQLiteDatabase;
        safeExecSQL(db, "ALTER TABLE notification ADD COLUMN expire_time TIMESTAMP;");
        safeExecSQL(db, "UPDATE notification SET expire_time = created_time + 259200;");
        safeExecSQL(db, OneSignalDbContract.NotificationTable.INDEX_CREATE_EXPIRE_TIME);
    }

    private static void upgradeToV4(SQLiteDatabase db) {
        safeExecSQL(db, SQL_CREATE_OUTCOME_ENTRIES);
    }

    private static void upgradeToV5(SQLiteDatabase sQLiteDatabase) {
        SQLiteDatabase db = sQLiteDatabase;
        safeExecSQL(db, SQL_CREATE_UNIQUE_OUTCOME_NOTIFICATION_ENTRIES);
        upgradeOutcomeTableRevision1To2(db);
    }

    private static void upgradeFromV5ToV6(SQLiteDatabase db) {
        upgradeOutcomeTableRevision1To2(db);
    }

    private static void upgradeToV7(SQLiteDatabase db) {
        safeExecSQL(db, SQL_CREATE_IN_APP_MESSAGE_ENTRIES);
    }

    private static void upgradeOutcomeTableRevision1To2(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        SQLiteDatabase db = sQLiteDatabase;
        String commonColumns = "_id,name,session,timestamp,notification_ids";
        try {
            db.execSQL("BEGIN TRANSACTION;");
            new StringBuilder();
            db.execSQL(sb.append("CREATE TEMPORARY TABLE outcome_backup(").append(commonColumns).append(");").toString());
            new StringBuilder();
            db.execSQL(sb2.append("INSERT INTO outcome_backup SELECT ").append(commonColumns).append(" FROM outcome;").toString());
            db.execSQL("DROP TABLE outcome;");
            db.execSQL(SQL_CREATE_OUTCOME_ENTRIES);
            new StringBuilder();
            db.execSQL(sb3.append("INSERT INTO outcome (").append(commonColumns).append(", weight) SELECT ").append(commonColumns).append(", 0 FROM outcome_backup;").toString());
            db.execSQL("DROP TABLE outcome_backup;");
            db.execSQL("COMMIT;");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    private static void safeExecSQL(SQLiteDatabase db, String sql) {
        try {
            db.execSQL(sql);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        List list;
        StringBuilder sb;
        SQLiteDatabase db = sQLiteDatabase;
        int i3 = i;
        int i4 = i2;
        OneSignal.Log(OneSignal.LOG_LEVEL.WARN, "SDK version rolled back! Clearing OneSignal.db as it could be in an unexpected state.");
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", (String[]) null);
        try {
            new ArrayList(cursor.getCount());
            List<String> list2 = list;
            while (cursor.moveToNext()) {
                boolean add = list2.add(cursor.getString(0));
            }
            for (String table : list2) {
                if (!table.startsWith("sqlite_")) {
                    new StringBuilder();
                    db.execSQL(sb.append("DROP TABLE IF EXISTS ").append(table).toString());
                }
            }
            cursor.close();
            onCreate(db);
        } catch (Throwable th) {
            Throwable th2 = th;
            cursor.close();
            throw th2;
        }
    }

    static StringBuilder recentUninteractedWithNotificationsWhere() {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        long currentTimeSec = System.currentTimeMillis() / 1000;
        new StringBuilder();
        new StringBuilder(sb2.append("created_time > ").append(currentTimeSec - 604800).append(" AND ").append(OneSignalDbContract.NotificationTable.COLUMN_NAME_DISMISSED).append(" = 0 AND ").append(OneSignalDbContract.NotificationTable.COLUMN_NAME_OPENED).append(" = 0 AND ").append(OneSignalDbContract.NotificationTable.COLUMN_NAME_IS_SUMMARY).append(" = 0").toString());
        StringBuilder where = sb;
        if (OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_RESTORE_TTL_FILTER, true)) {
            new StringBuilder();
            StringBuilder append = where.append(sb3.append(" AND expire_time > ").append(currentTimeSec).toString());
        }
        return where;
    }

    public void cleanOutcomeDatabase() {
        int delete = getWritableDatabase().delete(OneSignalDbContract.OutcomeEventsTable.TABLE_NAME, (String) null, (String[]) null);
    }
}
