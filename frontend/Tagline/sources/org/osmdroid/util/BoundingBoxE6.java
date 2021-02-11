package org.osmdroid.util;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.util.constants.MapViewConstants;

@Deprecated
public class BoundingBoxE6 implements Parcelable, Serializable, MapViewConstants {
    public static final Parcelable.Creator<BoundingBoxE6> CREATOR;
    static final long serialVersionUID = 2;
    protected final int mLatNorthE6;
    protected final int mLatSouthE6;
    protected final int mLonEastE6;
    protected final int mLonWestE6;

    public BoundingBoxE6(int northE6, int eastE6, int southE6, int westE6) {
        this.mLatNorthE6 = northE6;
        this.mLonEastE6 = eastE6;
        this.mLatSouthE6 = southE6;
        this.mLonWestE6 = westE6;
    }

    public BoundingBoxE6(double north, double east, double south, double west) {
        this.mLatNorthE6 = (int) (north * 1000000.0d);
        this.mLonEastE6 = (int) (east * 1000000.0d);
        this.mLatSouthE6 = (int) (south * 1000000.0d);
        this.mLonWestE6 = (int) (west * 1000000.0d);
    }

    public GeoPoint getCenter() {
        GeoPoint geoPoint;
        new GeoPoint((this.mLatNorthE6 + this.mLatSouthE6) / 2, (this.mLonEastE6 + this.mLonWestE6) / 2);
        return geoPoint;
    }

    public int getDiagonalLengthInMeters() {
        GeoPoint geoPoint;
        IGeoPoint iGeoPoint;
        new GeoPoint(this.mLatNorthE6, this.mLonWestE6);
        new GeoPoint(this.mLatSouthE6, this.mLonEastE6);
        return geoPoint.distanceTo(iGeoPoint);
    }

    public int getLatNorthE6() {
        return this.mLatNorthE6;
    }

    public int getLatSouthE6() {
        return this.mLatSouthE6;
    }

    public int getLonEastE6() {
        return this.mLonEastE6;
    }

    public int getLonWestE6() {
        return this.mLonWestE6;
    }

    public int getLatitudeSpanE6() {
        return Math.abs(this.mLatNorthE6 - this.mLatSouthE6);
    }

    public int getLongitudeSpanE6() {
        return Math.abs(this.mLonEastE6 - this.mLonWestE6);
    }

    /* renamed from: getRelativePositionOfGeoPointInBoundingBoxWithLinearInterpolation */
    public PointF mo25538x94d7c798(int i, int i2, PointF pointF) {
        PointF pointF2;
        PointF pointF3;
        int aLatitude = i;
        int aLongitude = i2;
        PointF reuse = pointF;
        if (reuse != null) {
            pointF3 = reuse;
        } else {
            pointF3 = pointF2;
            new PointF();
        }
        PointF out = pointF3;
        float y = ((float) (this.mLatNorthE6 - aLatitude)) / ((float) getLatitudeSpanE6());
        out.set(1.0f - (((float) (this.mLonEastE6 - aLongitude)) / ((float) getLongitudeSpanE6())), y);
        return out;
    }

    /* renamed from: getRelativePositionOfGeoPointInBoundingBoxWithExactGudermannInterpolation */
    public PointF mo25537x3b33f3a5(int i, int i2, PointF pointF) {
        PointF pointF2;
        PointF pointF3;
        int aLatitudeE6 = i;
        int aLongitudeE6 = i2;
        PointF reuse = pointF;
        if (reuse != null) {
            pointF3 = reuse;
        } else {
            pointF3 = pointF2;
            new PointF();
        }
        PointF out = pointF3;
        float y = (float) ((MyMath.gudermannInverse(((double) this.mLatNorthE6) / 1000000.0d) - MyMath.gudermannInverse(((double) aLatitudeE6) / 1000000.0d)) / (MyMath.gudermannInverse(((double) this.mLatNorthE6) / 1000000.0d) - MyMath.gudermannInverse(((double) this.mLatSouthE6) / 1000000.0d)));
        out.set(1.0f - (((float) (this.mLonEastE6 - aLongitudeE6)) / ((float) getLongitudeSpanE6())), y);
        return out;
    }

    public GeoPoint getGeoPointOfRelativePositionWithLinearInterpolation(float relX, float relY) {
        GeoPoint geoPoint;
        int lat = (int) (((float) this.mLatNorthE6) - (((float) getLatitudeSpanE6()) * relY));
        int lon = (int) (((float) this.mLonWestE6) + (((float) getLongitudeSpanE6()) * relX));
        while (lat > 90500000) {
            lat -= 90500000;
        }
        while (lat < -90500000) {
            lat += 90500000;
        }
        while (lon > 180000000) {
            lon -= 180000000;
        }
        while (lon < -180000000) {
            lon += 180000000;
        }
        new GeoPoint(lat, lon);
        return geoPoint;
    }

