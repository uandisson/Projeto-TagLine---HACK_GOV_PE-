package org.osmdroid.views.overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;

@Deprecated
public class PathOverlay extends Overlay {
    private final Rect mLineBounds;
    protected Paint mPaint;
    private final Path mPath;
    private ArrayList<Point> mPoints;
    private int mPointsPrecomputed;
    private final Point mTempPoint1;
    private final Point mTempPoint2;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public PathOverlay(int color, Context context) {
        this(color);
        Context context2 = context;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public PathOverlay(int color, float width, Context context) {
        this(color, width);
        Context context2 = context;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public PathOverlay(int color) {
        this(color, 2.0f);
    }

    public PathOverlay(int color, float width) {
        Paint paint;
        Path path;
        Point point;
        Point point2;
        Rect rect;
        new Paint();
        this.mPaint = paint;
        new Path();
        this.mPath = path;
        new Point();
        this.mTempPoint1 = point;
        new Point();
        this.mTempPoint2 = point2;
        new Rect();
        this.mLineBounds = rect;
        this.mPaint.setColor(color);
        this.mPaint.setStrokeWidth(width);
        this.mPaint.setStyle(Paint.Style.STROKE);
        clearPath();
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void setAlpha(int a) {
        this.mPaint.setAlpha(a);
    }

    public void addGreatCircle(GeoPoint geoPoint, GeoPoint geoPoint2) {
        GeoPoint startPoint = geoPoint;
        GeoPoint endPoint = geoPoint2;
        addGreatCircle(startPoint, endPoint, startPoint.distanceTo(endPoint) / 100000);
    }

    public void addGreatCircle(GeoPoint geoPoint, GeoPoint geoPoint2, int i) {
        GeoPoint startPoint = geoPoint;
        GeoPoint endPoint = geoPoint2;
        int numberOfPoints = i;
        double lat1 = (startPoint.getLatitude() * 3.141592653589793d) / 180.0d;
        double lon1 = (startPoint.getLongitude() * 3.141592653589793d) / 180.0d;
        double lat2 = (endPoint.getLatitude() * 3.141592653589793d) / 180.0d;
        double lon2 = (endPoint.getLongitude() * 3.141592653589793d) / 180.0d;
        double d = 2.0d * Math.asin(Math.sqrt(Math.pow(Math.sin((lat1 - lat2) / 2.0d), 2.0d) + (Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon1 - lon2) / 2.0d), 2.0d))));
        double bearing = Math.atan2(Math.sin(lon1 - lon2) * Math.cos(lat2), (Math.cos(lat1) * Math.sin(lat2)) - ((Math.sin(lat1) * Math.cos(lat2)) * Math.cos(lon1 - lon2))) / -0.017453292519943295d;
        double bearing2 = bearing < 0.0d ? 360.0d + bearing : bearing;
        int j = numberOfPoints + 1;
        for (int i2 = 0; i2 < j; i2++) {
            double f = (1.0d / ((double) numberOfPoints)) * ((double) i2);
            double A = Math.sin((1.0d - f) * d) / Math.sin(d);
            double B = Math.sin(f * d) / Math.sin(d);
            double x = (A * Math.cos(lat1) * Math.cos(lon1)) + (B * Math.cos(lat2) * Math.cos(lon2));
            double y = (A * Math.cos(lat1) * Math.sin(lon1)) + (B * Math.cos(lat2) * Math.sin(lon2));
            addPoint(Math.atan2((A * Math.sin(lat1)) + (B * Math.sin(lat2)), Math.sqrt(Math.pow(x, 2.0d) + Math.pow(y, 2.0d))) / 0.017453292519943295d, Math.atan2(y, x) / 0.017453292519943295d);
        }
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public void setPaint(Paint paint) {
        Throwable th;
        Paint pPaint = paint;
        if (pPaint == null) {
            Throwable th2 = th;
            new IllegalArgumentException("pPaint argument cannot be null");
            throw th2;
        }
        this.mPaint = pPaint;
    }

    public void clearPath() {
        ArrayList<Point> arrayList;
        new ArrayList<>();
        this.mPoints = arrayList;
        this.mPointsPrecomputed = 0;
    }

    public void addPoint(IGeoPoint iGeoPoint) {
        IGeoPoint aPoint = iGeoPoint;
        addPoint(aPoint.getLatitude(), aPoint.getLongitude());
    }

    public void addPoint(double aLatitude, double aLongitude) {
        Object obj;
        new Point((int) aLatitude, (int) aLongitude);
        boolean add = this.mPoints.add(obj);
    }

    public void addPoints(IGeoPoint... aPoints) {
        IGeoPoint[] iGeoPointArr = aPoints;
        int length = iGeoPointArr.length;
        for (int i = 0; i < length; i++) {
            addPoint(iGeoPointArr[i]);
        }
    }

    public void addPoints(List<IGeoPoint> aPoints) {
        for (IGeoPoint point : aPoints) {
            addPoint(point);
        }
    }

    public int getNumberOfPoints() {
        return this.mPoints.size();
    }

    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        Rect rect;
        Canvas canvas2 = canvas;
        MapView mapView2 = mapView;
        if (!shadow) {
            int size = this.mPoints.size();
            if (size >= 2) {
                Projection pj = mapView2.getProjection();
                while (true) {
                    if (this.mPointsPrecomputed >= size) {
                        break;
                    }
                    Point pt = this.mPoints.get(this.mPointsPrecomputed);
                    Point projectedPixels = pj.toProjectedPixels(pt.x, pt.y, pt);
                    this.mPointsPrecomputed++;
                }
                Point screenPoint0 = null;
                BoundingBox boundingBox = pj.getBoundingBox();
                Point topLeft = pj.toProjectedPixels(boundingBox.getLatNorth(), boundingBox.getLonWest(), (Point) null);
                Point bottomRight = pj.toProjectedPixels(boundingBox.getLatSouth(), boundingBox.getLonEast(), (Point) null);
                new Rect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
                Rect clipBounds = rect;
                this.mPath.rewind();
                Point projectedPoint0 = this.mPoints.get(size - 1);
                this.mLineBounds.set(projectedPoint0.x, projectedPoint0.y, projectedPoint0.x, projectedPoint0.y);
                for (int i = size - 2; i >= 0; i--) {
                    Point projectedPoint1 = this.mPoints.get(i);
                    this.mLineBounds.union(projectedPoint1.x, projectedPoint1.y);
                    if (!Rect.intersects(clipBounds, this.mLineBounds)) {
                        projectedPoint0 = projectedPoint1;
                        screenPoint0 = null;
                    } else {
                        if (screenPoint0 == null) {
                            screenPoint0 = pj.toPixelsFromProjected(projectedPoint0, this.mTempPoint1);
                            this.mPath.moveTo((float) screenPoint0.x, (float) screenPoint0.y);
                        }
                        Point screenPoint1 = pj.toPixelsFromProjected(projectedPoint1, this.mTempPoint2);
                        if (Math.abs(screenPoint1.x - screenPoint0.x) + Math.abs(screenPoint1.y - screenPoint0.y) > 1) {
                            this.mPath.lineTo((float) screenPoint1.x, (float) screenPoint1.y);
                            projectedPoint0 = projectedPoint1;
                            screenPoint0.x = screenPoint1.x;
                            screenPoint0.y = screenPoint1.y;
                            this.mLineBounds.set(projectedPoint0.x, projectedPoint0.y, projectedPoint0.x, projectedPoint0.y);
                        }
                    }
                }
                canvas2.drawPath(this.mPath, this.mPaint);
            }
        }
    }
}
