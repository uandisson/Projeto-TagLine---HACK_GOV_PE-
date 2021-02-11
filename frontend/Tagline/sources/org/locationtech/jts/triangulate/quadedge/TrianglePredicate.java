package org.locationtech.jts.triangulate.quadedge;

import java.io.PrintStream;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Triangle;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.math.C1564DD;
import org.locationtech.jts.p006io.WKTWriter;

public class TrianglePredicate {
    public TrianglePredicate() {
    }

    public static boolean isInCircleNonRobust(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        Coordinate p = coordinate4;
        return (((((a.f412x * a.f412x) + (a.f413y * a.f413y)) * triArea(b, c, p)) - (((b.f412x * b.f412x) + (b.f413y * b.f413y)) * triArea(a, c, p))) + (((c.f412x * c.f412x) + (c.f413y * c.f413y)) * triArea(a, b, p))) - (((p.f412x * p.f412x) + (p.f413y * p.f413y)) * triArea(a, b, c)) > 0.0d;
    }

    public static boolean isInCircleNormalized(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        Coordinate p = coordinate4;
        double adx = a.f412x - p.f412x;
        double ady = a.f413y - p.f413y;
        double bdx = b.f412x - p.f412x;
        double bdy = b.f413y - p.f413y;
        double cdx = c.f412x - p.f412x;
        double cdy = c.f413y - p.f413y;
        return ((((adx * adx) + (ady * ady)) * ((bdx * cdy) - (cdx * bdy))) + (((bdx * bdx) + (bdy * bdy)) * ((cdx * ady) - (adx * cdy)))) + (((cdx * cdx) + (cdy * cdy)) * ((adx * bdy) - (bdx * ady))) > 0.0d;
    }

    private static double triArea(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        return ((b.f412x - a.f412x) * (c.f413y - a.f413y)) - ((b.f413y - a.f413y) * (c.f412x - a.f412x));
    }

    public static boolean isInCircleRobust(Coordinate a, Coordinate b, Coordinate c, Coordinate p) {
        return isInCircleNormalized(a, b, c, p);
    }

    public static boolean isInCircleDDSlow(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        Coordinate p = coordinate4;
        C1564DD px = C1564DD.valueOf(p.f412x);
        C1564DD py = C1564DD.valueOf(p.f413y);
        C1564DD ax = C1564DD.valueOf(a.f412x);
        C1564DD ay = C1564DD.valueOf(a.f413y);
        C1564DD bx = C1564DD.valueOf(b.f412x);
        C1564DD by = C1564DD.valueOf(b.f413y);
        C1564DD cx = C1564DD.valueOf(c.f412x);
        C1564DD cy = C1564DD.valueOf(c.f413y);
        return ax.multiply(ax).add(ay.multiply(ay)).multiply(triAreaDDSlow(bx, by, cx, cy, px, py)).subtract(bx.multiply(bx).add(by.multiply(by)).multiply(triAreaDDSlow(ax, ay, cx, cy, px, py))).add(cx.multiply(cx).add(cy.multiply(cy)).multiply(triAreaDDSlow(ax, ay, bx, by, px, py))).subtract(px.multiply(px).add(py.multiply(py)).multiply(triAreaDDSlow(ax, ay, bx, by, cx, cy))).doubleValue() > 0.0d;
    }

    public static C1564DD triAreaDDSlow(C1564DD dd, C1564DD dd2, C1564DD bx, C1564DD by, C1564DD cx, C1564DD cy) {
        C1564DD ax = dd;
        C1564DD ay = dd2;
        return bx.subtract(ax).multiply(cy.subtract(ay)).subtract(by.subtract(ay).multiply(cx.subtract(ax)));
    }

    public static boolean isInCircleDDFast(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        Coordinate p = coordinate4;
        return C1564DD.sqr(a.f412x).selfAdd(C1564DD.sqr(a.f413y)).selfMultiply(triAreaDDFast(b, c, p)).selfSubtract(C1564DD.sqr(b.f412x).selfAdd(C1564DD.sqr(b.f413y)).selfMultiply(triAreaDDFast(a, c, p))).selfAdd(C1564DD.sqr(c.f412x).selfAdd(C1564DD.sqr(c.f413y)).selfMultiply(triAreaDDFast(a, b, p))).selfSubtract(C1564DD.sqr(p.f412x).selfAdd(C1564DD.sqr(p.f413y)).selfMultiply(triAreaDDFast(a, b, c))).doubleValue() > 0.0d;
    }

    public static C1564DD triAreaDDFast(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        return C1564DD.valueOf(b.f412x).selfSubtract(a.f412x).selfMultiply(C1564DD.valueOf(c.f413y).selfSubtract(a.f413y)).selfSubtract(C1564DD.valueOf(b.f413y).selfSubtract(a.f413y).selfMultiply(C1564DD.valueOf(c.f412x).selfSubtract(a.f412x)));
    }

