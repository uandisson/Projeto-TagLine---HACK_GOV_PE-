package org.osmdroid.util;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.util.List;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.util.constants.MapViewConstants;

public class BoundingBox implements Parcelable, Serializable, MapViewConstants {
    public static final Parcelable.Creator<BoundingBox> CREATOR;
    static final long serialVersionUID = 2;
    protected final double mLatNorth;
    protected final double mLatSouth;
    protected final double mLonEast;
    protected final double mLonWest;

    public BoundingBox(double north, double east, double south, double west) {
        this.mLatNorth = north;
        this.mLonEast = east;
        this.mLatSouth = south;
        this.mLonWest = west;
    }

    public BoundingBox clone() {
        BoundingBox boundingBox;
        new BoundingBox(this.mLatNorth, this.mLonEast, this.mLatSouth, this.mLonWest);
        return boundingBox;
    }

    public BoundingBox concat(BoundingBox boundingBox) {
        BoundingBox boundingBox2;
        BoundingBox bb2 = boundingBox;
        new BoundingBox(Math.max(this.mLatNorth, bb2.getLatNorth()), Math.max(this.mLonEast, bb2.getLonEast()), Math.min(this.mLatSouth, bb2.getLatSouth()), Math.min(this.mLonWest, bb2.getLonWest()));
        return boundingBox2;
    }

    public GeoPoint getCenter() {
        GeoPoint geoPoint;
        new GeoPoint((this.mLatNorth + this.mLatSouth) / 2.0d, (this.mLonEast + this.mLonWest) / 2.0d);
        return geoPoint;
    }

    public double getDiagonalLengthInMeters() {
        GeoPoint geoPoint;
        IGeoPoint iGeoPoint;
        new GeoPoint(this.mLatNorth, this.mLonWest);
        new GeoPoint(this.mLatSouth, this.mLonEast);
        return (double) geoPoint.distanceTo(iGeoPoint);
    }

    public double getLatNorth() {
        return this.mLatNorth;
    }

    public double getLatSouth() {
        return this.mLatSouth;
    }

    public double getLonEast() {
        return this.mLonEast;
    }

    public double getLonWest() {
        return this.mLonWest;
    }

    public double getLatitudeSpan() {
        return Math.abs(this.mLatNorth - this.mLatSouth);
    }

    public double getLongitudeSpan() {
        return Math.abs(this.mLonEast - this.mLonWest);
    }

    /* renamed from: getRelativePositionOfGeoPointInBoundingBoxWithLinearInterpolation */
    public PointF mo25517x94d7c798(double d, double d2, PointF pointF) {
        PointF pointF2;
        PointF pointF3;
        double aLatitude = d;
        double aLongitude = d2;
        PointF reuse = pointF;
        if (reuse != null) {
            pointF3 = reuse;
        } else {
            pointF3 = pointF2;
            new PointF();
        }
        PointF out = pointF3;
        float y = (float) ((this.mLatNorth - aLatitude) / getLatitudeSpan());
        out.set(1.0f - ((float) ((this.mLonEast - aLongitude) / getLongitudeSpan())), y);
        return out;
    }

    /* renamed from: getRelativePositionOfGeoPointInBoundingBoxWithExactGudermannInterpolation */
    public PointF mo25516x3b33f3a5(double d, double d2, PointF pointF) {
        PointF pointF2;
        PointF pointF3;
        double aLatitude = d;
        double aLongitude = d2;
        PointF reuse = pointF;
        if (reuse != null) {
            pointF3 = reuse;
        } else {
            pointF3 = pointF2;
            new PointF();
        }
        PointF out = pointF3;
        float y = (float) ((MyMath.gudermannInverse(this.mLatNorth) - MyMath.gudermannInverse(aLatitude)) / (MyMath.gudermannInverse(this.mLatNorth) - MyMath.gudermannInverse(this.mLatSouth)));
        out.set(1.0f - ((float) ((this.mLonEast - aLongitude) / getLongitudeSpan())), y);
        return out;
    }

    public GeoPoint getGeoPointOfRelativePositionWithLinearInterpolation(float relX, float relY) {
        GeoPoint geoPoint;
        double lat = this.mLatNorth - (getLatitudeSpan() * ((double) relY));
        double lon = this.mLonWest + (getLongitudeSpan() * ((double) relX));
        while (lat > 90.5d) {
            lat -= 90.5d;
        }
        while (lat < -90.5d) {
            lat += 90.5d;
        }
        while (lon > 180.0d) {
            lon -= 180.0d;
        }
        while (lon < -180.0d) {
            lon += 180.0d;
        }
        new GeoPoint(lat, lon);
        return geoPoint;
    }