    public GeoPoint getGeoPointOfRelativePositionWithExactGudermannInterpolation(float relX, float relY) {
        GeoPoint geoPoint;
        double gudNorth = MyMath.gudermannInverse(((double) this.mLatNorthE6) / 1000000.0d);
        double gudSouth = MyMath.gudermannInverse(((double) this.mLatSouthE6) / 1000000.0d);
        int lat = (int) (MyMath.gudermann(gudSouth + (((double) (1.0f - relY)) * (gudNorth - gudSouth))) * 1000000.0d);
        int lon = (int) (((float) this.mLonWestE6) + (((float) getLongitudeSpanE6()) * relX));
        while (lat > 90500000) {
            lat -= 90500000;
        }
        while (lat < -90500000) {
            lat += 90500000;
        }
        while (lon > 180000000) {
            lon -= 180000000;
        }
        while (lon < -180000000) {
            lon += 180000000;
        }
        new GeoPoint(lat, lon);
        return geoPoint;
    }

    public BoundingBoxE6 increaseByScale(float f) {
        BoundingBoxE6 boundingBoxE6;
        float pBoundingboxPaddingRelativeScale = f;
        GeoPoint pCenter = getCenter();
        int mLatSpanE6Padded_2 = (int) ((((float) getLatitudeSpanE6()) * pBoundingboxPaddingRelativeScale) / 2.0f);
        int mLonSpanE6Padded_2 = (int) ((((float) getLongitudeSpanE6()) * pBoundingboxPaddingRelativeScale) / 2.0f);
        new BoundingBoxE6(pCenter.getLatitudeE6() + mLatSpanE6Padded_2, pCenter.getLongitudeE6() + mLonSpanE6Padded_2, pCenter.getLatitudeE6() - mLatSpanE6Padded_2, pCenter.getLongitudeE6() - mLonSpanE6Padded_2);
        return boundingBoxE6;
    }

    public String toString() {
        StringBuffer stringBuffer;
        new StringBuffer();
        return stringBuffer.append("N:").append(this.mLatNorthE6).append("; E:").append(this.mLonEastE6).append("; S:").append(this.mLatSouthE6).append("; W:").append(this.mLonWestE6).toString();
    }

    public GeoPoint bringToBoundingBox(int aLatitudeE6, int aLongitudeE6) {
        GeoPoint geoPoint;
        new GeoPoint(Math.max(this.mLatSouthE6, Math.min(this.mLatNorthE6, aLatitudeE6)), Math.max(this.mLonWestE6, Math.min(this.mLonEastE6, aLongitudeE6)));
        return geoPoint;
    }

    public static BoundingBoxE6 fromGeoPoints(ArrayList<? extends GeoPoint> partialPolyLine) {
        BoundingBoxE6 boundingBoxE6;
        int minLat = Integer.MAX_VALUE;
        int minLon = Integer.MAX_VALUE;
        int maxLat = Integer.MIN_VALUE;
        int maxLon = Integer.MIN_VALUE;
        Iterator<? extends GeoPoint> it = partialPolyLine.iterator();
        while (it.hasNext()) {
            GeoPoint gp = (GeoPoint) it.next();
            int latitudeE6 = gp.getLatitudeE6();
            int longitudeE6 = gp.getLongitudeE6();
            minLat = Math.min(minLat, latitudeE6);
            minLon = Math.min(minLon, longitudeE6);
            maxLat = Math.max(maxLat, latitudeE6);
            maxLon = Math.max(maxLon, longitudeE6);
        }
        new BoundingBoxE6(maxLat, maxLon, minLat, minLon);
        return boundingBoxE6;
    }

    public boolean contains(IGeoPoint iGeoPoint) {
        IGeoPoint pGeoPoint = iGeoPoint;
        return contains(pGeoPoint.getLatitudeE6(), pGeoPoint.getLongitudeE6());
    }

    public boolean contains(int i, int i2) {
        int aLatitudeE6 = i;
        int aLongitudeE6 = i2;
        if (aLatitudeE6 < this.mLatNorthE6 && aLatitudeE6 > this.mLatSouthE6) {
            if (this.mLonWestE6 < this.mLonEastE6) {
                if (aLongitudeE6 < this.mLonEastE6 && aLongitudeE6 > this.mLonWestE6) {
                    return true;
                }
            } else if (aLongitudeE6 < this.mLonEastE6 || aLongitudeE6 > this.mLonWestE6) {
                return true;
            }
        }
        return false;
    }

    static {
        Parcelable.Creator<BoundingBoxE6> creator;
        new Parcelable.Creator<BoundingBoxE6>() {
            public BoundingBoxE6 createFromParcel(Parcel in) {
                return BoundingBoxE6.readFromParcel(in);
            }

            public BoundingBoxE6[] newArray(int size) {
                return new BoundingBoxE6[size];
            }
        };
        CREATOR = creator;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel out = parcel;
        int i2 = i;
        out.writeInt(this.mLatNorthE6);
        out.writeInt(this.mLonEastE6);
        out.writeInt(this.mLatSouthE6);
        out.writeInt(this.mLonWestE6);
    }

    /* access modifiers changed from: private */
    public static BoundingBoxE6 readFromParcel(Parcel parcel) {
        BoundingBoxE6 boundingBoxE6;
        Parcel in = parcel;
        new BoundingBoxE6(in.readInt(), in.readInt(), in.readInt(), in.readInt());
        return boundingBoxE6;
    }
}
