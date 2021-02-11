package org.osmdroid.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.ZoomButtonsController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.metalev.multitouch.controller.MultiTouchController;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.util.SimpleInvalidationHandler;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.GeometryMath;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayManager;
import org.osmdroid.views.overlay.TilesOverlay;
import org.osmdroid.views.util.constants.MapViewConstants;

public class MapView extends ViewGroup implements IMapView, MapViewConstants, MultiTouchController.MultiTouchObjectCanvas<Object> {
    private static final double ZOOM_LOG_BASE_INV = (ZOOM_SENSITIVITY / Math.log(2.0d));
    private static final double ZOOM_SENSITIVITY = 1.0d;
    private static Method sMotionEventTransformMethod;
    /* access modifiers changed from: private */
    public boolean enableFling;
    private IGeoPoint initCenter;
    private final MapController mController;
    /* access modifiers changed from: private */
    public boolean mEnableZoomController;
    private final GestureDetector mGestureDetector;
    private final Rect mInvalidateRect;
    protected final AtomicBoolean mIsAnimating;
    protected boolean mIsFlinging;
    private boolean mLayoutOccurred;
    private final Point mLayoutPoint;
    protected MapListener mListener;
    private TilesOverlay mMapOverlay;
    protected Integer mMaximumZoomLevel;
    protected Integer mMinimumZoomLevel;
    /* access modifiers changed from: private */
    public MultiTouchController<Object> mMultiTouchController;
    protected float mMultiTouchScale;
    protected PointF mMultiTouchScalePoint;
    private final LinkedList<OnFirstLayoutListener> mOnFirstLayoutListeners;
    /* access modifiers changed from: private */
    public final List<OnTapListener> mOnTapListeners;
    private OverlayManager mOverlayManager;
    protected Projection mProjection;
    final Matrix mRotateScaleMatrix;
    final Point mRotateScalePoint;
    protected BoundingBox mScrollableAreaBoundingBox;
    protected Rect mScrollableAreaLimit;
    /* access modifiers changed from: private */
    public final Scroller mScroller;
    protected final AtomicReference<Double> mTargetZoomLevel;
    private MapTileProviderBase mTileProvider;
    private Handler mTileRequestCompleteHandler;
    private boolean mTilesScaledToDpi;
    /* access modifiers changed from: private */
    public final ZoomButtonsController mZoomController;
    private double mZoomLevel;
    private float mapOrientation;
    /* access modifiers changed from: private */
    public boolean pauseFling;

    public interface OnFirstLayoutListener {
        void onFirstLayout(View view, int i, int i2, int i3, int i4);
    }

    public interface OnTapListener {
        void onDoubleTap(MapView mapView, double d, double d2);

        void onSingleTap(MapView mapView, double d, double d2);
    }

