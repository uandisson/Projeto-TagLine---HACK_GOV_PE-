package org.osmdroid.util;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.constants.GeoConstants;
import org.osmdroid.views.util.constants.MathConstants;

public class GeoPoint implements IGeoPoint, MathConstants, GeoConstants, Parcelable, Serializable, Cloneable {
    public static final Parcelable.Creator<GeoPoint> CREATOR;
    static final long serialVersionUID = 1;
    private double mAltitude;
    private double mLatitude;
    private double mLongitude;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    /* synthetic */ GeoPoint(Parcel x0, C15951 r7) {
        this(x0);
        C15951 r2 = r7;
    }

    @Deprecated
    public GeoPoint(int aLatitudeE6, int aLongitudeE6) {
        this.mLatitude = ((double) aLatitudeE6) / 1000000.0d;
        this.mLongitude = ((double) aLongitudeE6) / 1000000.0d;
    }

    @Deprecated
    public GeoPoint(int aLatitudeE6, int aLongitudeE6, int aAltitude) {
        this.mLatitude = ((double) aLatitudeE6) / 1000000.0d;
        this.mLongitude = ((double) aLongitudeE6) / 1000000.0d;
        this.mAltitude = (double) aAltitude;
    }

    public GeoPoint(double aLatitude, double aLongitude) {
        this.mLatitude = aLatitude;
        this.mLongitude = aLongitude;
    }

