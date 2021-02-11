package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.C1262R;
import org.osmdroid.tileprovider.BitmapPool;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.ReusableBitmapDrawable;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.TileLooper;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public class TilesOverlay extends Overlay implements IOverlayMenuProvider {
    public static final ColorFilter INVERT_COLORS;
    public static final int MENU_MAP_MODE = getSafeMenuId();
    public static final int MENU_OFFLINE = getSafeMenuId();
    public static final int MENU_TILE_SOURCE_STARTING_ID = getSafeMenuIdSequence(TileSourceFactory.getTileSources().size());
    static final float[] negate = {-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private Context ctx;
    private ColorFilter currentColorFilter;
    private Point mBottomRightMercator;
    protected final Paint mDebugPaint;
    private int mLoadingBackgroundColor;
    private int mLoadingLineColor;
    private BitmapDrawable mLoadingTile;
    private boolean mOptionsMenuEnabled;
    /* access modifiers changed from: private */
    public int mOvershootTileCache;
    private Projection mProjection;
    private final OverlayTileLooper mTileLooper;
    private Point mTilePointMercator;
    protected final MapTileProviderBase mTileProvider;
    /* access modifiers changed from: private */
    public final Rect mTileRect;
    private Point mTopLeftMercator;
    private final Rect mViewPort;
    protected Drawable userSelectedLoadingDrawable = null;

    static {
        ColorFilter colorFilter;
        new ColorMatrixColorFilter(negate);
        INVERT_COLORS = colorFilter;
    }

    public TilesOverlay(MapTileProviderBase mapTileProviderBase, Context aContext) {
        Paint paint;
        Rect rect;
        Rect rect2;
        Point point;
        Point point2;
        Point point3;
        OverlayTileLooper overlayTileLooper;
        Throwable th;
        MapTileProviderBase aTileProvider = mapTileProviderBase;
        new Paint();
        this.mDebugPaint = paint;
        new Rect();
        this.mTileRect = rect;
        new Rect();
        this.mViewPort = rect2;
        new Point();
        this.mTopLeftMercator = point;
        new Point();
        this.mBottomRightMercator = point2;
        new Point();
        this.mTilePointMercator = point3;
        this.mOptionsMenuEnabled = true;
        this.mLoadingTile = null;
        this.mLoadingBackgroundColor = Color.rgb(216, 208, 208);
        this.mLoadingLineColor = Color.rgb(200, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE);
        this.mOvershootTileCache = 0;
        this.currentColorFilter = null;
        new OverlayTileLooper(this, (C16081) null);
        this.mTileLooper = overlayTileLooper;
        this.ctx = aContext;
        if (aTileProvider == null) {
            Throwable th2 = th;
            new IllegalArgumentException("You must pass a valid tile provider to the tiles overlay.");
            throw th2;
        }
        this.mTileProvider = aTileProvider;
    }

    public void setLoadingDrawable(Drawable drawable) {
        Drawable drawable2 = drawable;
        this.userSelectedLoadingDrawable = drawable2;
    }

    public void onDetach(MapView mapView) {
        Bitmap bitmap;
        Bitmap bitmap2;
        MapView mapView2 = mapView;
        this.mTileProvider.detach();
        this.ctx = null;
        if (this.mLoadingTile != null) {
            if (Build.VERSION.SDK_INT < 9 && (this.mLoadingTile instanceof BitmapDrawable) && (bitmap2 = this.mLoadingTile.getBitmap()) != null) {
                bitmap2.recycle();
            }
            if (this.mLoadingTile instanceof ReusableBitmapDrawable) {
                BitmapPool.getInstance().returnDrawableToPool((ReusableBitmapDrawable) this.mLoadingTile);
            }
        }
        this.mLoadingTile = null;
        if (this.userSelectedLoadingDrawable != null) {
            if (Build.VERSION.SDK_INT < 9 && (this.userSelectedLoadingDrawable instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) this.userSelectedLoadingDrawable).getBitmap()) != null) {
                bitmap.recycle();
            }
            if (this.userSelectedLoadingDrawable instanceof ReusableBitmapDrawable) {
                BitmapPool.getInstance().returnDrawableToPool((ReusableBitmapDrawable) this.userSelectedLoadingDrawable);
            }
        }
        this.userSelectedLoadingDrawable = null;
    }

    public int getMinimumZoomLevel() {
        return this.mTileProvider.getMinimumZoomLevel();
    }

    public int getMaximumZoomLevel() {
        return this.mTileProvider.getMaximumZoomLevel();
    }

    public boolean useDataConnection() {
        return this.mTileProvider.useDataConnection();
    }

    public void setUseDataConnection(boolean aMode) {
        this.mTileProvider.setUseDataConnection(aMode);
    }

    public void draw(Canvas canvas, MapView mapView, boolean z) {
        StringBuilder sb;
        Canvas c = canvas;
        MapView osmv = mapView;
        boolean shadow = z;
        if (Configuration.getInstance().isDebugTileProviders()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("onDraw(").append(shadow).append(")").toString());
        }
        if (!shadow) {
            Projection projection = osmv.getProjection();
            Rect screenRect = projection.getScreenRect();
            Point mercatorPixels = projection.toMercatorPixels(screenRect.left, screenRect.top, this.mTopLeftMercator);
            Point mercatorPixels2 = projection.toMercatorPixels(screenRect.right, screenRect.bottom, this.mBottomRightMercator);
            this.mViewPort.set(this.mTopLeftMercator.x, this.mTopLeftMercator.y, this.mBottomRightMercator.x, this.mBottomRightMercator.y);
            drawTiles(c, projection, projection.getZoomLevel(), this.mViewPort);
        }
    }

    public void drawTiles(Canvas canvas, Projection projection, double d, Rect rect) {
        Point point;
        Canvas c = canvas;
        double zoomLevel = d;
        Rect viewPort = rect;
        this.mProjection = projection;
        this.mTileLooper.loop(zoomLevel, viewPort, c, TileSystem.getTileSize(zoomLevel));
        if (Configuration.getInstance().isDebugTileProviders()) {
            new Point(viewPort.centerX(), viewPort.centerY());
            Point centerPoint = point;
            c.drawLine((float) centerPoint.x, (float) (centerPoint.y - 9), (float) centerPoint.x, (float) (centerPoint.y + 9), this.mDebugPaint);
            c.drawLine((float) (centerPoint.x - 9), (float) centerPoint.y, (float) (centerPoint.x + 9), (float) centerPoint.y, this.mDebugPaint);
        }
    }

    private class OverlayTileLooper extends TileLooper {
        private Canvas mCanvas;
        private double mOutputTileSizePx;
        final /* synthetic */ TilesOverlay this$0;

        private OverlayTileLooper(TilesOverlay tilesOverlay) {
            this.this$0 = tilesOverlay;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ OverlayTileLooper(TilesOverlay x0, C16081 r7) {
            this(x0);
            C16081 r2 = r7;
        }

        public void loop(double pZoomLevel, Rect pViewPort, Canvas pCanvas, double pOutputTileSizePx) {
            this.mCanvas = pCanvas;
            this.mOutputTileSizePx = pOutputTileSizePx;
            loop(pZoomLevel, pViewPort);
        }

        public void initialiseLoop() {
            int mapTileUpperBound = 1 << this.mTileZoomLevel;
            int width = (this.mTiles.right - this.mTiles.left) + 1;
            if (width <= 0) {
                width += mapTileUpperBound;
            }
            int height = (this.mTiles.bottom - this.mTiles.top) + 1;
            if (height <= 0) {
                height += mapTileUpperBound;
            }
            this.this$0.mTileProvider.ensureCapacity((height * width) + this.this$0.mOvershootTileCache);
        }

        public void handleTile(MapTile mapTile, int i, int i2) {
            MapTile pTile = mapTile;
            int pX = i;
            int pY = i2;
            Drawable currentMapTile = this.this$0.mTileProvider.getMapTile(pTile);
            boolean isReusable = currentMapTile instanceof ReusableBitmapDrawable;
            ReusableBitmapDrawable reusableBitmapDrawable = isReusable ? (ReusableBitmapDrawable) currentMapTile : null;
            if (currentMapTile == null) {
                currentMapTile = this.this$0.getLoadingTile();
            }
            if (currentMapTile != null) {
                fillRect(pX, pY, this.mOutputTileSizePx);
                if (isReusable) {
                    reusableBitmapDrawable.beginUsingDrawable();
                }
                if (isReusable) {
                    try {
                        if (!((ReusableBitmapDrawable) currentMapTile).isBitmapValid()) {
                            currentMapTile = this.this$0.getLoadingTile();
                            isReusable = false;
                        }
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        if (isReusable) {
                            reusableBitmapDrawable.finishUsingDrawable();
                        }
                        throw th2;
                    }
                }
                this.this$0.onTileReadyToDraw(this.mCanvas, currentMapTile, this.this$0.mTileRect);
                if (isReusable) {
                    reusableBitmapDrawable.finishUsingDrawable();
                }
            }
            if (Configuration.getInstance().isDebugTileProviders()) {
                fillRect(pX, pY, this.mOutputTileSizePx);
                this.mCanvas.drawText(pTile.toString(), (float) (this.this$0.mTileRect.left + 1), ((float) this.this$0.mTileRect.top) + this.this$0.mDebugPaint.getTextSize(), this.this$0.mDebugPaint);
                this.mCanvas.drawLine((float) this.this$0.mTileRect.left, (float) this.this$0.mTileRect.top, (float) this.this$0.mTileRect.right, (float) this.this$0.mTileRect.top, this.this$0.mDebugPaint);
                this.mCanvas.drawLine((float) this.this$0.mTileRect.left, (float) this.this$0.mTileRect.top, (float) this.this$0.mTileRect.left, (float) this.this$0.mTileRect.bottom, this.this$0.mDebugPaint);
            }
        }

        private void fillRect(int pX, int pY, double d) {
            double pOutputTileSizePx = d;
            double x = ((double) pX) * pOutputTileSizePx;
            double y = ((double) pY) * pOutputTileSizePx;
            this.this$0.mTileRect.set((int) Math.round(x), (int) Math.round(y), (int) Math.round(x + pOutputTileSizePx), (int) Math.round(y + pOutputTileSizePx));
        }
    }

    /* access modifiers changed from: protected */
    public void onTileReadyToDraw(Canvas c, Drawable drawable, Rect rect) {
        Drawable currentMapTile = drawable;
        Rect tileRect = rect;
        currentMapTile.setColorFilter(this.currentColorFilter);
        Point pixelsFromMercator = this.mProjection.toPixelsFromMercator(tileRect.left, tileRect.top, this.mTilePointMercator);
        tileRect.offsetTo(this.mTilePointMercator.x, this.mTilePointMercator.y);
        currentMapTile.setBounds(tileRect);
        currentMapTile.draw(c);
    }

    public void setOptionsMenuEnabled(boolean pOptionsMenuEnabled) {
        boolean z = pOptionsMenuEnabled;
        this.mOptionsMenuEnabled = z;
    }

    public boolean isOptionsMenuEnabled() {
        return this.mOptionsMenuEnabled;
    }

    public boolean onCreateOptionsMenu(Menu menu, int i, MapView mapView) {
        Menu pMenu = menu;
        int pMenuIdOffset = i;
        MapView pMapView = mapView;
        SubMenu mapMenu = pMenu.addSubMenu(0, MENU_MAP_MODE + pMenuIdOffset, 0, C1262R.string.map_mode).setIcon(C1262R.C1263drawable.ic_menu_mapmode);
        for (int a = 0; a < TileSourceFactory.getTileSources().size(); a++) {
            MenuItem add = mapMenu.add(MENU_MAP_MODE + pMenuIdOffset, MENU_TILE_SOURCE_STARTING_ID + a + pMenuIdOffset, 0, TileSourceFactory.getTileSources().get(a).name());
        }
        mapMenu.setGroupCheckable(MENU_MAP_MODE + pMenuIdOffset, true, true);
        if (this.ctx != null) {
            MenuItem icon = pMenu.add(0, MENU_OFFLINE + pMenuIdOffset, 0, this.ctx.getString(pMapView.useDataConnection() ? C1262R.string.set_mode_offline : C1262R.string.set_mode_online)).setIcon(this.ctx.getResources().getDrawable(C1262R.C1263drawable.ic_menu_offline));
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu, int i, MapView mapView) {
        Menu pMenu = menu;
        int pMenuIdOffset = i;
        MapView pMapView = mapView;
        int index = TileSourceFactory.getTileSources().indexOf(pMapView.getTileProvider().getTileSource());
        if (index >= 0) {
            MenuItem checked = pMenu.findItem(MENU_TILE_SOURCE_STARTING_ID + index + pMenuIdOffset).setChecked(true);
        }
        MenuItem title = pMenu.findItem(MENU_OFFLINE + pMenuIdOffset).setTitle(pMapView.useDataConnection() ? C1262R.string.set_mode_offline : C1262R.string.set_mode_online);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem pItem, int pMenuIdOffset, MapView mapView) {
        MapView pMapView = mapView;
        int menuId = pItem.getItemId() - pMenuIdOffset;
        if (menuId >= MENU_TILE_SOURCE_STARTING_ID && menuId < MENU_TILE_SOURCE_STARTING_ID + TileSourceFactory.getTileSources().size()) {
            pMapView.setTileSource(TileSourceFactory.getTileSources().get(menuId - MENU_TILE_SOURCE_STARTING_ID));
            return true;
        } else if (menuId != MENU_OFFLINE) {
            return false;
        } else {
            pMapView.setUseDataConnection(!pMapView.useDataConnection());
            return true;
        }
    }

    public int getLoadingBackgroundColor() {
        return this.mLoadingBackgroundColor;
    }

    public void setLoadingBackgroundColor(int i) {
        int pLoadingBackgroundColor = i;
        if (this.mLoadingBackgroundColor != pLoadingBackgroundColor) {
            this.mLoadingBackgroundColor = pLoadingBackgroundColor;
            clearLoadingTile();
        }
    }

    public int getLoadingLineColor() {
        return this.mLoadingLineColor;
    }

    public void setLoadingLineColor(int i) {
        int pLoadingLineColor = i;
        if (this.mLoadingLineColor != pLoadingLineColor) {
            this.mLoadingLineColor = pLoadingLineColor;
            clearLoadingTile();
        }
    }

    /* access modifiers changed from: private */
    public Drawable getLoadingTile() {
        Canvas canvas;
        Paint paint;
        BitmapDrawable bitmapDrawable;
        if (this.userSelectedLoadingDrawable != null) {
            return this.userSelectedLoadingDrawable;
        }
        if (this.mLoadingTile == null && this.mLoadingBackgroundColor != 0) {
            try {
                int tileSize = this.mTileProvider.getTileSource() != null ? this.mTileProvider.getTileSource().getTileSizePixels() : 256;
                Bitmap bitmap = Bitmap.createBitmap(tileSize, tileSize, Bitmap.Config.ARGB_8888);
                new Canvas(bitmap);
                Canvas canvas2 = canvas;
                new Paint();
                Paint paint2 = paint;
                canvas2.drawColor(this.mLoadingBackgroundColor);
                paint2.setColor(this.mLoadingLineColor);
                paint2.setStrokeWidth(0.0f);
                int lineSize = tileSize / 16;
                for (int a = 0; a < tileSize; a += lineSize) {
                    canvas2.drawLine(0.0f, (float) a, (float) tileSize, (float) a, paint2);
                    canvas2.drawLine((float) a, 0.0f, (float) a, (float) tileSize, paint2);
                }
                new BitmapDrawable(bitmap);
                this.mLoadingTile = bitmapDrawable;
            } catch (OutOfMemoryError e) {
                OutOfMemoryError outOfMemoryError = e;
                int e2 = Log.e(IMapView.LOGTAG, "OutOfMemoryError getting loading tile");
                System.gc();
            } catch (NullPointerException e3) {
                NullPointerException nullPointerException = e3;
                int e4 = Log.e(IMapView.LOGTAG, "NullPointerException getting loading tile");
                System.gc();
            }
        }
        return this.mLoadingTile;
    }

    private void clearLoadingTile() {
        BitmapDrawable bitmapDrawable = this.mLoadingTile;
        this.mLoadingTile = null;
        if (Build.VERSION.SDK_INT < 9 && bitmapDrawable != null) {
            bitmapDrawable.getBitmap().recycle();
        }
    }

    public void setOvershootTileCache(int overshootTileCache) {
        int i = overshootTileCache;
        this.mOvershootTileCache = i;
    }

    public int getOvershootTileCache() {
        return this.mOvershootTileCache;
    }

    public void setColorFilter(ColorFilter filter) {
        ColorFilter colorFilter = filter;
        this.currentColorFilter = colorFilter;
    }
}
