package org.osmdroid.views;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IProjection;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.util.constants.MapViewConstants;

public class Projection implements IProjection, MapViewConstants {
    private final BoundingBox mBoundingBoxProjection;
    private final Rect mIntrinsicScreenRectProjection;
    private final int mMapViewHeight;
    private final int mMapViewWidth;
    protected final float mMultiTouchScale;
    protected final int mOffsetX;
    protected final int mOffsetY;
    private final Matrix mRotateAndScaleMatrix;
    private final float[] mRotateScalePoints = new float[2];
    private final Rect mScreenRectProjection;
    private final Matrix mUnrotateAndScaleMatrix;
    private final double mZoomLevelProjection;
    private MapView mapView;

    Projection(MapView mapView2) {
        Matrix matrix;
        Matrix matrix2;
        BoundingBox boundingBox;
        MapView mapView3 = mapView2;
        new Matrix();
        this.mRotateAndScaleMatrix = matrix;
        new Matrix();
        this.mUnrotateAndScaleMatrix = matrix2;
        this.mapView = mapView3;
        this.mZoomLevelProjection = mapView3.getZoomLevel(false);
        this.mScreenRectProjection = mapView3.getScreenRect((Rect) null);
        this.mIntrinsicScreenRectProjection = mapView3.getIntrinsicScreenRect((Rect) null);
        this.mMapViewWidth = mapView3.getWidth();
        this.mMapViewHeight = mapView3.getHeight();
        this.mOffsetX = -mapView3.getScrollX();
        this.mOffsetY = -mapView3.getScrollY();
        this.mRotateAndScaleMatrix.set(mapView3.mRotateScaleMatrix);
        boolean invert = this.mRotateAndScaleMatrix.invert(this.mUnrotateAndScaleMatrix);
        this.mMultiTouchScale = mapView3.mMultiTouchScale;
        IGeoPoint neGeoPoint = fromPixels(this.mMapViewWidth, 0, (GeoPoint) null);
        IGeoPoint swGeoPoint = fromPixels(0, this.mMapViewHeight, (GeoPoint) null);
        new BoundingBox(neGeoPoint.getLatitude(), neGeoPoint.getLongitude(), swGeoPoint.getLatitude(), swGeoPoint.getLongitude());
        this.mBoundingBoxProjection = boundingBox;
    }

    public double getZoomLevel() {
        return this.mZoomLevelProjection;
    }

    public BoundingBox getBoundingBox() {
        return this.mBoundingBoxProjection;
    }

    @Deprecated
    public BoundingBoxE6 getBoundingBoxE6() {
        BoundingBoxE6 x;
        new BoundingBoxE6(this.mBoundingBoxProjection.getLatNorth(), this.mBoundingBoxProjection.getLonEast(), this.mBoundingBoxProjection.getLatSouth(), this.mBoundingBoxProjection.getLonWest());
        return x;
    }

    public Rect getScreenRect() {
        return this.mScreenRectProjection;
    }

    public Rect getIntrinsicScreenRect() {
        return this.mIntrinsicScreenRectProjection;
    }

    public float getMapOrientation() {
        return this.mapView.getMapOrientation();
    }

    public IGeoPoint fromPixels(int x, int y) {
        return fromPixels(x, y, (GeoPoint) null);
    }

    public IGeoPoint fromPixels(int x, int y, GeoPoint reuse) {
        return TileSystem.PixelXYToLatLong(x - this.mOffsetX, y - this.mOffsetY, this.mZoomLevelProjection, reuse);
    }

    public IGeoPoint fromPixelsRotationSensitive(int x, int y, GeoPoint reuse) {
        Point point = unrotateAndScalePoint(x, y, (Point) null);
        return TileSystem.PixelXYToLatLong(point.x - this.mOffsetX, point.y - this.mOffsetY, this.mZoomLevelProjection, reuse);
    }

    public Point toPixels(IGeoPoint iGeoPoint, Point reuse) {
        IGeoPoint in = iGeoPoint;
        Point out = TileSystem.LatLongToPixelXY(in.getLatitude(), in.getLongitude(), getZoomLevel(), reuse);
        Point out2 = toPixelsFromMercator(out.x, out.y, out);
        return adjustForDateLine(out2.x, out2.y, out2);
    }

    /* access modifiers changed from: protected */
    public Point adjustForDateLine(int i, int i2, Point point) {
        Point point2;
        Point point3;
        int x = i;
        int y = i2;
        Point reuse = point;
        if (reuse != null) {
            point3 = reuse;
        } else {
            point3 = point2;
            new Point();
        }
        Point out = point3;
        out.set(x, y);
        out.offset((-this.mMapViewWidth) / 2, (-this.mMapViewHeight) / 2);
        int mapSize = (int) TileSystem.MapSize(getZoomLevel());
        int absX = Math.abs(out.x);
        int absY = Math.abs(out.y);
        int yCompare = mapSize > this.mMapViewHeight ? mapSize : this.mMapViewHeight;
        if (absX > Math.abs(out.x - mapSize)) {
            out.x -= mapSize;
        }
        if (absX > Math.abs(out.x + mapSize)) {
            out.x += mapSize;
        }
        if (absY > Math.abs(out.y - yCompare) && this.mMapViewHeight / 2 < mapSize) {
            out.y -= mapSize;
        }
        if (absY > Math.abs(out.y + yCompare) || this.mMapViewHeight / 2 >= mapSize) {
            out.y += mapSize;
        }
        out.offset(this.mMapViewWidth / 2, this.mMapViewHeight / 2);
        return out;
    }

