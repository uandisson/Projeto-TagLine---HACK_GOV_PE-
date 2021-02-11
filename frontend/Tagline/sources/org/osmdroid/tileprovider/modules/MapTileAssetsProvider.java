package org.osmdroid.tileprovider.modules;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import com.google.appinventor.components.common.ComponentDescriptorConstants;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import microsoft.mappoint.TileSystem;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.BitmapTileSourceBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

public class MapTileAssetsProvider extends MapTileFileStorageProviderBase {
    private final AssetManager mAssets;
    /* access modifiers changed from: private */
    public final AtomicReference<ITileSource> mTileSource;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileAssetsProvider(IRegisterReceiver pRegisterReceiver, AssetManager pAssets) {
        this(pRegisterReceiver, pAssets, TileSourceFactory.DEFAULT_TILE_SOURCE);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileAssetsProvider(IRegisterReceiver pRegisterReceiver, AssetManager pAssets, ITileSource pTileSource) {
        this(pRegisterReceiver, pAssets, pTileSource, Configuration.getInstance().getTileDownloadThreads(), Configuration.getInstance().getTileDownloadMaxQueueSize());
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapTileAssetsProvider(IRegisterReceiver pRegisterReceiver, AssetManager pAssets, ITileSource pTileSource, int pThreadPoolSize, int pPendingQueueSize) {
        super(pRegisterReceiver, pThreadPoolSize, pPendingQueueSize);
        AtomicReference<ITileSource> atomicReference;
        new AtomicReference<>();
        this.mTileSource = atomicReference;
        setTileSource(pTileSource);
        this.mAssets = pAssets;
    }

    public boolean getUsesDataConnection() {
        return false;
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return "Assets Cache Provider";
    }

    /* access modifiers changed from: protected */
    public String getThreadGroupName() {
        return ComponentDescriptorConstants.ASSETS_TARGET;
    }

    public TileLoader getTileLoader() {
        TileLoader tileLoader;
        new TileLoader(this, this.mAssets);
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
        private AssetManager mAssets = null;
        final /* synthetic */ MapTileAssetsProvider this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public TileLoader(org.osmdroid.tileprovider.modules.MapTileAssetsProvider r6, android.content.res.AssetManager r7) {
            /*
                r5 = this;
                r0 = r5
                r1 = r6
                r2 = r7
                r3 = r0
                r4 = r1
                r3.this$0 = r4
                r3 = r0
                r4 = r1
                r3.<init>(r4)
                r3 = r0
                r4 = 0
                r3.mAssets = r4
                r3 = r0
                r4 = r2
                r3.mAssets = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.MapTileAssetsProvider.TileLoader.<init>(org.osmdroid.tileprovider.modules.MapTileAssetsProvider, android.content.res.AssetManager):void");
        }

        public Drawable loadTile(MapTile mapTile) throws MapTileModuleProviderBase.CantContinueException {
            Throwable th;
            MapTile pTile = mapTile;
            ITileSource tileSource = (ITileSource) this.this$0.mTileSource.get();
            if (tileSource == null) {
                return null;
            }
            try {
                Drawable drawable = tileSource.getDrawable(this.mAssets.open(tileSource.getTileRelativeFilenameString(pTile)));
                if (drawable != null) {
                }
                return drawable;
            } catch (IOException e) {
                IOException iOException = e;
                return null;
            } catch (BitmapTileSourceBase.LowMemoryException e2) {
                BitmapTileSourceBase.LowMemoryException e3 = e2;
                Throwable th2 = th;
                new MapTileModuleProviderBase.CantContinueException((MapTileModuleProviderBase) this.this$0, (Throwable) e3);
                throw th2;
            }
        }
    }
}
