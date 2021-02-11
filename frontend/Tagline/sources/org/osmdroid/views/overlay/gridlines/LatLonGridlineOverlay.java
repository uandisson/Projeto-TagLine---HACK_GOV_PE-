package org.osmdroid.views.overlay.gridlines;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

public class LatLonGridlineOverlay {
    public static boolean DEBUG = false;
    public static boolean DEBUG2 = false;
    public static int backgroundColor = -16777216;

    /* renamed from: df */
    static final DecimalFormat f532df;
    public static int fontColor = -1;
    public static short fontSizeDp = 24;
    public static int lineColor = -16777216;
    public static float lineWidth = 1.0f;
    private static float multiplier = 1.0f;

    public LatLonGridlineOverlay() {
    }

    static {
        DecimalFormat decimalFormat;
        new DecimalFormat("#.#####");
        f532df = decimalFormat;
    }

    private static void applyMarkerAttributes(Marker marker) {
        Marker m = marker;
        m.setTextLabelBackgroundColor(backgroundColor);
        m.setTextLabelFontSize(fontSizeDp);
        m.setTextLabelForegroundColor(fontColor);
    }

    public static FolderOverlay getLatLonGrid(Context context, MapView mapView) {
        FolderOverlay folderOverlay;
        Marker marker;
        StringBuilder sb;
        GeoPoint geoPoint;
        StringBuilder sb2;
        Polyline polyline;
        List<GeoPoint> list;
        Object obj;
        Object obj2;
        Marker marker2;
        StringBuilder sb3;
        GeoPoint geoPoint2;
        StringBuilder sb4;
        StringBuilder sb5;
        Polyline polyline2;
        List<GeoPoint> list2;
        Object obj3;
        Object obj4;
        StringBuilder sb6;
        StringBuilder sb7;
        Polyline polyline3;
        List<GeoPoint> list3;
        Object obj5;
        Object obj6;
        Marker marker3;
        StringBuilder sb8;
        GeoPoint geoPoint3;
        StringBuilder sb9;
        StringBuilder sb10;
        Polyline polyline4;
        List<GeoPoint> list4;
        Object obj7;
        Object obj8;
        Marker marker4;
        StringBuilder sb11;
        GeoPoint geoPoint4;
        StringBuilder sb12;
        StringBuilder sb13;
        StringBuilder sb14;
        StringBuilder sb15;
        Context context2 = context;
        MapView mapView2 = mapView;
        BoundingBox box = mapView2.getBoundingBox();
        int zoom = mapView2.getZoomLevel();
        Marker.ENABLE_TEXT_LABELS_WHEN_NO_IMAGE = true;
        if (DEBUG) {
            System.out.println("######### getLatLonGrid ");
        }
        new FolderOverlay();
        FolderOverlay gridlines = folderOverlay;
        if (zoom >= 2) {
            double north = box.getLatNorth();
            double south = box.getLatSouth();
            double east = box.getLonEast();
            double west = box.getLonWest();
            if (north < south) {
                return gridlines;
            }
            if (DEBUG) {
                PrintStream printStream = System.out;
                new StringBuilder();
                printStream.println(sb15.append("N ").append(north).append(" S ").append(south).append(", ").append(0.0d).toString());
            }
            boolean dateLineVisible = false;
            if (east < 0.0d && west > 0.0d) {
                dateLineVisible = true;
            }
            if (DEBUG) {
                PrintStream printStream2 = System.out;
                new StringBuilder();
                printStream2.println(sb14.append("delta ").append(0.0d).toString());
            }
            double incrementor = getIncrementor(zoom);
            double[] startend = getStartEndPointsNS(north, south, zoom);
            double sn_start_point = startend[0];
            double sn_stop_point = startend[1];
            double d = sn_start_point;
            while (true) {
                double i = d;
                if (i > sn_stop_point) {
                    break;
                }
                new Polyline();
                Polyline p = polyline4;
                p.setWidth(lineWidth);
                p.setColor(lineColor);
                new ArrayList<>();
                List<GeoPoint> pts = list4;
                new GeoPoint(i, east);
                boolean add = pts.add(obj7);
                new GeoPoint(i, west);
                boolean add2 = pts.add(obj8);
                if (DEBUG) {
                    PrintStream printStream3 = System.out;
                    new StringBuilder();
                    printStream3.println(sb13.append("drawing NS ").append(i).append(",").append(east).append(" to ").append(i).append(",").append(west).append(", zoom ").append(zoom).toString());
                }
                p.setPoints(pts);
                boolean add3 = gridlines.add(p);
                new Marker(mapView2);
                Marker m = marker4;
                applyMarkerAttributes(m);
                if (i > 0.0d) {
                    new StringBuilder();
                    m.setTitle(sb12.append(f532df.format(i)).append("N").toString());
                } else {
                    new StringBuilder();
                    m.setTitle(sb11.append(f532df.format(i)).append("S").toString());
                }
                m.setIcon((Drawable) null);
                new GeoPoint(i, west + incrementor);
                m.setPosition(geoPoint4);
                boolean add4 = gridlines.add(m);
                d = i + incrementor;
            }
            double[] ew = getStartEndPointsWE(west, east, zoom);
            double we_startpoint = ew[1];
            double ws_stoppoint = ew[0];
            double d2 = we_startpoint;
            while (true) {
                double i2 = d2;
                if (i2 > ws_stoppoint) {
                    break;
                }
                new Polyline();
                Polyline p2 = polyline3;
                p2.setWidth(lineWidth);
                p2.setColor(lineColor);
                new ArrayList<>();
                List<GeoPoint> pts2 = list3;
                new GeoPoint(north, i2);
                boolean add5 = pts2.add(obj5);
                new GeoPoint(south, i2);
                boolean add6 = pts2.add(obj6);
                p2.setPoints(pts2);
                if (DEBUG) {
                    PrintStream printStream4 = System.err;
                    new StringBuilder();
                    printStream4.println(sb10.append("drawing EW ").append(south).append(",").append(i2).append(" to ").append(north).append(",").append(i2).append(", zoom ").append(zoom).toString());
                }
                boolean add7 = gridlines.add(p2);
                new Marker(mapView2);
                Marker m2 = marker3;
                applyMarkerAttributes(m2);
                m2.setRotation(-90.0f);
                if (i2 > 0.0d) {
                    new StringBuilder();
                    m2.setTitle(sb9.append(f532df.format(i2)).append("E").toString());
                } else {
                    new StringBuilder();
                    m2.setTitle(sb8.append(f532df.format(i2)).append("W").toString());
                }
                m2.setIcon((Drawable) null);
                new GeoPoint(south + incrementor, i2);
                m2.setPosition(geoPoint3);
                boolean add8 = gridlines.add(m2);
                d2 = i2 + incrementor;
            }
            if (dateLineVisible) {
                if (DEBUG) {
                    PrintStream printStream5 = System.out;
                    new StringBuilder();
                    printStream5.println(sb7.append("DATELINE zoom ").append(zoom).append(" ").append(we_startpoint).append(" ").append(ws_stoppoint).toString());
                }
                double d3 = we_startpoint;
                while (true) {
                    double i3 = d3;
                    if (i3 > 180.0d) {
                        break;
                    }
                    new Polyline();
                    Polyline p3 = polyline2;
                    p3.setWidth(lineWidth);
                    p3.setColor(lineColor);
                    new ArrayList<>();
                    List<GeoPoint> pts3 = list2;
                    new GeoPoint(north, i3);
                    boolean add9 = pts3.add(obj3);
                    new GeoPoint(south, i3);
                    boolean add10 = pts3.add(obj4);
                    p3.setPoints(pts3);
                    if (DEBUG2) {
                        PrintStream printStream6 = System.out;
                        new StringBuilder();
                        printStream6.println(sb6.append("DATELINE drawing NS").append(south).append(",").append(i3).append(" to ").append(north).append(",").append(i3).append(", zoom ").append(zoom).toString());
                    }
                    boolean add11 = gridlines.add(p3);
                    d3 = i3 + incrementor;
                }
                double d4 = -180.0d;
                while (true) {
                    double i4 = d4;
                    if (i4 > ws_stoppoint) {
                        break;
                    }
                    new Polyline();
                    Polyline p4 = polyline;
                    p4.setWidth(lineWidth);
                    p4.setColor(lineColor);
                    new ArrayList<>();
                    List<GeoPoint> pts4 = list;
                    new GeoPoint(north, i4);
                    boolean add12 = pts4.add(obj);
                    new GeoPoint(south, i4);
                    boolean add13 = pts4.add(obj2);
                    p4.setPoints(pts4);
                    if (DEBUG2) {
                        PrintStream printStream7 = System.out;
                        new StringBuilder();
                        printStream7.println(sb5.append("DATELINE drawing EW").append(south).append(",").append(i4).append(" to ").append(north).append(",").append(i4).append(", zoom ").append(zoom).toString());
                    }
                    boolean add14 = gridlines.add(p4);
                    new Marker(mapView2);
                    Marker m3 = marker2;
                    applyMarkerAttributes(m3);
                    m3.setRotation(-90.0f);
                    if (i4 > 0.0d) {
                        new StringBuilder();
                        m3.setTitle(sb4.append(f532df.format(i4)).append("E").toString());
                    } else {
                        new StringBuilder();
                        m3.setTitle(sb3.append(f532df.format(i4)).append("W").toString());
                    }
                    m3.setIcon((Drawable) null);
                    new GeoPoint(south + incrementor, i4);
                    m3.setPosition(geoPoint2);
                    boolean add15 = gridlines.add(m3);
                    d4 = i4 + incrementor;
                }
                double d5 = we_startpoint;
                while (true) {
                    double i5 = d5;
                    if (i5 >= 180.0d) {
                        break;
                    }
                    new Marker(mapView2);
                    Marker m4 = marker;
                    applyMarkerAttributes(m4);
                    m4.setRotation(-90.0f);
                    if (i5 > 0.0d) {
                        new StringBuilder();
                        m4.setTitle(sb2.append(f532df.format(i5)).append("E").toString());
                    } else {
                        new StringBuilder();
                        m4.setTitle(sb.append(f532df.format(i5)).append("W").toString());
                    }
                    m4.setIcon((Drawable) null);
                    new GeoPoint(south + incrementor, i5);
                    m4.setPosition(geoPoint);
                    boolean add16 = gridlines.add(m4);
                    d5 = i5 + incrementor;
                }
            }
        }
        return gridlines;
    }

