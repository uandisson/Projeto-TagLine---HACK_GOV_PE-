package org.osmdroid.views.util;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import java.util.List;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.Projection;

public class PathProjection {
    public PathProjection() {
    }

    public static Path toPixels(Projection projection, List<? extends GeoPoint> in, Path reuse) {
        return toPixels(projection, in, reuse, true);
    }

    public static Path toPixels(Projection projection, List<? extends GeoPoint> list, Path path, boolean z) throws IllegalArgumentException {
        Path path2;
        Path path3;
        BoundingBox boundingBox;
        PointF relativePositionInCenterMapTile;
        Throwable th;
        Projection projection2 = projection;
        List<? extends GeoPoint> in = list;
        Path reuse = path;
        boolean doGudermann = z;
        if (in.size() < 2) {
            Throwable th2 = th;
            new IllegalArgumentException("List of GeoPoints needs to be at least 2.");
            throw th2;
        }
        if (reuse != null) {
            path3 = reuse;
        } else {
            path3 = path2;
            new Path();
        }
        Path out = path3;
        out.incReserve(in.size());
        boolean first = true;
        for (GeoPoint gp : in) {
            Point underGeopointTileCoords = TileSystem.LatLongToPixelXY(gp.getLatitude(), gp.getLongitude(), projection2.getZoomLevel(), (Point) null);
            double tileSize = TileSystem.getTileSize(projection2.getZoomLevel());
            Point PixelXYToTileXY = TileSystem.PixelXYToTileXY(underGeopointTileCoords.x, underGeopointTileCoords.y, tileSize, underGeopointTileCoords);
            Point upperRight = TileSystem.TileXYToPixelXY(underGeopointTileCoords.x, underGeopointTileCoords.y, tileSize, (Point) null);
            Point lowerLeft = TileSystem.TileXYToPixelXY(underGeopointTileCoords.x + TileSystem.getTileSize(), underGeopointTileCoords.y + TileSystem.getTileSize(), tileSize, (Point) null);
            GeoPoint neGeoPoint = TileSystem.PixelXYToLatLong(upperRight.x, upperRight.y, projection2.getZoomLevel(), (GeoPoint) null);
            GeoPoint swGeoPoint = TileSystem.PixelXYToLatLong(lowerLeft.x, lowerLeft.y, projection2.getZoomLevel(), (GeoPoint) null);
            new BoundingBox(neGeoPoint.getLatitude(), neGeoPoint.getLongitude(), swGeoPoint.getLatitude(), swGeoPoint.getLongitude());
            BoundingBox bb = boundingBox;
            if (!doGudermann || projection2.getZoomLevel() >= 7.0d) {
                relativePositionInCenterMapTile = bb.mo25517x94d7c798(gp.getLatitude(), gp.getLongitude(), (PointF) null);
            } else {
                relativePositionInCenterMapTile = bb.mo25516x3b33f3a5(gp.getLatitude(), gp.getLongitude(), (PointF) null);
            }
            Rect screenRect = projection2.getScreenRect();
            Point centerMapTileCoords = TileSystem.PixelXYToTileXY(screenRect.centerX(), screenRect.centerY(), tileSize, (Point) null);
            Point upperLeftCornerOfCenterMapTile = TileSystem.TileXYToPixelXY(centerMapTileCoords.x, centerMapTileCoords.y, tileSize, (Point) null);
            int tileDiffX = centerMapTileCoords.x - underGeopointTileCoords.x;
            int tileDiffY = centerMapTileCoords.y - underGeopointTileCoords.y;
            int underGeopointTileScreenLeft = upperLeftCornerOfCenterMapTile.x - (TileSystem.getTileSize() * tileDiffX);
            int underGeopointTileScreenTop = upperLeftCornerOfCenterMapTile.y - (TileSystem.getTileSize() * tileDiffY);
            int x = underGeopointTileScreenLeft + ((int) (relativePositionInCenterMapTile.x * ((float) TileSystem.getTileSize())));
            int y = underGeopointTileScreenTop + ((int) (relativePositionInCenterMapTile.y * ((float) TileSystem.getTileSize())));
            if (first) {
                out.moveTo((float) x, (float) y);
            } else {
                out.lineTo((float) x, (float) y);
            }
            first = false;
        }
        return out;
    }
}
