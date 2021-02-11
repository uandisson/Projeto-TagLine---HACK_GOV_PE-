package org.osmdroid.tileprovider.modules;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteFullException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.ExpirableBitmapDrawable;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.util.Counters;
import org.osmdroid.tileprovider.util.StreamUtils;

public class SqlTileWriter implements IFilesystemCache {
    public static boolean CLEANUP_ON_START = true;
    public static final String COLUMN_EXPIRES = "expires";
    public static final String DATABASE_FILENAME = "cache.db";
    private static final String[] expireQueryColumn = {COLUMN_EXPIRES};
    static boolean hasInited = false;
    private static final String primaryKey = "key=? and provider=?";
    private static final String[] queryColumns;

    /* renamed from: db */
    protected SQLiteDatabase f528db;
    protected File db_file;
    protected long lastSizeCheck = 0;
    long tileSize = 0;

    static {
        String[] strArr = new String[2];
        strArr[0] = DatabaseFileArchive.COLUMN_TILE;
        String[] strArr2 = strArr;
        strArr2[1] = COLUMN_EXPIRES;
        queryColumns = strArr2;
    }

    public SqlTileWriter() {
        File file;
        StringBuilder sb;
        Thread thread;
        boolean mkdirs = Configuration.getInstance().getOsmdroidTileCache().mkdirs();
        new StringBuilder();
        new File(sb.append(Configuration.getInstance().getOsmdroidTileCache().getAbsolutePath()).append(File.separator).append(DATABASE_FILENAME).toString());
        this.db_file = file;
        try {
            this.f528db = SQLiteDatabase.openOrCreateDatabase(this.db_file, (SQLiteDatabase.CursorFactory) null);
            this.f528db.execSQL("CREATE TABLE IF NOT EXISTS tiles (key INTEGER , provider TEXT, tile BLOB, expires INTEGER, PRIMARY KEY (key, provider));");
        } catch (Throwable th) {
            int e = Log.e(IMapView.LOGTAG, "Unable to start the sqlite tile writer. Check external storage availability.", th);
        }
        if (!hasInited) {
            hasInited = true;
            if (CLEANUP_ON_START) {
                new Thread(this) {
                    final /* synthetic */ SqlTileWriter this$0;

                    {
                        this.this$0 = this$0;
                    }

                    public void run() {
                        this.this$0.runCleanupOperation();
                    }
                };
                Thread t = thread;
                t.setPriority(1);
                t.start();
            }
        }
    }

    public void runCleanupOperation() {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        StringBuilder sb5;
        if (this.f528db != null) {
            try {
                if (this.db_file.length() > Configuration.getInstance().getTileFileSystemCacheMaxBytes()) {
                    long now = System.currentTimeMillis();
                    new StringBuilder();
                    int i = Log.i(IMapView.LOGTAG, sb.append("Local cache is now ").append(this.db_file.length()).append(" max size is ").append(Configuration.getInstance().getTileFileSystemCacheMaxBytes()).toString());
                    long diff = this.db_file.length() - Configuration.getInstance().getTileFileSystemCacheTrimBytes();
                    if (this.tileSize == 0) {
                        long count = getRowCount((String) null);
                        this.tileSize = count > 0 ? this.db_file.length() / count : 4000;
                        if (Configuration.getInstance().isDebugMode()) {
                            new StringBuilder();
                            int d = Log.d(IMapView.LOGTAG, sb5.append("Number of cached tiles is ").append(count).append(", mean size is ").append(this.tileSize).toString());
                        }
                    }
                    long tilesToKill = diff / this.tileSize;
                    new StringBuilder();
                    int d2 = Log.d(IMapView.LOGTAG, sb2.append("Local cache purging ").append(tilesToKill).append(" tiles.").toString());
                    if (tilesToKill > 0) {
                        try {
                            SQLiteDatabase sQLiteDatabase = this.f528db;
                            new StringBuilder();
                            sQLiteDatabase.execSQL(sb4.append("DELETE FROM tiles WHERE key in (SELECT key FROM tiles ORDER BY expires DESC LIMIT ").append(tilesToKill).append(")").toString());
                        } catch (Throwable th) {
                            int e = Log.e(IMapView.LOGTAG, "error purging tiles from the tile cache", th);
                        }
                    }
                    new StringBuilder();
                    int d3 = Log.d(IMapView.LOGTAG, sb3.append("purge completed in ").append(System.currentTimeMillis() - now).append("ms, cache size is ").append(this.db_file.length()).append(" bytes").toString());
                }
            } catch (Exception e2) {
                Exception ex = e2;
                if (Configuration.getInstance().isDebugMode()) {
                    int d4 = Log.d(IMapView.LOGTAG, "SqliteTileWriter init thread crash, db is probably not available", ex);
                }
            }
            if (Configuration.getInstance().isDebugMode()) {
                int d5 = Log.d(IMapView.LOGTAG, "Finished init thread");
            }
        } else if (Configuration.getInstance().isDebugMode()) {
            int d6 = Log.d(IMapView.LOGTAG, "Finished init thread, aborted due to null database reference");
        }
    }

