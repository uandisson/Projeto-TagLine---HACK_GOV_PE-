package org.osmdroid.views.overlay;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import java.util.List;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

public abstract class ClickableIconOverlay<DataType> extends IconOverlay {
    private DataType mData = null;
    protected int mId = 0;

    /* access modifiers changed from: protected */
    public abstract boolean onMarkerClicked(MapView mapView, int i, IGeoPoint iGeoPoint, DataType datatype);

    protected ClickableIconOverlay(DataType data) {
        this.mData = data;
    }

    public ClickableIconOverlay set(int id, IGeoPoint position, Drawable icon, DataType data) {
        IconOverlay iconOverlay = set(position, icon);
        this.mId = id;
        this.mData = data;
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean hitTest(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        Projection pj = mapView.getProjection();
        if (this.mPosition == null || this.mPositionPixels == null || pj == null) {
            return false;
        }
        Point pixels = pj.toPixels(this.mPosition, this.mPositionPixels);
        Rect screenRect = pj.getIntrinsicScreenRect();
        return this.mIcon.getBounds().contains((-this.mPositionPixels.x) + screenRect.left + ((int) event.getX()), (-this.mPositionPixels.y) + screenRect.top + ((int) event.getY()));
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (hitTest(event, mapView2)) {
            return onMarkerClicked(mapView2, this.mId, this.mPosition, this.mData);
        }
        return super.onSingleTapConfirmed(event, mapView2);
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (hitTest(event, mapView2)) {
            return onMarkerLongPress(mapView2, this.mId, this.mPosition, this.mData);
        }
        return super.onLongPress(event, mapView2);
    }

    /* access modifiers changed from: protected */
    public boolean onMarkerLongPress(MapView mapView, int i, IGeoPoint iGeoPoint, Object obj) {
        MapView mapView2 = mapView;
        int i2 = i;
        IGeoPoint iGeoPoint2 = iGeoPoint;
        Object obj2 = obj;
        return false;
    }

    public static ClickableIconOverlay find(List<ClickableIconOverlay> list, int i) {
        int id = i;
        for (ClickableIconOverlay item : list) {
            if (item != null && item.mId == id) {
                return item;
            }
        }
        return null;
    }

    public int getID() {
        return this.mId;
    }

    public DataType getData() {
        return this.mData;
    }
}
