package org.osmdroid.tileprovider.modules;

import android.graphics.drawable.Drawable;
import android.util.Log;
import java.util.concurrent.atomic.AtomicReference;
import microsoft.mappoint.TileSystem;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.BitmapTileSourceBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.util.Counters;

public class MapTileSqlCacheProvider extends MapTileFileStorageProviderBase {
    private static final String[] columns;
    /* access modifiers changed from: private */
    public final AtomicReference<ITileSource> mTileSource;
    /* access modifiers changed from: private */
    public SqlTileWriter mWriter;

    static {
        String[] strArr = new String[2];
        strArr[0] = DatabaseFileArchive.COLUMN_TILE;
        String[] strArr2 = strArr;
        strArr2[1] = SqlTileWriter.COLUMN_EXPIRES;
        columns = strArr2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public MapTileSqlCacheProvider(IRegisterReceiver pRegisterReceiver, ITileSource pTileSource, long j) {
        this(pRegisterReceiver, pTileSource);
        long j2 = j;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapTileSqlCacheProvider(IRegisterReceiver pRegisterReceiver, ITileSource pTileSource) {
        super(pRegisterReceiver, Configuration.getInstance().getTileFileSystemThreads(), Configuration.getInstance().getTileFileSystemMaxQueueSize());
        AtomicReference<ITileSource> atomicReference;
        SqlTileWriter sqlTileWriter;
        new AtomicReference<>();
        this.mTileSource = atomicReference;
        setTileSource(pTileSource);
        new SqlTileWriter();
        this.mWriter = sqlTileWriter;
    }

    public boolean getUsesDataConnection() {
        return false;
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return "SQL Cache Archive Provider";
    }

    /* access modifiers changed from: protected */
    public String getThreadGroupName() {
        return "sqlcache";
    }

    public TileLoader getTileLoader() {
        TileLoader tileLoader;
        new TileLoader(this);
        return tileLoader;
    }

    public int getMinimumZoomLevel() {
        ITileSource tileSource = this.mTileSource.get();
        return tileSource != null ? tileSource.getMinimumZoomLevel() : 0;
    }

    public int getMaximumZoomLevel() {
        int maximumZoomLevel;
        ITileSource tileSource = this.mTileSource.get();
        if (tileSource != null) {
            maximumZoomLevel = tileSource.getMaximumZoomLevel();
        } else {
            maximumZoomLevel = TileSystem.getMaximumZoomLevel();
        }
        return maximumZoomLevel;
    }

    /* access modifiers changed from: protected */
    public void onMediaMounted() {
    }

    /* access modifiers changed from: protected */
    public void onMediaUnmounted() {
        SqlTileWriter sqlTileWriter;
        if (this.mWriter != null) {
            this.mWriter.onDetach();
        }
        new SqlTileWriter();
        this.mWriter = sqlTileWriter;
    }

    public void setTileSource(ITileSource pTileSource) {
        this.mTileSource.set(pTileSource);
    }

    public void detach() {
        if (this.mWriter != null) {
            this.mWriter.onDetach();
        }
        this.mWriter = null;
        super.detach();
    }

    public boolean hasTile(MapTile mapTile) {
        MapTile pTile = mapTile;
        ITileSource tileSource = this.mTileSource.get();
        if (tileSource == null) {
            return false;
        }
        return this.mWriter.getExpirationTimestamp(tileSource, pTile) != null;
    }

    protected class TileLoader extends MapTileModuleProviderBase.TileLoader {
        final /* synthetic */ MapTileSqlCacheProvider this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected TileLoader(org.osmdroid.tileprovider.modules.MapTileSqlCacheProvider r5) {
            /*
                r4 = this;
                r0 = r4
                r1 = r5
                r2 = r0
                r3 = r1
                r2.this$0 = r3
                r2 = r0
                r3 = r1
                r2.<init>(r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.MapTileSqlCacheProvider.TileLoader.<init>(org.osmdroid.tileprovider.modules.MapTileSqlCacheProvider):void");
        }

        public Drawable loadTile(MapTile mapTile) throws MapTileModuleProviderBase.CantContinueException {
            StringBuilder sb;
            Throwable th;
            StringBuilder sb2;
            MapTile pTile = mapTile;
            ITileSource tileSource = (ITileSource) this.this$0.mTileSource.get();
            if (tileSource == null) {
                return null;
            }
            if (!MapTileFileStorageProviderBase.isSdCardAvailable()) {
                if (Configuration.getInstance().isDebugMode()) {
                    new StringBuilder();
                    int d = Log.d(IMapView.LOGTAG, sb2.append("No sdcard - do nothing for tile: ").append(pTile).toString());
                }
                Counters.fileCacheMiss++;
                return null;
            }
            try {
                Drawable result = this.this$0.mWriter.loadTile(tileSource, pTile);
                if (result == null) {
                    Counters.fileCacheMiss++;
                } else {
                    Counters.fileCacheHit++;
                }
                return result;
            } catch (BitmapTileSourceBase.LowMemoryException e) {
                BitmapTileSourceBase.LowMemoryException e2 = e;
                new StringBuilder();
                int w = Log.w(IMapView.LOGTAG, sb.append("LowMemoryException downloading MapTile: ").append(pTile).append(" : ").append(e2).toString());
                Counters.fileCacheOOM++;
                Throwable th2 = th;
                new MapTileModuleProviderBase.CantContinueException((MapTileModuleProviderBase) this.this$0, (Throwable) e2);
                throw th2;
            } catch (Throwable th3) {
                int e3 = Log.e(IMapView.LOGTAG, "Error loading tile", th3);
                return null;
            }
        }
    }
}
