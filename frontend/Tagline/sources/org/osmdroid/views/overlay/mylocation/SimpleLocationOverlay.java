package org.osmdroid.views.overlay.mylocation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import org.osmdroid.library.C1262R;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

public class SimpleLocationOverlay extends Overlay {
    protected Point PERSON_HOTSPOT;
    protected Bitmap PERSON_ICON;
    protected GeoPoint mLocation;
    protected final Paint mPaint;
    private final Point screenCoords;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public SimpleLocationOverlay(Context ctx) {
        this(((BitmapDrawable) ctx.getResources().getDrawable(C1262R.C1263drawable.person)).getBitmap());
    }

    public SimpleLocationOverlay(Bitmap theIcon) {
        Paint paint;
        Point point;
        Point point2;
        new Paint();
        this.mPaint = paint;
        new Point(24, 39);
        this.PERSON_HOTSPOT = point;
        new Point();
        this.screenCoords = point2;
        this.PERSON_ICON = theIcon;
    }

    public void setLocation(GeoPoint mp) {
        GeoPoint geoPoint = mp;
        this.mLocation = geoPoint;
    }

    public GeoPoint getMyLocation() {
        return this.mLocation;
    }

    public void onDetach(MapView mapView) {
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Canvas c = canvas;
        MapView osmv = mapView;
        if (!shadow && this.mLocation != null) {
            Point pixels = osmv.getProjection().toPixels(this.mLocation, this.screenCoords);
            c.drawBitmap(this.PERSON_ICON, (float) (this.screenCoords.x - this.PERSON_HOTSPOT.x), (float) (this.screenCoords.y - this.PERSON_HOTSPOT.y), this.mPaint);
        }
    }

    public void setPersonIcon(Bitmap bmp, Point hotspot) {
        this.PERSON_ICON = bmp;
        this.PERSON_HOTSPOT = hotspot;
    }
}
