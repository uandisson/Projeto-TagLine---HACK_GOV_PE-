package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Lineal;

public class LinearIterator {
    private int componentIndex;
    private LineString currentLine;
    private Geometry linearGeom;
    private final int numLines;
    private int vertexIndex;

    private static int segmentEndVertexIndex(LinearLocation linearLocation) {
        LinearLocation loc = linearLocation;
        if (loc.getSegmentFraction() > 0.0d) {
            return loc.getSegmentIndex() + 1;
        }
        return loc.getSegmentIndex();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public LinearIterator(Geometry linear) {
        this(linear, 0, 0);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LinearIterator(org.locationtech.jts.geom.Geometry r8, org.locationtech.jts.linearref.LinearLocation r9) {
        /*
            r7 = this;
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r0
            r4 = r1
            r5 = r2
            int r5 = r5.getComponentIndex()
            r6 = r2
            int r6 = segmentEndVertexIndex(r6)
            r3.<init>(r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.linearref.LinearIterator.<init>(org.locationtech.jts.geom.Geometry, org.locationtech.jts.linearref.LinearLocation):void");
    }

    public LinearIterator(Geometry geometry, int i, int i2) {
        Throwable th;
        Geometry linearGeom2 = geometry;
        int componentIndex2 = i;
        int vertexIndex2 = i2;
        this.componentIndex = 0;
        this.vertexIndex = 0;
        if (!(linearGeom2 instanceof Lineal)) {
            Throwable th2 = th;
            new IllegalArgumentException("Lineal geometry is required");
            throw th2;
        }
        this.linearGeom = linearGeom2;
        this.numLines = linearGeom2.getNumGeometries();
        this.componentIndex = componentIndex2;
        this.vertexIndex = vertexIndex2;
        loadCurrentLine();
    }

    private void loadCurrentLine() {
        if (this.componentIndex >= this.numLines) {
            this.currentLine = null;
            return;
        }
        this.currentLine = (LineString) this.linearGeom.getGeometryN(this.componentIndex);
    }

    public boolean hasNext() {
        if (this.componentIndex >= this.numLines) {
            return false;
        }
        if (this.componentIndex != this.numLines - 1 || this.vertexIndex < this.currentLine.getNumPoints()) {
            return true;
        }
        return false;
    }

    public void next() {
        if (hasNext()) {
            this.vertexIndex++;
            if (this.vertexIndex >= this.currentLine.getNumPoints()) {
                this.componentIndex++;
                loadCurrentLine();
                this.vertexIndex = 0;
            }
        }
    }

    public boolean isEndOfLine() {
        if (this.componentIndex >= this.numLines) {
            return false;
        }
        if (this.vertexIndex < this.currentLine.getNumPoints() - 1) {
            return false;
        }
        return true;
    }

    public int getComponentIndex() {
        return this.componentIndex;
    }

    public int getVertexIndex() {
        return this.vertexIndex;
    }

    public LineString getLine() {
        return this.currentLine;
    }

    public Coordinate getSegmentStart() {
        return this.currentLine.getCoordinateN(this.vertexIndex);
    }

    public Coordinate getSegmentEnd() {
        if (this.vertexIndex < getLine().getNumPoints() - 1) {
            return this.currentLine.getCoordinateN(this.vertexIndex + 1);
        }
        return null;
    }
}
