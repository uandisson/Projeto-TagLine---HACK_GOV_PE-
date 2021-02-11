package microsoft.mappoint;

import android.graphics.Point;
import org.osmdroid.util.GeoPoint;

public final class TileSystem {
    private static final double EarthRadius = 6378137.0d;
    private static final double MaxLatitude = 85.05112878d;
    private static final double MaxLongitude = 180.0d;
    private static final double MinLatitude = -85.05112878d;
    private static final double MinLongitude = -180.0d;
    private static int mMaxZoomLevel = 22;
    protected static int mTileSize = 256;

    public TileSystem() {
    }

    public static void setTileSize(int i) {
        int tileSize = i;
        mMaxZoomLevel = (31 - ((int) (Math.log((double) tileSize) / Math.log(2.0d)))) - 1;
        mTileSize = tileSize;
    }

    public static int getTileSize() {
        return mTileSize;
    }

    public static int getMaximumZoomLevel() {
        return mMaxZoomLevel;
    }

    private static double Clip(double n, double minValue, double maxValue) {
        return Math.min(Math.max(n, minValue), maxValue);
    }

    public static int MapSize(int i) {
        int maximumZoomLevel;
        int levelOfDetail = i;
        int i2 = mTileSize;
        if (levelOfDetail < getMaximumZoomLevel()) {
            maximumZoomLevel = levelOfDetail;
        } else {
            maximumZoomLevel = getMaximumZoomLevel();
        }
        return i2 << maximumZoomLevel;
    }

    public static double GroundResolution(double latitude, int levelOfDetail) {
        return (((Math.cos((Clip(latitude, -85.05112878d, 85.05112878d) * 3.141592653589793d) / 180.0d) * 2.0d) * 3.141592653589793d) * 6378137.0d) / ((double) MapSize(levelOfDetail));
    }

    public static double MapScale(double latitude, int levelOfDetail, int screenDpi) {
        return (GroundResolution(latitude, levelOfDetail) * ((double) screenDpi)) / 0.0254d;
    }

    public static Point LatLongToPixelXY(double d, double d2, int i, Point point) {
        Point point2;
        Point point3;
        double latitude = d;
        double longitude = d2;
        int levelOfDetail = i;
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
        int mapSize = MapSize(levelOfDetail);
        out.x = (int) Clip((x * ((double) mapSize)) + 0.5d, 0.0d, (double) (mapSize - 1));
        out.y = (int) Clip((y * ((double) mapSize)) + 0.5d, 0.0d, (double) (mapSize - 1));
        return out;
    }

    public static GeoPoint PixelXYToLatLong(int i, int i2, int i3, GeoPoint geoPoint) {
        GeoPoint geoPoint2;
        GeoPoint geoPoint3;
        int pixelX = i;
        int pixelY = i2;
        int levelOfDetail = i3;
        GeoPoint reuse = geoPoint;
        if (reuse == null) {
            geoPoint2 = geoPoint3;
            new GeoPoint(0, 0);
        } else {
            geoPoint2 = reuse;
        }
        GeoPoint out = geoPoint2;
        double mapSize = (double) MapSize(levelOfDetail);
        double x = (Clip((double) pixelX, 0.0d, mapSize - 1.0d) / mapSize) - 0.5d;
        out.setLatitude(90.0d - ((360.0d * Math.atan(Math.exp(((-(0.5d - (Clip((double) pixelY, 0.0d, mapSize - 1.0d) / mapSize))) * 2.0d) * 3.141592653589793d))) / 3.141592653589793d));
        out.setLongitude(360.0d * x);
        return out;
    }

    public static Point PixelXYToTileXY(int i, int i2, Point point) {
        Point point2;
        Point point3;
        int pixelX = i;
        int pixelY = i2;
        Point reuse = point;
        if (reuse == null) {
            point2 = point3;
            new Point();
        } else {
            point2 = reuse;
        }
        Point out = point2;
        out.x = pixelX / mTileSize;
        out.y = pixelY / mTileSize;
        return out;
    }

    public static Point TileXYToPixelXY(int i, int i2, Point point) {
        Point point2;
        Point point3;
        int tileX = i;
        int tileY = i2;
        Point reuse = point;
        if (reuse == null) {
            point2 = point3;
            new Point();
        } else {
            point2 = reuse;
        }
        Point out = point2;
        out.x = tileX * mTileSize;
        out.y = tileY * mTileSize;
        return out;
    }

    public static String TileXYToQuadKey(int i, int i2, int levelOfDetail) {
        StringBuilder sb;
        int tileX = i;
        int tileY = i2;
        new StringBuilder();
        StringBuilder quadKey = sb;
        for (int i3 = levelOfDetail; i3 > 0; i3--) {
            char digit = '0';
            int mask = 1 << (i3 - 1);
            if ((tileX & mask) != 0) {
                digit = (char) (48 + 1);
            }
            if ((tileY & mask) != 0) {
                digit = (char) (((char) (digit + 1)) + 1);
            }
            StringBuilder append = quadKey.append(digit);
        }
        return quadKey.toString();
    }

    public static Point QuadKeyToTileXY(String str, Point point) {
        Point point2;
        Throwable th;
        Point point3;
        String quadKey = str;
        Point reuse = point;
        if (reuse == null) {
            point2 = point3;
            new Point();
        } else {
            point2 = reuse;
        }
        Point out = point2;
        int tileX = 0;
        int tileY = 0;
        int levelOfDetail = quadKey.length();
        for (int i = levelOfDetail; i > 0; i--) {
            int mask = 1 << (i - 1);
            switch (quadKey.charAt(levelOfDetail - i)) {
                case '0':
                    break;
                case '1':
                    tileX |= mask;
                    break;
                case '2':
                    tileY |= mask;
                    break;
                case '3':
                    tileX |= mask;
                    tileY |= mask;
                    break;
                default:
                    Throwable th2 = th;
                    new IllegalArgumentException("Invalid QuadKey digit sequence.");
                    throw th2;
            }
        }
        out.set(tileX, tileY);
        return out;
    }
}
