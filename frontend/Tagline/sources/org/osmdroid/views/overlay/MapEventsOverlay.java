package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapEventsOverlay extends Overlay {
    private MapEventsReceiver mReceiver;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public MapEventsOverlay(Context context, MapEventsReceiver receiver) {
        this(receiver);
        Context context2 = context;
    }

    public MapEventsOverlay(MapEventsReceiver receiver) {
        this.mReceiver = receiver;
    }

    public void draw(Canvas c, MapView osmv, boolean shadow) {
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        return this.mReceiver.singleTapConfirmedHelper((GeoPoint) mapView.getProjection().fromPixels((int) e.getX(), (int) e.getY()));
    }

    public boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
        MotionEvent e = motionEvent;
        return this.mReceiver.longPressHelper((GeoPoint) mapView.getProjection().fromPixels((int) e.getX(), (int) e.getY()));
    }
}
