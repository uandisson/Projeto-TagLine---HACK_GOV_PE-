package org.osmdroid.tileprovider;

import android.graphics.drawable.Drawable;
import org.osmdroid.config.Configuration;

public class MapTileCache {
    protected LRUMapTileCache mCachedTiles;
    protected final Object mCachedTilesLockObject;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileCache() {
        this(Configuration.getInstance().getCacheMapTileCount());
    }

    public MapTileCache(int aMaximumCacheSize) {
        Object obj;
        LRUMapTileCache lRUMapTileCache;
        new Object();
        this.mCachedTilesLockObject = obj;
        new LRUMapTileCache(aMaximumCacheSize);
        this.mCachedTiles = lRUMapTileCache;
    }

    public void ensureCapacity(int i) {
        int aCapacity = i;
        Object obj = this.mCachedTilesLockObject;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                this.mCachedTiles.ensureCapacity(aCapacity);
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    public Drawable getMapTile(MapTile mapTile) {
        MapTile aTile = mapTile;
        Object obj = this.mCachedTilesLockObject;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                Drawable drawable = (Drawable) this.mCachedTiles.get(aTile);
                return drawable;
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public void putTile(MapTile mapTile, Drawable drawable) {
        MapTile aTile = mapTile;
        Drawable aDrawable = drawable;
        if (aDrawable != null) {
            Object obj = this.mCachedTilesLockObject;
            Object obj2 = obj;
            synchronized (obj) {
                try {
                    Object put = this.mCachedTiles.put(aTile, aDrawable);
                } catch (Throwable th) {
                    Throwable th2 = th;
                    Object obj3 = obj2;
                    throw th2;
                }
            }
        }
    }

    public boolean containsTile(MapTile mapTile) {
        MapTile aTile = mapTile;
        Object obj = this.mCachedTilesLockObject;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                boolean containsKey = this.mCachedTiles.containsKey(aTile);
                return containsKey;
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    public void clear() {
        Object obj = this.mCachedTilesLockObject;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                this.mCachedTiles.clear();
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }
}
