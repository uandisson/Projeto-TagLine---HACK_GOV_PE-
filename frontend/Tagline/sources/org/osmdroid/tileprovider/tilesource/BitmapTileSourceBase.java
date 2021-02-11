package org.osmdroid.tileprovider.tilesource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import java.io.File;
import java.io.InputStream;
import java.util.Random;
import org.osmdroid.api.IMapView;
import org.osmdroid.tileprovider.BitmapPool;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.ReusableBitmapDrawable;
import org.osmdroid.tileprovider.util.Counters;

public abstract class BitmapTileSourceBase implements ITileSource {
    private static int globalOrdinal = 0;
    protected String mCopyright;
    protected final String mImageFilenameEnding;
    private final int mMaximumZoomLevel;
    private final int mMinimumZoomLevel;
    protected String mName;
    private final int mOrdinal;
    private final int mTileSizePixels;
    protected final Random random;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public BitmapTileSourceBase(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding) {
        this(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, (String) null);
    }

    public BitmapTileSourceBase(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String aCopyrightNotice) {
        Random random2;
        new Random();
        this.random = random2;
        int i = globalOrdinal;
        globalOrdinal = i + 1;
        this.mOrdinal = i;
        this.mName = aName;
        this.mMinimumZoomLevel = aZoomMinLevel;
        this.mMaximumZoomLevel = aZoomMaxLevel;
        this.mTileSizePixels = aTileSizePixels;
        this.mImageFilenameEnding = aImageFilenameEnding;
        this.mCopyright = aCopyrightNotice;
    }

    public int ordinal() {
        return this.mOrdinal;
    }

    public String name() {
        return this.mName;
    }

    public String pathBase() {
        return this.mName;
    }

    public String imageFilenameEnding() {
        return this.mImageFilenameEnding;
    }

    public int getMinimumZoomLevel() {
        return this.mMinimumZoomLevel;
    }

    public int getMaximumZoomLevel() {
        return this.mMaximumZoomLevel;
    }

    public int getTileSizePixels() {
        return this.mTileSizePixels;
    }

    public String toString() {
        return name();
    }

    public Drawable getDrawable(String str) throws LowMemoryException {
        StringBuilder sb;
        StringBuilder sb2;
        Throwable th;
        BitmapFactory.Options options;
        Bitmap bitmap;
        File bmp;
        StringBuilder sb3;
        StringBuilder sb4;
        StringBuilder sb5;
        File file;
        Drawable drawable;
        String aFilePath = str;
        try {
            new BitmapFactory.Options();
            BitmapFactory.Options bitmapOptions = options;
            BitmapPool.getInstance().applyReusableOptions(bitmapOptions);
            if (Build.VERSION.SDK_INT == 15) {
                bitmap = BitmapFactory.decodeFile(aFilePath);
            } else {
                bitmap = BitmapFactory.decodeFile(aFilePath, bitmapOptions);
            }
            if (bitmap != null) {
                new ReusableBitmapDrawable(bitmap);
                return drawable;
            }
            new File(aFilePath);
            if (bmp.exists()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb4.append(aFilePath).append(" is an invalid image file, deleting...").toString());
                try {
                    new File(aFilePath);
                    boolean delete = file.delete();
                } catch (Throwable th2) {
                    Throwable e = th2;
                    new StringBuilder();
                    int e2 = Log.e(IMapView.LOGTAG, sb5.append("Error deleting invalid file: ").append(aFilePath).toString(), e);
                }
            } else {
                new StringBuilder();
                int d2 = Log.d(IMapView.LOGTAG, sb3.append("Request tile: ").append(aFilePath).append(" does not exist").toString());
            }
            return null;
        } catch (OutOfMemoryError e3) {
            OutOfMemoryError e4 = e3;
            new StringBuilder();
            int e5 = Log.e(IMapView.LOGTAG, sb2.append("OutOfMemoryError loading bitmap: ").append(aFilePath).toString());
            System.gc();
            Throwable th3 = th;
            new LowMemoryException((Throwable) e4);
            throw th3;
        } catch (Exception e6) {
            new StringBuilder();
            int e7 = Log.e(IMapView.LOGTAG, sb.append("Unexpected error loading bitmap: ").append(aFilePath).toString(), e6);
            Counters.tileDownloadErrors++;
            System.gc();
        }
    }

    public String getTileRelativeFilenameString(MapTile mapTile) {
        StringBuilder sb;
        MapTile tile = mapTile;
        new StringBuilder();
        StringBuilder sb2 = sb;
        StringBuilder append = sb2.append(pathBase());
        StringBuilder append2 = sb2.append('/');
        StringBuilder append3 = sb2.append(tile.getZoomLevel());
        StringBuilder append4 = sb2.append('/');
        StringBuilder append5 = sb2.append(tile.getX());
        StringBuilder append6 = sb2.append('/');
        StringBuilder append7 = sb2.append(tile.getY());
        StringBuilder append8 = sb2.append(imageFilenameEnding());
        return sb2.toString();
    }

    public Drawable getDrawable(InputStream inputStream) throws LowMemoryException {
        StringBuilder sb;
        Throwable th;
        BitmapFactory.Options options;
        Drawable drawable;
        InputStream aFileInputStream = inputStream;
        try {
            new BitmapFactory.Options();
            BitmapFactory.Options bitmapOptions = options;
            BitmapPool.getInstance().applyReusableOptions(bitmapOptions);
            Bitmap bitmap = BitmapFactory.decodeStream(aFileInputStream, (Rect) null, bitmapOptions);
            if (bitmap != null) {
                Drawable drawable2 = drawable;
                new ReusableBitmapDrawable(bitmap);
                return drawable2;
            }
        } catch (OutOfMemoryError e) {
            OutOfMemoryError e2 = e;
            int e3 = Log.e(IMapView.LOGTAG, "OutOfMemoryError loading bitmap");
            System.gc();
            Throwable th2 = th;
            new LowMemoryException((Throwable) e2);
            throw th2;
        } catch (Exception e4) {
            new StringBuilder();
            int w = Log.w(IMapView.LOGTAG, sb.append("#547 Error loading bitmap").append(pathBase()).toString(), e4);
        }
        return null;
    }

    public static final class LowMemoryException extends Exception {
        private static final long serialVersionUID = 146526524087765134L;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LowMemoryException(String pDetailMessage) {
            super(pDetailMessage);
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LowMemoryException(Throwable pThrowable) {
            super(pThrowable);
        }
    }

    public String getCopyrightNotice() {
        return this.mCopyright;
    }
}
