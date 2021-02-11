package org.osmdroid.views.overlay.mylocation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import java.util.Iterator;
import java.util.LinkedList;
import org.osmdroid.api.IMapController;
import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.C1262R;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.IOverlayMenuProvider;
import org.osmdroid.views.overlay.Overlay;

public class MyLocationNewOverlay extends Overlay implements IMyLocationConsumer, IOverlayMenuProvider, Overlay.Snappable {
    public static final int MENU_MY_LOCATION = getSafeMenuId();
    protected boolean enableAutoStop;
    protected Paint mCirclePaint;
    protected Bitmap mDirectionArrowBitmap;
    protected float mDirectionArrowCenterX;
    protected float mDirectionArrowCenterY;
    protected boolean mDrawAccuracyEnabled;
    private final GeoPoint mGeoPoint;
    private Handler mHandler;
    private Object mHandlerToken;
    protected boolean mIsFollowing;
    private boolean mIsLocationEnabled;
    private Location mLocation;
    private IMapController mMapController;
    private final Point mMapCoordsProjected;
    private final Point mMapCoordsTranslated;
    protected MapView mMapView;
    private Matrix mMatrix;
    private final float[] mMatrixValues;
    private Rect mMyLocationPreviousRect;
    public IMyLocationProvider mMyLocationProvider;
    private Rect mMyLocationRect;
    private boolean mOptionsMenuEnabled;
    protected Paint mPaint;
    protected Bitmap mPersonBitmap;
    protected final PointF mPersonHotspot;
    /* access modifiers changed from: private */
    public final LinkedList<Runnable> mRunOnFirstFix;
    protected final float mScale;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MyLocationNewOverlay(org.osmdroid.views.MapView r8) {
        /*
            r7 = this;
            r0 = r7
            r1 = r8
            r2 = r0
            org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider r3 = new org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
            r6 = r3
            r3 = r6
            r4 = r6
            r5 = r1
            android.content.Context r5 = r5.getContext()
            r4.<init>(r5)
            r4 = r1
            r2.<init>(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay.<init>(org.osmdroid.views.MapView):void");
    }

    public MyLocationNewOverlay(IMyLocationProvider myLocationProvider, MapView mapView) {
        Paint paint;
        Paint paint2;
        LinkedList<Runnable> linkedList;
        Point point;
        Point point2;
        Object obj;
        GeoPoint geoPoint;
        Matrix matrix;
        Rect rect;
        Rect rect2;
        PointF pointF;
        Handler handler;
        MapView mapView2 = mapView;
        new Paint();
        this.mPaint = paint;
        new Paint();
        this.mCirclePaint = paint2;
        new LinkedList<>();
        this.mRunOnFirstFix = linkedList;
        new Point();
        this.mMapCoordsProjected = point;
        new Point();
        this.mMapCoordsTranslated = point2;
        new Object();
        this.mHandlerToken = obj;
        this.enableAutoStop = true;
        new GeoPoint(0, 0);
        this.mGeoPoint = geoPoint;
        this.mIsLocationEnabled = false;
        this.mIsFollowing = false;
        this.mDrawAccuracyEnabled = true;
        this.mOptionsMenuEnabled = true;
        this.mMatrixValues = new float[9];
        new Matrix();
        this.mMatrix = matrix;
        new Rect();
        this.mMyLocationRect = rect;
        new Rect();
        this.mMyLocationPreviousRect = rect2;
        this.mScale = mapView2.getContext().getResources().getDisplayMetrics().density;
        this.mMapView = mapView2;
        this.mMapController = mapView2.getController();
        this.mCirclePaint.setARGB(0, 100, 100, 255);
        this.mCirclePaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        setDirectionArrow(((BitmapDrawable) mapView2.getContext().getResources().getDrawable(C1262R.C1263drawable.person)).getBitmap(), ((BitmapDrawable) mapView2.getContext().getResources().getDrawable(C1262R.C1263drawable.direction_arrow)).getBitmap());
        new PointF((24.0f * this.mScale) + 0.5f, (39.0f * this.mScale) + 0.5f);
        this.mPersonHotspot = pointF;
        new Handler(Looper.getMainLooper());
        this.mHandler = handler;
        setMyLocationProvider(myLocationProvider);
    }

    public void setDirectionArrow(Bitmap personBitmap, Bitmap directionArrowBitmap) {
        this.mPersonBitmap = personBitmap;
        this.mDirectionArrowBitmap = directionArrowBitmap;
        this.mDirectionArrowCenterX = (((float) this.mDirectionArrowBitmap.getWidth()) / 2.0f) - 0.5f;
        this.mDirectionArrowCenterY = (((float) this.mDirectionArrowBitmap.getHeight()) / 2.0f) - 0.5f;
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        disableMyLocation();
        this.mMapView = null;
        this.mMapController = null;
        this.mHandler = null;
        this.mMatrix = null;
        this.mCirclePaint = null;
        this.mHandlerToken = null;
        this.mLocation = null;
        this.mMapController = null;
        this.mMyLocationPreviousRect = null;
        if (this.mMyLocationProvider != null) {
            this.mMyLocationProvider.destroy();
        }
        this.mMyLocationProvider = null;
        super.onDetach(mapView2);
    }

    public void setDrawAccuracyEnabled(boolean drawAccuracyEnabled) {
        boolean z = drawAccuracyEnabled;
        this.mDrawAccuracyEnabled = z;
    }

    public boolean isDrawAccuracyEnabled() {
        return this.mDrawAccuracyEnabled;
    }

    public IMyLocationProvider getMyLocationProvider() {
        return this.mMyLocationProvider;
    }

    /* access modifiers changed from: protected */
    public void setMyLocationProvider(IMyLocationProvider iMyLocationProvider) {
        Throwable th;
        IMyLocationProvider myLocationProvider = iMyLocationProvider;
        if (myLocationProvider == null) {
            Throwable th2 = th;
            new RuntimeException("You must pass an IMyLocationProvider to setMyLocationProvider()");
            throw th2;
        }
        if (isMyLocationEnabled()) {
            stopLocationProvider();
        }
        this.mMyLocationProvider = myLocationProvider;
    }

    public void setPersonHotspot(float x, float y) {
        this.mPersonHotspot.set(x, y);
    }

    /* access modifiers changed from: protected */
    public void drawMyLocation(Canvas canvas, MapView mapView, Location location) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        Canvas canvas2 = canvas;
        MapView mapView2 = mapView;
        Location lastFix = location;
        Point pixelsFromProjected = mapView2.getProjection().toPixelsFromProjected(this.mMapCoordsProjected, this.mMapCoordsTranslated);
        if (this.mDrawAccuracyEnabled) {
            float radius = lastFix.getAccuracy() / ((float) TileSystem.GroundResolution(lastFix.getLatitude(), mapView2.getZoomLevelDouble()));
            this.mCirclePaint.setAlpha(50);
            this.mCirclePaint.setStyle(Paint.Style.FILL);
            canvas2.drawCircle((float) this.mMapCoordsTranslated.x, (float) this.mMapCoordsTranslated.y, radius, this.mCirclePaint);
            this.mCirclePaint.setAlpha(150);
            this.mCirclePaint.setStyle(Paint.Style.STROKE);
            canvas2.drawCircle((float) this.mMapCoordsTranslated.x, (float) this.mMapCoordsTranslated.y, radius, this.mCirclePaint);
        }
        canvas2.getMatrix(this.mMatrix);
        this.mMatrix.getValues(this.mMatrixValues);
        if (Configuration.getInstance().isDebugMode()) {
            float tx = ((-this.mMatrixValues[2]) + 20.0f) / this.mMatrixValues[0];
            float ty = ((-this.mMatrixValues[5]) + 90.0f) / this.mMatrixValues[4];
            new StringBuilder();
            canvas2.drawText(sb.append("Lat: ").append(lastFix.getLatitude()).toString(), tx, ty + 5.0f, this.mPaint);
            new StringBuilder();
            canvas2.drawText(sb2.append("Lon: ").append(lastFix.getLongitude()).toString(), tx, ty + 20.0f, this.mPaint);
            new StringBuilder();
            canvas2.drawText(sb3.append("Alt: ").append(lastFix.getAltitude()).toString(), tx, ty + 35.0f, this.mPaint);
            new StringBuilder();
            canvas2.drawText(sb4.append("Acc: ").append(lastFix.getAccuracy()).toString(), tx, ty + 50.0f, this.mPaint);
        }
        float scaleX = (float) Math.sqrt((double) ((this.mMatrixValues[0] * this.mMatrixValues[0]) + (this.mMatrixValues[3] * this.mMatrixValues[3])));
        float scaleY = (float) Math.sqrt((double) ((this.mMatrixValues[4] * this.mMatrixValues[4]) + (this.mMatrixValues[1] * this.mMatrixValues[1])));
        if (lastFix.hasBearing()) {
            int save = canvas2.save();
            float mapOrientation = mapView2.getMapOrientation();
            float mapRotation = lastFix.getBearing();
            if (mapRotation >= 360.0f) {
                mapRotation -= 360.0f;
            }
            canvas2.rotate(mapRotation, (float) this.mMapCoordsTranslated.x, (float) this.mMapCoordsTranslated.y);
            canvas2.scale(1.0f / scaleX, 1.0f / scaleY, (float) this.mMapCoordsTranslated.x, (float) this.mMapCoordsTranslated.y);
            canvas2.drawBitmap(this.mDirectionArrowBitmap, ((float) this.mMapCoordsTranslated.x) - this.mDirectionArrowCenterX, ((float) this.mMapCoordsTranslated.y) - this.mDirectionArrowCenterY, this.mPaint);
            canvas2.restore();
            return;
        }
        int save2 = canvas2.save();
        canvas2.rotate(-this.mMapView.getMapOrientation(), (float) this.mMapCoordsTranslated.x, (float) this.mMapCoordsTranslated.y);
        canvas2.scale(1.0f / scaleX, 1.0f / scaleY, (float) this.mMapCoordsTranslated.x, (float) this.mMapCoordsTranslated.y);
        canvas2.drawBitmap(this.mPersonBitmap, ((float) this.mMapCoordsTranslated.x) - this.mPersonHotspot.x, ((float) this.mMapCoordsTranslated.y) - this.mPersonHotspot.y, this.mPaint);
        canvas2.restore();
    }

