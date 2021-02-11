package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import java.util.List;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.OverlayItem;

public class ItemizedIconOverlay<Item extends OverlayItem> extends ItemizedOverlay<Item> {
    private int mDrawnItemsLimit;
    protected List<Item> mItemList;
    private final Point mItemPoint;
    protected OnItemGestureListener<Item> mOnItemGestureListener;

    public interface ActiveItem {
        boolean run(int i);
    }

    public interface OnItemGestureListener<T> {
        boolean onItemLongPress(int i, T t);

        boolean onItemSingleTapUp(int i, T t);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ItemizedIconOverlay(List<Item> pList, Drawable pDefaultMarker, OnItemGestureListener<Item> pOnItemGestureListener, Context context) {
        super(pDefaultMarker);
        Point point;
        Context context2 = context;
        this.mDrawnItemsLimit = Integer.MAX_VALUE;
        new Point();
        this.mItemPoint = point;
        this.mItemList = pList;
        this.mOnItemGestureListener = pOnItemGestureListener;
        populate();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ItemizedIconOverlay(java.util.List<Item> r10, org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> r11, android.content.Context r12) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r0
            r5 = r1
            r6 = r3
            android.content.res.Resources r6 = r6.getResources()
            int r7 = org.osmdroid.library.C1262R.C1263drawable.marker_default
            android.graphics.drawable.Drawable r6 = r6.getDrawable(r7)
            r7 = r2
            r8 = r3
            r4.<init>(r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.ItemizedIconOverlay.<init>(java.util.List, org.osmdroid.views.overlay.ItemizedIconOverlay$OnItemGestureListener, android.content.Context):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ItemizedIconOverlay(android.content.Context r10, java.util.List<Item> r11, org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<Item> r12) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r0
            r5 = r2
            r6 = r1
            android.content.res.Resources r6 = r6.getResources()
            int r7 = org.osmdroid.library.C1262R.C1263drawable.marker_default
            android.graphics.drawable.Drawable r6 = r6.getDrawable(r7)
            r7 = r3
            r8 = r1
            r4.<init>(r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.ItemizedIconOverlay.<init>(android.content.Context, java.util.List, org.osmdroid.views.overlay.ItemizedIconOverlay$OnItemGestureListener):void");
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        if (this.mItemList != null) {
            this.mItemList.clear();
        }
        this.mItemList = null;
        this.mOnItemGestureListener = null;
    }

    public boolean onSnapToItem(int i, int i2, Point point, IMapView iMapView) {
        int i3 = i;
        int i4 = i2;
        Point point2 = point;
        IMapView iMapView2 = iMapView;
        return false;
    }

    /* access modifiers changed from: protected */
    public Item createItem(int index) {
        return (OverlayItem) this.mItemList.get(index);
    }

    public int size() {
        return Math.min(this.mItemList.size(), this.mDrawnItemsLimit);
    }

    public boolean addItem(Item item) {
        boolean result = this.mItemList.add(item);
        populate();
        return result;
    }

    public void addItem(int location, Item item) {
        this.mItemList.add(location, item);
        populate();
    }

    public boolean addItems(List<Item> items) {
        boolean result = this.mItemList.addAll(items);
        populate();
        return result;
    }

    public void removeAllItems() {
        removeAllItems(true);
    }

    public void removeAllItems(boolean withPopulate) {
        this.mItemList.clear();
        if (withPopulate) {
            populate();
        }
    }

    public boolean removeItem(Item item) {
        boolean result = this.mItemList.remove(item);
        populate();
        return result;
    }

    public Item removeItem(int position) {
        ItemizedIconOverlay<Item> result = (OverlayItem) this.mItemList.remove(position);
        populate();
        return result;
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        ActiveItem activeItem;
        boolean onSingleTapConfirmed;
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        final MapView mapView3 = mapView2;
        new ActiveItem(this) {
            final /* synthetic */ ItemizedIconOverlay this$0;

            {
                this.this$0 = this$0;
            }

            public boolean run(int i) {
                int index = i;
                ItemizedIconOverlay<Item> that = this.this$0;
                if (that.mOnItemGestureListener == null) {
                    return false;
                }
                return this.this$0.onSingleTapUpHelper(index, (OverlayItem) that.mItemList.get(index), mapView3);
            }
        };
        if (activateSelectedItems(event, mapView2, activeItem)) {
            onSingleTapConfirmed = true;
        } else {
            onSingleTapConfirmed = super.onSingleTapConfirmed(event, mapView2);
        }
        return onSingleTapConfirmed;
    }

    /* access modifiers changed from: protected */
    public boolean onSingleTapUpHelper(int index, Item item, MapView mapView) {
        MapView mapView2 = mapView;
        return this.mOnItemGestureListener.onItemSingleTapUp(index, item);
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        ActiveItem activeItem;
        boolean onLongPress;
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        new ActiveItem(this) {
            final /* synthetic */ ItemizedIconOverlay this$0;

            {
                this.this$0 = this$0;
            }

            public boolean run(int i) {
                int index = i;
                if (this.this$0.mOnItemGestureListener == null) {
                    return false;
                }
                return this.this$0.onLongPressHelper(index, this.this$0.getItem(index));
            }
        };
        if (activateSelectedItems(event, mapView2, activeItem)) {
            onLongPress = true;
        } else {
            onLongPress = super.onLongPress(event, mapView2);
        }
        return onLongPress;
    }

    /* access modifiers changed from: protected */
    public boolean onLongPressHelper(int index, Item item) {
        return this.mOnItemGestureListener.onItemLongPress(index, item);
    }

    private boolean activateSelectedItems(MotionEvent motionEvent, MapView mapView, ActiveItem activeItem) {
        Drawable marker;
        MotionEvent event = motionEvent;
        ActiveItem task = activeItem;
        Projection pj = mapView.getProjection();
        int eventX = (int) event.getX();
        int eventY = (int) event.getY();
        for (int i = 0; i < this.mItemList.size(); i++) {
            Item item = getItem(i);
            if (item != null) {
                if (item.getMarker(0) == null) {
                    marker = this.mDefaultMarker;
                } else {
                    marker = item.getMarker(0);
                }
                Drawable marker2 = marker;
                Point pixels = pj.toPixels(item.getPoint(), this.mItemPoint);
                if (hitTest(item, marker2, eventX - this.mItemPoint.x, eventY - this.mItemPoint.y) && task.run(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getDrawnItemsLimit() {
        return this.mDrawnItemsLimit;
    }

    public void setDrawnItemsLimit(int aLimit) {
        int i = aLimit;
        this.mDrawnItemsLimit = i;
    }
}
