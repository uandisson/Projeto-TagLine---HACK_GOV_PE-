package org.locationtech.jts.operation.linemerge;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.planargraph.Edge;

public class LineMergeEdge extends Edge {
    private LineString line;

    public LineMergeEdge(LineString line2) {
        this.line = line2;
    }

    public LineString getLine() {
        return this.line;
    }
}