    /* access modifiers changed from: protected */
    public Rect getMyLocationDrawingBounds(int i, Location location, Rect rect) {
        double strokeWidth;
        Rect rect2;
        int zoomLevel = i;
        Location lastFix = location;
        Rect reuse = rect;
        if (reuse == null) {
            new Rect();
            reuse = rect2;
        }
        Point pixelsFromProjected = this.mMapView.getProjection().toPixelsFromProjected(this.mMapCoordsProjected, this.mMapCoordsTranslated);
        if (lastFix.hasBearing()) {
            int widestEdge = (int) Math.ceil(((double) Math.max(this.mDirectionArrowBitmap.getWidth(), this.mDirectionArrowBitmap.getHeight())) * Math.sqrt(2.0d));
            reuse.set(this.mMapCoordsTranslated.x, this.mMapCoordsTranslated.y, this.mMapCoordsTranslated.x + widestEdge, this.mMapCoordsTranslated.y + widestEdge);
            reuse.offset((-widestEdge) / 2, (-widestEdge) / 2);
        } else {
            reuse.set(this.mMapCoordsTranslated.x, this.mMapCoordsTranslated.y, this.mMapCoordsTranslated.x + this.mPersonBitmap.getWidth(), this.mMapCoordsTranslated.y + this.mPersonBitmap.getHeight());
            reuse.offset((int) ((-this.mPersonHotspot.x) + 0.5f), (int) ((-this.mPersonHotspot.y) + 0.5f));
        }
        if (this.mDrawAccuracyEnabled) {
            int radius = (int) Math.ceil((double) (lastFix.getAccuracy() / ((float) TileSystem.GroundResolution(lastFix.getLatitude(), zoomLevel))));
            reuse.union(this.mMapCoordsTranslated.x - radius, this.mMapCoordsTranslated.y - radius, this.mMapCoordsTranslated.x + radius, this.mMapCoordsTranslated.y + radius);
            if (this.mCirclePaint.getStrokeWidth() == 0.0f) {
                strokeWidth = 1.0d;
            } else {
                strokeWidth = (double) this.mCirclePaint.getStrokeWidth();
            }
            int strokeWidth2 = (int) Math.ceil(strokeWidth);
            reuse.inset(-strokeWidth2, -strokeWidth2);
        }
        return reuse;
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Canvas c = canvas;
        MapView mapView2 = mapView;
        if (!shadow && this.mLocation != null && isMyLocationEnabled()) {
            drawMyLocation(c, mapView2, this.mLocation);
        }
    }

