package org.locationtech.jts.operation.overlay.validate;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Location;
import org.locationtech.jts.operation.overlay.OverlayOp;
import org.locationtech.jts.operation.overlay.snap.GeometrySnapper;

public class OverlayResultValidator {
    private static final double TOLERANCE = 1.0E-6d;
    private double boundaryDistanceTolerance = TOLERANCE;
    private Geometry[] geom;
    private Coordinate invalidLocation = null;
    private FuzzyPointLocator[] locFinder;
    private int[] location = new int[3];
    private List testCoords;

    public static boolean isValid(Geometry a, Geometry b, int overlayOp, Geometry result) {
        OverlayResultValidator validator;
        new OverlayResultValidator(a, b, result);
        return validator.isValid(overlayOp);
    }

    private static double computeBoundaryDistanceTolerance(Geometry g0, Geometry g1) {
        return Math.min(GeometrySnapper.computeSizeBasedSnapTolerance(g0), GeometrySnapper.computeSizeBasedSnapTolerance(g1));
    }

    public OverlayResultValidator(Geometry geometry, Geometry geometry2, Geometry result) {
        List list;
        FuzzyPointLocator fuzzyPointLocator;
        FuzzyPointLocator fuzzyPointLocator2;
        FuzzyPointLocator fuzzyPointLocator3;
        Geometry a = geometry;
        Geometry b = geometry2;
        new ArrayList();
        this.testCoords = list;
        this.boundaryDistanceTolerance = computeBoundaryDistanceTolerance(a, b);
        Geometry[] geometryArr = new Geometry[3];
        geometryArr[0] = a;
        Geometry[] geometryArr2 = geometryArr;
        geometryArr2[1] = b;
        Geometry[] geometryArr3 = geometryArr2;
        geometryArr3[2] = result;
        this.geom = geometryArr3;
        FuzzyPointLocator[] fuzzyPointLocatorArr = new FuzzyPointLocator[3];
        new FuzzyPointLocator(this.geom[0], this.boundaryDistanceTolerance);
        fuzzyPointLocatorArr[0] = fuzzyPointLocator;
        FuzzyPointLocator[] fuzzyPointLocatorArr2 = fuzzyPointLocatorArr;
        new FuzzyPointLocator(this.geom[1], this.boundaryDistanceTolerance);
        fuzzyPointLocatorArr2[1] = fuzzyPointLocator2;
        FuzzyPointLocator[] fuzzyPointLocatorArr3 = fuzzyPointLocatorArr2;
        new FuzzyPointLocator(this.geom[2], this.boundaryDistanceTolerance);
        fuzzyPointLocatorArr3[2] = fuzzyPointLocator3;
        this.locFinder = fuzzyPointLocatorArr3;
    }

    public boolean isValid(int overlayOp) {
        addTestPts(this.geom[0]);
        addTestPts(this.geom[1]);
        return checkValid(overlayOp);
    }

    public Coordinate getInvalidLocation() {
        return this.invalidLocation;
    }

    private void addTestPts(Geometry g) {
        OffsetPointGenerator ptGen;
        new OffsetPointGenerator(g);
        boolean addAll = this.testCoords.addAll(ptGen.getPoints(5.0d * this.boundaryDistanceTolerance));
    }

    private boolean checkValid(int i) {
        int overlayOp = i;
        for (int i2 = 0; i2 < this.testCoords.size(); i2++) {
            Coordinate pt = (Coordinate) this.testCoords.get(i2);
            if (!checkValid(overlayOp, pt)) {
                this.invalidLocation = pt;
                return false;
            }
        }
        return true;
    }

    private boolean checkValid(int i, Coordinate coordinate) {
        int overlayOp = i;
        Coordinate pt = coordinate;
        this.location[0] = this.locFinder[0].getLocation(pt);
        this.location[1] = this.locFinder[1].getLocation(pt);
        this.location[2] = this.locFinder[2].getLocation(pt);
        if (hasLocation(this.location, 1)) {
            return true;
        }
        return isValidResult(overlayOp, this.location);
    }

    private static boolean hasLocation(int[] iArr, int i) {
        int[] location2 = iArr;
        int loc = i;
        for (int i2 = 0; i2 < 3; i2++) {
            if (location2[i2] == loc) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidResult(int i, int[] iArr) {
        int overlayOp = i;
        int[] location2 = iArr;
        boolean expectedInterior = OverlayOp.isResultOfOp(location2[0], location2[1], overlayOp);
        boolean isValid = !(expectedInterior ^ (location2[2] == 0));
        if (!isValid) {
            reportResult(overlayOp, location2, expectedInterior);
        }
        return isValid;
    }

    private void reportResult(int i, int[] iArr, boolean expectedInterior) {
        StringBuilder sb;
        int i2 = i;
        int[] location2 = iArr;
        PrintStream printStream = System.out;
        new StringBuilder();
        printStream.println(sb.append("Overlay result invalid - A:").append(Location.toLocationSymbol(location2[0])).append(" B:").append(Location.toLocationSymbol(location2[1])).append(" expected:").append(expectedInterior ? 'i' : 'e').append(" actual:").append(Location.toLocationSymbol(location2[2])).toString());
    }
}
