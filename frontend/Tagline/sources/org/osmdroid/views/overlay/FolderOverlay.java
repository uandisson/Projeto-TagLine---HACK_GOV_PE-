package org.osmdroid.views.overlay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import java.util.List;
import org.osmdroid.views.MapView;

public class FolderOverlay extends Overlay {
    protected String mDescription;
    protected String mName;
    protected OverlayManager mOverlayManager;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public FolderOverlay(Context context) {
        this();
        Context context2 = context;
    }

    public FolderOverlay() {
        OverlayManager overlayManager;
        new DefaultOverlayManager((TilesOverlay) null);
        this.mOverlayManager = overlayManager;
        this.mName = "";
        this.mDescription = "";
    }

    public void setName(String name) {
        String str = name;
        this.mName = str;
    }

    public String getName() {
        return this.mName;
    }

    public void setDescription(String description) {
        String str = description;
        this.mDescription = str;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public List<Overlay> getItems() {
        return this.mOverlayManager;
    }

    public boolean add(Overlay item) {
        return this.mOverlayManager.add(item);
    }

    public boolean remove(Overlay item) {
        return this.mOverlayManager.remove(item);
    }

    @SuppressLint({"WrongCall"})
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Canvas canvas2 = canvas;
        MapView osm = mapView;
        if (!shadow) {
            this.mOverlayManager.onDraw(canvas2, osm);
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        MapView mapView2 = mapView;
        if (isEnabled()) {
            return this.mOverlayManager.onSingleTapUp(e, mapView2);
        }
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        MapView mapView2 = mapView;
        if (isEnabled()) {
            return this.mOverlayManager.onSingleTapConfirmed(e, mapView2);
        }
        return false;
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        MapView mapView2 = mapView;
        if (isEnabled()) {
            return this.mOverlayManager.onLongPress(e, mapView2);
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        MapView mapView2 = mapView;
        if (isEnabled()) {
            return this.mOverlayManager.onTouchEvent(e, mapView2);
        }
        return false;
    }

    public void closeAllInfoWindows() {
        for (Overlay overlay : this.mOverlayManager) {
            if (overlay instanceof FolderOverlay) {
                ((FolderOverlay) overlay).closeAllInfoWindows();
            } else if (overlay instanceof OverlayWithIW) {
                ((OverlayWithIW) overlay).closeInfoWindow();
            }
        }
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        if (this.mOverlayManager != null) {
            this.mOverlayManager.onDetach(mapView2);
        }
        this.mOverlayManager = null;
    }
}