    public boolean onSnapToItem(int i, int i2, Point point, IMapView iMapView) {
        StringBuilder sb;
        int x = i;
        int y = i2;
        Point snapPoint = point;
        IMapView iMapView2 = iMapView;
        if (this.mLocation == null) {
            return false;
        }
        Point pixelsFromProjected = this.mMapView.getProjection().toPixelsFromProjected(this.mMapCoordsProjected, this.mMapCoordsTranslated);
        snapPoint.x = this.mMapCoordsTranslated.x;
        snapPoint.y = this.mMapCoordsTranslated.y;
        double xDiff = (double) (x - this.mMapCoordsTranslated.x);
        double yDiff = (double) (y - this.mMapCoordsTranslated.y);
        boolean snap = (xDiff * xDiff) + (yDiff * yDiff) < 64.0d;
        if (Configuration.getInstance().isDebugMode()) {
            new StringBuilder();
            int d = Log.d(IMapView.LOGTAG, sb.append("snap=").append(snap).toString());
        }
        return snap;
    }

    public void setEnableAutoStop(boolean value) {
        boolean z = value;
        this.enableAutoStop = z;
    }

    public boolean getEnableAutoStop() {
        return this.enableAutoStop;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (event.getAction() == 2) {
            if (!this.enableAutoStop) {
                return true;
            }
            disableFollowLocation();
        }
        return super.onTouchEvent(event, mapView2);
    }