    public Point toProjectedPixels(GeoPoint geoPoint, Point reuse) {
        GeoPoint geoPoint2 = geoPoint;
        return toProjectedPixels(geoPoint2.getLatitude(), geoPoint2.getLongitude(), reuse);
    }

    public Point toProjectedPixels(int latituteE6, int longitudeE6, Point reuse) {
        return TileSystem.LatLongToPixelXY(((double) latituteE6) * 1.0E-6d, ((double) longitudeE6) * 1.0E-6d, microsoft.mappoint.TileSystem.getMaximumZoomLevel(), reuse);
    }

    public Point toProjectedPixels(double latitude, double longitude, Point reuse) {
        return TileSystem.LatLongToPixelXY(latitude, longitude, microsoft.mappoint.TileSystem.getMaximumZoomLevel(), reuse);
    }

    public Point toPixelsFromProjected(Point point, Point point2) {
        Point point3;
        Point point4;
        Point in = point;
        Point reuse = point2;
        if (reuse != null) {
            point4 = reuse;
        } else {
            point4 = point3;
            new Point();
        }
        Point out = point4;
        double power = TileSystem.getFactor(((double) microsoft.mappoint.TileSystem.getMaximumZoomLevel()) - getZoomLevel());
        out.set((int) (((double) in.x) / power), (int) (((double) in.y) / power));
        Point out2 = toPixelsFromMercator(out.x, out.y, out);
        return adjustForDateLine(out2.x, out2.y, out2);
    }

    public Point toPixelsFromMercator(int i, int i2, Point point) {
        Point point2;
        Point point3;
        int x = i;
        int y = i2;
        Point reuse = point;
        if (reuse != null) {
            point3 = reuse;
        } else {
            point3 = point2;
            new Point();
        }
        Point out = point3;
        out.set(x, y);
        out.offset(this.mOffsetX, this.mOffsetY);
        return out;
    }

    public Point toMercatorPixels(int i, int i2, Point point) {
        Point point2;
        Point point3;
        int x = i;
        int y = i2;
        Point reuse = point;
        if (reuse != null) {
            point3 = reuse;
        } else {
            point3 = point2;
            new Point();
        }
        Point out = point3;
        out.set(x, y);
        out.offset(-this.mOffsetX, -this.mOffsetY);
        return out;
    }

    public float metersToEquatorPixels(float meters) {
        return metersToPixels(meters, 0.0d, this.mZoomLevelProjection);
    }

    public float metersToPixels(float meters) {
        return metersToPixels(meters, getBoundingBox().getCenter().getLatitude(), this.mZoomLevelProjection);
    }

    public static float metersToPixels(float meters, double latitude, double zoomLevel) {
        return (float) (((double) meters) / TileSystem.GroundResolution(latitude, zoomLevel));
    }

    public IGeoPoint getNorthEast() {
        return fromPixels(this.mMapViewWidth, 0, (GeoPoint) null);
    }

    public IGeoPoint getSouthWest() {
        return fromPixels(0, this.mMapViewHeight, (GeoPoint) null);
    }

    public Matrix getInvertedScaleRotateCanvasMatrix() {
        return this.mUnrotateAndScaleMatrix;
    }

    public Point unrotateAndScalePoint(int i, int i2, Point point) {
        Point point2;
        int x = i;
        int y = i2;
        Point reuse = point;
        if (reuse == null) {
            new Point();
            reuse = point2;
        }
        if (getMapOrientation() == 0.0f && this.mMultiTouchScale == 1.0f) {
            reuse.set(x, y);
        } else {
            this.mRotateScalePoints[0] = (float) x;
            this.mRotateScalePoints[1] = (float) y;
            this.mUnrotateAndScaleMatrix.mapPoints(this.mRotateScalePoints);
            reuse.set((int) this.mRotateScalePoints[0], (int) this.mRotateScalePoints[1]);
        }
        return reuse;
    }

    public Point rotateAndScalePoint(int i, int i2, Point point) {
        Point point2;
        int x = i;
        int y = i2;
        Point reuse = point;
        if (reuse == null) {
            new Point();
            reuse = point2;
        }
        if (getMapOrientation() == 0.0f && this.mMultiTouchScale == 1.0f) {
            reuse.set(x, y);
        } else {
            this.mRotateScalePoints[0] = (float) x;
            this.mRotateScalePoints[1] = (float) y;
            this.mRotateAndScaleMatrix.mapPoints(this.mRotateScalePoints);
            reuse.set((int) this.mRotateScalePoints[0], (int) this.mRotateScalePoints[1]);
        }
        return reuse;
    }

    public void detach() {
        this.mapView = null;
    }
}
