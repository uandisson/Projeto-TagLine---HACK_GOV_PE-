package org.osmdroid.views.util;

import android.graphics.Point;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.util.constants.MapViewConstants;

@Deprecated
public class Mercator implements MapViewConstants {
    static final double DEG2RAD = 0.017453292519943295d;

    private Mercator() {
    }

    public static Point projectGeoPoint(IGeoPoint iGeoPoint, int aZoom, Point aReuse) {
        IGeoPoint aGeoPoint = iGeoPoint;
        return projectGeoPoint(aGeoPoint.getLatitude(), aGeoPoint.getLongitude(), aZoom, aReuse);
    }

    public static Point projectGeoPoint(double d, double d2, int i, Point point) {
        Point point2;
        Point point3;
        double aLat = d;
        double aLon = d2;
        int aZoom = i;
        Point aReuse = point;
        if (aReuse != null) {
            point3 = aReuse;
        } else {
            point3 = point2;
            new Point(0, 0);
        }
        Point p = point3;
        p.x = (int) Math.floor(((aLon + 180.0d) / 360.0d) * ((double) (1 << aZoom)));
        p.y = (int) Math.floor(((1.0d - (Math.log(Math.tan(aLat * 0.017453292519943295d) + (1.0d / Math.cos(aLat * 0.017453292519943295d))) / 3.141592653589793d)) / 2.0d) * ((double) (1 << aZoom)));
        return p;
    }

    public static BoundingBox getBoundingBoxFromCoords(double left, double top, double right, double bottom, int i) {
        BoundingBox boundingBox;
        int zoom = i;
        new BoundingBox(tile2lat((int) top, zoom), tile2lon((int) right, zoom), tile2lat((int) bottom, zoom), tile2lon((int) left, zoom));
        return boundingBox;
    }

    public static BoundingBox getBoundingBoxFromPointInMapTile(Point point, int i) {
        BoundingBox boundingBox;
        Point aMapTile = point;
        int aZoom = i;
        new BoundingBox(tile2lat(aMapTile.y, aZoom), tile2lon(aMapTile.x + 1, aZoom), tile2lat(aMapTile.y + 1, aZoom), tile2lon(aMapTile.x, aZoom));
        return boundingBox;
    }

    public static GeoPoint projectPoint(int x, int y, int i) {
        GeoPoint geoPoint;
        int aZoom = i;
        new GeoPoint(tile2lat(y, aZoom), tile2lon(x, aZoom));
        return geoPoint;
    }

    public static double tile2lon(int x, int aZoom) {
        return ((((double) x) / ((double) (1 << aZoom))) * 360.0d) - 180.0d;
    }

    public static double tile2lat(int y, int aZoom) {
        double n = 3.141592653589793d - ((6.283185307179586d * ((double) y)) / ((double) (1 << aZoom)));
        return 57.29577951308232d * Math.atan(0.5d * (Math.exp(n) - Math.exp(-n)));
    }
}