    private static double[] getStartEndPointsNS(double d, double d2, int i) {
        StringBuilder sb;
        StringBuilder sb2;
        double x;
        double x2;
        double north = d;
        double south = d2;
        int zoom = i;
        if (zoom < 10) {
            double sn_start_point = Math.floor(south);
            double incrementor = getIncrementor(zoom);
            double d3 = -90.0d;
            while (true) {
                x = d3;
                if (x >= sn_start_point) {
                    break;
                }
                d3 = x + incrementor;
            }
            double sn_start_point2 = x;
            double d4 = 90.0d;
            while (true) {
                x2 = d4;
                if (x2 <= Math.ceil(north)) {
                    break;
                }
                d4 = x2 - incrementor;
            }
            double sn_stop_point = x2;
            if (sn_stop_point > 90.0d) {
                sn_stop_point = 90.0d;
            }
            if (sn_start_point2 < -90.0d) {
                sn_start_point2 = -90.0d;
            }
            double[] dArr = new double[2];
            dArr[0] = sn_start_point2;
            double[] dArr2 = dArr;
            dArr2[1] = sn_stop_point;
            return dArr2;
        }
        double sn_start_point3 = -90.0d;
        if (south > 0.0d) {
            sn_start_point3 = 0.0d;
        }
        double sn_stop_point2 = 90.0d;
        if (north < 0.0d) {
            sn_stop_point2 = 0.0d;
        }
        for (int xx = 2; xx <= zoom; xx++) {
            double inc = getIncrementor(xx);
            while (sn_start_point3 < south - inc) {
                sn_start_point3 += inc;
                if (DEBUG) {
                    PrintStream printStream = System.out;
                    new StringBuilder();
                    printStream.println(sb2.append("south ").append(sn_start_point3).toString());
                }
            }
            while (sn_stop_point2 > north + inc) {
                sn_stop_point2 -= inc;
                if (DEBUG) {
                    PrintStream printStream2 = System.out;
                    new StringBuilder();
                    printStream2.println(sb.append("north ").append(sn_stop_point2).toString());
                }
            }
        }
        double[] dArr3 = new double[2];
        dArr3[0] = sn_start_point3;
        double[] dArr4 = dArr3;
        dArr4[1] = sn_stop_point2;
        return dArr4;
    }

