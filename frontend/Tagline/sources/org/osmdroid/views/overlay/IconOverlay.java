package org.osmdroid.views.overlay;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.MapView;

public class IconOverlay extends Overlay {
    public static final float ANCHOR_BOTTOM = 1.0f;
    public static final float ANCHOR_CENTER = 0.5f;
    public static final float ANCHOR_LEFT = 0.0f;
    public static final float ANCHOR_RIGHT = 1.0f;
    public static final float ANCHOR_TOP = 0.0f;
    protected float mAlpha = 1.0f;
    protected float mAnchorU = 0.5f;
    protected float mAnchorV = 0.5f;
    protected float mBearing = 0.0f;
    protected boolean mFlat = false;
    protected Drawable mIcon = null;
    protected IGeoPoint mPosition = null;
    protected Point mPositionPixels;

    public IconOverlay() {
        Point point;
        new Point();
        this.mPositionPixels = point;
    }

    public IconOverlay(IGeoPoint position, Drawable icon) {
        Point point;
        new Point();
        this.mPositionPixels = point;
        IconOverlay iconOverlay = set(position, icon);
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Rect rect;
        Canvas canvas2 = canvas;
        MapView mapView2 = mapView;
        if (!shadow && this.mIcon != null && this.mPosition != null) {
            Point pixels = mapView2.getProjection().toPixels(this.mPosition, this.mPositionPixels);
            int width = this.mIcon.getIntrinsicWidth();
            int height = this.mIcon.getIntrinsicHeight();
            new Rect(0, 0, width, height);
            Rect rect2 = rect;
            rect2.offset(-((int) (this.mAnchorU * ((float) width))), -((int) (this.mAnchorV * ((float) height))));
            this.mIcon.setBounds(rect2);
            this.mIcon.setAlpha((int) (this.mAlpha * 255.0f));
            drawAt(canvas2, this.mIcon, this.mPositionPixels.x, this.mPositionPixels.y, false, this.mFlat ? -this.mBearing : mapView2.getMapOrientation() - this.mBearing);
        }
    }

    public IGeoPoint getPosition() {
        return this.mPosition;
    }

    public IconOverlay set(IGeoPoint position, Drawable icon) {
        this.mPosition = position;
        this.mIcon = icon;
        return this;
    }

    public IconOverlay moveTo(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        IconOverlay moveTo = moveTo(mapView2.getProjection().fromPixels((int) event.getX(), (int) event.getY()), mapView2);
        return this;
    }

    public IconOverlay moveTo(IGeoPoint position, MapView mapView) {
        this.mPosition = position;
        mapView.invalidate();
        return this;
    }
}
