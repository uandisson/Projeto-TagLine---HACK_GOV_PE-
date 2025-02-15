package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory;
import com.google.appinventor.components.runtime.util.YailList;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MAPS, description = "Rectangle", version = 2)
public class Rectangle extends PolygonBase implements MapFactory.MapRectangle {
    private static final MapFactory.MapFeatureVisitor<Double> hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    private double ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud = 0.0d;
    private double KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2 = 0.0d;
    private double sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb = 0.0d;
    private double wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0 = 0.0d;

    static {
        MapFactory.MapFeatureVisitor<Double> mapFeatureVisitor;
        new MapFactory.MapFeatureVisitor<Double>() {
            public final /* synthetic */ Object visit(MapFactory.MapRectangle mapRectangle, Object[] objArr) {
                MapFactory.MapRectangle mapRectangle2 = mapRectangle;
                Object[] objArr2 = objArr;
                if (((Boolean) objArr2[1]).booleanValue()) {
                    return Double.valueOf(GeometryUtil.distanceBetweenCentroids(mapRectangle2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
                }
                return Double.valueOf(GeometryUtil.distanceBetweenEdges(mapRectangle2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
            }

            public final /* synthetic */ Object visit(MapFactory.MapCircle mapCircle, Object[] objArr) {
                MapFactory.MapCircle mapCircle2 = mapCircle;
                Object[] objArr2 = objArr;
                if (((Boolean) objArr2[1]).booleanValue()) {
                    return Double.valueOf(GeometryUtil.distanceBetweenCentroids(mapCircle2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
                }
                return Double.valueOf(GeometryUtil.distanceBetweenEdges(mapCircle2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
            }

            public final /* synthetic */ Object visit(MapFactory.MapPolygon mapPolygon, Object[] objArr) {
                MapFactory.MapPolygon mapPolygon2 = mapPolygon;
                Object[] objArr2 = objArr;
                if (((Boolean) objArr2[1]).booleanValue()) {
                    return Double.valueOf(GeometryUtil.distanceBetweenCentroids(mapPolygon2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
                }
                return Double.valueOf(GeometryUtil.distanceBetweenEdges(mapPolygon2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
            }

            public final /* synthetic */ Object visit(MapFactory.MapLineString mapLineString, Object[] objArr) {
                MapFactory.MapLineString mapLineString2 = mapLineString;
                Object[] objArr2 = objArr;
                if (((Boolean) objArr2[1]).booleanValue()) {
                    return Double.valueOf(GeometryUtil.distanceBetweenCentroids(mapLineString2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
                }
                return Double.valueOf(GeometryUtil.distanceBetweenEdges(mapLineString2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
            }

            public final /* synthetic */ Object visit(MapFactory.MapMarker mapMarker, Object[] objArr) {
                MapFactory.MapMarker mapMarker2 = mapMarker;
                Object[] objArr2 = objArr;
                if (((Boolean) objArr2[1]).booleanValue()) {
                    return Double.valueOf(GeometryUtil.distanceBetweenCentroids(mapMarker2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
                }
                return Double.valueOf(GeometryUtil.distanceBetweenEdges(mapMarker2, (MapFactory.MapRectangle) (Rectangle) objArr2[0]));
            }
        };
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = mapFeatureVisitor;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Rectangle(com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer r7) {
        /*
            r6 = this;
            r1 = r6
            r2 = r7
            r3 = r1
            r4 = r2
            com.google.appinventor.components.runtime.util.MapFactory$MapFeatureVisitor<java.lang.Double> r5 = hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME
            r3.<init>(r4, r5)
            r3 = r1
            r4 = 0
            r3.ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud = r4
            r3 = r1
            r4 = 0
            r3.wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0 = r4
            r3 = r1
            r4 = 0
            r3.KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2 = r4
            r3 = r1
            r4 = 0
            r3.sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb = r4
            r3 = r2
            r4 = r1
            r3.addFeature(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.Rectangle.<init>(com.google.appinventor.components.runtime.util.MapFactory$MapFeatureContainer):void");
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The type of the feature. For rectangles, this returns the text \"Rectangle\".")
    public String Type() {
        return MapFactory.MapFeatureType.TYPE_RECTANGLE;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty
    public void EastLongitude(double d) {
        this.ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud = d;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapFactory.MapRectangle) this);
    }

    @SimpleProperty
    public double EastLongitude() {
        return this.ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty
    public void NorthLatitude(double d) {
        this.KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2 = d;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapFactory.MapRectangle) this);
    }

    @SimpleProperty
    public double NorthLatitude() {
        return this.KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty
    public void SouthLatitude(double d) {
        this.sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb = d;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapFactory.MapRectangle) this);
    }

    @SimpleProperty
    public double SouthLatitude() {
        return this.sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty
    public void WestLongitude(double d) {
        this.wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0 = d;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapFactory.MapRectangle) this);
    }

    @SimpleProperty
    public double WestLongitude() {
        return this.wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0;
    }

    @SimpleFunction(description = "Returns the center of the Rectangle as a list of the form (Latitude Longitude).")
    public YailList Center() {
        return GeometryUtil.asYailList(getCentroid());
    }

    @SimpleFunction(description = "Returns the bounding box of the Rectangle in the format ((North West) (South East)).")
    public YailList Bounds() {
        Double[] dArr = new Double[2];
        dArr[0] = Double.valueOf(this.KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2);
        Double[] dArr2 = dArr;
        dArr2[1] = Double.valueOf(this.wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0);
        YailList makeList = YailList.makeList((Object[]) dArr2);
        Double[] dArr3 = new Double[2];
        dArr3[0] = Double.valueOf(this.sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb);
        Double[] dArr4 = dArr3;
        dArr4[1] = Double.valueOf(this.ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud);
        YailList makeList2 = YailList.makeList((Object[]) dArr4);
        YailList[] yailListArr = new YailList[2];
        yailListArr[0] = makeList;
        YailList[] yailListArr2 = yailListArr;
        yailListArr2[1] = makeList2;
        return YailList.makeList((Object[]) yailListArr2);
    }

    @SimpleFunction(description = "Moves the Rectangle so that it is centered on the given latitude and longitude while attempting to maintain the width and height of the Rectangle as measured from the center to the edges.")
    public void SetCenter(double d, double d2) {
        IGeoPoint iGeoPoint;
        IGeoPoint iGeoPoint2;
        IGeoPoint iGeoPoint3;
        IGeoPoint iGeoPoint4;
        double d3 = d;
        double d4 = d2;
        if (d3 < -90.0d || d3 > 90.0d) {
            Object[] objArr = new Object[2];
            objArr[0] = Double.valueOf(d3);
            Object[] objArr2 = objArr;
            objArr2[1] = Double.valueOf(d4);
            this.container.$form().dispatchErrorOccurredEvent(this, "SetCenter", ErrorMessages.ERROR_INVALID_POINT, objArr2);
        } else if (d4 < -180.0d || d4 > 180.0d) {
            Object[] objArr3 = new Object[2];
            objArr3[0] = Double.valueOf(d3);
            Object[] objArr4 = objArr3;
            objArr4[1] = Double.valueOf(d4);
            this.container.$form().dispatchErrorOccurredEvent(this, "SetCenter", ErrorMessages.ERROR_INVALID_POINT, objArr4);
        } else {
            GeoPoint centroid = getCentroid();
            new GeoPoint(this.KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2, centroid.getLongitude());
            new GeoPoint(this.sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb, centroid.getLongitude());
            new GeoPoint(centroid.getLatitude(), this.ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud);
            new GeoPoint(centroid.getLatitude(), this.wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0);
            double distanceBetween = GeometryUtil.distanceBetween(iGeoPoint, iGeoPoint2) / 2.0d;
            double distanceBetween2 = GeometryUtil.distanceBetween(iGeoPoint3, iGeoPoint4) / 2.0d;
            centroid.setCoords(d3, d4);
            this.KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2 = centroid.destinationPoint(distanceBetween, 0.0f).getLatitude();
            this.sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb = centroid.destinationPoint(distanceBetween, 180.0f).getLatitude();
            this.ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud = centroid.destinationPoint(distanceBetween2, 90.0f).getLongitude();
            this.wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0 = centroid.destinationPoint(distanceBetween2, 270.0f).getLongitude();
            clearGeometry();
            this.map.getController().updateFeaturePosition((MapFactory.MapRectangle) this);
        }
    }

    public <T> T accept(MapFactory.MapFeatureVisitor<T> mapFeatureVisitor, Object... objArr) {
        return mapFeatureVisitor.visit((MapFactory.MapRectangle) this, objArr);
    }

    /* access modifiers changed from: protected */
    public Geometry computeGeometry() {
        return GeometryUtil.createGeometry(this.KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2, this.ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud, this.sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb, this.wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0);
    }

    public void updateBounds(double d, double d2, double d3, double d4) {
        this.KXDzEMeLg0aMKCNRnRJuQGoMaVrKUgtBW3gGmn2kxU5q0F1ZNh5DKQo95IN9JPm2 = d;
        this.wGMjbGuJ9Yk6s2LaEm8v1pEJlXt36TYBWZSsia0LUgb1yMdHNGB7uRz3VqnF79D0 = d2;
        this.sLSXXiYjDERyx7CKvO5GstTCcI8HiXXLiPYrugcXt2517h4ADL52v0RLLmUd9xMb = d3;
        this.ANz72NxTeEmYo9CF87MXRUuH7WvE4u0mpZwxffTnyiMdygEQRKvmdTCHaXqAvud = d4;
        clearGeometry();
    }
}
