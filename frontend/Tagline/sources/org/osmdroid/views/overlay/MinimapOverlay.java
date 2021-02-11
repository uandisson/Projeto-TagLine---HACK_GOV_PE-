package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public class MinimapOverlay extends TilesOverlay {
    private Point mBottomRightMercator;
    private int mHeight;
    private final Rect mIntersectionRect;
    private final Rect mMiniMapCanvasRect;
    private int mPadding;
    private final Paint mPaint;
    private Projection mProjection;
    private final Rect mTileArea;
    private Point mTopLeftMercator;
    private int mWidth;
    private int mZoomDifference;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MinimapOverlay(android.content.Context r11, android.os.Handler r12, org.osmdroid.tileprovider.MapTileProviderBase r13, int r14) {
        /*
            r10 = this;
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r13
            r4 = r14
            r6 = r0
            r7 = r3
            r8 = r1
            r6.<init>(r7, r8)
            r6 = r0
            r7 = 100
            r6.mWidth = r7
            r6 = r0
            r7 = 100
            r6.mHeight = r7
            r6 = r0
            r7 = 10
            r6.mPadding = r7
            r6 = r0
            android.graphics.Rect r7 = new android.graphics.Rect
            r9 = r7
            r7 = r9
            r8 = r9
            r8.<init>()
            r6.mTileArea = r7
            r6 = r0
            android.graphics.Rect r7 = new android.graphics.Rect
            r9 = r7
            r7 = r9
            r8 = r9
            r8.<init>()
            r6.mMiniMapCanvasRect = r7
            r6 = r0
            android.graphics.Rect r7 = new android.graphics.Rect
            r9 = r7
            r7 = r9
            r8 = r9
            r8.<init>()
            r6.mIntersectionRect = r7
            r6 = r0
            android.graphics.Point r7 = new android.graphics.Point
            r9 = r7
            r7 = r9
            r8 = r9
            r8.<init>()
            r6.mTopLeftMercator = r7
            r6 = r0
            android.graphics.Point r7 = new android.graphics.Point
            r9 = r7
            r7 = r9
            r8 = r9
            r8.<init>()
            r6.mBottomRightMercator = r7
            r6 = r0
            r7 = r4
            r6.setZoomDifference(r7)
            r6 = r0
            org.osmdroid.tileprovider.MapTileProviderBase r6 = r6.mTileProvider
            r7 = r2
            r6.setTileRequestCompleteHandler(r7)
            r6 = r0
            r7 = r0
            int r7 = r7.getLoadingBackgroundColor()
            r6.setLoadingLineColor(r7)
            r6 = r1
            android.content.res.Resources r6 = r6.getResources()
            android.util.DisplayMetrics r6 = r6.getDisplayMetrics()
            float r6 = r6.density
            r5 = r6
            r6 = r0
            r9 = r6
            r6 = r9
            r7 = r9
            int r7 = r7.mWidth
            float r7 = (float) r7
            r8 = r5
            float r7 = r7 * r8
            int r7 = (int) r7
            r6.mWidth = r7
            r6 = r0
            r9 = r6
            r6 = r9
            r7 = r9
            int r7 = r7.mHeight
            float r7 = (float) r7
            r8 = r5
            float r7 = r7 * r8
            int r7 = (int) r7
            r6.mHeight = r7
            r6 = r0
            android.graphics.Paint r7 = new android.graphics.Paint
            r9 = r7
            r7 = r9
            r8 = r9
            r8.<init>()
            r6.mPaint = r7
            r6 = r0
            android.graphics.Paint r6 = r6.mPaint
            r7 = -7829368(0xffffffffff888888, float:NaN)
            r6.setColor(r7)
            r6 = r0
            android.graphics.Paint r6 = r6.mPaint
            android.graphics.Paint$Style r7 = android.graphics.Paint.Style.FILL
            r6.setStyle(r7)
            r6 = r0
            android.graphics.Paint r6 = r6.mPaint
            r7 = 1073741824(0x40000000, float:2.0)
            r6.setStrokeWidth(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.MinimapOverlay.<init>(android.content.Context, android.os.Handler, org.osmdroid.tileprovider.MapTileProviderBase, int):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MinimapOverlay(Context pContext, Handler pTileRequestCompleteHandler, MapTileProviderBase pTileProvider) {
        this(pContext, pTileRequestCompleteHandler, pTileProvider, 3);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MinimapOverlay(android.content.Context r11, android.os.Handler r12) {
        /*
            r10 = this;
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r0
            r4 = r1
            r5 = r2
            org.osmdroid.tileprovider.MapTileProviderBasic r6 = new org.osmdroid.tileprovider.MapTileProviderBasic
            r9 = r6
            r6 = r9
            r7 = r9
            r8 = r1
            r7.<init>(r8)
            r3.<init>(r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.MinimapOverlay.<init>(android.content.Context, android.os.Handler):void");
    }

    public void setTileSource(ITileSource pTileSource) {
        this.mTileProvider.setTileSource(pTileSource);
    }

    public int getZoomDifference() {
        return this.mZoomDifference;
    }

    public void setZoomDifference(int zoomDifference) {
        int i = zoomDifference;
        this.mZoomDifference = i;
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Canvas c = canvas;
        MapView osmv = mapView;
        if (!shadow && !osmv.isAnimating()) {
            this.mProjection = osmv.getProjection();
            double zoomLevel = this.mProjection.getZoomLevel();
            Rect screenRect = this.mProjection.getScreenRect();
            Point mercatorPixels = this.mProjection.toMercatorPixels(screenRect.left, screenRect.top, this.mTopLeftMercator);
            Point mercatorPixels2 = this.mProjection.toMercatorPixels(screenRect.right, screenRect.bottom, this.mBottomRightMercator);
            this.mTileArea.set(this.mTopLeftMercator.x, this.mTopLeftMercator.y, this.mBottomRightMercator.x, this.mBottomRightMercator.y);
            int miniMapZoomLevelDifference = getZoomDifference();
            if (zoomLevel - ((double) getZoomDifference()) < ((double) this.mTileProvider.getMinimumZoomLevel())) {
                miniMapZoomLevelDifference = (int) (((double) miniMapZoomLevelDifference) + ((zoomLevel - ((double) getZoomDifference())) - ((double) this.mTileProvider.getMinimumZoomLevel())));
            }
            this.mTileArea.set(this.mTileArea.left >> miniMapZoomLevelDifference, this.mTileArea.top >> miniMapZoomLevelDifference, this.mTileArea.right >> miniMapZoomLevelDifference, this.mTileArea.bottom >> miniMapZoomLevelDifference);
            this.mTileArea.set(this.mTileArea.centerX() - (getWidth() / 2), this.mTileArea.centerY() - (getHeight() / 2), this.mTileArea.centerX() + (getWidth() / 2), this.mTileArea.centerY() + (getHeight() / 2));
            this.mMiniMapCanvasRect.set((screenRect.right - getPadding()) - getWidth(), (screenRect.bottom - getPadding()) - getHeight(), screenRect.right - getPadding(), screenRect.bottom - getPadding());
            c.drawRect((float) (this.mMiniMapCanvasRect.left - 2), (float) (this.mMiniMapCanvasRect.top - 2), (float) (this.mMiniMapCanvasRect.right + 2), (float) (this.mMiniMapCanvasRect.bottom + 2), this.mPaint);
            super.drawTiles(c, this.mProjection, this.mProjection.getZoomLevel() - ((double) miniMapZoomLevelDifference), this.mTileArea);
        }
    }

    /* access modifiers changed from: protected */
    public void onTileReadyToDraw(Canvas canvas, Drawable drawable, Rect rect) {
        Canvas c = canvas;
        Drawable currentMapTile = drawable;
        Rect tileRect = rect;
        int xOffset = (tileRect.left - this.mTileArea.left) + this.mMiniMapCanvasRect.left;
        int yOffset = (tileRect.top - this.mTileArea.top) + this.mMiniMapCanvasRect.top;
        currentMapTile.setBounds(xOffset, yOffset, xOffset + tileRect.width(), yOffset + tileRect.height());
        int save = c.save();
        if (this.mIntersectionRect.setIntersect(c.getClipBounds(), this.mMiniMapCanvasRect)) {
            boolean clipRect = c.clipRect(this.mIntersectionRect);
            currentMapTile.draw(c);
        }
        c.restore();
    }

    public boolean onSingleTapUp(MotionEvent motionEvent, MapView mapView) {
        MotionEvent pEvent = motionEvent;
        MapView mapView2 = mapView;
        if (this.mMiniMapCanvasRect.contains((int) pEvent.getX(), (int) pEvent.getY())) {
            return true;
        }
        return false;
    }

    public boolean onDoubleTap(MotionEvent motionEvent, MapView mapView) {
        MotionEvent pEvent = motionEvent;
        MapView mapView2 = mapView;
        if (this.mMiniMapCanvasRect.contains((int) pEvent.getX(), (int) pEvent.getY())) {
            return true;
        }
        return false;
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent pEvent = motionEvent;
        MapView mapView2 = mapView;
        if (this.mMiniMapCanvasRect.contains((int) pEvent.getX(), (int) pEvent.getY())) {
            return true;
        }
        return false;
    }

    public boolean isOptionsMenuEnabled() {
        return false;
    }

    public void setWidth(int width) {
        int i = width;
        this.mWidth = i;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setHeight(int height) {
        int i = height;
        this.mHeight = i;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setPadding(int padding) {
        int i = padding;
        this.mPadding = i;
    }

    public int getPadding() {
        return this.mPadding;
    }
}
