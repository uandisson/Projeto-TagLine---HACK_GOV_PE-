package org.osmdroid.tileprovider;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.runtime.Component;
import java.util.HashMap;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.modules.IFilesystemCache;
import org.osmdroid.tileprovider.modules.MapTileApproximater;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.util.TileLooper;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.Projection;

public abstract class MapTileProviderBase implements IMapTileProviderCallback {
    protected final MapTileCache mTileCache;
    protected Drawable mTileNotFoundImage;
    protected Handler mTileRequestCompleteHandler;
    private ITileSource mTileSource;
    protected boolean mUseDataConnection;

    public abstract Drawable getMapTile(MapTile mapTile);

    public abstract int getMaximumZoomLevel();

    public abstract int getMinimumZoomLevel();

    public abstract long getQueueSize();

    public abstract IFilesystemCache getTileWriter();

    public void detach() {
        Bitmap bitmap;
        if (this.mTileNotFoundImage != null) {
            if (Build.VERSION.SDK_INT < 9 && (this.mTileNotFoundImage instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) this.mTileNotFoundImage).getBitmap()) != null) {
                bitmap.recycle();
            }
            if (this.mTileNotFoundImage instanceof ReusableBitmapDrawable) {
                BitmapPool.getInstance().returnDrawableToPool((ReusableBitmapDrawable) this.mTileNotFoundImage);
            }
        }
        this.mTileNotFoundImage = null;
    }

    public void setTileSource(ITileSource pTileSource) {
        this.mTileSource = pTileSource;
        clearTileCache();
    }

    public ITileSource getTileSource() {
        return this.mTileSource;
    }

    public MapTileCache createTileCache() {
        MapTileCache mapTileCache;
        new MapTileCache();
        return mapTileCache;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapTileProviderBase(ITileSource pTileSource) {
        this(pTileSource, (Handler) null);
    }

    public MapTileProviderBase(ITileSource pTileSource, Handler pDownloadFinishedListener) {
        this.mUseDataConnection = true;
        this.mTileNotFoundImage = null;
        this.mTileCache = createTileCache();
        this.mTileRequestCompleteHandler = pDownloadFinishedListener;
        this.mTileSource = pTileSource;
    }

    public void setTileLoadFailureImage(Drawable drawable) {
        Drawable drawable2 = drawable;
        this.mTileNotFoundImage = drawable2;
    }

    public void mapTileRequestCompleted(MapTileRequestState mapTileRequestState, Drawable pDrawable) {
        StringBuilder sb;
        MapTileRequestState pState = mapTileRequestState;
        putTileIntoCache(pState.getMapTile(), pDrawable, -1);
        if (this.mTileRequestCompleteHandler != null) {
            boolean sendEmptyMessage = this.mTileRequestCompleteHandler.sendEmptyMessage(0);
        }
        if (Configuration.getInstance().isDebugTileProviders()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("MapTileProviderBase.mapTileRequestCompleted(): ").append(pState.getMapTile()).toString());
        }
    }

    public void mapTileRequestFailed(MapTileRequestState mapTileRequestState) {
        StringBuilder sb;
        MapTileRequestState pState = mapTileRequestState;
        if (this.mTileNotFoundImage != null) {
            putTileIntoCache(pState.getMapTile(), this.mTileNotFoundImage, -4);
            if (this.mTileRequestCompleteHandler != null) {
                boolean sendEmptyMessage = this.mTileRequestCompleteHandler.sendEmptyMessage(0);
            }
        } else if (this.mTileRequestCompleteHandler != null) {
            boolean sendEmptyMessage2 = this.mTileRequestCompleteHandler.sendEmptyMessage(1);
        }
        if (Configuration.getInstance().isDebugTileProviders()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("MapTileProviderBase.mapTileRequestFailed(): ").append(pState.getMapTile()).toString());
        }
    }

    public void mapTileRequestExpiredTile(MapTileRequestState mapTileRequestState, Drawable drawable) {
        StringBuilder sb;
        MapTileRequestState pState = mapTileRequestState;
        Drawable pDrawable = drawable;
        putTileIntoCache(pState.getMapTile(), pDrawable, ExpirableBitmapDrawable.getState(pDrawable));
        if (this.mTileRequestCompleteHandler != null) {
            boolean sendEmptyMessage = this.mTileRequestCompleteHandler.sendEmptyMessage(0);
        }
        if (Configuration.getInstance().isDebugTileProviders()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("MapTileProviderBase.mapTileRequestExpiredTile(): ").append(pState.getMapTile()).toString());
        }
    }

    /* access modifiers changed from: protected */
    public void putTileIntoCache(MapTile mapTile, Drawable drawable, int i) {
        MapTile pTile = mapTile;
        Drawable pDrawable = drawable;
        int pState = i;
        if (pDrawable != null) {
            Drawable before = this.mTileCache.getMapTile(pTile);
            if (before == null || ExpirableBitmapDrawable.getState(before) <= pState) {
                ExpirableBitmapDrawable.setState(pDrawable, pState);
                this.mTileCache.putTile(pTile, pDrawable);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void putExpiredTileIntoCache(MapTileRequestState pState, Drawable pDrawable) {
        putTileIntoCache(pState.getMapTile(), pDrawable, -2);
    }

    public void setTileRequestCompleteHandler(Handler handler) {
        Handler handler2 = handler;
        this.mTileRequestCompleteHandler = handler2;
    }

    public void ensureCapacity(int pCapacity) {
        this.mTileCache.ensureCapacity(pCapacity);
    }

    public void clearTileCache() {
        this.mTileCache.clear();
    }

    public boolean useDataConnection() {
        return this.mUseDataConnection;
    }

    public void setUseDataConnection(boolean pMode) {
        boolean z = pMode;
        this.mUseDataConnection = z;
    }

    public void rescaleCache(Projection projection, double d, double d2, Rect rect) {
        StringBuilder sb;
        Rect rect2;
        ScaleTileLooper scaleTileLooper;
        ScaleTileLooper tileLooper;
        StringBuilder sb2;
        ScaleTileLooper scaleTileLooper2;
        Projection pProjection = projection;
        double pNewZoomLevel = d;
        double pOldZoomLevel = d2;
        Rect pViewPort = rect;
        if (pNewZoomLevel != pOldZoomLevel) {
            long startMs = System.currentTimeMillis();
            new StringBuilder();
            int i = Log.i(IMapView.LOGTAG, sb.append("rescale tile cache from ").append(pOldZoomLevel).append(" to ").append(pNewZoomLevel).toString());
            Point topLeftMercator = pProjection.toMercatorPixels(pViewPort.left, pViewPort.top, (Point) null);
            Point bottomRightMercator = pProjection.toMercatorPixels(pViewPort.right, pViewPort.bottom, (Point) null);
            new Rect(topLeftMercator.x, topLeftMercator.y, bottomRightMercator.x, bottomRightMercator.y);
            Rect viewPort = rect2;
            if (pNewZoomLevel > pOldZoomLevel) {
                tileLooper = scaleTileLooper2;
                new ZoomInTileLooper(this, (C15761) null);
            } else {
                tileLooper = scaleTileLooper;
                new ZoomOutTileLooper(this, (C15761) null);
            }
            tileLooper.loop(pNewZoomLevel, viewPort, pOldZoomLevel, getTileSource().getTileSizePixels());
            new StringBuilder();
            int i2 = Log.i(IMapView.LOGTAG, sb2.append("Finished rescale in ").append(System.currentTimeMillis() - startMs).append("ms").toString());
        }
    }

    private abstract class ScaleTileLooper extends TileLooper {
        private boolean isWorth;
        protected Paint mDebugPaint;
        protected Rect mDestRect;
        protected int mDiff;
        protected final HashMap<MapTile, Bitmap> mNewTiles;
        protected int mOldTileZoomLevel;
        protected Rect mSrcRect;
        protected int mTileSize;
        protected int mTileSize_2;
        final /* synthetic */ MapTileProviderBase this$0;

        /* access modifiers changed from: protected */
        public abstract void computeTile(MapTile mapTile, int i, int i2);

        private ScaleTileLooper(MapTileProviderBase mapTileProviderBase) {
            HashMap<MapTile, Bitmap> hashMap;
            this.this$0 = mapTileProviderBase;
            new HashMap<>();
            this.mNewTiles = hashMap;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ ScaleTileLooper(MapTileProviderBase x0, C15761 r7) {
            this(x0);
            C15761 r2 = r7;
        }

        public void loop(double pZoomLevel, Rect pViewPort, double pOldZoomLevel, int pTileSize) {
            Rect rect;
            Rect rect2;
            Paint paint;
            new Rect();
            this.mSrcRect = rect;
            new Rect();
            this.mDestRect = rect2;
            new Paint();
            this.mDebugPaint = paint;
            this.mOldTileZoomLevel = TileSystem.getInputTileZoomLevel(pOldZoomLevel);
            this.mTileSize = pTileSize;
            loop(pZoomLevel, pViewPort);
        }

        public void initialiseLoop() {
            this.mDiff = Math.abs(this.mTileZoomLevel - this.mOldTileZoomLevel);
            this.mTileSize_2 = this.mTileSize >> this.mDiff;
            this.isWorth = this.mDiff != 0;
        }

        public void handleTile(MapTile mapTile, int i, int i2) {
            MapTile pTile = mapTile;
            int pX = i;
            int pY = i2;
            if (this.isWorth && this.this$0.getMapTile(pTile) == null) {
                try {
                    computeTile(pTile, pX, pY);
                } catch (OutOfMemoryError e) {
                    OutOfMemoryError outOfMemoryError = e;
                    int e2 = Log.e(IMapView.LOGTAG, "OutOfMemoryError rescaling cache");
                }
            }
        }

        public void finaliseLoop() {
            while (!this.mNewTiles.isEmpty()) {
                MapTile tile = this.mNewTiles.keySet().iterator().next();
                putScaledTileIntoCache(tile, this.mNewTiles.remove(tile));
            }
        }

        /* access modifiers changed from: protected */
        public void putScaledTileIntoCache(MapTile mapTile, Bitmap bitmap) {
            Drawable drawable;
            StringBuilder sb;
            Canvas canvas;
            MapTile pTile = mapTile;
            Bitmap pBitmap = bitmap;
            new ReusableBitmapDrawable(pBitmap);
            this.this$0.putTileIntoCache(pTile, drawable, -3);
            if (Configuration.getInstance().isDebugMode()) {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb.append("Created scaled tile: ").append(pTile).toString());
                this.mDebugPaint.setTextSize(40.0f);
                new Canvas(pBitmap);
                canvas.drawText("scaled", 50.0f, 50.0f, this.mDebugPaint);
            }
        }
    }

    private class ZoomInTileLooper extends ScaleTileLooper {
        final /* synthetic */ MapTileProviderBase this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private ZoomInTileLooper(org.osmdroid.tileprovider.MapTileProviderBase r6) {
            /*
                r5 = this;
                r0 = r5
                r1 = r6
                r2 = r0
                r3 = r1
                r2.this$0 = r3
                r2 = r0
                r3 = r1
                r4 = 0
                r2.<init>(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.MapTileProviderBase.ZoomInTileLooper.<init>(org.osmdroid.tileprovider.MapTileProviderBase):void");
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ ZoomInTileLooper(MapTileProviderBase x0, C15761 r7) {
            this(x0);
            C15761 r2 = r7;
        }

        public void computeTile(MapTile mapTile, int i, int i2) {
            MapTile oldTile;
            Bitmap bitmap;
            MapTile pTile = mapTile;
            int i3 = i;
            int i4 = i2;
            new MapTile(this.mOldTileZoomLevel, pTile.getX() >> this.mDiff, pTile.getY() >> this.mDiff);
            Drawable oldDrawable = this.this$0.mTileCache.getMapTile(oldTile);
            if ((oldDrawable instanceof BitmapDrawable) && (bitmap = MapTileApproximater.approximateTileFromLowerZoom((BitmapDrawable) oldDrawable, pTile, this.mDiff)) != null) {
                Object put = this.mNewTiles.put(pTile, bitmap);
            }
        }
    }

    private class ZoomOutTileLooper extends ScaleTileLooper {
        private static final int MAX_ZOOM_OUT_DIFF = 4;
        final /* synthetic */ MapTileProviderBase this$0;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private ZoomOutTileLooper(org.osmdroid.tileprovider.MapTileProviderBase r6) {
            /*
                r5 = this;
                r0 = r5
                r1 = r6
                r2 = r0
                r3 = r1
                r2.this$0 = r3
                r2 = r0
                r3 = r1
                r4 = 0
                r2.<init>(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.tileprovider.MapTileProviderBase.ZoomOutTileLooper.<init>(org.osmdroid.tileprovider.MapTileProviderBase):void");
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ ZoomOutTileLooper(MapTileProviderBase x0, C15761 r7) {
            this(x0);
            C15761 r2 = r7;
        }

        /* access modifiers changed from: protected */
        public void computeTile(MapTile mapTile, int i, int i2) {
            MapTile mapTile2;
            Bitmap oldBitmap;
            Canvas canvas;
            MapTile pTile = mapTile;
            int i3 = i;
            int i4 = i2;
            if (this.mDiff < 4) {
                int xx = pTile.getX() << this.mDiff;
                int yy = pTile.getY() << this.mDiff;
                int numTiles = 1 << this.mDiff;
                Bitmap bitmap = null;
                Canvas canvas2 = null;
                for (int x = 0; x < numTiles; x++) {
                    for (int y = 0; y < numTiles; y++) {
                        new MapTile(this.mOldTileZoomLevel, xx + x, yy + y);
                        MapTile oldTile = mapTile2;
                        Drawable oldDrawable = this.this$0.mTileCache.getMapTile(oldTile);
                        if ((oldDrawable instanceof BitmapDrawable) && (oldBitmap = ((BitmapDrawable) oldDrawable).getBitmap()) != null) {
                            if (bitmap == null) {
                                bitmap = MapTileApproximater.getTileBitmap(this.mTileSize);
                                new Canvas(bitmap);
                                canvas2 = canvas;
                                canvas2.drawColor(Component.COLOR_LIGHT_GRAY);
                            }
                            this.mDestRect.set(x * this.mTileSize_2, y * this.mTileSize_2, (x + 1) * this.mTileSize_2, (y + 1) * this.mTileSize_2);
                            canvas2.drawBitmap(oldBitmap, (Rect) null, this.mDestRect, (Paint) null);
                            Drawable remove = this.this$0.mTileCache.mCachedTiles.remove((Object) oldTile);
                        }
                    }
                }
                if (bitmap != null) {
                    Object put = this.mNewTiles.put(pTile, bitmap);
                }
            }
        }
    }
}