    private static double[] getStartEndPointsWE(double d, double d2, int i) {
        StringBuilder sb;
        StringBuilder sb2;
        double x;
        double west = d;
        double east = d2;
        int zoom = i;
        double incrementor = getIncrementor(zoom);
        if (zoom < 10) {
            double d3 = 180.0d;
            while (true) {
                x = d3;
                if (x <= Math.floor(west)) {
                    break;
                }
                d3 = x - incrementor;
            }
            double we_startpoint = x;
            double ws_stoppoint = Math.ceil(east);
            double d4 = -180.0d;
            while (true) {
                double x2 = d4;
                if (x2 >= ws_stoppoint) {
                    break;
                }
                d4 = x2 + incrementor;
            }
            if (we_startpoint < -180.0d) {
                we_startpoint = -180.0d;
            }
            if (ws_stoppoint > 180.0d) {
                ws_stoppoint = 180.0d;
            }
            double[] dArr = new double[2];
            dArr[0] = ws_stoppoint;
            double[] dArr2 = dArr;
            dArr2[1] = we_startpoint;
            return dArr2;
        }
        double west_start_point = -180.0d;
        if (west > 0.0d) {
            west_start_point = 0.0d;
        }
        double easter_stop_point = 180.0d;
        if (east < 0.0d) {
            easter_stop_point = 0.0d;
        }
        for (int xx = 2; xx <= zoom; xx++) {
            double inc = getIncrementor(xx);
            while (easter_stop_point > east + inc) {
                easter_stop_point -= inc;
            }
            while (west_start_point < west - inc) {
                west_start_point += inc;
                if (DEBUG) {
                    PrintStream printStream = System.out;
                    new StringBuilder();
                    printStream.println(sb2.append("west ").append(west_start_point).toString());
                }
            }
        }
        if (DEBUG) {
            PrintStream printStream2 = System.out;
            new StringBuilder();
            printStream2.println(sb.append("return EW set as ").append(west_start_point).append(" ").append(easter_stop_point).toString());
        }
        double[] dArr3 = new double[2];
        dArr3[0] = easter_stop_point;
        double[] dArr4 = dArr3;
        dArr4[1] = west_start_point;
        return dArr4;
    }