    public static boolean isInCircleDDNormalized(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        Coordinate p = coordinate4;
        C1564DD adx = C1564DD.valueOf(a.f412x).selfSubtract(p.f412x);
        C1564DD ady = C1564DD.valueOf(a.f413y).selfSubtract(p.f413y);
        C1564DD bdx = C1564DD.valueOf(b.f412x).selfSubtract(p.f412x);
        C1564DD bdy = C1564DD.valueOf(b.f413y).selfSubtract(p.f413y);
        C1564DD cdx = C1564DD.valueOf(c.f412x).selfSubtract(p.f412x);
        C1564DD cdy = C1564DD.valueOf(c.f413y).selfSubtract(p.f413y);
        C1564DD abdet = adx.multiply(bdy).selfSubtract(bdx.multiply(ady));
        return adx.multiply(adx).selfAdd(ady.multiply(ady)).selfMultiply(bdx.multiply(cdy).selfSubtract(cdx.multiply(bdy))).selfAdd(bdx.multiply(bdx).selfAdd(bdy.multiply(bdy)).selfMultiply(cdx.multiply(ady).selfSubtract(adx.multiply(cdy)))).selfAdd(cdx.multiply(cdx).selfAdd(cdy.multiply(cdy)).selfMultiply(abdet)).doubleValue() > 0.0d;
    }

    public static boolean isInCircleCC(Coordinate coordinate, Coordinate b, Coordinate c, Coordinate p) {
        Coordinate a = coordinate;
        Coordinate cc = Triangle.circumcentre(a, b, c);
        return p.distance(cc) - a.distance(cc) <= 0.0d;
    }

    private static void checkRobustInCircle(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        StringBuilder sb;
        StringBuilder sb2;
        CoordinateSequence coordinateSequence;
        StringBuilder sb3;
        StringBuilder sb4;
        StringBuilder sb5;
        StringBuilder sb6;
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        Coordinate p = coordinate4;
        boolean nonRobustInCircle = isInCircleNonRobust(a, b, c, p);
        boolean isInCircleDD = isInCircleDDSlow(a, b, c, p);
        boolean isInCircleCC = isInCircleCC(a, b, c, p);
        Coordinate circumCentre = Triangle.circumcentre(a, b, c);
        PrintStream printStream = System.out;
        new StringBuilder();
        printStream.println(sb.append("p radius diff a = ").append(Math.abs(p.distance(circumCentre) - a.distance(circumCentre)) / a.distance(circumCentre)).toString());
        if (nonRobustInCircle != isInCircleDD || nonRobustInCircle != isInCircleCC) {
            PrintStream printStream2 = System.out;
            new StringBuilder();
            printStream2.println(sb2.append("inCircle robustness failure (double result = ").append(nonRobustInCircle).append(", DD result = ").append(isInCircleDD).append(", CC result = ").append(isInCircleCC).append(")").toString());
            PrintStream printStream3 = System.out;
            CoordinateSequence coordinateSequence2 = coordinateSequence;
            Coordinate[] coordinateArr = new Coordinate[4];
            coordinateArr[0] = a;
            Coordinate[] coordinateArr2 = coordinateArr;
            coordinateArr2[1] = b;
            Coordinate[] coordinateArr3 = coordinateArr2;
            coordinateArr3[2] = c;
            Coordinate[] coordinateArr4 = coordinateArr3;
            coordinateArr4[3] = p;
            new CoordinateArraySequence(coordinateArr4);
            printStream3.println(WKTWriter.toLineString(coordinateSequence2));
            PrintStream printStream4 = System.out;
            new StringBuilder();
            printStream4.println(sb3.append("Circumcentre = ").append(WKTWriter.toPoint(circumCentre)).append(" radius = ").append(a.distance(circumCentre)).toString());
            PrintStream printStream5 = System.out;
            new StringBuilder();
            printStream5.println(sb4.append("p radius diff a = ").append(Math.abs((p.distance(circumCentre) / a.distance(circumCentre)) - 1.0d)).toString());
            PrintStream printStream6 = System.out;
            new StringBuilder();
            printStream6.println(sb5.append("p radius diff b = ").append(Math.abs((p.distance(circumCentre) / b.distance(circumCentre)) - 1.0d)).toString());
            PrintStream printStream7 = System.out;
            new StringBuilder();
            printStream7.println(sb6.append("p radius diff c = ").append(Math.abs((p.distance(circumCentre) / c.distance(circumCentre)) - 1.0d)).toString());
            System.out.println();
        }
    }
}
