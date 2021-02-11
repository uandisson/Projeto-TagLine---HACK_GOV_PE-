package org.osmdroid.views.overlay;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

public class DefaultOverlayManager extends AbstractList<Overlay> implements OverlayManager {
    /* access modifiers changed from: private */
    public final CopyOnWriteArrayList<Overlay> mOverlayList;
    private TilesOverlay mTilesOverlay;

    public DefaultOverlayManager(TilesOverlay tilesOverlay) {
        CopyOnWriteArrayList<Overlay> copyOnWriteArrayList;
        setTilesOverlay(tilesOverlay);
        new CopyOnWriteArrayList<>();
        this.mOverlayList = copyOnWriteArrayList;
    }

    public Overlay get(int pIndex) {
        return this.mOverlayList.get(pIndex);
    }

    public int size() {
        return this.mOverlayList.size();
    }

    public void add(int i, Overlay overlay) {
        Throwable th;
        int pIndex = i;
        Overlay pElement = overlay;
        if (pElement == null) {
            new Exception();
            int e = Log.e(IMapView.LOGTAG, "Attempt to add a null overlay to the collection. This is probably a bug and should be reported!", th);
            return;
        }
        this.mOverlayList.add(pIndex, pElement);
    }

    public Overlay remove(int pIndex) {
        return this.mOverlayList.remove(pIndex);
    }

    public Overlay set(int i, Overlay overlay) {
        Throwable th;
        int pIndex = i;
        Overlay pElement = overlay;
        if (pElement != null) {
            return this.mOverlayList.set(pIndex, pElement);
        }
        new Exception();
        int e = Log.e(IMapView.LOGTAG, "Attempt to set a null overlay to the collection. This is probably a bug and should be reported!", th);
        return null;
    }

    public TilesOverlay getTilesOverlay() {
        return this.mTilesOverlay;
    }

    public void setTilesOverlay(TilesOverlay tilesOverlay) {
        TilesOverlay tilesOverlay2 = tilesOverlay;
        this.mTilesOverlay = tilesOverlay2;
    }

    public Iterable<Overlay> overlaysReversed() {
        Iterable<Overlay> iterable;
        new Iterable<Overlay>(this) {
            final /* synthetic */ DefaultOverlayManager this$0;

            {
                this.this$0 = this$0;
            }

            public Iterator<Overlay> iterator() {
                Iterator<Overlay> it;
                final ListIterator<Overlay> listIterator = this.this$0.mOverlayList.listIterator(this.this$0.mOverlayList.size());
                new Iterator<Overlay>(this) {
                    final /* synthetic */ C15981 this$1;

                    {
                        this.this$1 = this$1;
                    }

                    public boolean hasNext() {
                        return listIterator.hasPrevious();
                    }

                    public Overlay next() {
                        return (Overlay) listIterator.previous();
                    }

                    public void remove() {
                        listIterator.remove();
                    }
                };
                return it;
            }
        };
        return iterable;
    }

    public List<Overlay> overlays() {
        return this.mOverlayList;
    }

    public void onDraw(Canvas canvas, MapView mapView) {
        Canvas c = canvas;
        MapView pMapView = mapView;
        if (this.mTilesOverlay != null && this.mTilesOverlay.isEnabled()) {
            this.mTilesOverlay.draw(c, pMapView, false);
        }
        Iterator<Overlay> it = this.mOverlayList.iterator();
        while (it.hasNext()) {
            Overlay overlay = it.next();
            if (overlay != null && overlay.isEnabled()) {
                overlay.draw(c, pMapView, false);
            }
        }
    }

