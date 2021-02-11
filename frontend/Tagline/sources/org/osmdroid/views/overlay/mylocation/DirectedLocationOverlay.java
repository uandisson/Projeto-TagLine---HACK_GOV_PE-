package org.osmdroid.views.overlay.mylocation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import org.osmdroid.library.C1262R;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Overlay;

public class DirectedLocationOverlay extends Overlay {
    protected Bitmap DIRECTION_ARROW;
    private float DIRECTION_ARROW_CENTER_X;
    private float DIRECTION_ARROW_CENTER_Y;
    private int DIRECTION_ARROW_HEIGHT;
    private int DIRECTION_ARROW_WIDTH;
    private final Matrix directionRotater;
    private int mAccuracy = 0;
    protected Paint mAccuracyPaint;
    protected float mBearing;
    protected GeoPoint mLocation;
    protected Paint mPaint;
    private boolean mShowAccuracy = true;
    private final Point screenCoords;

    public DirectedLocationOverlay(Context ctx) {
        Paint paint;
        Paint paint2;
        Matrix matrix;
        Point point;
        new Paint();
        this.mPaint = paint;
        new Paint();
        this.mAccuracyPaint = paint2;
        new Matrix();
        this.directionRotater = matrix;
        new Point();
        this.screenCoords = point;
        setDirectionArrow(((BitmapDrawable) ctx.getResources().getDrawable(C1262R.C1263drawable.direction_arrow)).getBitmap());
        this.DIRECTION_ARROW_CENTER_X = ((float) (this.DIRECTION_ARROW.getWidth() / 2)) - 0.5f;
        this.DIRECTION_ARROW_CENTER_Y = ((float) (this.DIRECTION_ARROW.getHeight() / 2)) - 0.5f;
        this.DIRECTION_ARROW_HEIGHT = this.DIRECTION_ARROW.getHeight();
        this.DIRECTION_ARROW_WIDTH = this.DIRECTION_ARROW.getWidth();
        this.mAccuracyPaint.setStrokeWidth(2.0f);
        this.mAccuracyPaint.setColor(-16776961);
        this.mAccuracyPaint.setAntiAlias(true);
    }

    public void setDirectionArrow(Bitmap image) {
        this.DIRECTION_ARROW = image;
        this.DIRECTION_ARROW_CENTER_X = ((float) (this.DIRECTION_ARROW.getWidth() / 2)) - 0.5f;
        this.DIRECTION_ARROW_CENTER_Y = ((float) (this.DIRECTION_ARROW.getHeight() / 2)) - 0.5f;
        this.DIRECTION_ARROW_HEIGHT = this.DIRECTION_ARROW.getHeight();
        this.DIRECTION_ARROW_WIDTH = this.DIRECTION_ARROW.getWidth();
    }

    public void setShowAccuracy(boolean pShowIt) {
        boolean z = pShowIt;
        this.mShowAccuracy = z;
    }

    public void setLocation(GeoPoint mp) {
        GeoPoint geoPoint = mp;
        this.mLocation = geoPoint;
    }

    public GeoPoint getLocation() {
        return this.mLocation;
    }

    public void setAccuracy(int pAccuracy) {
        int i = pAccuracy;
        this.mAccuracy = i;
    }

    public void setBearing(float aHeading) {
        float f = aHeading;
        this.mBearing = f;
    }

    public void onDetach(MapView mapView) {
        MapView mapView2 = mapView;
        this.mPaint = null;
        this.mAccuracyPaint = null;
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Canvas c = canvas;
        MapView osmv = mapView;
        if (!shadow && this.mLocation != null) {
            Projection pj = osmv.getProjection();
            Point pixels = pj.toPixels(this.mLocation, this.screenCoords);
            if (this.mShowAccuracy && this.mAccuracy > 10) {
                float accuracyRadius = pj.metersToEquatorPixels((float) this.mAccuracy);
                if (accuracyRadius > 8.0f) {
                    this.mAccuracyPaint.setAntiAlias(false);
                    this.mAccuracyPaint.setAlpha(30);
                    this.mAccuracyPaint.setStyle(Paint.Style.FILL);
                    c.drawCircle((float) this.screenCoords.x, (float) this.screenCoords.y, accuracyRadius, this.mAccuracyPaint);
                    this.mAccuracyPaint.setAntiAlias(true);
                    this.mAccuracyPaint.setAlpha(150);
                    this.mAccuracyPaint.setStyle(Paint.Style.STROKE);
                    c.drawCircle((float) this.screenCoords.x, (float) this.screenCoords.y, accuracyRadius, this.mAccuracyPaint);
                }
            }
            this.directionRotater.setRotate(this.mBearing, this.DIRECTION_ARROW_CENTER_X, this.DIRECTION_ARROW_CENTER_Y);
            Bitmap rotatedDirection = Bitmap.createBitmap(this.DIRECTION_ARROW, 0, 0, this.DIRECTION_ARROW_WIDTH, this.DIRECTION_ARROW_HEIGHT, this.directionRotater, false);
            c.drawBitmap(rotatedDirection, (float) (this.screenCoords.x - (rotatedDirection.getWidth() / 2)), (float) (this.screenCoords.y - (rotatedDirection.getHeight() / 2)), this.mPaint);
        }
    }
}
