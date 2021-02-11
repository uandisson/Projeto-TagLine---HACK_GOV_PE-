package org.osmdroid.tileprovider.modules;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.List;
import microsoft.mappoint.TileSystem;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.BitmapPool;
import org.osmdroid.tileprovider.ExpirableBitmapDrawable;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public class MapTileApproximater extends MapTileModuleProviderBase {
    private final List<MapTileModuleProviderBase> mProviders;
    private int minZoomLevel;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileApproximater() {
        this(Configuration.getInstance().getTileFileSystemThreads(), Configuration.getInstance().getTileFileSystemMaxQueueSize());
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapTileApproximater(int pThreadPoolSize, int pPendingQueueSize) {
        super(pThreadPoolSize, pPendingQueueSize);
        List<MapTileModuleProviderBase> list;
        new ArrayList();
        this.mProviders = list;
    }

    public void addProvider(MapTileModuleProviderBase pProvider) {
        boolean add = this.mProviders.add(pProvider);
        computeZoomLevels();
    }

    private void computeZoomLevels() {
        boolean first = true;
        this.minZoomLevel = 0;
        for (MapTileModuleProviderBase provider : this.mProviders) {
            int otherMin = provider.getMinimumZoomLevel();
            if (first) {
                first = false;
                this.minZoomLevel = otherMin;
            } else {
                this.minZoomLevel = Math.min(this.minZoomLevel, otherMin);
            }
        }
    }

    public boolean getUsesDataConnection() {
        return false;
    }

    /* access modifiers changed from: protected */
    public String getName() {
        return "Offline Tile Approximation Provider";
    }

    /* access modifiers changed from: protected */
    public String getThreadGroupName() {
        return "approximater";
    }

    public TileLoader getTileLoader() {
        TileLoader tileLoader;
        new TileLoader(this);
        return tileLoader;
    }

    public int getMinimumZoomLevel() {
        return this.minZoomLevel;
    }

    public int getMaximumZoomLevel() {
        return TileSystem.getMaximumZoomLevel();
    }

    @Deprecated
    public void setTileSource(ITileSource pTileSource) {
    }

    protected class TileLoader extends MapTileModuleProviderBase.TileLoader {
        final /* synthetic */ MapTileApproximater this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected TileLoader(org.osmdroid.tileprovider.modules.MapTileApproximater r5) {
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
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.MapTileApproximater.TileLoader.<init>(org.osmdroid.tileprovider.modules.MapTileApproximater):void");
        }

        public Drawable loadTile(MapTile pMapTile) {
            BitmapDrawable bitmapDrawable;
            Bitmap bitmap = this.this$0.approximateTileFromLowerZoom(pMapTile);
            if (bitmap == null) {
                return null;
            }
            new BitmapDrawable(bitmap);
            BitmapDrawable drawable = bitmapDrawable;
            ExpirableBitmapDrawable.setState(drawable, -3);
            return drawable;
        }
    }

    public Bitmap approximateTileFromLowerZoom(MapTile mapTile) {
        MapTile pMapTile = mapTile;
        for (int zoomDiff = 1; pMapTile.getZoomLevel() - zoomDiff >= 0; zoomDiff++) {
            Bitmap bitmap = approximateTileFromLowerZoom(pMapTile, zoomDiff);
            if (bitmap != null) {
                return bitmap;
            }
        }
        return null;
    }

    public Bitmap approximateTileFromLowerZoom(MapTile mapTile, int i) {
        MapTile pMapTile = mapTile;
        int pZoomDiff = i;
        for (MapTileModuleProviderBase provider : this.mProviders) {
            Bitmap bitmap = approximateTileFromLowerZoom(provider, pMapTile, pZoomDiff);
            if (bitmap != null) {
                return bitmap;
            }
        }
        return null;
    }

    public static Bitmap approximateTileFromLowerZoom(MapTileModuleProviderBase mapTileModuleProviderBase, MapTile mapTile, int i) {
        MapTile srcTile;
        MapTileModuleProviderBase pProvider = mapTileModuleProviderBase;
        MapTile pMapTile = mapTile;
        int pZoomDiff = i;
        if (pZoomDiff <= 0) {
            return null;
        }
        int srcZoomLevel = pMapTile.getZoomLevel() - pZoomDiff;
        if (srcZoomLevel < pProvider.getMinimumZoomLevel()) {
            return null;
        }
        if (srcZoomLevel > pProvider.getMaximumZoomLevel()) {
            return null;
        }
        new MapTile(srcZoomLevel, pMapTile.getX() >> pZoomDiff, pMapTile.getY() >> pZoomDiff);
        try {
            Drawable srcDrawable = pProvider.getTileLoader().loadTile(srcTile);
            if (!(srcDrawable instanceof BitmapDrawable)) {
                return null;
            }
            return approximateTileFromLowerZoom((BitmapDrawable) srcDrawable, pMapTile, pZoomDiff);
        } catch (Exception e) {
            Exception exc = e;
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0046, code lost:
        if (r7.isBitmapValid() != false) goto L_0x0048;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap approximateTileFromLowerZoom(android.graphics.drawable.BitmapDrawable r23, org.osmdroid.tileprovider.MapTile r24, int r25) {
        /*
            r0 = r23
            r1 = r24
            r2 = r25
            r15 = r2
            if (r15 > 0) goto L_0x000c
            r15 = 0
            r0 = r15
        L_0x000b:
            return r0
        L_0x000c:
            r15 = r0
            android.graphics.Bitmap r15 = r15.getBitmap()
            int r15 = r15.getWidth()
            r3 = r15
            r15 = r3
            android.graphics.Bitmap r15 = getTileBitmap(r15)
            r4 = r15
            android.graphics.Canvas r15 = new android.graphics.Canvas
            r22 = r15
            r15 = r22
            r16 = r22
            r17 = r4
            r16.<init>(r17)
            r5 = r15
            r15 = r0
            boolean r15 = r15 instanceof org.osmdroid.tileprovider.ReusableBitmapDrawable
            r6 = r15
            r15 = r6
            if (r15 == 0) goto L_0x0060
            r15 = r0
            org.osmdroid.tileprovider.ReusableBitmapDrawable r15 = (org.osmdroid.tileprovider.ReusableBitmapDrawable) r15
        L_0x0034:
            r7 = r15
            r15 = 0
            r8 = r15
            r15 = r6
            if (r15 == 0) goto L_0x003e
            r15 = r7
            r15.beginUsingDrawable()
        L_0x003e:
            r15 = r6
            if (r15 == 0) goto L_0x0048
            r15 = r7
            boolean r15 = r15.isBitmapValid()     // Catch:{ all -> 0x00c9 }
            if (r15 == 0) goto L_0x0053
        L_0x0048:
            r15 = r3
            r16 = r2
            int r15 = r15 >> r16
            r9 = r15
            r15 = r9
            if (r15 != 0) goto L_0x0062
            r15 = 0
            r8 = r15
        L_0x0053:
            r15 = r6
            if (r15 == 0) goto L_0x005a
            r15 = r7
            r15.finishUsingDrawable()
        L_0x005a:
            r15 = r8
            if (r15 != 0) goto L_0x00d4
            r15 = 0
            r0 = r15
            goto L_0x000b
        L_0x0060:
            r15 = 0
            goto L_0x0034
        L_0x0062:
            r15 = r1
            int r15 = r15.getX()     // Catch:{ all -> 0x00c9 }
            r16 = 1
            r17 = r2
            int r16 = r16 << r17
            int r15 = r15 % r16
            r16 = r9
            int r15 = r15 * r16
            r10 = r15
            r15 = r1
            int r15 = r15.getY()     // Catch:{ all -> 0x00c9 }
            r16 = 1
            r17 = r2
            int r16 = r16 << r17
            int r15 = r15 % r16
            r16 = r9
            int r15 = r15 * r16
            r11 = r15
            android.graphics.Rect r15 = new android.graphics.Rect     // Catch:{ all -> 0x00c9 }
            r22 = r15
            r15 = r22
            r16 = r22
            r17 = r10
            r18 = r11
            r19 = r10
            r20 = r9
            int r19 = r19 + r20
            r20 = r11
            r21 = r9
            int r20 = r20 + r21
            r16.<init>(r17, r18, r19, r20)     // Catch:{ all -> 0x00c9 }
            r12 = r15
            android.graphics.Rect r15 = new android.graphics.Rect     // Catch:{ all -> 0x00c9 }
            r22 = r15
            r15 = r22
            r16 = r22
            r17 = 0
            r18 = 0
            r19 = r3
            r20 = r3
            r16.<init>(r17, r18, r19, r20)     // Catch:{ all -> 0x00c9 }
            r13 = r15
            r15 = r5
            r16 = r0
            android.graphics.Bitmap r16 = r16.getBitmap()     // Catch:{ all -> 0x00c9 }
            r17 = r12
            r18 = r13
            r19 = 0
            r15.drawBitmap(r16, r17, r18, r19)     // Catch:{ all -> 0x00c9 }
            r15 = 1
            r8 = r15
            goto L_0x0053
        L_0x00c9:
            r15 = move-exception
            r14 = r15
            r15 = r6
            if (r15 == 0) goto L_0x00d2
            r15 = r7
            r15.finishUsingDrawable()
        L_0x00d2:
            r15 = r14
            throw r15
        L_0x00d4:
            r15 = r4
            r0 = r15
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.modules.MapTileApproximater.approximateTileFromLowerZoom(android.graphics.drawable.BitmapDrawable, org.osmdroid.tileprovider.MapTile, int):android.graphics.Bitmap");
    }

    public static Bitmap getTileBitmap(int i) {
        int pTileSizePx = i;
        Bitmap bitmap = BitmapPool.getInstance().obtainSizedBitmapFromPool(pTileSizePx, pTileSizePx);
        if (bitmap != null) {
            return bitmap;
        }
        return Bitmap.createBitmap(pTileSizePx, pTileSizePx, Bitmap.Config.ARGB_8888);
    }
}