    static /* synthetic */ boolean access$702(MapView x0, boolean x1) {
        boolean z = x1;
        boolean z2 = z;
        x0.pauseFling = z2;
        return z;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        this(context, tileProvider, tileRequestCompleteHandler, attrs, Configuration.getInstance().isMapViewHardwareAccelerated());
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MapView(android.content.Context r17, org.osmdroid.tileprovider.MapTileProviderBase r18, android.os.Handler r19, android.util.AttributeSet r20, boolean r21) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            r7 = r0
            r8 = r1
            r9 = r4
            r7.<init>(r8, r9)
            r7 = r0
            r8 = 0
            r7.mZoomLevel = r8
            r7 = r0
            java.util.concurrent.atomic.AtomicReference r8 = new java.util.concurrent.atomic.AtomicReference
            r15 = r8
            r8 = r15
            r9 = r15
            r9.<init>()
            r7.mTargetZoomLevel = r8
            r7 = r0
            java.util.concurrent.atomic.AtomicBoolean r8 = new java.util.concurrent.atomic.AtomicBoolean
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = 0
            r9.<init>(r10)
            r7.mIsAnimating = r8
            r7 = r0
            r8 = 0
            r7.mEnableZoomController = r8
            r7 = r0
            r8 = 1065353216(0x3f800000, float:1.0)
            r7.mMultiTouchScale = r8
            r7 = r0
            android.graphics.PointF r8 = new android.graphics.PointF
            r15 = r8
            r8 = r15
            r9 = r15
            r9.<init>()
            r7.mMultiTouchScalePoint = r8
            r7 = r0
            r8 = 0
            r7.mapOrientation = r8
            r7 = r0
            android.graphics.Rect r8 = new android.graphics.Rect
            r15 = r8
            r8 = r15
            r9 = r15
            r9.<init>()
            r7.mInvalidateRect = r8
            r7 = r0
            r8 = 0
            r7.mTilesScaledToDpi = r8
            r7 = r0
            android.graphics.Matrix r8 = new android.graphics.Matrix
            r15 = r8
            r8 = r15
            r9 = r15
            r9.<init>()
            r7.mRotateScaleMatrix = r8
            r7 = r0
            android.graphics.Point r8 = new android.graphics.Point
            r15 = r8
            r8 = r15
            r9 = r15
            r9.<init>()
            r7.mRotateScalePoint = r8
            r7 = r0
            android.graphics.Point r8 = new android.graphics.Point
            r15 = r8
            r8 = r15
            r9 = r15
            r9.<init>()
            r7.mLayoutPoint = r8
            r7 = r0
            java.util.LinkedList r8 = new java.util.LinkedList
            r15 = r8
            r8 = r15
            r9 = r15
            r9.<init>()
            r7.mOnFirstLayoutListeners = r8
            r7 = r0
            r8 = 0
            r7.mLayoutOccurred = r8
            r7 = r0
            java.util.LinkedList r8 = new java.util.LinkedList
            r15 = r8
            r8 = r15
            r9 = r15
            r9.<init>()
            r7.mOnTapListeners = r8
            r7 = r0
            r8 = 1
            r7.enableFling = r8
            r7 = r0
            r8 = 0
            r7.pauseFling = r8
            r7 = r0
            boolean r7 = r7.isInEditMode()
            if (r7 == 0) goto L_0x00b4
            r7 = r0
            r8 = 0
            r7.mTileRequestCompleteHandler = r8
            r7 = r0
            r8 = 0
            r7.mController = r8
            r7 = r0
            r8 = 0
            r7.mZoomController = r8
            r7 = r0
            r8 = 0
            r7.mScroller = r8
            r7 = r0
            r8 = 0
            r7.mGestureDetector = r8
        L_0x00b3:
            return
        L_0x00b4:
            r7 = r5
            if (r7 != 0) goto L_0x00c3
            int r7 = android.os.Build.VERSION.SDK_INT
            r8 = 11
            if (r7 < r8) goto L_0x00c3
            r7 = r0
            r8 = 1
            r9 = 0
            r7.setLayerType(r8, r9)
        L_0x00c3:
            r7 = r0
            org.osmdroid.views.MapController r8 = new org.osmdroid.views.MapController
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r0
            r9.<init>(r10)
            r7.mController = r8
            r7 = r0
            android.widget.Scroller r8 = new android.widget.Scroller
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r1
            r9.<init>(r10)
            r7.mScroller = r8
            r7 = r2
            if (r7 != 0) goto L_0x00fa
            r7 = r0
            r8 = r4
            org.osmdroid.tileprovider.tilesource.ITileSource r7 = r7.getTileSourceFromAttributes(r8)
            r6 = r7
            r7 = r0
            boolean r7 = r7.isInEditMode()
            if (r7 == 0) goto L_0x0186
            org.osmdroid.tileprovider.MapTileProviderArray r7 = new org.osmdroid.tileprovider.MapTileProviderArray
            r15 = r7
            r7 = r15
            r8 = r15
            r9 = r6
            r10 = 0
            r11 = 0
            org.osmdroid.tileprovider.modules.MapTileModuleProviderBase[] r11 = new org.osmdroid.tileprovider.modules.MapTileModuleProviderBase[r11]
            r8.<init>(r9, r10, r11)
        L_0x00f9:
            r2 = r7
        L_0x00fa:
            r7 = r0
            r8 = r3
            if (r8 != 0) goto L_0x0196
            org.osmdroid.tileprovider.util.SimpleInvalidationHandler r8 = new org.osmdroid.tileprovider.util.SimpleInvalidationHandler
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r0
            r9.<init>(r10)
        L_0x0107:
            r7.mTileRequestCompleteHandler = r8
            r7 = r0
            r8 = r2
            r7.mTileProvider = r8
            r7 = r0
            org.osmdroid.tileprovider.MapTileProviderBase r7 = r7.mTileProvider
            r8 = r0
            android.os.Handler r8 = r8.mTileRequestCompleteHandler
            r7.setTileRequestCompleteHandler(r8)
            r7 = r0
            r8 = r0
            org.osmdroid.tileprovider.MapTileProviderBase r8 = r8.mTileProvider
            org.osmdroid.tileprovider.tilesource.ITileSource r8 = r8.getTileSource()
            r7.updateTileSizeForDensity(r8)
            r7 = r0
            org.osmdroid.views.overlay.TilesOverlay r8 = new org.osmdroid.views.overlay.TilesOverlay
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r0
            org.osmdroid.tileprovider.MapTileProviderBase r10 = r10.mTileProvider
            r11 = r1
            r9.<init>(r10, r11)
            r7.mMapOverlay = r8
            r7 = r0
            org.osmdroid.views.overlay.DefaultOverlayManager r8 = new org.osmdroid.views.overlay.DefaultOverlayManager
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r0
            org.osmdroid.views.overlay.TilesOverlay r10 = r10.mMapOverlay
            r9.<init>(r10)
            r7.mOverlayManager = r8
            r7 = r0
            boolean r7 = r7.isInEditMode()
            if (r7 == 0) goto L_0x0199
            r7 = r0
            r8 = 0
            r7.mZoomController = r8
        L_0x0149:
            r7 = r0
            android.view.GestureDetector r8 = new android.view.GestureDetector
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r1
            org.osmdroid.views.MapView$MapViewGestureDetectorListener r11 = new org.osmdroid.views.MapView$MapViewGestureDetectorListener
            r15 = r11
            r11 = r15
            r12 = r15
            r13 = r0
            r14 = 0
            r12.<init>(r13, r14)
            r9.<init>(r10, r11)
            r7.mGestureDetector = r8
            r7 = r0
            android.view.GestureDetector r7 = r7.mGestureDetector
            org.osmdroid.views.MapView$MapViewDoubleClickListener r8 = new org.osmdroid.views.MapView$MapViewDoubleClickListener
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r0
            r11 = 0
            r9.<init>(r10, r11)
            r7.setOnDoubleTapListener(r8)
            org.osmdroid.config.IConfigurationProvider r7 = org.osmdroid.config.Configuration.getInstance()
            boolean r7 = r7.isMapViewRecyclerFriendly()
            if (r7 == 0) goto L_0x0184
            int r7 = android.os.Build.VERSION.SDK_INT
            r8 = 16
            if (r7 < r8) goto L_0x0184
            r7 = r0
            r8 = 1
            r7.setHasTransientState(r8)
        L_0x0184:
            goto L_0x00b3
        L_0x0186:
            org.osmdroid.tileprovider.MapTileProviderBasic r7 = new org.osmdroid.tileprovider.MapTileProviderBasic
            r15 = r7
            r7 = r15
            r8 = r15
            r9 = r1
            android.content.Context r9 = r9.getApplicationContext()
            r10 = r6
            r8.<init>(r9, r10)
            goto L_0x00f9
        L_0x0196:
            r8 = r3
            goto L_0x0107
        L_0x0199:
            r7 = r0
            android.widget.ZoomButtonsController r8 = new android.widget.ZoomButtonsController
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r0
            r9.<init>(r10)
            r7.mZoomController = r8
            r7 = r0
            android.widget.ZoomButtonsController r7 = r7.mZoomController
            org.osmdroid.views.MapView$MapViewZoomListener r8 = new org.osmdroid.views.MapView$MapViewZoomListener
            r15 = r8
            r8 = r15
            r9 = r15
            r10 = r0
            r11 = 0
            r9.<init>(r10, r11)
            r7.setOnZoomListener(r8)
            goto L_0x0149
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.MapView.<init>(android.content.Context, org.osmdroid.tileprovider.MapTileProviderBase, android.os.Handler, android.util.AttributeSet, boolean):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapView(Context context, AttributeSet attrs) {
        this(context, (MapTileProviderBase) null, (Handler) null, attrs);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapView(Context context) {
        this(context, (MapTileProviderBase) null, (Handler) null, (AttributeSet) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapView(Context context, MapTileProviderBase aTileProvider) {
        this(context, aTileProvider, (Handler) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MapView(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        this(context, aTileProvider, tileRequestCompleteHandler, (AttributeSet) null);
    }

    public IMapController getController() {
        return this.mController;
    }

    public List<Overlay> getOverlays() {
        return getOverlayManager().overlays();
    }

    public OverlayManager getOverlayManager() {
        return this.mOverlayManager;
    }

    public void setOverlayManager(OverlayManager overlayManager) {
        OverlayManager overlayManager2 = overlayManager;
        this.mOverlayManager = overlayManager2;
    }

    public MapTileProviderBase getTileProvider() {
        return this.mTileProvider;
    }

    public Scroller getScroller() {
        return this.mScroller;
    }

    public Handler getTileRequestCompleteHandler() {
        return this.mTileRequestCompleteHandler;
    }

    public int getLatitudeSpan() {
        return getBoundingBoxE6().getLatitudeSpanE6();
    }

    public double getLatitudeSpanDouble() {
        return getBoundingBox().getLatitudeSpan();
    }

    public int getLongitudeSpan() {
        return getBoundingBoxE6().getLongitudeSpanE6();
    }

    public double getLongitudeSpanDouble() {
        return getBoundingBox().getLongitudeSpan();
    }

    public BoundingBoxE6 getBoundingBoxE6() {
        return getProjection().getBoundingBoxE6();
    }

    public BoundingBox getBoundingBox() {
        return getProjection().getBoundingBox();
    }

    public Rect getScreenRect(Rect reuse) {
        Rect out = getIntrinsicScreenRect(reuse);
        if (!(getMapOrientation() == 0.0f || getMapOrientation() == 180.0f)) {
            Rect boundingBoxForRotatatedRectangle = GeometryMath.getBoundingBoxForRotatatedRectangle(out, out.centerX(), out.centerY(), getMapOrientation(), out);
        }
        return out;
    }

    public Rect getIntrinsicScreenRect(Rect rect) {
        Rect rect2;
        Rect rect3;
        Rect reuse = rect;
        if (reuse == null) {
            rect2 = rect3;
            new Rect();
        } else {
            rect2 = reuse;
        }
        Rect out = rect2;
        out.set(0, 0, getWidth(), getHeight());
        return out;
    }

    public Projection getProjection() {
        Projection projection;
        if (this.mProjection == null) {
            new Projection(this);
            this.mProjection = projection;
        }
        return this.mProjection;
    }

    /* access modifiers changed from: protected */
    public void setProjection(Projection p) {
        Projection projection = p;
        this.mProjection = projection;
    }

    /* access modifiers changed from: package-private */
    public void setMapCenter(IGeoPoint aCenter) {
        getController().animateTo(aCenter);
    }

    /* access modifiers changed from: package-private */
    public void setMapCenter(int aLatitudeE6, int aLongitudeE6) {
        IGeoPoint iGeoPoint;
        new GeoPoint(aLatitudeE6, aLongitudeE6);
        setMapCenter(iGeoPoint);
    }

    /* access modifiers changed from: package-private */
    public void setMapCenter(double aLatitude, double aLongitude) {
        IGeoPoint iGeoPoint;
        new GeoPoint(aLatitude, aLongitude);
        setMapCenter(iGeoPoint);
    }

    public boolean isTilesScaledToDpi() {
        return this.mTilesScaledToDpi;
    }

    public void setTilesScaledToDpi(boolean tilesScaledToDpi) {
        this.mTilesScaledToDpi = tilesScaledToDpi;
        updateTileSizeForDensity(getTileProvider().getTileSource());
    }

    private void updateTileSizeForDensity(ITileSource aTileSource) {
        StringBuilder sb;
        int tile_size = aTileSource.getTileSizePixels();
        int size = (int) (((float) tile_size) * (isTilesScaledToDpi() ? (getResources().getDisplayMetrics().density * 256.0f) / ((float) tile_size) : 1.0f));
        if (Configuration.getInstance().isDebugMapView()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("Scaling tiles to ").append(size).toString());
        }
        TileSystem.setTileSize(size);
    }

    public void setTileSource(ITileSource iTileSource) {
        ITileSource aTileSource = iTileSource;
        this.mTileProvider.setTileSource(aTileSource);
        updateTileSizeForDensity(aTileSource);
        checkZoomButtons();
        double zoomLevel = setZoomLevel(this.mZoomLevel);
        postInvalidate();
    }

    /* access modifiers changed from: package-private */
    public double setZoomLevel(double aZoomLevel) {
        ZoomEvent event;
        Point point;
        double newZoomLevel = Math.max((double) getMinZoomLevel(), Math.min((double) getMaxZoomLevel(), aZoomLevel));
        double curZoomLevel = this.mZoomLevel;
        if (newZoomLevel != curZoomLevel) {
            if (this.mScroller != null) {
                this.mScroller.forceFinished(true);
            }
            this.mIsFlinging = false;
        }
        IGeoPoint centerGeoPoint = getMapCenter();
        this.mZoomLevel = newZoomLevel;
        setProjection((Projection) null);
        checkZoomButtons();
        if (isLayoutOccurred()) {
            getController().setCenter(centerGeoPoint);
            new Point();
            Point snapPoint = point;
            Projection pj = getProjection();
            if (getOverlayManager().onSnapToItem((int) this.mMultiTouchScalePoint.x, (int) this.mMultiTouchScalePoint.y, snapPoint, this)) {
                getController().animateTo(pj.fromPixels(snapPoint.x, snapPoint.y, (GeoPoint) null));
            }
            this.mTileProvider.rescaleCache(pj, newZoomLevel, curZoomLevel, getScreenRect((Rect) null));
            this.pauseFling = true;
        }
        if (newZoomLevel != curZoomLevel) {
            if (this.mListener != null) {
                new ZoomEvent(this, newZoomLevel);
                boolean onZoom = this.mListener.onZoom(event);
            }
        }
        requestLayout();
        return this.mZoomLevel;
    }

    @Deprecated
    public void zoomToBoundingBox(BoundingBoxE6 boundingBoxE6) {
        BoundingBox box;
        BoundingBoxE6 boundingBox = boundingBoxE6;
        new BoundingBox(((double) boundingBox.getLatNorthE6()) / 1000000.0d, ((double) boundingBox.getLonEastE6()) / 1000000.0d, ((double) boundingBox.getLatSouthE6()) / 1000000.0d, ((double) boundingBox.getLonWestE6()) / 1000000.0d);
        zoomToBoundingBox(box, false);
    }

    public void zoomToBoundingBox(BoundingBox boundingBox, boolean z) {
        double latitudeSpan;
        double longitudeSpan;
        IGeoPoint iGeoPoint;
        StringBuilder sb;
        StringBuilder sb2;
        BoundingBox boundingBox2 = boundingBox;
        boolean animated = z;
        BoundingBox currentBox = getBoundingBox();
        if (this.mZoomLevel == ((double) getMaxZoomLevel())) {
            latitudeSpan = currentBox.getLatitudeSpan();
        } else {
            latitudeSpan = currentBox.getLatitudeSpan() / Math.pow(2.0d, ((double) getMaxZoomLevel()) - this.mZoomLevel);
        }
        double maxZoomLatitudeSpan = latitudeSpan;
        double requiredLatitudeZoom = ((double) getMaxZoomLevel()) - Math.ceil(Math.log(boundingBox2.getLatitudeSpan() / maxZoomLatitudeSpan) / Math.log(2.0d));
        if (this.mZoomLevel == ((double) getMaxZoomLevel())) {
            longitudeSpan = currentBox.getLongitudeSpan();
        } else {
            longitudeSpan = currentBox.getLongitudeSpan() / Math.pow(2.0d, ((double) getMaxZoomLevel()) - this.mZoomLevel);
        }
        double maxZoomLongitudeSpan = longitudeSpan;
        double requiredLongitudeZoom = ((double) getMaxZoomLevel()) - Math.ceil(Math.log(boundingBox2.getLongitudeSpan() / maxZoomLongitudeSpan) / Math.log(2.0d));
        if (Configuration.getInstance().isDebugMode()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("current bounds ").append(currentBox.toString()).toString());
            new StringBuilder();
            int d2 = Log.d(IMapView.LOGTAG, sb2.append("ZoomToBoundingBox calculations: ").append(maxZoomLatitudeSpan).append(",").append(maxZoomLongitudeSpan).append(",").append(requiredLatitudeZoom).append(",").append(requiredLongitudeZoom).toString());
        }
        if (animated) {
            boolean zoomTo = getController().zoomTo((int) (requiredLatitudeZoom < requiredLongitudeZoom ? requiredLatitudeZoom : requiredLongitudeZoom));
        } else {
            int zoom = getController().setZoom((int) (requiredLatitudeZoom < requiredLongitudeZoom ? requiredLatitudeZoom : requiredLongitudeZoom));
        }
        new GeoPoint(boundingBox2.getCenter().getLatitude(), boundingBox2.getCenter().getLongitude());
        getController().setCenter(iGeoPoint);
    }

    @Deprecated
    public int getZoomLevel() {
        return (int) getZoomLevelDouble();
    }

    public double getZoomLevelDouble() {
        return getZoomLevel(true);
    }

    public double getZoomLevel(boolean aPending) {
        if (!aPending || !isAnimating()) {
            return this.mZoomLevel;
        }
        return this.mTargetZoomLevel.get().doubleValue();
    }

    public int getMinZoomLevel() {
        return this.mMinimumZoomLevel == null ? this.mMapOverlay.getMinimumZoomLevel() : this.mMinimumZoomLevel.intValue();
    }

    public int getMaxZoomLevel() {
        return this.mMaximumZoomLevel == null ? this.mMapOverlay.getMaximumZoomLevel() : this.mMaximumZoomLevel.intValue();
    }

    public void setMinZoomLevel(Integer zoomLevel) {
        Integer num = zoomLevel;
        this.mMinimumZoomLevel = num;
    }

    public void setMaxZoomLevel(Integer zoomLevel) {
        Integer num = zoomLevel;
        this.mMaximumZoomLevel = num;
    }

    public boolean canZoomIn() {
        if ((isAnimating() ? this.mTargetZoomLevel.get().doubleValue() : this.mZoomLevel) >= ((double) getMaxZoomLevel())) {
            return false;
        }
        return true;
    }

    public boolean canZoomOut() {
        if ((isAnimating() ? this.mTargetZoomLevel.get().doubleValue() : this.mZoomLevel) <= ((double) getMinZoomLevel())) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean zoomIn() {
        return getController().zoomIn();
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public boolean zoomInFixing(IGeoPoint point) {
        Point coords = getProjection().toPixels(point, (Point) null);
        return getController().zoomInFixing(coords.x, coords.y);
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public boolean zoomInFixing(int xPixel, int yPixel) {
        return getController().zoomInFixing(xPixel, yPixel);
    }

    /* access modifiers changed from: package-private */
    public boolean zoomOut() {
        return getController().zoomOut();
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public boolean zoomOutFixing(IGeoPoint point) {
        Point coords = getProjection().toPixels(point, (Point) null);
        return zoomOutFixing(coords.x, coords.y);
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public boolean zoomOutFixing(int xPixel, int yPixel) {
        return getController().zoomOutFixing(xPixel, yPixel);
    }

    public IGeoPoint getMapCenter() {
        return getProjection().fromPixels(getWidth() / 2, getHeight() / 2, (GeoPoint) null);
    }

    public void setMapOrientation(float degrees) {
        setMapOrientation(degrees, true);
    }

    public void setMapOrientation(float degrees, boolean forceRedraw) {
        this.mapOrientation = degrees % 360.0f;
        if (forceRedraw) {
            requestLayout();
            invalidate();
        }
    }

    public float getMapOrientation() {
        return this.mapOrientation;
    }

    public boolean useDataConnection() {
        return this.mMapOverlay.useDataConnection();
    }

    public void setUseDataConnection(boolean aMode) {
        this.mMapOverlay.setUseDataConnection(aMode);
    }

    @Deprecated
    public void setScrollableAreaLimit(BoundingBoxE6 boundingBoxE6) {
        BoundingBox boundingBox;
        Rect rect;
        BoundingBoxE6 boundingBox2 = boundingBoxE6;
        new BoundingBox(((double) boundingBox2.getLatNorthE6()) / 1000000.0d, ((double) boundingBox2.getLonEastE6()) / 1000000.0d, ((double) boundingBox2.getLatSouthE6()) / 1000000.0d, ((double) boundingBox2.getLonWestE6()) / 1000000.0d);
        this.mScrollableAreaBoundingBox = boundingBox;
        if (boundingBox2 == null) {
            this.mScrollableAreaLimit = null;
            return;
        }
        Point upperLeft = TileSystem.LatLongToPixelXY(((double) boundingBox2.getLatNorthE6()) / 1000000.0d, ((double) boundingBox2.getLonWestE6()) / 1000000.0d, microsoft.mappoint.TileSystem.getMaximumZoomLevel(), (Point) null);
        Point lowerRight = TileSystem.LatLongToPixelXY(((double) boundingBox2.getLatSouthE6()) / 1000000.0d, ((double) boundingBox2.getLonEastE6()) / 1000000.0d, microsoft.mappoint.TileSystem.getMaximumZoomLevel(), (Point) null);
        new Rect(upperLeft.x, upperLeft.y, lowerRight.x, lowerRight.y);
        this.mScrollableAreaLimit = rect;
    }

    public void setScrollableAreaLimitDouble(BoundingBox boundingBox) {
        Rect rect;
        BoundingBox boundingBox2 = boundingBox;
        this.mScrollableAreaBoundingBox = boundingBox2;
        if (boundingBox2 == null) {
            this.mScrollableAreaLimit = null;
            return;
        }
        Point upperLeft = TileSystem.LatLongToPixelXY(boundingBox2.getLatNorth(), boundingBox2.getLonWest(), 23, (Point) null);
        Point lowerRight = TileSystem.LatLongToPixelXY(boundingBox2.getLatSouth(), boundingBox2.getLonEast(), 23, (Point) null);
        new Rect(upperLeft.x, upperLeft.y, lowerRight.x, lowerRight.y);
        this.mScrollableAreaLimit = rect;
    }

    public BoundingBox getScrollableAreaLimit() {
        return this.mScrollableAreaBoundingBox;
    }

    public void invalidateMapCoordinates(Rect rect) {
        Rect dirty = rect;
        invalidateMapCoordinates(dirty.left, dirty.top, dirty.right, dirty.bottom, false);
    }

    public void invalidateMapCoordinates(int left, int top, int right, int bottom) {
        invalidateMapCoordinates(left, top, right, bottom, false);
    }

    public void postInvalidateMapCoordinates(int left, int top, int right, int bottom) {
        invalidateMapCoordinates(left, top, right, bottom, true);
    }

    private void invalidateMapCoordinates(int left, int top, int right, int bottom, boolean z) {
        boolean post = z;
        this.mInvalidateRect.set(left, top, right, bottom);
        this.mInvalidateRect.offset(getScrollX(), getScrollY());
        int centerX = getScrollX() + (getWidth() / 2);
        int centerY = getScrollY() + (getHeight() / 2);
        if (getMapOrientation() != 0.0f) {
            Rect boundingBoxForRotatatedRectangle = GeometryMath.getBoundingBoxForRotatatedRectangle(this.mInvalidateRect, centerX, centerY, getMapOrientation() + 180.0f, this.mInvalidateRect);
        }
        if (post) {
            super.postInvalidate(this.mInvalidateRect.left, this.mInvalidateRect.top, this.mInvalidateRect.right, this.mInvalidateRect.bottom);
        } else {
            super.invalidate(this.mInvalidateRect);
        }
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        ViewGroup.LayoutParams layoutParams;
        new LayoutParams(-2, -2, (IGeoPoint) null, 8, 0, 0);
        return layoutParams;
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        ViewGroup.LayoutParams layoutParams;
        new LayoutParams(getContext(), attrs);
        return layoutParams;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        ViewGroup.LayoutParams layoutParams;
        new LayoutParams(p);
        return layoutParams;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int widthMeasureSpec = i;
        int heightMeasureSpec = i2;
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        myOnLayout(changed, l, t, r, b);
    }

    /* access modifiers changed from: protected */
    public void myOnLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = z;
        int l = i;
        int t = i2;
        int r = i3;
        int b = i4;
        if (this.initCenter != null) {
            Point scroll = TileSystem.LatLongToPixelXY(this.initCenter.getLatitude(), this.initCenter.getLongitude(), getZoomLevelDouble(), (Point) null);
            this.initCenter = null;
            scrollTo(scroll.x - (getWidth() / 2), scroll.y - (getHeight() / 2));
        }
        int count = getChildCount();
        for (int i5 = 0; i5 < count; i5++) {
            View child = getChildAt(i5);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childHeight = child.getMeasuredHeight();
                int childWidth = child.getMeasuredWidth();
                Point pixels = getProjection().toPixels(lp.geoPoint, this.mLayoutPoint);
                if (getMapOrientation() != 0.0f) {
                    Point p = getProjection().rotateAndScalePoint(this.mLayoutPoint.x, this.mLayoutPoint.y, (Point) null);
                    this.mLayoutPoint.x = p.x;
                    this.mLayoutPoint.y = p.y;
                }
                Point mercatorPixels = getProjection().toMercatorPixels(this.mLayoutPoint.x, this.mLayoutPoint.y, this.mLayoutPoint);
                int x = this.mLayoutPoint.x;
                int y = this.mLayoutPoint.y;
                int childLeft = x;
                int childTop = y;
                switch (lp.alignment) {
                    case 1:
                        childLeft = getPaddingLeft() + x;
                        childTop = getPaddingTop() + y;
                        break;
                    case 2:
                        childLeft = (getPaddingLeft() + x) - (childWidth / 2);
                        childTop = getPaddingTop() + y;
                        break;
                    case 3:
                        childLeft = (getPaddingLeft() + x) - childWidth;
                        childTop = getPaddingTop() + y;
                        break;
                    case 4:
                        childLeft = getPaddingLeft() + x;
                        childTop = (getPaddingTop() + y) - (childHeight / 2);
                        break;
                    case 5:
                        childLeft = (getPaddingLeft() + x) - (childWidth / 2);
                        childTop = (getPaddingTop() + y) - (childHeight / 2);
                        break;
                    case 6:
                        childLeft = (getPaddingLeft() + x) - childWidth;
                        childTop = (getPaddingTop() + y) - (childHeight / 2);
                        break;
                    case 7:
                        childLeft = getPaddingLeft() + x;
                        childTop = (getPaddingTop() + y) - childHeight;
                        break;
                    case 8:
                        childLeft = (getPaddingLeft() + x) - (childWidth / 2);
                        childTop = (getPaddingTop() + y) - childHeight;
                        break;
                    case 9:
                        childLeft = (getPaddingLeft() + x) - childWidth;
                        childTop = (getPaddingTop() + y) - childHeight;
                        break;
                }
                int childLeft2 = childLeft + lp.offsetX;
                int childTop2 = childTop + lp.offsetY;
                child.layout(childLeft2, childTop2, childLeft2 + childWidth, childTop2 + childHeight);
            }
        }
        if (!isLayoutOccurred()) {
            this.mLayoutOccurred = true;
            Iterator it = this.mOnFirstLayoutListeners.iterator();
            while (it.hasNext()) {
                ((OnFirstLayoutListener) it.next()).onFirstLayout(this, l, t, r, b);
            }
            this.mOnFirstLayoutListeners.clear();
        }
        setProjection((Projection) null);
    }

    public void addOnFirstLayoutListener(OnFirstLayoutListener onFirstLayoutListener) {
        OnFirstLayoutListener listener = onFirstLayoutListener;
        if (!isLayoutOccurred()) {
            boolean add = this.mOnFirstLayoutListeners.add(listener);
        }
    }

    public void removeOnFirstLayoutListener(OnFirstLayoutListener listener) {
        boolean remove = this.mOnFirstLayoutListeners.remove(listener);
    }

    public boolean isLayoutOccurred() {
        return this.mLayoutOccurred;
    }

    public void addOnTapListener(OnTapListener listener) {
        boolean add = this.mOnTapListeners.add(listener);
    }

    public void removeOnTapListener(OnTapListener listener) {
        boolean remove = this.mOnTapListeners.remove(listener);
    }

    public void onDetach() {
        getOverlayManager().onDetach(this);
        this.mTileProvider.detach();
        this.mTileProvider.clearTileCache();
        this.mZoomController.setVisible(false);
        if (this.mTileRequestCompleteHandler instanceof SimpleInvalidationHandler) {
            ((SimpleInvalidationHandler) this.mTileRequestCompleteHandler).destroy();
        }
        this.mTileRequestCompleteHandler = null;
        if (this.mProjection != null) {
            this.mProjection.detach();
        }
        this.mProjection = null;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        return getOverlayManager().onKeyDown(keyCode, event, this) || super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        return getOverlayManager().onKeyUp(keyCode, event, this) || super.onKeyUp(keyCode, event);
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        if (getOverlayManager().onTrackballEvent(event, this)) {
            return true;
        }
        scrollBy((int) (event.getX() * 25.0f), (int) (event.getY() * 25.0f));
        return super.onTrackballEvent(event);
    }

    /* JADX INFO: finally extract failed */
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        StringBuilder sb;
        MotionEvent event = motionEvent;
        if (Configuration.getInstance().isDebugMapView()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("dispatchTouchEvent(").append(event).append(")").toString());
        }
        if (this.mZoomController.isVisible() && this.mZoomController.onTouch(this, event)) {
            return true;
        }
        MotionEvent rotatedEvent = rotateTouchEvent(event);
        try {
            if (super.dispatchTouchEvent(event)) {
                if (Configuration.getInstance().isDebugMapView()) {
                    int d2 = Log.d(IMapView.LOGTAG, "super handled onTouchEvent");
                }
                if (rotatedEvent != event) {
                    rotatedEvent.recycle();
                }
                return true;
            } else if (getOverlayManager().onTouchEvent(rotatedEvent, this)) {
                if (rotatedEvent != event) {
                    rotatedEvent.recycle();
                }
                return true;
            } else {
                boolean handled = false;
                if (this.mMultiTouchController != null && this.mMultiTouchController.onTouchEvent(event)) {
                    if (Configuration.getInstance().isDebugMapView()) {
                        int d3 = Log.d(IMapView.LOGTAG, "mMultiTouchController handled onTouchEvent");
                    }
                    handled = true;
                }
                if (this.mGestureDetector.onTouchEvent(rotatedEvent)) {
                    if (Configuration.getInstance().isDebugMapView()) {
                        int d4 = Log.d(IMapView.LOGTAG, "mGestureDetector handled onTouchEvent");
                    }
                    handled = true;
                }
                if (handled) {
                    if (rotatedEvent != event) {
                        rotatedEvent.recycle();
                    }
                    return true;
                }
                if (rotatedEvent != event) {
                    rotatedEvent.recycle();
                }
                if (Configuration.getInstance().isDebugMapView()) {
                    int d5 = Log.d(IMapView.LOGTAG, "no-one handled onTouchEvent");
                }
                return false;
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            if (rotatedEvent != event) {
                rotatedEvent.recycle();
            }
            throw th2;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = motionEvent;
        return false;
    }

    private MotionEvent rotateTouchEvent(MotionEvent motionEvent) {
        MotionEvent ev = motionEvent;
        if (getMapOrientation() == 0.0f) {
            return ev;
        }
        MotionEvent rotatedEvent = MotionEvent.obtain(ev);
        if (Build.VERSION.SDK_INT < 11) {
            Point unrotateAndScalePoint = getProjection().unrotateAndScalePoint((int) ev.getX(), (int) ev.getY(), this.mRotateScalePoint);
            rotatedEvent.setLocation((float) this.mRotateScalePoint.x, (float) this.mRotateScalePoint.y);
        } else {
            try {
                if (sMotionEventTransformMethod == null) {
                    sMotionEventTransformMethod = MotionEvent.class.getDeclaredMethod("transform", new Class[]{Matrix.class});
                }
                Object invoke = sMotionEventTransformMethod.invoke(rotatedEvent, new Object[]{getProjection().getInvertedScaleRotateCanvasMatrix()});
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            }
        }
        return rotatedEvent;
    }

    public void computeScroll() {
        if (this.mScroller != null && this.mScroller.computeScrollOffset()) {
            if (this.mScroller.isFinished()) {
                scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
                double zoomLevel = setZoomLevel(this.mZoomLevel);
                this.mIsFlinging = false;
            } else {
                scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            }
            postInvalidate();
        }
    }

    public void scrollTo(int i, int i2) {
        ScrollEvent event;
        int x = i;
        int y = i2;
        double currentZoomLevel = getZoomLevel(false);
        double worldSize = TileSystem.MapSize(currentZoomLevel);
        while (x < 0) {
            x = (int) (((double) x) + worldSize);
        }
        while (((double) x) >= worldSize) {
            x = (int) (((double) x) - worldSize);
        }
        while (y < 0) {
            y = (int) (((double) y) + worldSize);
        }
        while (((double) y) >= worldSize) {
            y = (int) (((double) y) - worldSize);
        }
        if (this.mScrollableAreaLimit != null) {
            double power = TileSystem.getFactor(((double) microsoft.mappoint.TileSystem.getMaximumZoomLevel()) - currentZoomLevel);
            int minX = (int) (((double) this.mScrollableAreaLimit.left) / power);
            int minY = (int) (((double) this.mScrollableAreaLimit.top) / power);
            int maxX = (int) (((double) this.mScrollableAreaLimit.right) / power);
            int maxY = (int) (((double) this.mScrollableAreaLimit.bottom) / power);
            int scrollableWidth = maxX - minX;
            int scrollableHeight = maxY - minY;
            int width = getWidth();
            int height = getHeight();
            if (scrollableWidth <= width) {
                if (x > minX) {
                    x = minX;
                } else {
                    if (x + width < maxX) {
                        x = maxX - width;
                    }
                }
            } else if (x < minX) {
                x = minX;
            } else {
                if (x + width > maxX) {
                    x = maxX - width;
                }
            }
            if (scrollableHeight <= height) {
                if (y > minY) {
                    y = minY;
                } else {
                    if (y + height < maxY) {
                        y = maxY - height;
                    }
                }
            } else if (y + 0 < minY) {
                y = minY + 0;
            } else {
                if (y + height > maxY) {
                    y = maxY - height;
                }
            }
        }
        super.scrollTo(x, y);
        setProjection((Projection) null);
        if (getMapOrientation() != 0.0f) {
            myOnLayout(true, getLeft(), getTop(), getRight(), getBottom());
        }
        if (this.mListener != null) {
            new ScrollEvent(this, x, y);
            boolean onScroll = this.mListener.onScroll(event);
        }
    }

    public void setBackgroundColor(int pColor) {
        this.mMapOverlay.setLoadingBackgroundColor(pColor);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        StringBuilder sb;
        Canvas c = canvas;
        long startMs = System.currentTimeMillis();
        int save = c.save();
        this.mRotateScaleMatrix.reset();
        c.translate((float) getScrollX(), (float) getScrollY());
        boolean preScale = this.mRotateScaleMatrix.preScale(this.mMultiTouchScale, this.mMultiTouchScale, this.mMultiTouchScalePoint.x, this.mMultiTouchScalePoint.y);
        boolean preRotate = this.mRotateScaleMatrix.preRotate(this.mapOrientation, (float) (getWidth() / 2), (float) (getHeight() / 2));
        c.concat(this.mRotateScaleMatrix);
        setProjection((Projection) null);
        try {
            getOverlayManager().onDraw(c, this);
            c.restore();
            super.dispatchDraw(c);
        } catch (Exception e) {
            int e2 = Log.e(IMapView.LOGTAG, "error dispatchDraw, probably in edit mode", e);
        }
        if (Configuration.getInstance().isDebugMapView()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("Rendering overall: ").append(System.currentTimeMillis() - startMs).append("ms").toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mZoomController.setVisible(false);
        onDetach();
        super.onDetachedFromWindow();
    }

    public boolean isAnimating() {
        return this.mIsAnimating.get();
    }

    public Object getDraggableObjectAtPoint(MultiTouchController.PointInfo pointInfo) {
        MultiTouchController.PointInfo pt = pointInfo;
        if (isAnimating()) {
            return null;
        }
        this.mMultiTouchScalePoint.x = pt.getX();
        this.mMultiTouchScalePoint.y = pt.getY();
        return this;
    }

    public void getPositionAndScale(Object obj, MultiTouchController.PositionAndScale objPosAndScaleOut) {
        Object obj2 = obj;
        objPosAndScaleOut.set(0.0f, 0.0f, true, this.mMultiTouchScale, false, 0.0f, 0.0f, false, 0.0f);
    }

    public void selectObject(Object obj, MultiTouchController.PointInfo pointInfo) {
        MultiTouchController.PointInfo pointInfo2 = pointInfo;
        if (obj == null && this.mMultiTouchScale != 1.0f) {
            float scaleDiffFloat = (float) (Math.log((double) this.mMultiTouchScale) * ZOOM_LOG_BASE_INV);
            int round = Math.round(scaleDiffFloat);
            if (scaleDiffFloat != 0.0f) {
                Rect screenRect = getProjection().getScreenRect();
                Point unrotateAndScalePoint = getProjection().unrotateAndScalePoint(screenRect.centerX(), screenRect.centerY(), this.mRotateScalePoint);
                Point p = getProjection().toMercatorPixels(this.mRotateScalePoint.x, this.mRotateScalePoint.y, (Point) null);
                scrollTo(p.x - (getWidth() / 2), p.y - (getHeight() / 2));
            }
            double zoomLevel = setZoomLevel(this.mZoomLevel + ((double) scaleDiffFloat));
        }
        this.mMultiTouchScale = 1.0f;
    }

    public boolean setPositionAndScale(Object obj, MultiTouchController.PositionAndScale aNewObjPosAndScale, MultiTouchController.PointInfo pointInfo) {
        Object obj2 = obj;
        MultiTouchController.PointInfo pointInfo2 = pointInfo;
        float multiTouchScale = aNewObjPosAndScale.getScale();
        if (multiTouchScale > 1.0f && !canZoomIn()) {
            multiTouchScale = 1.0f;
        }
        if (multiTouchScale < 1.0f && !canZoomOut()) {
            multiTouchScale = 1.0f;
        }
        this.mMultiTouchScale = multiTouchScale;
        requestLayout();
        invalidate();
        return true;
    }

    public void setMapListener(MapListener ml) {
        MapListener mapListener = ml;
        this.mListener = mapListener;
    }

    private void checkZoomButtons() {
        this.mZoomController.setZoomInEnabled(canZoomIn());
        this.mZoomController.setZoomOutEnabled(canZoomOut());
    }

    public void setBuiltInZoomControls(boolean on) {
        this.mEnableZoomController = on;
        checkZoomButtons();
    }

    public void setMultiTouchControls(boolean on) {
        MultiTouchController<Object> multiTouchController;
        MultiTouchController<Object> multiTouchController2;
        if (on) {
            multiTouchController = multiTouchController2;
            new MultiTouchController<>(this, false);
        } else {
            multiTouchController = null;
        }
        this.mMultiTouchController = multiTouchController;
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.osmdroid.tileprovider.tilesource.ITileSource getTileSourceFromAttributes(android.util.AttributeSet r10) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase r5 = org.osmdroid.tileprovider.tilesource.TileSourceFactory.DEFAULT_TILE_SOURCE
            r2 = r5
            r5 = r1
            if (r5 == 0) goto L_0x003c
            r5 = r1
            r6 = 0
            java.lang.String r7 = "tilesource"
            java.lang.String r5 = r5.getAttributeValue(r6, r7)
            r3 = r5
            r5 = r3
            if (r5 == 0) goto L_0x003c
            r5 = r3
            org.osmdroid.tileprovider.tilesource.ITileSource r5 = org.osmdroid.tileprovider.tilesource.TileSourceFactory.getTileSource((java.lang.String) r5)     // Catch:{ IllegalArgumentException -> 0x0081 }
            r4 = r5
            java.lang.String r5 = "OsmDroid"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0081 }
            r8 = r6
            r6 = r8
            r7 = r8
            r7.<init>()     // Catch:{ IllegalArgumentException -> 0x0081 }
            java.lang.String r7 = "Using tile source specified in layout attributes: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IllegalArgumentException -> 0x0081 }
            r7 = r4
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IllegalArgumentException -> 0x0081 }
            java.lang.String r6 = r6.toString()     // Catch:{ IllegalArgumentException -> 0x0081 }
            int r5 = android.util.Log.i(r5, r6)     // Catch:{ IllegalArgumentException -> 0x0081 }
            r5 = r4
            r2 = r5
        L_0x003c:
            r5 = r1
            if (r5 == 0) goto L_0x005b
            r5 = r2
            boolean r5 = r5 instanceof org.osmdroid.tileprovider.tilesource.IStyledTileSource
            if (r5 == 0) goto L_0x005b
            r5 = r1
            r6 = 0
            java.lang.String r7 = "style"
            java.lang.String r5 = r5.getAttributeValue(r6, r7)
            r3 = r5
            r5 = r3
            if (r5 != 0) goto L_0x00a3
            java.lang.String r5 = "OsmDroid"
            java.lang.String r6 = "Using default style: 1"
            int r5 = android.util.Log.i(r5, r6)
        L_0x005b:
            java.lang.String r5 = "OsmDroid"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r8 = r6
            r6 = r8
            r7 = r8
            r7.<init>()
            java.lang.String r7 = "Using tile source: "
            java.lang.StringBuilder r6 = r6.append(r7)
            r7 = r2
            java.lang.String r7 = r7.name()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            int r5 = android.util.Log.i(r5, r6)
            r5 = r2
            r0 = r5
            return r0
        L_0x0081:
            r5 = move-exception
            r4 = r5
            java.lang.String r5 = "OsmDroid"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r8 = r6
            r6 = r8
            r7 = r8
            r7.<init>()
            java.lang.String r7 = "Invalid tile source specified in layout attributes: "
            java.lang.StringBuilder r6 = r6.append(r7)
            r7 = r2
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            int r5 = android.util.Log.w(r5, r6)
            goto L_0x003c
        L_0x00a3:
            java.lang.String r5 = "OsmDroid"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r8 = r6
            r6 = r8
            r7 = r8
            r7.<init>()
            java.lang.String r7 = "Using style specified in layout attributes: "
            java.lang.StringBuilder r6 = r6.append(r7)
            r7 = r3
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            int r5 = android.util.Log.i(r5, r6)
            r5 = r2
            org.osmdroid.tileprovider.tilesource.IStyledTileSource r5 = (org.osmdroid.tileprovider.tilesource.IStyledTileSource) r5
            r6 = r3
            r5.setStyle((java.lang.String) r6)
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.MapView.getTileSourceFromAttributes(android.util.AttributeSet):org.osmdroid.tileprovider.tilesource.ITileSource");
    }

    public void setFlingEnabled(boolean b) {
        boolean z = b;
        this.enableFling = z;
    }

    public boolean isFlingEnabled() {
        return this.enableFling;
    }

    private class MapViewGestureDetectorListener implements GestureDetector.OnGestureListener {
        final /* synthetic */ MapView this$0;

        private MapViewGestureDetectorListener(MapView mapView) {
            this.this$0 = mapView;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ MapViewGestureDetectorListener(MapView x0, C15971 r7) {
            this(x0);
            C15971 r2 = r7;
        }

        public boolean onDown(MotionEvent motionEvent) {
            MotionEvent e = motionEvent;
            if (this.this$0.mIsFlinging) {
                if (this.this$0.mScroller != null) {
                    this.this$0.mScroller.abortAnimation();
                }
                this.this$0.mIsFlinging = false;
            }
            if (this.this$0.getOverlayManager().onDown(e, this.this$0)) {
                return true;
            }
            this.this$0.mZoomController.setVisible(this.this$0.mEnableZoomController);
            return true;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            MotionEvent e1 = motionEvent;
            MotionEvent e2 = motionEvent2;
            float velocityX = f;
            float velocityY = f2;
            if (!this.this$0.enableFling || this.this$0.pauseFling) {
                boolean access$702 = MapView.access$702(this.this$0, false);
                return false;
            } else if (this.this$0.getOverlayManager().onFling(e1, e2, velocityX, velocityY, this.this$0)) {
                return true;
            } else {
                double worldSize = TileSystem.MapSize(this.this$0.getZoomLevel(false));
                this.this$0.mIsFlinging = true;
                if (this.this$0.mScroller != null) {
                    this.this$0.mScroller.fling(this.this$0.getScrollX(), this.this$0.getScrollY(), (int) (-velocityX), (int) (-velocityY), -((int) worldSize), (int) worldSize, -((int) worldSize), (int) worldSize);
                }
                return true;
            }
        }

        public void onLongPress(MotionEvent motionEvent) {
            MotionEvent e = motionEvent;
            if (this.this$0.mMultiTouchController == null || !this.this$0.mMultiTouchController.isPinching()) {
                boolean onLongPress = this.this$0.getOverlayManager().onLongPress(e, this.this$0);
            }
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float f, float f2) {
            float distanceX = f;
            float distanceY = f2;
            if (this.this$0.getOverlayManager().onScroll(e1, e2, distanceX, distanceY, this.this$0)) {
                return true;
            }
            this.this$0.scrollBy((int) distanceX, (int) distanceY);
            return true;
        }

        public void onShowPress(MotionEvent e) {
            this.this$0.getOverlayManager().onShowPress(e, this.this$0);
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            MotionEvent e = motionEvent;
            if (this.this$0.getOverlayManager().onSingleTapUp(e, this.this$0)) {
                return true;
            }
            IGeoPoint pt = this.this$0.getProjection().fromPixels((int) e.getX(), (int) e.getY());
            for (OnTapListener listener : this.this$0.mOnTapListeners) {
                listener.onDoubleTap(this.this$0, pt.getLatitude(), pt.getLongitude());
            }
            return false;
        }
    }

    private class MapViewDoubleClickListener implements GestureDetector.OnDoubleTapListener {
        final /* synthetic */ MapView this$0;

        private MapViewDoubleClickListener(MapView mapView) {
            this.this$0 = mapView;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ MapViewDoubleClickListener(MapView x0, C15971 r7) {
            this(x0);
            C15971 r2 = r7;
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            MotionEvent e = motionEvent;
            if (this.this$0.getOverlayManager().onDoubleTap(e, this.this$0)) {
                return true;
            }
            if (this.this$0.mMultiTouchController == null) {
                return false;
            }
            Point rotateAndScalePoint = this.this$0.getProjection().rotateAndScalePoint((int) e.getX(), (int) e.getY(), this.this$0.mRotateScalePoint);
            return this.this$0.zoomInFixing(this.this$0.mRotateScalePoint.x, this.this$0.mRotateScalePoint.y);
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            if (this.this$0.getOverlayManager().onDoubleTapEvent(e, this.this$0)) {
                return true;
            }
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            MotionEvent e = motionEvent;
            if (this.this$0.getOverlayManager().onSingleTapConfirmed(e, this.this$0)) {
                return true;
            }
            IGeoPoint pt = this.this$0.getProjection().fromPixels((int) e.getX(), (int) e.getY());
            for (OnTapListener listener : this.this$0.mOnTapListeners) {
                listener.onSingleTap(this.this$0, pt.getLatitude(), pt.getLongitude());
            }
            return false;
        }
    }

    private class MapViewZoomListener implements ZoomButtonsController.OnZoomListener {
        final /* synthetic */ MapView this$0;

        private MapViewZoomListener(MapView mapView) {
            this.this$0 = mapView;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ MapViewZoomListener(MapView x0, C15971 r7) {
            this(x0);
            C15971 r2 = r7;
        }

        public void onZoom(boolean zoomIn) {
            if (zoomIn) {
                boolean zoomIn2 = this.this$0.getController().zoomIn();
            } else {
                boolean zoomOut = this.this$0.getController().zoomOut();
            }
        }

        public void onVisibilityChanged(boolean visible) {
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public static final int BOTTOM_CENTER = 8;
        public static final int BOTTOM_LEFT = 7;
        public static final int BOTTOM_RIGHT = 9;
        public static final int CENTER = 5;
        public static final int CENTER_LEFT = 4;
        public static final int CENTER_RIGHT = 6;
        public static final int TOP_CENTER = 2;
        public static final int TOP_LEFT = 1;
        public static final int TOP_RIGHT = 3;
        public int alignment;
        public IGeoPoint geoPoint;
        public int offsetX;
        public int offsetY;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LayoutParams(int width, int height, IGeoPoint iGeoPoint, int i, int i2, int i3) {
            super(width, height);
            IGeoPoint iGeoPoint2;
            IGeoPoint geoPoint2 = iGeoPoint;
            int alignment2 = i;
            int offsetX2 = i2;
            int offsetY2 = i3;
            if (geoPoint2 != null) {
                this.geoPoint = geoPoint2;
            } else {
                new GeoPoint(0, 0);
                this.geoPoint = iGeoPoint2;
            }
            this.alignment = alignment2;
            this.offsetX = offsetX2;
            this.offsetY = offsetY2;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            IGeoPoint iGeoPoint;
            new GeoPoint(0, 0);
            this.geoPoint = iGeoPoint;
            this.alignment = 8;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public void setTileProvider(MapTileProviderBase base) {
        TilesOverlay tilesOverlay;
        this.mTileProvider.detach();
        this.mTileProvider.clearTileCache();
        this.mTileProvider = base;
        this.mTileProvider.setTileRequestCompleteHandler(this.mTileRequestCompleteHandler);
        updateTileSizeForDensity(this.mTileProvider.getTileSource());
        new TilesOverlay(this.mTileProvider, getContext());
        this.mMapOverlay = tilesOverlay;
        this.mOverlayManager.setTilesOverlay(this.mMapOverlay);
        invalidate();
    }

    public void setInitCenter(IGeoPoint geoPoint) {
        IGeoPoint iGeoPoint = geoPoint;
        this.initCenter = iGeoPoint;
    }
}