    private static double getIncrementor(int zoom) {
        switch (zoom) {
            case 0:
            case 1:
                return 30.0d * ((double) multiplier);
            case 2:
                return 15.0d * ((double) multiplier);
            case 3:
                return 9.0d * ((double) multiplier);
            case 4:
                return 6.0d * ((double) multiplier);
            case 5:
                return 3.0d * ((double) multiplier);
            case 6:
                return 2.0d * ((double) multiplier);
            case 7:
                return 1.0d * ((double) multiplier);
            case 8:
                return 0.5d * ((double) multiplier);
            case 9:
                return 0.25d * ((double) multiplier);
            case 10:
                return 0.1d * ((double) multiplier);
            case 11:
                return 0.05d * ((double) multiplier);
            case 12:
                return 0.025d * ((double) multiplier);
            case 13:
                return 0.0125d * ((double) multiplier);
            case 14:
                return 0.00625d * ((double) multiplier);
            case 15:
                return 0.003125d * ((double) multiplier);
            case 16:
                return 0.0015625d * ((double) multiplier);
            case 17:
                return 7.8125E-4d * ((double) multiplier);
            case 18:
                return 3.90625E-4d * ((double) multiplier);
            case 19:
                return 1.953125E-4d * ((double) multiplier);
            case 20:
                return 9.765625E-5d * ((double) multiplier);
            case 21:
                return 4.8828125E-5d * ((double) multiplier);
            default:
                return 2.44140625E-5d * ((double) multiplier);
        }
    }

    public static void setDefaults() {
        lineColor = -16777216;
        fontColor = -1;
        backgroundColor = -16777216;
        lineWidth = 1.0f;
        fontSizeDp = 32;
        DEBUG = false;
        DEBUG2 = false;
    }
}
