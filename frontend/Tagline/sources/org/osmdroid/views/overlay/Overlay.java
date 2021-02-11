package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.concurrent.atomic.AtomicInteger;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.util.constants.OverlayConstants;

public abstract class Overlay implements OverlayConstants {
    protected static final float SHADOW_X_SKEW = -0.9f;
    protected static final float SHADOW_Y_SCALE = 0.5f;
    private static final Rect mRect;
    private static AtomicInteger sOrdinal;
    private boolean mEnabled = true;

    public interface Snappable {
        boolean onSnapToItem(int i, int i2, Point point, IMapView iMapView);
    }

    public abstract void draw(Canvas canvas, MapView mapView, boolean z);

    static {
        AtomicInteger atomicInteger;
        Rect rect;
        new AtomicInteger();
        sOrdinal = atomicInteger;
        new Rect();
        mRect = rect;
    }

    @Deprecated
    public Overlay(Context context) {
        Context context2 = context;
    }

    public Overlay() {
    }

    public void setEnabled(boolean pEnabled) {
        boolean z = pEnabled;
        this.mEnabled = z;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    protected static final int getSafeMenuId() {
        return sOrdinal.getAndIncrement();
    }

    protected static final int getSafeMenuIdSequence(int count) {
        return sOrdinal.getAndAdd(count);
    }

    public void onDetach(MapView mapView) {
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent, MapView mapView) {
        int i2 = i;
        KeyEvent keyEvent2 = keyEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent, MapView mapView) {
        int i2 = i;
        KeyEvent keyEvent2 = keyEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent motionEvent2 = motionEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent motionEvent2 = motionEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onDoubleTap(MotionEvent motionEvent, MapView mapView) {
        MotionEvent motionEvent2 = motionEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent motionEvent2 = motionEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        MotionEvent motionEvent2 = motionEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onDown(MotionEvent motionEvent, MapView mapView) {
        MotionEvent motionEvent2 = motionEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, MapView mapView) {
        MotionEvent motionEvent3 = motionEvent;
        MotionEvent motionEvent4 = motionEvent2;
        float f3 = f;
        float f4 = f2;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent motionEvent2 = motionEvent;
        MapView mapView2 = mapView;
        return false;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, MapView mapView) {
        MotionEvent motionEvent3 = motionEvent;
        MotionEvent motionEvent4 = motionEvent2;
        float f3 = f;
        float f4 = f2;
        MapView mapView2 = mapView;
        return false;
    }

    public void onShowPress(MotionEvent pEvent, MapView pMapView) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent, MapView mapView) {
        MotionEvent motionEvent2 = motionEvent;
        MapView mapView2 = mapView;
        return false;
    }

    protected static synchronized void drawAt(Canvas canvas, Drawable drawable, int i, int i2, boolean z, float f) {
        Canvas canvas2 = canvas;
        Drawable drawable2 = drawable;
        int x = i;
        int y = i2;
        boolean z2 = z;
        float aMapOrientation = f;
        synchronized (Overlay.class) {
            int save = canvas2.save();
            canvas2.rotate(-aMapOrientation, (float) x, (float) y);
            drawable2.copyBounds(mRect);
            drawable2.setBounds(mRect.left + x, mRect.top + y, mRect.right + x, mRect.bottom + y);
            drawable2.draw(canvas2);
            drawable2.setBounds(mRect);
            canvas2.restore();
        }
    }
}
