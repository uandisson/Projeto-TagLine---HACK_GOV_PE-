package org.osmdroid.views.overlay.simplefastpoint;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import org.osmdroid.util.GeoPoint;

public class LabelledGeoPoint extends GeoPoint {
    public static final Parcelable.Creator<LabelledGeoPoint> CREATOR;
    private String mLabel;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    /* synthetic */ LabelledGeoPoint(Parcel x0, C16111 r7) {
        this(x0);
        C16111 r2 = r7;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LabelledGeoPoint(int aLatitudeE6, int aLongitudeE6) {
        super(aLatitudeE6, aLongitudeE6);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LabelledGeoPoint(int aLatitudeE6, int aLongitudeE6, int aAltitude) {
        super(aLatitudeE6, aLongitudeE6, aAltitude);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LabelledGeoPoint(double aLatitude, double aLongitude) {
        super(aLatitude, aLongitude);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LabelledGeoPoint(double aLatitude, double aLongitude, double aAltitude) {
        super(aLatitude, aLongitude, aAltitude);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LabelledGeoPoint(double aLatitude, double aLongitude, double aAltitude, String aLabel) {
        super(aLatitude, aLongitude, aAltitude);
        this.mLabel = aLabel;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LabelledGeoPoint(Location aLocation) {
        super(aLocation);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LabelledGeoPoint(GeoPoint aGeopoint) {
        super(aGeopoint);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LabelledGeoPoint(double aLatitude, double aLongitude, String aLabel) {
        super(aLatitude, aLongitude);
        this.mLabel = aLabel;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LabelledGeoPoint(org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint r12) {
        /*
            r11 = this;
            r1 = r11
            r2 = r12
            r3 = r1
            r4 = r2
            double r4 = r4.getLatitude()
            r6 = r2
            double r6 = r6.getLongitude()
            r8 = r2
            double r8 = r8.getAltitude()
            r10 = r2
            java.lang.String r10 = r10.getLabel()
            r3.<init>(r4, r6, r8, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint.<init>(org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint):void");
    }

    public String getLabel() {
        return this.mLabel;
    }

    public void setLabel(String label) {
        String str = label;
        this.mLabel = str;
    }

    public LabelledGeoPoint clone() {
        LabelledGeoPoint labelledGeoPoint;
        new LabelledGeoPoint(getLatitude(), getLongitude(), getAltitude(), this.mLabel);
        return labelledGeoPoint;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private LabelledGeoPoint(android.os.Parcel r11) {
        /*
            r10 = this;
            r1 = r10
            r2 = r11
            r3 = r1
            r4 = r2
            double r4 = r4.readDouble()
            r6 = r2
            double r6 = r6.readDouble()
            r8 = r2
            double r8 = r8.readDouble()
            r3.<init>((double) r4, (double) r6, (double) r8)
            r3 = r1
            r4 = r2
            java.lang.String r4 = r4.readString()
            r3.setLabel(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint.<init>(android.os.Parcel):void");
    }

    public void writeToParcel(Parcel parcel, int flags) {
        Parcel out = parcel;
        super.writeToParcel(out, flags);
        out.writeString(this.mLabel);
    }

    static {
        Parcelable.Creator<LabelledGeoPoint> creator;
        new Parcelable.Creator<LabelledGeoPoint>() {
            public LabelledGeoPoint createFromParcel(Parcel in) {
                LabelledGeoPoint labelledGeoPoint;
                new LabelledGeoPoint(in, (C16111) null);
                return labelledGeoPoint;
            }

            public LabelledGeoPoint[] newArray(int size) {
                return new LabelledGeoPoint[size];
            }
        };
        CREATOR = creator;
    }
}
