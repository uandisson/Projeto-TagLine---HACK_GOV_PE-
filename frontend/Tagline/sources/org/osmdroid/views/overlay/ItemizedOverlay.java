package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

public abstract class ItemizedOverlay<Item extends OverlayItem> extends Overlay implements Overlay.Snappable {
    private final Point mCurScreenCoords;
    protected final Drawable mDefaultMarker;
    protected boolean mDrawFocusedItem;
    private Item mFocusedItem;
    private boolean[] mInternalItemDisplayedList;
    private final ArrayList<Item> mInternalItemList;
    private final Matrix mMatrix;
    private final float[] mMatrixValues;
    private OnFocusChangeListener mOnFocusChangeListener;
    private boolean mPendingFocusChangedEvent;
    private final Rect mRect;
    protected float scaleX;
    protected float scaleY;

    public interface OnFocusChangeListener {
        void onFocusChanged(ItemizedOverlay<?> itemizedOverlay, OverlayItem overlayItem);
    }

    /* access modifiers changed from: protected */
    public abstract Item createItem(int i);

    public abstract int size();

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public ItemizedOverlay(Context context, Drawable pDefaultMarker) {
        this(pDefaultMarker);
        Context context2 = context;
    }

    public ItemizedOverlay(Drawable drawable) {
        Rect rect;
        Point point;
        Matrix matrix;
        ArrayList<Item> arrayList;
        Throwable th;
        Drawable pDefaultMarker = drawable;
        new Rect();
        this.mRect = rect;
        new Point();
        this.mCurScreenCoords = point;
        this.mDrawFocusedItem = true;
        this.mPendingFocusChangedEvent = false;
        this.mMatrixValues = new float[9];
        new Matrix();
        this.mMatrix = matrix;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        if (pDefaultMarker == null) {
            Throwable th2 = th;
            new IllegalArgumentException("You must pass a default marker to ItemizedOverlay.");
            throw th2;
        }
        this.mDefaultMarker = pDefaultMarker;
        new ArrayList<>();
        this.mInternalItemList = arrayList;
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        if (this.mDefaultMarker != null) {
        }
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Canvas canvas2 = canvas;
        MapView mapView2 = mapView;
        if (!shadow) {
            if (this.mPendingFocusChangedEvent && this.mOnFocusChangeListener != null) {
                this.mOnFocusChangeListener.onFocusChanged(this, this.mFocusedItem);
            }
            this.mPendingFocusChangedEvent = false;
            Projection pj = mapView2.getProjection();
            int size = this.mInternalItemList.size();
            if (this.mInternalItemDisplayedList == null || this.mInternalItemDisplayedList.length != size) {
                this.mInternalItemDisplayedList = new boolean[size];
            }
            canvas2.getMatrix(this.mMatrix);
            this.mMatrix.getValues(this.mMatrixValues);
            this.scaleX = (float) Math.sqrt((double) ((this.mMatrixValues[0] * this.mMatrixValues[0]) + (this.mMatrixValues[3] * this.mMatrixValues[3])));
            this.scaleY = (float) Math.sqrt((double) ((this.mMatrixValues[4] * this.mMatrixValues[4]) + (this.mMatrixValues[1] * this.mMatrixValues[1])));
            for (int i = size - 1; i >= 0; i--) {
                Item item = getItem(i);
                if (item != null) {
                    Point pixels = pj.toPixels(item.getPoint(), this.mCurScreenCoords);
                    this.mInternalItemDisplayedList[i] = onDrawItem(canvas2, item, this.mCurScreenCoords, mapView2.getMapOrientation());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void populate() {
        int size = size();
        this.mInternalItemList.clear();
        this.mInternalItemList.ensureCapacity(size);
        for (int a = 0; a < size; a++) {
            boolean add = this.mInternalItemList.add(createItem(a));
        }
        this.mInternalItemDisplayedList = null;
    }

    public final Item getItem(int position) {
        try {
            return (OverlayItem) this.mInternalItemList.get(position);
        } catch (IndexOutOfBoundsException e) {
            IndexOutOfBoundsException indexOutOfBoundsException = e;
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public boolean onDrawItem(Canvas canvas, Item item, Point point, float f) {
        Drawable marker;
        Canvas canvas2 = canvas;
        Item item2 = item;
        Point point2 = point;
        float aMapOrientation = f;
        int state = (!this.mDrawFocusedItem || this.mFocusedItem != item2) ? 0 : 4;
        if (item2.getMarker(state) == null) {
            marker = getDefaultMarker(state);
        } else {
            marker = item2.getMarker(state);
        }
        Drawable marker2 = marker;
        Drawable boundToHotspot = boundToHotspot(marker2, item2.getMarkerHotspot());
        int x = this.mCurScreenCoords.x;
        int y = this.mCurScreenCoords.y;
        int save = canvas2.save();
        canvas2.rotate(-aMapOrientation, (float) x, (float) y);
        marker2.copyBounds(this.mRect);
        marker2.setBounds(this.mRect.left + x, this.mRect.top + y, this.mRect.right + x, this.mRect.bottom + y);
        canvas2.scale(1.0f / this.scaleX, 1.0f / this.scaleY, (float) x, (float) y);
        boolean displayed = Rect.intersects(marker2.getBounds(), canvas2.getClipBounds());
        if (displayed) {
            marker2.draw(canvas2);
        }
        marker2.setBounds(this.mRect);
        canvas2.restore();
        return displayed;
    }

    public List<Item> getDisplayedItems() {
        List<Item> list;
        new ArrayList();
        List<Item> result = list;
        if (this.mInternalItemDisplayedList == null) {
            return result;
        }
        for (int i = 0; i < this.mInternalItemDisplayedList.length; i++) {
            if (this.mInternalItemDisplayedList[i]) {
                boolean add = result.add(getItem(i));
            }
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public Drawable getDefaultMarker(int state) {
        OverlayItem.setState(this.mDefaultMarker, state);
        return this.mDefaultMarker;
    }

    /* access modifiers changed from: protected */
    public boolean hitTest(Item item, Drawable marker, int hitX, int hitY) {
        Item item2 = item;
        return marker.getBounds().contains(hitX, hitY);
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        Drawable marker;
        MotionEvent e = motionEvent;
        MapView mapView2 = mapView;
        Projection pj = mapView2.getProjection();
        Rect screenRect = pj.getIntrinsicScreenRect();
        int size = size();
        for (int i = 0; i < size; i++) {
            Item item = getItem(i);
            if (item != null) {
                Point pixels = pj.toPixels(item.getPoint(), this.mCurScreenCoords);
                int state = (!this.mDrawFocusedItem || this.mFocusedItem != item) ? 0 : 4;
                if (item.getMarker(state) == null) {
                    marker = getDefaultMarker(state);
                } else {
                    marker = item.getMarker(state);
                }
                Drawable marker2 = marker;
                Drawable boundToHotspot = boundToHotspot(marker2, item.getMarkerHotspot());
                if (hitTest(item, marker2, (-this.mCurScreenCoords.x) + screenRect.left + ((int) e.getX()), (-this.mCurScreenCoords.y) + screenRect.top + ((int) e.getY())) && onTap(i)) {
                    return true;
                }
            }
        }
        return super.onSingleTapConfirmed(e, mapView2);
    }

    /* access modifiers changed from: protected */
    public boolean onTap(int i) {
        int i2 = i;
        return false;
    }

    public void setDrawFocusedItem(boolean drawFocusedItem) {
        boolean z = drawFocusedItem;
        this.mDrawFocusedItem = z;
    }

    public void setFocus(Item item) {
        Item item2 = item;
        this.mPendingFocusChangedEvent = item2 != this.mFocusedItem;
        this.mFocusedItem = item2;
    }

    public Item getFocus() {
        return this.mFocusedItem;
    }

    /* access modifiers changed from: protected */
    public synchronized Drawable boundToHotspot(Drawable drawable, OverlayItem.HotspotPlace hotspotPlace) {
        Drawable drawable2;
        Drawable marker = drawable;
        OverlayItem.HotspotPlace hotspot = hotspotPlace;
        synchronized (this) {
            int markerWidth = marker.getIntrinsicWidth();
            int markerHeight = marker.getIntrinsicHeight();
            this.mRect.set(0, 0, 0 + markerWidth, 0 + markerHeight);
            if (hotspot == null) {
                hotspot = OverlayItem.HotspotPlace.BOTTOM_CENTER;
            }
            switch (hotspot) {
                case CENTER:
                    this.mRect.offset((-markerWidth) / 2, (-markerHeight) / 2);
                    break;
                case BOTTOM_CENTER:
                    this.mRect.offset((-markerWidth) / 2, -markerHeight);
                    break;
                case TOP_CENTER:
                    this.mRect.offset((-markerWidth) / 2, 0);
                    break;
                case RIGHT_CENTER:
                    this.mRect.offset(-markerWidth, (-markerHeight) / 2);
                    break;
                case LEFT_CENTER:
                    this.mRect.offset(0, (-markerHeight) / 2);
                    break;
                case UPPER_RIGHT_CORNER:
                    this.mRect.offset(-markerWidth, 0);
                    break;
                case LOWER_RIGHT_CORNER:
                    this.mRect.offset(-markerWidth, -markerHeight);
                    break;
                case UPPER_LEFT_CORNER:
                    this.mRect.offset(0, 0);
                    break;
                case LOWER_LEFT_CORNER:
                    this.mRect.offset(0, -markerHeight);
                    break;
            }
            marker.setBounds(this.mRect);
            drawable2 = marker;
        }
        return drawable2;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        OnFocusChangeListener onFocusChangeListener = l;
        this.mOnFocusChangeListener = onFocusChangeListener;
    }
}