    public boolean saveFile(ITileSource iTileSource, MapTile mapTile, InputStream inputStream) {
        StringBuilder sb;
        StringBuilder sb2;
        ContentValues contentValues;
        BufferedInputStream bufferedInputStream;
        List list;
        StringBuilder sb3;
        ITileSource pTileSourceInfo = iTileSource;
        MapTile pTile = mapTile;
        InputStream pStream = inputStream;
        if (this.f528db == null || !this.f528db.isOpen()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("Unable to store cached tile from ").append(pTileSourceInfo.name()).append(" ").append(pTile.toString()).append(", database not available.").toString());
            Counters.fileCacheSaveErrors++;
            return false;
        }
        try {
            new ContentValues();
            ContentValues cv = contentValues;
            long index = getIndex(pTile);
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
            if (pTile.getExpires() != null) {
                cv.put(COLUMN_EXPIRES, Long.valueOf(pTile.getExpires().getTime()));
            }
            int delete = this.f528db.delete("tiles", primaryKey, getPrimaryKeyParameters(index, pTileSourceInfo));
            long insert = this.f528db.insert("tiles", (String) null, cv);
            if (Configuration.getInstance().isDebugMode()) {
                new StringBuilder();
                int d2 = Log.d(IMapView.LOGTAG, sb3.append("tile inserted ").append(pTileSourceInfo.name()).append(pTile.toString()).toString());
            }
            if (System.currentTimeMillis() > this.lastSizeCheck + 300000) {
                this.lastSizeCheck = System.currentTimeMillis();
                if (this.db_file != null && this.db_file.length() > Configuration.getInstance().getTileFileSystemCacheMaxBytes()) {
                    runCleanupOperation();
                }
            }
        } catch (SQLiteFullException e) {
            SQLiteFullException sQLiteFullException = e;
            runCleanupOperation();
        } catch (Throwable th) {
            Throwable ex = th;
            new StringBuilder();
            int e2 = Log.e(IMapView.LOGTAG, sb2.append("Unable to store cached tile from ").append(pTileSourceInfo.name()).append(" ").append(pTile.toString()).append(" db is ").append(this.f528db == null ? "null" : "not null").toString(), ex);
            Counters.fileCacheSaveErrors++;
        }
        return false;
    }

    public boolean exists(String str, MapTile mapTile) {
        StringBuilder sb;
        StringBuilder sb2;
        String pTileSource = str;
        MapTile pTile = mapTile;
        if (this.f528db == null || !this.f528db.isOpen()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("Unable to test for tile exists cached tile from ").append(pTileSource).append(" ").append(pTile.toString()).append(", database not available.").toString());
            return false;
        }
        try {
            Cursor cur = getTileCursor(getPrimaryKeyParameters(getIndex(pTile), pTileSource), expireQueryColumn);
            if (cur.getCount() != 0) {
                cur.close();
                return true;
            }
            cur.close();
            return false;
        } catch (Throwable th) {
            new StringBuilder();
            int e = Log.e(IMapView.LOGTAG, sb2.append("Unable to store cached tile from ").append(pTileSource).append(" ").append(pTile.toString()).toString(), th);
        }
    }

    public boolean exists(ITileSource pTileSource, MapTile pTile) {
        return exists(pTileSource.name(), pTile);
    }

    public void onDetach() {
        if (this.f528db != null && this.f528db.isOpen()) {
            try {
                this.f528db.close();
                int i = Log.i(IMapView.LOGTAG, "Database detached");
            } catch (Exception e) {
                int e2 = Log.e(IMapView.LOGTAG, "Database detach failed", e);
            }
        }
        this.f528db = null;
        this.db_file = null;
    }

    public boolean purgeCache() {
        if (this.f528db != null && this.f528db.isOpen()) {
            try {
                int delete = this.f528db.delete("tiles", (String) null, (String[]) null);
                return true;
            } catch (Throwable th) {
                int w = Log.w(IMapView.LOGTAG, "Error purging the db", th);
            }
        }
        return false;
    }

    public boolean purgeCache(String str) {
        String mTileSourceName = str;
        if (this.f528db != null && this.f528db.isOpen()) {
            try {
                int delete = this.f528db.delete("tiles", "provider = ?", new String[]{mTileSourceName});
                return true;
            } catch (Throwable th) {
                int w = Log.w(IMapView.LOGTAG, "Error purging the db", th);
            }
        }
        return false;
    }

    public int[] importFromFileCache(boolean z) {
        File[] tileSources;
        StringBuilder sb;
        StringBuilder sb2;
        File[] x;
        StringBuilder sb3;
        StringBuilder sb4;
        ContentValues contentValues;
        MapTile mapTile;
        BufferedInputStream bufferedInputStream;
        InputStream inputStream;
        List list;
        StringBuilder sb5;
        StringBuilder sb6;
        boolean removeFromFileSystem = z;
        int[] ret = {0, 0, 0, 0};
        File tilePathBase = Configuration.getInstance().getOsmdroidTileCache();
        if (tilePathBase.exists() && (tileSources = tilePathBase.listFiles()) != null) {
            for (int i = 0; i < tileSources.length; i++) {
                if (tileSources[i].isDirectory() && !tileSources[i].isHidden()) {
                    File[] z2 = tileSources[i].listFiles();
                    if (z2 != null) {
                        for (int zz = 0; zz < z2.length; zz++) {
                            if (z2[zz].isDirectory() && !z2[zz].isHidden() && (x = z2[zz].listFiles()) != null) {
                                for (int xx = 0; xx < x.length; xx++) {
                                    if (x[xx].isDirectory() && !x[xx].isHidden()) {
                                        File[] y = x[xx].listFiles();
                                        if (x != null) {
                                            for (int yy = 0; yy < y.length; yy++) {
                                                if (!y[yy].isHidden() && !y[yy].isDirectory()) {
                                                    try {
                                                        new ContentValues();
                                                        ContentValues cv = contentValues;
                                                        long x1 = Long.parseLong(x[xx].getName());
                                                        long y1 = Long.parseLong(y[yy].getName().substring(0, y[yy].getName().indexOf(".")));
                                                        long z1 = Long.parseLong(z2[zz].getName());
                                                        long index = getIndex(x1, y1, z1);
                                                        cv.put(DatabaseFileArchive.COLUMN_PROVIDER, tileSources[i].getName());
                                                        new MapTile((int) z1, (int) x1, (int) y1);
                                                        if (!exists(tileSources[i].getName(), mapTile)) {
                                                            new FileInputStream(y[yy]);
                                                            new BufferedInputStream(inputStream);
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
                                                            for (int bi = 0; bi < list2.size(); bi++) {
                                                                bits[bi] = ((Byte) list2.get(bi)).byteValue();
                                                            }
                                                            cv.put(DatabaseFileArchive.COLUMN_KEY, Long.valueOf(index));
                                                            cv.put(DatabaseFileArchive.COLUMN_TILE, bits);
                                                            if (this.f528db.insert("tiles", (String) null, cv) > 0) {
                                                                if (Configuration.getInstance().isDebugMode()) {
                                                                    new StringBuilder();
                                                                    int d = Log.d(IMapView.LOGTAG, sb6.append("tile inserted ").append(tileSources[i].getName()).append("/").append(z1).append("/").append(x1).append("/").append(y1).toString());
                                                                }
                                                                int[] iArr = ret;
                                                                iArr[0] = iArr[0] + 1;
                                                                if (removeFromFileSystem) {
                                                                    boolean delete = y[yy].delete();
                                                                    int[] iArr2 = ret;
                                                                    iArr2[2] = iArr2[2] + 1;
                                                                }
                                                            } else {
                                                                new StringBuilder();
                                                                int w = Log.w(IMapView.LOGTAG, sb5.append("tile NOT inserted ").append(tileSources[i].getName()).append("/").append(z1).append("/").append(x1).append("/").append(y1).toString());
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        Exception exc = e;
                                                        int[] iArr3 = ret;
                                                        iArr3[3] = iArr3[3] + 1;
                                                    } catch (Throwable th) {
                                                        Throwable ex = th;
                                                        new StringBuilder();
                                                        int e2 = Log.e(IMapView.LOGTAG, sb4.append("Unable to store cached tile from ").append(tileSources[i].getName()).append(" db is ").append(this.f528db == null ? "null" : "not null").toString(), ex);
                                                        int[] iArr4 = ret;
                                                        iArr4[1] = iArr4[1] + 1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (removeFromFileSystem) {
                                        try {
                                            boolean delete2 = x[xx].delete();
                                        } catch (Exception e3) {
                                            new StringBuilder();
                                            int e4 = Log.e(IMapView.LOGTAG, sb3.append("Unable to delete directory from ").append(x[xx].getAbsolutePath()).toString(), e3);
                                            int[] iArr5 = ret;
                                            iArr5[3] = iArr5[3] + 1;
                                        }
                                    }
                                }
                            }
                            if (removeFromFileSystem) {
                                try {
                                    boolean delete3 = z2[zz].delete();
                                } catch (Exception e5) {
                                    new StringBuilder();
                                    int e6 = Log.e(IMapView.LOGTAG, sb2.append("Unable to delete directory from ").append(z2[zz].getAbsolutePath()).toString(), e5);
                                    int[] iArr6 = ret;
                                    iArr6[3] = iArr6[3] + 1;
                                }
                            }
                        }
                    }
                    if (removeFromFileSystem) {
                        try {
                            boolean delete4 = tileSources[i].delete();
                        } catch (Exception e7) {
                            new StringBuilder();
                            int e8 = Log.e(IMapView.LOGTAG, sb.append("Unable to delete directory from ").append(tileSources[i].getAbsolutePath()).toString(), e7);
                            int[] iArr7 = ret;
                            iArr7[3] = iArr7[3] + 1;
                        }
                    }
                }
            }
        }
        return ret;
    }

    public boolean remove(ITileSource iTileSource, MapTile mapTile) {
        StringBuilder sb;
        StringBuilder sb2;
        ITileSource pTileSourceInfo = iTileSource;
        MapTile pTile = mapTile;
        if (this.f528db == null) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb2.append("Unable to delete cached tile from ").append(pTileSourceInfo.name()).append(" ").append(pTile.toString()).append(", database not available.").toString());
            Counters.fileCacheSaveErrors++;
            return false;
        }
        try {
            int delete = this.f528db.delete("tiles", primaryKey, getPrimaryKeyParameters(getIndex(pTile), pTileSourceInfo));
            return true;
        } catch (Throwable th) {
            Throwable ex = th;
            new StringBuilder();
            int e = Log.e(IMapView.LOGTAG, sb.append("Unable to delete cached tile from ").append(pTileSourceInfo.name()).append(" ").append(pTile.toString()).append(" db is ").append(this.f528db == null ? "null" : "not null").toString(), ex);
            Counters.fileCacheSaveErrors++;
            return false;
        }
    }

    public long getRowCount(String str) {
        StringBuilder sb;
        Cursor mCount;
        StringBuilder sb2;
        String tileSourceName = str;
        if (tileSourceName == null) {
            try {
                mCount = this.f528db.rawQuery("select count(*) from tiles", (String[]) null);
            } catch (Throwable th) {
                new StringBuilder();
                int e = Log.e(IMapView.LOGTAG, sb2.append("Unable to query for row count ").append(tileSourceName).toString(), th);
                return 0;
            }
        } else {
            SQLiteDatabase sQLiteDatabase = this.f528db;
            new StringBuilder();
            mCount = sQLiteDatabase.rawQuery(sb.append("select count(*) from tiles where provider='").append(tileSourceName).append("'").toString(), (String[]) null);
        }
        boolean moveToFirst = mCount.moveToFirst();
        long count = mCount.getLong(0);
        mCount.close();
        return count;
    }

    public long getSize() {
        return this.db_file.length();
    }

    public long getFirstExpiry() {
        try {
            Cursor cursor = this.f528db.rawQuery("select min(expires) from tiles", (String[]) null);
            boolean moveToFirst = cursor.moveToFirst();
            long time = cursor.getLong(0);
            cursor.close();
            return time;
        } catch (Throwable th) {
            int e = Log.e(IMapView.LOGTAG, "Unable to query for oldest tile", th);
            return 0;
        }
    }

    public static long getIndex(long pX, long pY, long j) {
        long pZ = j;
        return (((pZ << ((int) pZ)) + pX) << ((int) pZ)) + pY;
    }

    public static long getIndex(MapTile mapTile) {
        MapTile pTile = mapTile;
        return getIndex((long) pTile.getX(), (long) pTile.getY(), (long) pTile.getZoomLevel());
    }

    public Long getExpirationTimestamp(ITileSource pTileSource, MapTile pTile) {
        try {
            Cursor cursor = getTileCursor(getPrimaryKeyParameters(getIndex(pTile), pTileSource), expireQueryColumn);
            if (cursor.moveToNext()) {
                Long valueOf = Long.valueOf(cursor.getLong(0));
                if (cursor != null) {
                    cursor.close();
                }
                return valueOf;
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th) {
            Throwable th2 = th;
            if (0 != 0) {
                Cursor cursor2 = null;
                cursor2.close();
            }
            throw th2;
        }
    }

    public static String getPrimaryKey() {
        return primaryKey;
    }

    public static String[] getPrimaryKeyParameters(long pIndex, ITileSource pTileSourceInfo) {
        return getPrimaryKeyParameters(pIndex, pTileSourceInfo.name());
    }

    public static String[] getPrimaryKeyParameters(long pIndex, String pTileSourceInfo) {
        String[] strArr = new String[2];
        strArr[0] = String.valueOf(pIndex);
        String[] strArr2 = strArr;
        strArr2[1] = pTileSourceInfo;
        return strArr2;
    }

    public Cursor getTileCursor(String[] pPrimaryKeyParameters, String[] pColumns) {
        return this.f528db.query("tiles", pColumns, primaryKey, pPrimaryKeyParameters, (String) null, (String) null, (String) null);
    }

    /* JADX INFO: finally extract failed */
    public Drawable loadTile(ITileSource iTileSource, MapTile mapTile) throws Exception {
        InputStream inputStream;
        StringBuilder sb;
        StringBuilder sb2;
        ITileSource pTileSource = iTileSource;
        MapTile pTile = mapTile;
        try {
            Cursor cur = getTileCursor(getPrimaryKeyParameters(getIndex(pTile), pTileSource), queryColumns);
            byte[] bits = null;
            long expirationTimestamp = 0;
            if (cur.getCount() != 0) {
                boolean moveToFirst = cur.moveToFirst();
                bits = cur.getBlob(cur.getColumnIndex(DatabaseFileArchive.COLUMN_TILE));
                expirationTimestamp = cur.getLong(cur.getColumnIndex(COLUMN_EXPIRES));
            }
            cur.close();
            if (bits == null) {
                if (Configuration.getInstance().isDebugMode()) {
                    new StringBuilder();
                    int d = Log.d(IMapView.LOGTAG, sb2.append("SqlCache - Tile doesn't exist: ").append(pTileSource.name()).append(pTile).toString());
                }
                if (0 != 0) {
                    StreamUtils.closeStream((Closeable) null);
                }
                return null;
            }
            new ByteArrayInputStream(bits);
            InputStream inputStream2 = inputStream;
            Drawable drawable = pTileSource.getDrawable(inputStream2);
            if ((expirationTimestamp < System.currentTimeMillis()) && drawable != null) {
                if (Configuration.getInstance().isDebugMode()) {
                    new StringBuilder();
                    int d2 = Log.d(IMapView.LOGTAG, sb.append("Tile expired: ").append(pTileSource.name()).append(pTile).toString());
                }
                ExpirableBitmapDrawable.setState(drawable, -2);
            }
            Drawable drawable2 = drawable;
            if (inputStream2 != null) {
                StreamUtils.closeStream(inputStream2);
            }
            return drawable2;
        } catch (Throwable th) {
            Throwable th2 = th;
            if (0 != 0) {
                StreamUtils.closeStream((Closeable) null);
            }
            throw th2;
        }
    }
}
