package org.osmdroid.tileprovider;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import java.util.LinkedHashMap;
import java.util.Map;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;

public class LRUMapTileCache extends LinkedHashMap<MapTile, Drawable> {
    private static final long serialVersionUID = -541142277575493335L;
    private int mCapacity;
    private TileRemovedListener mTileRemovedListener;

    public interface TileRemovedListener {
        void onTileRemoved(MapTile mapTile);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LRUMapTileCache(int r7) {
        /*
            r6 = this;
            r0 = r6
            r1 = r7
            r2 = r0
            r3 = r1
            r4 = 2
            int r3 = r3 + 2
            r4 = 1036831949(0x3dcccccd, float:0.1)
            r5 = 1
            r2.<init>(r3, r4, r5)
            r2 = r0
            r3 = r1
            r2.mCapacity = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.LRUMapTileCache.<init>(int):void");
    }

    public void ensureCapacity(int i) {
        StringBuilder sb;
        int aCapacity = i;
        if (aCapacity > this.mCapacity) {
            new StringBuilder();
            int i2 = Log.i(IMapView.LOGTAG, sb.append("Tile cache increased from ").append(this.mCapacity).append(" to ").append(aCapacity).toString());
            this.mCapacity = aCapacity;
        }
    }

    public Drawable remove(Object obj) {
        Bitmap bitmap;
        Object aKey = obj;
        Drawable drawable = (Drawable) super.remove(aKey);
        if (Build.VERSION.SDK_INT < 9 && (drawable instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null) {
            bitmap.recycle();
        }
        if (getTileRemovedListener() != null && (aKey instanceof MapTile)) {
            getTileRemovedListener().onTileRemoved((MapTile) aKey);
        }
        if (drawable instanceof ReusableBitmapDrawable) {
            BitmapPool.getInstance().returnDrawableToPool((ReusableBitmapDrawable) drawable);
        }
        return drawable;
    }

    public void clear() {
        while (!isEmpty()) {
            Drawable remove = remove(keySet().iterator().next());
        }
        super.clear();
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Map.Entry<MapTile, Drawable> entry) {
        StringBuilder sb;
        Map.Entry<MapTile, Drawable> aEldest = entry;
        if (size() > this.mCapacity) {
            MapTile eldest = aEldest.getKey();
            if (Configuration.getInstance().isDebugMode()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb.append("LRU Remove old tile: ").append(eldest).toString());
            }
            Drawable remove = remove((Object) eldest);
        }
        return false;
    }

    public TileRemovedListener getTileRemovedListener() {
        return this.mTileRemovedListener;
    }

    public void setTileRemovedListener(TileRemovedListener tileRemovedListener) {
        TileRemovedListener tileRemovedListener2 = tileRemovedListener;
        this.mTileRemovedListener = tileRemovedListener2;
    }
}