    public void setOptionsMenuEnabled(boolean pOptionsMenuEnabled) {
        boolean z = pOptionsMenuEnabled;
        this.mOptionsMenuEnabled = z;
    }

    public boolean isOptionsMenuEnabled() {
        return this.mOptionsMenuEnabled;
    }

    public boolean onCreateOptionsMenu(Menu pMenu, int pMenuIdOffset, MapView mapView) {
        MapView pMapView = mapView;
        MenuItem checkable = pMenu.add(0, MENU_MY_LOCATION + pMenuIdOffset, 0, pMapView.getContext().getResources().getString(C1262R.string.my_location)).setIcon(pMapView.getContext().getResources().getDrawable(C1262R.C1263drawable.ic_menu_mylocation)).setCheckable(true);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu pMenu, int pMenuIdOffset, MapView mapView) {
        MapView mapView2 = mapView;
        MenuItem checked = pMenu.findItem(MENU_MY_LOCATION + pMenuIdOffset).setChecked(isMyLocationEnabled());
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem pItem, int pMenuIdOffset, MapView mapView) {
        MapView mapView2 = mapView;
        if (pItem.getItemId() - pMenuIdOffset != MENU_MY_LOCATION) {
            return false;
        }
        if (isMyLocationEnabled()) {
            disableFollowLocation();
            disableMyLocation();
        } else {
            enableFollowLocation();
            boolean enableMyLocation = enableMyLocation();
        }
        return true;
    }

    public GeoPoint getMyLocation() {
        GeoPoint geoPoint;
        if (this.mLocation == null) {
            return null;
        }
        new GeoPoint(this.mLocation);
        return geoPoint;
    }

    public Location getLastFix() {
        return this.mLocation;
    }

    public void enableFollowLocation() {
        Location location;
        this.mIsFollowing = true;
        if (isMyLocationEnabled() && (location = this.mMyLocationProvider.getLastKnownLocation()) != null) {
            setLocation(location);
        }
        if (this.mMapView != null) {
            this.mMapView.postInvalidate();
        }
    }

