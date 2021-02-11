package org.osmdroid.util;

import android.graphics.Point;
import android.graphics.Rect;

public final class TileSystem {
    public static final double EarthRadius = 6378137.0d;
    public static final double MaxLatitude = 85.05112878d;
    public static final double MaxLongitude = 180.0d;
    public static final double MinLatitude = -85.05112878d;
    public static final double MinLongitude = -180.0d;

    public TileSystem() {
    }

    public static void setTileSize(int tileSize) {
        microsoft.mappoint.TileSystem.setTileSize(tileSize);
    }

    public static int getTileSize() {
        return microsoft.mappoint.TileSystem.getTileSize();
    }

    public static double getTileSize(double d) {
        double pZoomLevel = d;
        return MapSize(pZoomLevel - ((double) getInputTileZoomLevel(pZoomLevel)));
    }

    public static int getInputTileZoomLevel(double pZoomLevel) {
        return (int) pZoomLevel;
    }

    @Deprecated
    public static int MapSize(int levelOfDetail) {
        return microsoft.mappoint.TileSystem.MapSize(levelOfDetail);
    }

    public static double MapSize(double pZoomLevel) {
        return ((double) getTileSize()) * getFactor(pZoomLevel);
    }

    public static double getFactor(double pZoomLevel) {
        return Math.pow(2.0d, pZoomLevel);
    }

    public static double GroundResolution(double latitude, int levelOfDetail) {
        return microsoft.mappoint.TileSystem.GroundResolution(wrap(latitude, -90.0d, 90.0d, 180.0d), levelOfDetail);
    }

    public static double GroundResolution(double latitude, double zoomLevel) {
        return GroundResolutionMapSize(wrap(latitude, -90.0d, 90.0d, 180.0d), MapSize(zoomLevel));
    }

    public static double GroundResolutionMapSize(double latitude, double mapSize) {
        return (((Math.cos((Clip(latitude, -85.05112878d, 85.05112878d) * 3.141592653589793d) / 180.0d) * 2.0d) * 3.141592653589793d) * 6378137.0d) / mapSize;
    }

    public static double MapScale(double latitude, int levelOfDetail, int screenDpi) {
        return microsoft.mappoint.TileSystem.MapScale(latitude, levelOfDetail, screenDpi);
    }

    @Deprecated
    public static Point LatLongToPixelXY(double latitude, double longitude, int levelOfDetail, Point reuse) {
        return microsoft.mappoint.TileSystem.LatLongToPixelXY(wrap(latitude, -90.0d, 90.0d, 180.0d), wrap(longitude, -180.0d, 180.0d, 360.0d), levelOfDetail, reuse);
    }

    public static Point LatLongToPixelXY(double latitude, double longitude, double zoomLevel, Point reuse) {
        return LatLongToPixelXYMapSize(wrap(latitude, -90.0d, 90.0d, 180.0d), wrap(longitude, -180.0d, 180.0d, 360.0d), MapSize(zoomLevel), reuse);
    }

    public static Point LatLongToPixelXYMapSize(double d, double d2, double d3, Point point) {
        Point point2;
        Point point3;
        double latitude = d;
        double longitude = d2;
        double mapSize = d3;
        Point reuse = point;
        if (reuse == null) {
            point2 = point3;
            new Point();
        } else {
            point2 = reuse;
        }
        Point out = point2;
        double latitude2 = Clip(latitude, -85.05112878d, 85.05112878d);
        double x = (Clip(longitude, -180.0d, 180.0d) + 180.0d) / 360.0d;
        double sinLatitude = Math.sin((latitude2 * 3.141592653589793d) / 180.0d);
        double y = 0.5d - (Math.log((1.0d + sinLatitude) / (1.0d - sinLatitude)) / 12.566370614359172d);
        out.x = (int) Clip((x * mapSize) + 0.5d, 0.0d, mapSize - 1.0d);
        out.y = (int) Clip((y * mapSize) + 0.5d, 0.0d, mapSize - 1.0d);
        return out;
    }

    @Deprecated
    public static GeoPoint PixelXYToLatLong(int pixelX, int pixelY, int i, GeoPoint reuse) {
        int levelOfDetail = i;
        int mapSize = MapSize(levelOfDetail);
        return microsoft.mappoint.TileSystem.PixelXYToLatLong((int) wrap((double) pixelX, 0.0d, (double) (mapSize - 1), (double) mapSize), (int) wrap((double) pixelY, 0.0d, (double) (mapSize - 1), (double) mapSize), levelOfDetail, reuse);
    }

