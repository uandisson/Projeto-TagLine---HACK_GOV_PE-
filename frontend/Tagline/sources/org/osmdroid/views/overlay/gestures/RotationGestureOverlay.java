package org.osmdroid.views.overlay.gestures;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.IOverlayMenuProvider;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.gestures.RotationGestureDetector;

public class RotationGestureOverlay extends Overlay implements RotationGestureDetector.RotationListener, IOverlayMenuProvider {
    private static final int MENU_ENABLED = getSafeMenuId();
    private static final int MENU_ROTATE_CCW = getSafeMenuId();
    private static final int MENU_ROTATE_CW = getSafeMenuId();
    private static final boolean SHOW_ROTATE_MENU_ITEMS = false;
    float currentAngle;
    final long deltaTime;
    private MapView mMapView;
    private boolean mOptionsMenuEnabled;
    private final RotationGestureDetector mRotationDetector;
    long timeLastSet;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public RotationGestureOverlay(Context context, MapView mapView) {
        this(mapView);
        Context context2 = context;
    }

    public RotationGestureOverlay(MapView mapView) {
        RotationGestureDetector rotationGestureDetector;
        this.mOptionsMenuEnabled = true;
        this.timeLastSet = 0;
        this.deltaTime = 25;
        this.currentAngle = 0.0f;
        this.mMapView = mapView;
        new RotationGestureDetector(this);
        this.mRotationDetector = rotationGestureDetector;
    }

    public void draw(Canvas c, MapView osmv, boolean shadow) {
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView mapView2 = mapView;
        if (isEnabled()) {
            this.mRotationDetector.onTouch(event);
        }
        return super.onTouchEvent(event, mapView2);
    }

    public void onRotate(float deltaAngle) {
        this.currentAngle += deltaAngle;
        if (System.currentTimeMillis() - 25 > this.timeLastSet) {
            this.timeLastSet = System.currentTimeMillis();
            this.mMapView.setMapOrientation(this.mMapView.getMapOrientation() + this.currentAngle);
        }
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        this.mMapView = null;
    }

    public boolean isOptionsMenuEnabled() {
        return this.mOptionsMenuEnabled;
    }

    public boolean onCreateOptionsMenu(Menu pMenu, int pMenuIdOffset, MapView mapView) {
        MapView mapView2 = mapView;
        MenuItem icon = pMenu.add(0, MENU_ENABLED + pMenuIdOffset, 0, "Enable rotation").setIcon(17301569);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem, int i, MapView mapView) {
        MenuItem pItem = menuItem;
        int pMenuIdOffset = i;
        MapView mapView2 = mapView;
        if (pItem.getItemId() == MENU_ENABLED + pMenuIdOffset) {
            if (isEnabled()) {
                this.mMapView.setMapOrientation(0.0f);
                setEnabled(false);
            } else {
                setEnabled(true);
                return true;
            }
        } else if (pItem.getItemId() == MENU_ROTATE_CCW + pMenuIdOffset) {
            this.mMapView.setMapOrientation(this.mMapView.getMapOrientation() - 10.0f);
        } else if (pItem.getItemId() == MENU_ROTATE_CW + pMenuIdOffset) {
            this.mMapView.setMapOrientation(this.mMapView.getMapOrientation() + 10.0f);
        }
        return false;
    }

    public boolean onPrepareOptionsMenu(Menu pMenu, int pMenuIdOffset, MapView mapView) {
        MapView mapView2 = mapView;
        MenuItem title = pMenu.findItem(MENU_ENABLED + pMenuIdOffset).setTitle(isEnabled() ? "Disable rotation" : "Enable rotation");
        return false;
    }

    public void setOptionsMenuEnabled(boolean enabled) {
        boolean z = enabled;
        this.mOptionsMenuEnabled = z;
    }
}
