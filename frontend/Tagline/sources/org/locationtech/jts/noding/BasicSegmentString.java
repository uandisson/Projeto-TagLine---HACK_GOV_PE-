package org.locationtech.jts.noding;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.p006io.WKTWriter;

public class BasicSegmentString implements SegmentString {
    private Object data;
    private Coordinate[] pts;

    public BasicSegmentString(Coordinate[] pts2, Object data2) {
        this.pts = pts2;
        this.data = data2;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data2) {
        Object obj = data2;
        this.data = obj;
    }

    public int size() {
        return this.pts.length;
    }

    public Coordinate getCoordinate(int i) {
        return this.pts[i];
    }

    public Coordinate[] getCoordinates() {
        return this.pts;
    }

    public boolean isClosed() {
        return this.pts[0].equals(this.pts[this.pts.length - 1]);
    }

    public int getSegmentOctant(int i) {
        int index = i;
        if (index == this.pts.length - 1) {
            return -1;
        }
        return Octant.octant(getCoordinate(index), getCoordinate(index + 1));
    }

    public String toString() {
        CoordinateSequence coordinateSequence;
        new CoordinateArraySequence(this.pts);
        return WKTWriter.toLineString(coordinateSequence);
    }
}
