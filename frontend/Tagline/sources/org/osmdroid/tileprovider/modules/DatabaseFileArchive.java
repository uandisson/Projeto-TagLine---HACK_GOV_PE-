package org.osmdroid.tileprovider.modules;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import org.osmdroid.api.IMapView;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public class DatabaseFileArchive implements IArchiveFile {
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_PROVIDER = "provider";
    public static final String COLUMN_TILE = "tile";
    public static final String TABLE = "tiles";
    static final String[] tile_column = {COLUMN_TILE};
    private SQLiteDatabase mDatabase;

    public DatabaseFileArchive() {
    }

    private DatabaseFileArchive(SQLiteDatabase pDatabase) {
        this.mDatabase = pDatabase;
    }

    public static DatabaseFileArchive getDatabaseFileArchive(File pFile) throws SQLiteException {
        DatabaseFileArchive databaseFileArchive;
        new DatabaseFileArchive(SQLiteDatabase.openDatabase(pFile.getAbsolutePath(), (SQLiteDatabase.CursorFactory) null, 0));
        return databaseFileArchive;
    }

    public Set<String> getTileSources() {
        Set<String> set;
        new HashSet();
        Set<String> ret = set;
        try {
            Cursor cur = this.mDatabase.rawQuery("SELECT distinct provider FROM tiles", (String[]) null);
            while (cur.moveToNext()) {
                boolean add = ret.add(cur.getString(0));
            }
            cur.close();
        } catch (Exception e) {
            int w = Log.w(IMapView.LOGTAG, "Error getting tile sources: ", e);
        }
        return ret;
    }

    public void init(File pFile) throws Exception {
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(pFile.getAbsolutePath(), (SQLiteDatabase.CursorFactory) null, 17);
        this.mDatabase = openDatabase;
    }

    public byte[] getImage(ITileSource iTileSource, MapTile mapTile) {
        StringBuilder sb;
        StringBuilder sb2;
        ITileSource pTileSource = iTileSource;
        MapTile pTile = mapTile;
        byte[] bits = null;
        try {
            long x = (long) pTile.getX();
            long y = (long) pTile.getY();
            long z = (long) pTile.getZoomLevel();
            new StringBuilder();
            Cursor cur = this.mDatabase.query("tiles", new String[]{COLUMN_TILE}, sb2.append("key = ").append((((z << ((int) z)) + x) << ((int) z)) + y).append(" and ").append(COLUMN_PROVIDER).append(" = '").append(pTileSource.name()).append("'").toString(), (String[]) null, (String) null, (String) null, (String) null);
            if (cur.getCount() != 0) {
                boolean moveToFirst = cur.moveToFirst();
                bits = cur.getBlob(0);
            }
            cur.close();
            if (bits != null) {
                return bits;
            }
        } catch (Throwable th) {
            new StringBuilder();
            int w = Log.w(IMapView.LOGTAG, sb.append("Error getting db stream: ").append(pTile).toString(), th);
        }
        return null;
    }

    public InputStream getInputStream(ITileSource pTileSource, MapTile mapTile) {
        StringBuilder sb;
        InputStream inputStream;
        MapTile pTile = mapTile;
        InputStream ret = null;
        try {
            byte[] bits = getImage(pTileSource, pTile);
            if (bits != null) {
                InputStream inputStream2 = inputStream;
                new ByteArrayInputStream(bits);
                ret = inputStream2;
            }
            if (ret != null) {
                return ret;
            }
        } catch (Throwable th) {
            new StringBuilder();
            int w = Log.w(IMapView.LOGTAG, sb.append("Error getting db stream: ").append(pTile).toString(), th);
        }
        return null;
    }

    public void close() {
        this.mDatabase.close();
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("DatabaseFileArchive [mDatabase=").append(this.mDatabase.getPath()).append("]").toString();
    }
}