    public void disableFollowLocation() {
        this.mIsFollowing = false;
    }

    public boolean isFollowLocationEnabled() {
        return this.mIsFollowing;
    }

    public void onLocationChanged(Location location, IMyLocationProvider iMyLocationProvider) {
        Runnable runnable;
        Location location2 = location;
        IMyLocationProvider iMyLocationProvider2 = iMyLocationProvider;
        if (location2 != null && this.mHandler != null) {
            final Location location3 = location2;
            new Runnable(this) {
                final /* synthetic */ MyLocationNewOverlay this$0;

                {
                    this.this$0 = this$0;
                }

                public void run() {
                    Thread thread;
                    this.this$0.setLocation(location3);
                    Iterator it = this.this$0.mRunOnFirstFix.iterator();
                    while (it.hasNext()) {
                        new Thread((Runnable) it.next());
                        thread.start();
                    }
                    this.this$0.mRunOnFirstFix.clear();
                }
            };
            boolean postAtTime = this.mHandler.postAtTime(runnable, this.mHandlerToken, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void setLocation(Location location) {
        Location location2 = location;
        Location oldLocation = this.mLocation;
        if (oldLocation != null) {
            Rect myLocationDrawingBounds = getMyLocationDrawingBounds(this.mMapView.getZoomLevel(), oldLocation, this.mMyLocationPreviousRect);
        }
        this.mLocation = location2;
        Point projectedPixels = this.mMapView.getProjection().toProjectedPixels(this.mLocation.getLatitude(), this.mLocation.getLongitude(), this.mMapCoordsProjected);
        if (this.mIsFollowing) {
            this.mGeoPoint.setLatitude(this.mLocation.getLatitude());
            this.mGeoPoint.setLongitude(this.mLocation.getLongitude());
            this.mMapController.animateTo(this.mGeoPoint);
            return;
        }
        Rect myLocationDrawingBounds2 = getMyLocationDrawingBounds(this.mMapView.getZoomLevel(), this.mLocation, this.mMyLocationRect);
        if (oldLocation != null) {
            this.mMyLocationRect.union(this.mMyLocationPreviousRect);
        }
        this.mMapView.invalidateMapCoordinates(this.mMyLocationRect.left, this.mMyLocationRect.top, this.mMyLocationRect.right, this.mMyLocationRect.bottom);
    }

    public boolean enableMyLocation(IMyLocationProvider myLocationProvider) {
        Location location;
        setMyLocationProvider(myLocationProvider);
        boolean success = this.mMyLocationProvider.startLocationProvider(this);
        this.mIsLocationEnabled = success;
        if (success && (location = this.mMyLocationProvider.getLastKnownLocation()) != null) {
            setLocation(location);
        }
        if (this.mMapView != null) {
            this.mMapView.postInvalidate();
        }
        return success;
    }

    public boolean enableMyLocation() {
        return enableMyLocation(this.mMyLocationProvider);
    }

    public void disableMyLocation() {
        this.mIsLocationEnabled = false;
        stopLocationProvider();
        if (this.mMapView != null) {
            this.mMapView.postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void stopLocationProvider() {
        if (this.mMyLocationProvider != null) {
            this.mMyLocationProvider.stopLocationProvider();
        }
        if (this.mHandler != null && this.mHandlerToken != null) {
            this.mHandler.removeCallbacksAndMessages(this.mHandlerToken);
        }
    }

    public boolean isMyLocationEnabled() {
        return this.mIsLocationEnabled;
    }

    public boolean runOnFirstFix(Runnable runnable) {
        Thread thread;
        Runnable runnable2 = runnable;
        if (this.mMyLocationProvider == null || this.mLocation == null) {
            this.mRunOnFirstFix.addLast(runnable2);
            return false;
        }
        new Thread(runnable2);
        thread.start();
        return true;
    }

    public void setPersonIcon(Bitmap icon) {
        Bitmap bitmap = icon;
        this.mPersonBitmap = bitmap;
    }
}
