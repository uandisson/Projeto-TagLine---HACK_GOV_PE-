package org.osmdroid.tileprovider.modules;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public class SqliteArchiveTileWriter implements IFilesystemCache {
    static boolean hasInited = false;
    private static final String[] queryColumns = {DatabaseFileArchive.COLUMN_TILE};

    /* renamed from: db */
    final SQLiteDatabase f529db;
    final File db_file;
    final int questimate = 8000;

    public SqliteArchiveTileWriter(String str) throws Exception {
        File file;
        Throwable th;
        StringBuilder sb;
        String outputFile = str;
        new File(outputFile);
        this.db_file = file;
        try {
            this.f529db = SQLiteDatabase.openOrCreateDatabase(this.db_file.getAbsolutePath(), (SQLiteDatabase.CursorFactory) null);
            try {
                this.f529db.execSQL("CREATE TABLE IF NOT EXISTS tiles (key INTEGER , provider TEXT, tile BLOB, PRIMARY KEY (key, provider));");
            } catch (Throwable th2) {
                Throwable t = th2;
                t.printStackTrace();
                int d = Log.d(IMapView.LOGTAG, "error setting db schema, it probably exists already", t);
            }
        } catch (Exception e) {
            Exception ex = e;
            Throwable th3 = th;
            new StringBuilder();
            new Exception(sb.append("Trouble creating database file at ").append(outputFile).toString(), ex);
            throw th3;
        }
    }

    public boolean saveFile(ITileSource iTileSource, MapTile mapTile, InputStream inputStream) {
        StringBuilder sb;
        ContentValues contentValues;
        BufferedInputStream bufferedInputStream;
        List list;
        StringBuilder sb2;
        ITileSource pTileSourceInfo = iTileSource;
        MapTile pTile = mapTile;
        InputStream pStream = inputStream;
        try {
            new ContentValues();
            ContentValues cv = contentValues;
            long index = SqlTileWriter.getIndex(pTile);
            cv.put(DatabaseFileArchive.COLUMN_PROVIDER, pTileSourceInfo.name());
            new BufferedInputStream(pStream);
            BufferedInputStream bis = bufferedInputStream;
            new ArrayList();
            List list2 = list;
            while (true) {
                int read = bis.read();
                int current = read;
                if (read == -1) {
                    break;
                }
                boolean add = list2.add(Byte.valueOf((byte) current));
            }
            byte[] bits = new byte[list2.size()];
            for (int i = 0; i < list2.size(); i++) {
                bits[i] = ((Byte) list2.get(i)).byteValue();
            }
            cv.put(DatabaseFileArchive.COLUMN_KEY, Long.valueOf(index));
            cv.put(DatabaseFileArchive.COLUMN_TILE, bits);
            long insert = this.f529db.insert("tiles", (String) null, cv);
            if (Configuration.getInstance().isDebugMode()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb2.append("tile inserted ").append(pTileSourceInfo.name()).append(pTile.toString()).toString());
            }
        } catch (Throwable th) {
            new StringBuilder();
            int e = Log.e(IMapView.LOGTAG, sb.append("Unable to store cached tile from ").append(pTileSourceInfo.name()).append(" ").append(pTile.toString()).toString(), th);
        }
        return false;
    }

    public boolean exists(ITileSource iTileSource, MapTile mapTile) {
        StringBuilder sb;
        ITileSource pTileSource = iTileSource;
        MapTile pTile = mapTile;
        try {
            Cursor cur = getTileCursor(SqlTileWriter.getPrimaryKeyParameters(SqlTileWriter.getIndex(pTile), pTileSource));
            boolean result = cur.getCount() != 0;
            cur.close();
            return result;
        } catch (Throwable th) {
            new StringBuilder();
            int e = Log.e(IMapView.LOGTAG, sb.append("Unable to store cached tile from ").append(pTileSource.name()).append(" ").append(pTile.toString()).toString(), th);
            return false;
        }
    }

    public void onDetach() {
        if (this.f529db != null) {
            this.f529db.close();
        }
    }

    public boolean remove(ITileSource iTileSource, MapTile mapTile) {
        ITileSource iTileSource2 = iTileSource;
        MapTile mapTile2 = mapTile;
        return false;
    }

    public Long getExpirationTimestamp(ITileSource iTileSource, MapTile mapTile) {
        ITileSource iTileSource2 = iTileSource;
        MapTile mapTile2 = mapTile;
        return null;
    }

    public Cursor getTileCursor(String[] pPrimaryKeyParameters) {
        return this.f529db.query("tiles", queryColumns, SqlTileWriter.getPrimaryKey(), pPrimaryKeyParameters, (String) null, (String) null, (String) null);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: type inference failed for: r11v3, types: [java.io.InputStream] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.drawable.Drawable loadTile(org.osmdroid.tileprovider.tilesource.ITileSource r16, org.osmdroid.tileprovider.MapTile r17) throws java.lang.Exception {
        /*
            r15 = this;
            r0 = r15
            r1 = r16
            r2 = r17
            r10 = 0
            r3 = r10
            r10 = r2
            long r10 = org.osmdroid.tileprovider.modules.SqlTileWriter.getIndex(r10)     // Catch:{ all -> 0x0095 }
            r4 = r10
            r10 = r0
            r11 = r4
            r13 = r1
            java.lang.String[] r11 = org.osmdroid.tileprovider.modules.SqlTileWriter.getPrimaryKeyParameters((long) r11, (org.osmdroid.tileprovider.tilesource.ITileSource) r13)     // Catch:{ all -> 0x0095 }
            android.database.Cursor r10 = r10.getTileCursor(r11)     // Catch:{ all -> 0x0095 }
            r6 = r10
            r10 = 0
            r7 = r10
            r10 = r6
            int r10 = r10.getCount()     // Catch:{ all -> 0x0095 }
            if (r10 == 0) goto L_0x0035
            r10 = r6
            boolean r10 = r10.moveToFirst()     // Catch:{ all -> 0x0095 }
            r10 = r6
            r11 = r6
            java.lang.String r12 = "tile"
            int r11 = r11.getColumnIndex(r12)     // Catch:{ all -> 0x0095 }
            byte[] r10 = r10.getBlob(r11)     // Catch:{ all -> 0x0095 }
            r7 = r10
        L_0x0035:
            r10 = r6
            r10.close()     // Catch:{ all -> 0x0095 }
            r10 = r7
            if (r10 != 0) goto L_0x007a
            org.osmdroid.config.IConfigurationProvider r10 = org.osmdroid.config.Configuration.getInstance()     // Catch:{ all -> 0x0095 }
            boolean r10 = r10.isDebugMode()     // Catch:{ all -> 0x0095 }
            if (r10 == 0) goto L_0x006e
            java.lang.String r10 = "OsmDroid"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0095 }
            r14 = r11
            r11 = r14
            r12 = r14
            r12.<init>()     // Catch:{ all -> 0x0095 }
            java.lang.String r12 = "SqlCache - Tile doesn't exist: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0095 }
            r12 = r1
            java.lang.String r12 = r12.name()     // Catch:{ all -> 0x0095 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0095 }
            r12 = r2
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0095 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0095 }
            int r10 = android.util.Log.d(r10, r11)     // Catch:{ all -> 0x0095 }
        L_0x006e:
            r10 = 0
            r8 = r10
            r10 = r3
            if (r10 == 0) goto L_0x0077
            r10 = r3
            org.osmdroid.tileprovider.util.StreamUtils.closeStream(r10)
        L_0x0077:
            r10 = r8
            r0 = r10
        L_0x0079:
            return r0
        L_0x007a:
            java.io.ByteArrayInputStream r10 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0095 }
            r14 = r10
            r10 = r14
            r11 = r14
            r12 = r7
            r11.<init>(r12)     // Catch:{ all -> 0x0095 }
            r3 = r10
            r10 = r1
            r11 = r3
            android.graphics.drawable.Drawable r10 = r10.getDrawable((java.io.InputStream) r11)     // Catch:{ all -> 0x0095 }
            r8 = r10
            r10 = r3
            if (r10 == 0) goto L_0x0092
            r10 = r3
            org.osmdroid.tileprovider.util.StreamUtils.closeStream(r10)
        L_0x0092:
            r10 = r8
            r0 = r10
            goto L_0x0079
        L_0x0095:
            r10 = move-exception
            r9 = r10
            r10 = r3
            if (r10 == 0) goto L_0x009e
            r10 = r3
            org.osmdroid.tileprovider.util.StreamUtils.closeStream(r10)
        L_0x009e:
            r10 = r9
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.SqliteArchiveTileWriter.loadTile(org.osmdroid.tileprovider.tilesource.ITileSource, org.osmdroid.tileprovider.MapTile):android.graphics.drawable.Drawable");
    }
}
