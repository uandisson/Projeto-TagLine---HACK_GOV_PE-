package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Geometry;

public class LengthLocationMap {
    private Geometry linearGeom;

    public static LinearLocation getLocation(Geometry linearGeom2, double length) {
        LengthLocationMap locater;
        new LengthLocationMap(linearGeom2);
        return locater.getLocation(length);
    }

    public static LinearLocation getLocation(Geometry linearGeom2, double length, boolean resolveLower) {
        LengthLocationMap locater;
        new LengthLocationMap(linearGeom2);
        return locater.getLocation(length, resolveLower);
    }

    public static double getLength(Geometry linearGeom2, LinearLocation loc) {
        LengthLocationMap locater;
        new LengthLocationMap(linearGeom2);
        return locater.getLength(loc);
    }

    public LengthLocationMap(Geometry linearGeom2) {
        this.linearGeom = linearGeom2;
    }

    public LinearLocation getLocation(double length) {
        return getLocation(length, true);
    }

    public LinearLocation getLocation(double d, boolean z) {
        double length = d;
        boolean resolveLower = z;
        double forwardLength = length;
        if (length < 0.0d) {
            forwardLength = this.linearGeom.getLength() + length;
        }
        LinearLocation loc = getLocationForward(forwardLength);
        if (resolveLower) {
            return loc;
        }
        return resolveHigher(loc);
    }

    private LinearLocation getLocationForward(double d) {
        LinearIterator linearIterator;
        LinearLocation linearLocation;
        LinearLocation linearLocation2;
        LinearLocation linearLocation3;
        double length = d;
        if (length <= 0.0d) {
            new LinearLocation();
            return linearLocation3;
        }
        double totalLength = 0.0d;
        new LinearIterator(this.linearGeom);
        LinearIterator it = linearIterator;
        while (it.hasNext()) {
            if (!it.isEndOfLine()) {
                double segLen = it.getSegmentEnd().distance(it.getSegmentStart());
                if (totalLength + segLen > length) {
                    new LinearLocation(it.getComponentIndex(), it.getVertexIndex(), (length - totalLength) / segLen);
                    return linearLocation;
                }
                totalLength += segLen;
            } else if (totalLength == length) {
                new LinearLocation(it.getComponentIndex(), it.getVertexIndex(), 0.0d);
                return linearLocation2;
            }
            it.next();
        }
        return LinearLocation.getEndLocation(this.linearGeom);
    }

    private LinearLocation resolveHigher(LinearLocation linearLocation) {
        LinearLocation linearLocation2;
        LinearLocation loc = linearLocation;
        if (!loc.isEndpoint(this.linearGeom)) {
            return loc;
        }
        int compIndex = loc.getComponentIndex();
        if (compIndex >= this.linearGeom.getNumGeometries() - 1) {
            return loc;
        }
        do {
            compIndex++;
            if (compIndex >= this.linearGeom.getNumGeometries() - 1 || this.linearGeom.getGeometryN(compIndex).getLength() != 0.0d) {
                new LinearLocation(compIndex, 0, 0.0d);
            }
            compIndex++;
            break;
        } while (this.linearGeom.getGeometryN(compIndex).getLength() != 0.0d);
        new LinearLocation(compIndex, 0, 0.0d);
        return linearLocation2;
    }

    public double getLength(LinearLocation linearLocation) {
        LinearIterator linearIterator;
        LinearLocation loc = linearLocation;
        double totalLength = 0.0d;
        new LinearIterator(this.linearGeom);
        LinearIterator it = linearIterator;
        while (it.hasNext()) {
            if (!it.isEndOfLine()) {
                double segLen = it.getSegmentEnd().distance(it.getSegmentStart());
                if (loc.getComponentIndex() == it.getComponentIndex() && loc.getSegmentIndex() == it.getVertexIndex()) {
                    return totalLength + (segLen * loc.getSegmentFraction());
                }
                totalLength += segLen;
            }
            it.next();
        }
        return totalLength;
    }
}
