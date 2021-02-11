package org.locationtech.jts.operation.polygonize;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.planargraph.Edge;

class PolygonizeEdge extends Edge {
    private LineString line;

    public PolygonizeEdge(LineString line2) {
        this.line = line2;
    }

    public LineString getLine() {
        return this.line;
    }
}
