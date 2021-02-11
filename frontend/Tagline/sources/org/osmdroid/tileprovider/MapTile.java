package org.osmdroid.tileprovider;

import java.util.Date;

public class MapTile {
    public static final int MAPTILE_FAIL_ID = 1;
    public static final int MAPTILE_SUCCESS_ID = 0;
    private Date expires;

    /* renamed from: x */
    private final int f526x;

    /* renamed from: y */
    private final int f527y;
    private final int zoomLevel;

    public MapTile(int zoomLevel2, int tileX, int tileY) {
        this.zoomLevel = zoomLevel2;
        this.f526x = tileX;
        this.f527y = tileY;
    }

    public Date getExpires() {
        return this.expires;
    }

    public void setExpires(Date expires2) {
        Date date = expires2;
        this.expires = date;
    }

    public int getZoomLevel() {
        return this.zoomLevel;
    }

    public int getX() {
        return this.f526x;
    }

    public int getY() {
        return this.f527y;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("/").append(this.zoomLevel).append("/").append(this.f526x).append("/").append(this.f527y).toString();
    }

    public boolean equals(Object obj) {
        Object obj2 = obj;
        if (obj2 == null) {
            return false;
        }
        if (obj2 == this) {
            return true;
        }
        if (!(obj2 instanceof MapTile)) {
            return false;
        }
        MapTile rhs = (MapTile) obj2;
        return this.zoomLevel == rhs.zoomLevel && this.f526x == rhs.f526x && this.f527y == rhs.f527y;
    }

    public int hashCode() {
        return 17 * (37 + this.zoomLevel) * (37 + this.f526x) * (37 + this.f527y);
    }
}