    public GeoPoint getGeoPointOfRelativePositionWithExactGudermannInterpolation(float relX, float relY) {
        GeoPoint geoPoint;
        double gudNorth = MyMath.gudermannInverse(this.mLatNorth);
        double gudSouth = MyMath.gudermannInverse(this.mLatSouth);
        double lat = MyMath.gudermann(gudSouth + (((double) (1.0f - relY)) * (gudNorth - gudSouth)));
        double lon = this.mLonWest + (getLongitudeSpan() * ((double) relX));
        while (lat > 90.5d) {
            lat -= 90.5d;
        }
        while (lat < -90.5d) {
            lat += 90.5d;
        }
        while (lon > 180.0d) {
            lon -= 180.0d;
        }
        while (lon < -180.0d) {
            lon += 180.0d;
        }
        new GeoPoint(lat, lon);
        return geoPoint;
    }

    public BoundingBox increaseByScale(float f) {
        BoundingBox boundingBox;
        float pBoundingboxPaddingRelativeScale = f;
        GeoPoint pCenter = getCenter();
        double mLatSpanPadded_2 = (getLatitudeSpan() * ((double) pBoundingboxPaddingRelativeScale)) / 2.0d;
        double mLonSpanPadded_2 = (getLongitudeSpan() * ((double) pBoundingboxPaddingRelativeScale)) / 2.0d;
        new BoundingBox(pCenter.getLatitude() + mLatSpanPadded_2, pCenter.getLongitude() + mLonSpanPadded_2, pCenter.getLatitude() - mLatSpanPadded_2, pCenter.getLongitude() - mLonSpanPadded_2);
        return boundingBox;
    }

    public String toString() {
        StringBuffer stringBuffer;
        new StringBuffer();
        return stringBuffer.append("N:").append(this.mLatNorth).append("; E:").append(this.mLonEast).append("; S:").append(this.mLatSouth).append("; W:").append(this.mLonWest).toString();
    }

    public GeoPoint bringToBoundingBox(double aLatitude, double aLongitude) {
        GeoPoint geoPoint;
        new GeoPoint(Math.max(this.mLatSouth, Math.min(this.mLatNorth, aLatitude)), Math.max(this.mLonWest, Math.min(this.mLonEast, aLongitude)));
        return geoPoint;
    }

    public static BoundingBox fromGeoPoints(List<? extends IGeoPoint> partialPolyLine) {
        BoundingBox boundingBox;
        double minLat = Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE;
        double maxLat = -1.7976931348623157E308d;
        double maxLon = -1.7976931348623157E308d;
        for (IGeoPoint gp : partialPolyLine) {
            double latitude = gp.getLatitude();
            double longitude = gp.getLongitude();
            minLat = Math.min(minLat, latitude);
            minLon = Math.min(minLon, longitude);
            maxLat = Math.max(maxLat, latitude);
            maxLon = Math.max(maxLon, longitude);
        }
        new BoundingBox(maxLat, maxLon, minLat, minLon);
        return boundingBox;
    }

    public boolean contains(IGeoPoint iGeoPoint) {
        IGeoPoint pGeoPoint = iGeoPoint;
        return contains(pGeoPoint.getLatitude(), pGeoPoint.getLongitude());
    }

    public boolean contains(double d, double d2) {
        double aLatitude = d;
        double aLongitude = d2;
        return aLatitude < this.mLatNorth && aLatitude > this.mLatSouth && aLongitude < this.mLonEast && aLongitude > this.mLonWest;
    }

    static {
        Parcelable.Creator<BoundingBox> creator;
        new Parcelable.Creator<BoundingBox>() {
            public BoundingBox createFromParcel(Parcel in) {
                return BoundingBox.readFromParcel(in);
            }

            public BoundingBox[] newArray(int size) {
                return new BoundingBox[size];
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
        out.writeDouble(this.mLatNorth);
        out.writeDouble(this.mLonEast);
        out.writeDouble(this.mLatSouth);
        out.writeDouble(this.mLonWest);
    }

    /* access modifiers changed from: private */
    public static BoundingBox readFromParcel(Parcel parcel) {
        BoundingBox boundingBox;
        Parcel in = parcel;
        new BoundingBox(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble());
        return boundingBox;
    }

    @Deprecated
    public int getLatitudeSpanE6() {
        return (int) (getLatitudeSpan() * 1000000.0d);
    }

    @Deprecated
    public int getLongitudeSpanE6() {
        return (int) (getLongitudeSpan() * 1000000.0d);
    }
}
