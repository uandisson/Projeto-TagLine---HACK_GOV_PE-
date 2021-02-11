package org.osmdroid.views.drawing;

import android.graphics.Path;
import android.graphics.Point;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.Projection;

public class OsmPath extends Path {
    private static final GeoPoint sReferenceGeoPoint;
    private double mLastZoomLevel = -1.0d;
    protected final Point mReferencePoint;

    static {
        GeoPoint geoPoint;
        new GeoPoint(0, 0);
        sReferenceGeoPoint = geoPoint;
    }

    public OsmPath() {
        Point point;
        new Point();
        this.mReferencePoint = point;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OsmPath(Path src) {
        super(src);
        Point point;
        new Point();
        this.mReferencePoint = point;
    }

    public void onDrawCycle(Projection projection) {
        Projection proj = projection;
        if (this.mLastZoomLevel != proj.getZoomLevel()) {
            Point pixels = proj.toPixels(sReferenceGeoPoint, this.mReferencePoint);
            this.mLastZoomLevel = proj.getZoomLevel();
        }
        int x = this.mReferencePoint.x;
        int y = this.mReferencePoint.y;
        Point pixels2 = proj.toPixels(sReferenceGeoPoint, this.mReferencePoint);
        offset((float) (this.mReferencePoint.x - x), (float) (this.mReferencePoint.y - y));
    }
}