    public void onDetach(MapView mapView) {
        MapView pMapView = mapView;
        if (this.mTilesOverlay != null) {
            this.mTilesOverlay.onDetach(pMapView);
        }
        for (Overlay overlay : overlaysReversed()) {
            overlay.onDetach(pMapView);
        }
        clear();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent, MapView mapView) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onKeyDown(keyCode, event, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent, MapView mapView) {
        int keyCode = i;
        KeyEvent event = keyEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onKeyUp(keyCode, event, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onTouchEvent(event, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent event = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onTrackballEvent(event, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onSnapToItem(int i, int i2, Point point, IMapView iMapView) {
        int x = i;
        int y = i2;
        Point snapPoint = point;
        IMapView pMapView = iMapView;
        for (Overlay overlay : overlaysReversed()) {
            if ((overlay instanceof Overlay.Snappable) && ((Overlay.Snappable) overlay).onSnapToItem(x, y, snapPoint, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onDoubleTap(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onDoubleTap(e, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onDoubleTapEvent(e, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onSingleTapConfirmed(e, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onDown(MotionEvent motionEvent, MapView mapView) {
        MotionEvent pEvent = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onDown(pEvent, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, MapView mapView) {
        MotionEvent pEvent1 = motionEvent;
        MotionEvent pEvent2 = motionEvent2;
        float pVelocityX = f;
        float pVelocityY = f2;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onFling(pEvent1, pEvent2, pVelocityX, pVelocityY, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent pEvent = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onLongPress(pEvent, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, MapView mapView) {
        MotionEvent pEvent1 = motionEvent;
        MotionEvent pEvent2 = motionEvent2;
        float pDistanceX = f;
        float pDistanceY = f2;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onScroll(pEvent1, pEvent2, pDistanceX, pDistanceY, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public void onShowPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent pEvent = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            overlay.onShowPress(pEvent, pMapView);
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent, MapView mapView) {
        MotionEvent pEvent = motionEvent;
        MapView pMapView = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay.onSingleTapUp(pEvent, pMapView)) {
                return true;
            }
        }
        return false;
    }

    public void setOptionsMenusEnabled(boolean z) {
        boolean pEnabled = z;
        Iterator<Overlay> it = this.mOverlayList.iterator();
        while (it.hasNext()) {
            Overlay overlay = it.next();
            if ((overlay instanceof IOverlayMenuProvider) && ((IOverlayMenuProvider) overlay).isOptionsMenuEnabled()) {
                ((IOverlayMenuProvider) overlay).setOptionsMenuEnabled(pEnabled);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu, int i, MapView mapView) {
        Menu pMenu = menu;
        int menuIdOffset = i;
        MapView mapView2 = mapView;
        boolean result = true;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay instanceof IOverlayMenuProvider) {
                IOverlayMenuProvider overlayMenuProvider = (IOverlayMenuProvider) overlay;
                if (overlayMenuProvider.isOptionsMenuEnabled()) {
                    result &= overlayMenuProvider.onCreateOptionsMenu(pMenu, menuIdOffset, mapView2);
                }
            }
        }
        if (this.mTilesOverlay != null && this.mTilesOverlay.isOptionsMenuEnabled()) {
            result &= this.mTilesOverlay.onCreateOptionsMenu(pMenu, menuIdOffset, mapView2);
        }
        return result;
    }

    public boolean onPrepareOptionsMenu(Menu menu, int i, MapView mapView) {
        Menu pMenu = menu;
        int menuIdOffset = i;
        MapView mapView2 = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay instanceof IOverlayMenuProvider) {
                IOverlayMenuProvider overlayMenuProvider = (IOverlayMenuProvider) overlay;
                if (overlayMenuProvider.isOptionsMenuEnabled()) {
                    boolean onPrepareOptionsMenu = overlayMenuProvider.onPrepareOptionsMenu(pMenu, menuIdOffset, mapView2);
                }
            }
        }
        if (this.mTilesOverlay != null && this.mTilesOverlay.isOptionsMenuEnabled()) {
            boolean onPrepareOptionsMenu2 = this.mTilesOverlay.onPrepareOptionsMenu(pMenu, menuIdOffset, mapView2);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem, int i, MapView mapView) {
        MenuItem item = menuItem;
        int menuIdOffset = i;
        MapView mapView2 = mapView;
        for (Overlay overlay : overlaysReversed()) {
            if (overlay instanceof IOverlayMenuProvider) {
                IOverlayMenuProvider overlayMenuProvider = (IOverlayMenuProvider) overlay;
                if (overlayMenuProvider.isOptionsMenuEnabled() && overlayMenuProvider.onOptionsItemSelected(item, menuIdOffset, mapView2)) {
                    return true;
                }
            }
        }
        if (this.mTilesOverlay == null || !this.mTilesOverlay.isOptionsMenuEnabled() || !this.mTilesOverlay.onOptionsItemSelected(item, menuIdOffset, mapView2)) {
            return false;
        }
        return true;
    }
}
