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
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.Counters;

public class MapTileFilesystemProvider extends MapTileFileStorageProviderBase {
    /* access modifiers changed from: private */
    public final AtomicReference<ITileSource> mTileSource;
    /* access modifiers changed from: private */
    public final TileWriter mWriter;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileFilesystemProvider(IRegisterReceiver pRegisterReceiver) {
        this(pRegisterReceiver, TileSourceFactory.DEFAULT_TILE_SOURCE);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileFilesystemProvider(IRegisterReceiver pRegisterReceiver, ITileSource aTileSource) {
        this(pRegisterReceiver, aTileSource, Configuration.getInstance().getExpirationExtendedDuration() + 604800000);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileFilesystemProvider(IRegisterReceiver pRegisterReceiver, ITileSource pTileSource, long pMaximumCachedFileAge) {
        this(pRegisterReceiver, pTileSource, pMaximumCachedFileAge, Configuration.getInstance().getTileFileSystemThreads(), Configuration.getInstance().getTileFileSystemMaxQueueSize());
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapTileFilesystemProvider(IRegisterReceiver pRegisterReceiver, ITileSource pTileSource, long pMaximumCachedFileAge, int pThreadPoolSize, int pPendingQueueSize) {
        super(pRegisterReceiver, pThreadPoolSize, pPendingQueueSize);
        TileWriter tileWriter;
        AtomicReference<ITileSource> atomicReference;
        new TileWriter();
        this.mWriter = tileWriter;
        new AtomicReference<>();
        this.mTileSource = atomicReference;
        setTileSource(pTileSource);
        this.mWriter.setMaximumCachedFileAge(pMaximumCachedFileAge);
    }

    public boolean getUsesDataConnection() {
        return false;
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return "File System Cache Provider";
    }

    /* access modifiers changed from: protected */
    public String getThreadGroupName() {
        return "filesystem";
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

    public void setTileSource(ITileSource pTileSource) {
        this.mTileSource.set(pTileSource);
    }

    protected class TileLoader extends MapTileModuleProviderBase.TileLoader {
        final /* synthetic */ MapTileFilesystemProvider this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected TileLoader(org.osmdroid.tileprovider.modules.MapTileFilesystemProvider r5) {
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
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.MapTileFilesystemProvider.TileLoader.<init>(org.osmdroid.tileprovider.modules.MapTileFilesystemProvider):void");
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