    public GeoPoint(double aLatitude, double aLongitude, double aAltitude) {
        this.mLatitude = aLatitude;
        this.mLongitude = aLongitude;
        this.mAltitude = aAltitude;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GeoPoint(android.location.Location r11) {
        /*
            r10 = this;
            r1 = r10
            r2 = r11
            r3 = r1
            r4 = r2
            double r4 = r4.getLatitude()
            r6 = r2
            double r6 = r6.getLongitude()
            r8 = r2
            double r8 = r8.getAltitude()
            r3.<init>((double) r4, (double) r6, (double) r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.util.GeoPoint.<init>(android.location.Location):void");
    }

    public GeoPoint(GeoPoint geoPoint) {
        GeoPoint aGeopoint = geoPoint;
        this.mLatitude = aGeopoint.mLatitude;
        this.mLongitude = aGeopoint.mLongitude;
        this.mAltitude = aGeopoint.mAltitude;
    }

    public static GeoPoint fromDoubleString(String str, char c) {
        GeoPoint geoPoint;
        GeoPoint geoPoint2;
        String s = str;
        char spacer = c;
        int spacerPos1 = s.indexOf(spacer);
        int spacerPos2 = s.indexOf(spacer, spacerPos1 + 1);
        if (spacerPos2 == -1) {
            new GeoPoint(Double.parseDouble(s.substring(0, spacerPos1)), Double.parseDouble(s.substring(spacerPos1 + 1, s.length())));
            return geoPoint2;
        }
        new GeoPoint(Double.parseDouble(s.substring(0, spacerPos1)), Double.parseDouble(s.substring(spacerPos1 + 1, spacerPos2)), Double.parseDouble(s.substring(spacerPos2 + 1, s.length())));
        return geoPoint;
    }

    public static GeoPoint fromInvertedDoubleString(String str, char c) {
        GeoPoint geoPoint;
        GeoPoint geoPoint2;
        String s = str;
        char spacer = c;
        int spacerPos1 = s.indexOf(spacer);
        int spacerPos2 = s.indexOf(spacer, spacerPos1 + 1);
        if (spacerPos2 == -1) {
            new GeoPoint(Double.parseDouble(s.substring(spacerPos1 + 1, s.length())), Double.parseDouble(s.substring(0, spacerPos1)));
            return geoPoint2;
        }
        new GeoPoint(Double.parseDouble(s.substring(spacerPos1 + 1, spacerPos2)), Double.parseDouble(s.substring(0, spacerPos1)), Double.parseDouble(s.substring(spacerPos2 + 1, s.length())));
        return geoPoint;
    }

    public static GeoPoint fromIntString(String str) {
        GeoPoint geoPoint;
        GeoPoint geoPoint2;
        String s = str;
        int commaPos1 = s.indexOf(44);
        int commaPos2 = s.indexOf(44, commaPos1 + 1);
        if (commaPos2 == -1) {
            new GeoPoint(Integer.parseInt(s.substring(0, commaPos1)), Integer.parseInt(s.substring(commaPos1 + 1, s.length())));
            return geoPoint2;
        }
        new GeoPoint(Integer.parseInt(s.substring(0, commaPos1)), Integer.parseInt(s.substring(commaPos1 + 1, commaPos2)), Integer.parseInt(s.substring(commaPos2 + 1, s.length())));
        return geoPoint;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getAltitude() {
        return this.mAltitude;
    }

    public void setLatitude(double aLatitude) {
        double d = aLatitude;
        this.mLatitude = d;
    }

    public void setLongitude(double aLongitude) {
        double d = aLongitude;
        this.mLongitude = d;
    }

    public void setAltitude(double aAltitude) {
        double d = aAltitude;
        this.mAltitude = d;
    }

    public void setCoords(double aLatitude, double aLongitude) {
        this.mLatitude = aLatitude;
        this.mLongitude = aLongitude;
    }

    public GeoPoint clone() {
        GeoPoint geoPoint;
        new GeoPoint(this.mLatitude, this.mLongitude, this.mAltitude);
        return geoPoint;
    }

    public String toIntString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append((int) (this.mLatitude * 1000000.0d)).append(",").append((int) (this.mLongitude * 1000000.0d)).append(",").append((int) this.mAltitude).toString();
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append(this.mLatitude).append(",").append(this.mLongitude).append(",").append(this.mAltitude).toString();
    }

    public boolean equals(Object obj) {
        Object obj2 = obj;
        if (obj2 == null) {
            return false;
        }
        if (obj2 == this) {
            return true;
        }
        if (obj2.getClass() != getClass()) {
            return false;
        }
        GeoPoint rhs = (GeoPoint) obj2;
        return rhs.mLatitude == this.mLatitude && rhs.mLongitude == this.mLongitude && rhs.mAltitude == this.mAltitude;
    }

    public int hashCode() {
        return (37 * ((17 * ((int) (this.mLatitude * 1.0E-6d))) + ((int) (this.mLongitude * 1.0E-6d)))) + ((int) this.mAltitude);
    }

    private GeoPoint(Parcel parcel) {
        Parcel in = parcel;
        this.mLatitude = in.readDouble();
        this.mLongitude = in.readDouble();
        this.mAltitude = in.readDouble();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        Parcel out = parcel;
        int i2 = i;
        out.writeDouble(this.mLatitude);
        out.writeDouble(this.mLongitude);
        out.writeDouble(this.mAltitude);
    }

    static {
        Parcelable.Creator<GeoPoint> creator;
        new Parcelable.Creator<GeoPoint>() {
            public GeoPoint createFromParcel(Parcel in) {
                GeoPoint geoPoint;
                new GeoPoint(in, (C15951) null);
                return geoPoint;
            }

            public GeoPoint[] newArray(int size) {
                return new GeoPoint[size];
            }
        };
        CREATOR = creator;
    }

    public int distanceTo(IGeoPoint iGeoPoint) {
        IGeoPoint other = iGeoPoint;
        double a1 = 0.01745329238474369d * this.mLatitude;
        double a2 = 0.01745329238474369d * this.mLongitude;
        double b1 = 0.01745329238474369d * other.getLatitude();
        double b2 = 0.01745329238474369d * other.getLongitude();
        double cosa1 = Math.cos(a1);
        double cosb1 = Math.cos(b1);
        double t1 = cosa1 * Math.cos(a2) * cosb1 * Math.cos(b2);
        double t2 = cosa1 * Math.sin(a2) * cosb1 * Math.sin(b2);
        return (int) (6378137.0d * Math.acos(t1 + t2 + (Math.sin(a1) * Math.sin(b1))));
    }

    public double bearingTo(IGeoPoint iGeoPoint) {
        IGeoPoint other = iGeoPoint;
        double lat1 = Math.toRadians(this.mLatitude);
        double long1 = Math.toRadians(this.mLongitude);
        double lat2 = Math.toRadians(other.getLatitude());
        double delta_long = Math.toRadians(other.getLongitude()) - long1;
        return (Math.toDegrees(Math.atan2(Math.sin(delta_long) * Math.cos(lat2), (Math.cos(lat1) * Math.sin(lat2)) - ((Math.sin(lat1) * Math.cos(lat2)) * Math.cos(delta_long)))) + 360.0d) % 360.0d;
    }

    public GeoPoint destinationPoint(double aDistanceInMeters, float aBearingInDegrees) {
        GeoPoint geoPoint;
        double dist = aDistanceInMeters / 6378137.0d;
        float brng = 0.017453292f * aBearingInDegrees;
        double lat1 = 0.01745329238474369d * getLatitude();
        double lon1 = 0.01745329238474369d * getLongitude();
        double lat2 = Math.asin((Math.sin(lat1) * Math.cos(dist)) + (Math.cos(lat1) * Math.sin(dist) * Math.cos((double) brng)));
        new GeoPoint(lat2 / 0.01745329238474369d, (lon1 + Math.atan2((Math.sin((double) brng) * Math.sin(dist)) * Math.cos(lat1), Math.cos(dist) - (Math.sin(lat1) * Math.sin(lat2)))) / 0.01745329238474369d);
        return geoPoint;
    }

    public static GeoPoint fromCenterBetween(GeoPoint geoPoint, GeoPoint geoPoint2) {
        GeoPoint geoPointA;
        GeoPoint geoPointA2 = geoPoint;
        GeoPoint geoPointB = geoPoint2;
        new GeoPoint((geoPointA2.getLatitude() + geoPointB.getLatitude()) / 2.0d, (geoPointA2.getLongitude() + geoPointB.getLongitude()) / 2.0d);
        return geoPointA;
    }

    public String toDoubleString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append(this.mLatitude).append(",").append(this.mLongitude).append(",").append(this.mAltitude).toString();
    }

    public String toInvertedDoubleString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append(this.mLongitude).append(",").append(this.mLatitude).append(",").append(this.mAltitude).toString();
    }

    @Deprecated
    public int getLatitudeE6() {
        return (int) (getLatitude() * 1000000.0d);
    }

    @Deprecated
    public int getLongitudeE6() {
        return (int) (getLongitude() * 1000000.0d);
    }
}
