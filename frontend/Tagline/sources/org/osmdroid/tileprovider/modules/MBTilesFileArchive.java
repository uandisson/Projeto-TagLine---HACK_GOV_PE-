package org.osmdroid.tileprovider.modules;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;
import org.osmdroid.api.IMapView;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public class MBTilesFileArchive implements IArchiveFile {
    public static final String COL_TILES_TILE_COLUMN = "tile_column";
    public static final String COL_TILES_TILE_DATA = "tile_data";
    public static final String COL_TILES_TILE_ROW = "tile_row";
    public static final String COL_TILES_ZOOM_LEVEL = "zoom_level";
    public static final String TABLE_TILES = "tiles";
    private SQLiteDatabase mDatabase;

    public MBTilesFileArchive() {
    }

    private MBTilesFileArchive(SQLiteDatabase pDatabase) {
        this.mDatabase = pDatabase;
    }

    public static MBTilesFileArchive getDatabaseFileArchive(File pFile) throws SQLiteException {
        MBTilesFileArchive mBTilesFileArchive;
        new MBTilesFileArchive(SQLiteDatabase.openDatabase(pFile.getAbsolutePath(), (SQLiteDatabase.CursorFactory) null, 17));
        return mBTilesFileArchive;
    }

    public void init(File pFile) throws Exception {
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(pFile.getAbsolutePath(), (SQLiteDatabase.CursorFactory) null, 17);
        this.mDatabase = openDatabase;
    }

    public InputStream getInputStream(ITileSource iTileSource, MapTile mapTile) {
        StringBuilder sb;
        InputStream inputStream;
        ITileSource iTileSource2 = iTileSource;
        MapTile pTile = mapTile;
        InputStream ret = null;
        try {
            String[] tile = {COL_TILES_TILE_DATA};
            String[] strArr = new String[3];
            strArr[0] = Integer.toString(pTile.getX());
            String[] strArr2 = strArr;
            strArr2[1] = Double.toString((Math.pow(2.0d, (double) pTile.getZoomLevel()) - ((double) pTile.getY())) - 1.0d);
            String[] xyz = strArr2;
            xyz[2] = Integer.toString(pTile.getZoomLevel());
            Cursor cur = this.mDatabase.query("tiles", tile, "tile_column=? and tile_row=? and zoom_level=?", xyz, (String) null, (String) null, (String) null);
            if (cur.getCount() != 0) {
                boolean moveToFirst = cur.moveToFirst();
                new ByteArrayInputStream(cur.getBlob(0));
                ret = inputStream;
            }
            cur.close();
            if (ret != null) {
                return ret;
            }
        } catch (Throwable th) {
            new StringBuilder();
            int w = Log.w(IMapView.LOGTAG, sb.append("Error getting db stream: ").append(pTile).toString(), th);
        }
        return null;
    }

    public Set<String> getTileSources() {
        return Collections.EMPTY_SET;
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