    public static GeoPoint PixelXYToLatLong(int pixelX, int pixelY, double zoomLevel, GeoPoint reuse) {
        double mapSize = MapSize(zoomLevel);
        return PixelXYToLatLongMapSize((int) wrap((double) pixelX, 0.0d, mapSize - 1.0d, mapSize), (int) wrap((double) pixelY, 0.0d, mapSize - 1.0d, mapSize), mapSize, reuse);
    }

    public static GeoPoint PixelXYToLatLongMapSize(int i, int i2, double d, GeoPoint geoPoint) {
        GeoPoint geoPoint2;
        GeoPoint geoPoint3;
        int pixelX = i;
        int pixelY = i2;
        double mapSize = d;
        GeoPoint reuse = geoPoint;
        if (reuse == null) {
            geoPoint2 = geoPoint3;
            new GeoPoint(0.0d, 0.0d);
        } else {
            geoPoint2 = reuse;
        }
        GeoPoint out = geoPoint2;
        double x = (Clip((double) pixelX, 0.0d, mapSize - 1.0d) / mapSize) - 0.5d;
        out.setLatitude(90.0d - ((360.0d * Math.atan(Math.exp(((-(0.5d - (Clip((double) pixelY, 0.0d, mapSize - 1.0d) / mapSize))) * 2.0d) * 3.141592653589793d))) / 3.141592653589793d));
        out.setLongitude(360.0d * x);
        return out;
    }

    public static double Clip(double n, double minValue, double maxValue) {
        return Math.min(Math.max(n, minValue), maxValue);
    }

    @Deprecated
    public static Point PixelXYToTileXY(int pixelX, int pixelY, Point reuse) {
        return microsoft.mappoint.TileSystem.PixelXYToTileXY(pixelX, pixelY, reuse);
    }

    public static Point PixelXYToTileXY(int i, int i2, double d, Point point) {
        Point point2;
        Point point3;
        int pPixelX = i;
        int pPixelY = i2;
        double pTileSize = d;
        Point pReuse = point;
        if (pReuse == null) {
            point2 = point3;
            new Point();
        } else {
            point2 = pReuse;
        }
        Point out = point2;
        out.x = (int) (((double) pPixelX) / pTileSize);
        out.y = (int) (((double) pPixelY) / pTileSize);
        return out;
    }

    public static Rect PixelXYToTileXY(Rect rect, double d, Rect rect2) {
        Rect rect3;
        Rect rect4;
        Rect rect5 = rect;
        double pTileSize = d;
        Rect pReuse = rect2;
        if (pReuse == null) {
            rect3 = rect4;
            new Rect();
        } else {
            rect3 = pReuse;
        }
        Rect out = rect3;
        out.set((int) (((double) rect5.left) / pTileSize), (int) (((double) rect5.top) / pTileSize), (int) (((double) rect5.right) / pTileSize), (int) (((double) rect5.bottom) / pTileSize));
        return out;
    }

    @Deprecated
    public static Point TileXYToPixelXY(int tileX, int tileY, Point reuse) {
        return microsoft.mappoint.TileSystem.TileXYToPixelXY(tileX, tileY, reuse);
    }

    public static Point TileXYToPixelXY(int i, int i2, double d, Point point) {
        Point point2;
        Point point3;
        int pTileX = i;
        int pTileY = i2;
        double pTileSize = d;
        Point pReuse = point;
        if (pReuse == null) {
            point2 = point3;
            new Point();
        } else {
            point2 = pReuse;
        }
        Point out = point2;
        out.x = (int) (((double) pTileX) * pTileSize);
        out.y = (int) (((double) pTileY) * pTileSize);
        return out;
    }

    public static String TileXYToQuadKey(int tileX, int tileY, int levelOfDetail) {
        return microsoft.mappoint.TileSystem.TileXYToQuadKey(tileX, tileY, levelOfDetail);
    }

    public static Point QuadKeyToTileXY(String quadKey, Point reuse) {
        return microsoft.mappoint.TileSystem.QuadKeyToTileXY(quadKey, reuse);
    }

    private static double wrap(double d, double d2, double d3, double d4) {
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        StringBuilder sb2;
        double n = d;
        double minValue = d2;
        double maxValue = d3;
        double interval = d4;
        if (minValue > maxValue) {
            Throwable th3 = th2;
            new StringBuilder();
            new IllegalArgumentException(sb2.append("minValue must be smaller than maxValue: ").append(minValue).append(">").append(maxValue).toString());
            throw th3;
        } else if (interval > (maxValue - minValue) + 1.0d) {
            Throwable th4 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("interval must be equal or smaller than maxValue-minValue: min: ").append(minValue).append(" max:").append(maxValue).append(" int:").append(interval).toString());
            throw th4;
        } else {
            while (n < minValue) {
                n += interval;
            }
            while (n > maxValue) {
                n -= interval;
            }
            return n;
        }
    }
}
