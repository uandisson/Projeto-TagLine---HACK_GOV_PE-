package org.locationtech.jts.operation.linemerge;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

public class EdgeString {
    private Coordinate[] coordinates = null;
    private List directedEdges;
    private GeometryFactory factory;

    public EdgeString(GeometryFactory factory2) {
        List list;
        new ArrayList();
        this.directedEdges = list;
        this.factory = factory2;
    }

    public void add(LineMergeDirectedEdge directedEdge) {
        boolean add = this.directedEdges.add(directedEdge);
    }

    private Coordinate[] getCoordinates() {
        CoordinateList coordinateList;
        if (this.coordinates == null) {
            int forwardDirectedEdges = 0;
            int reverseDirectedEdges = 0;
            new CoordinateList();
            CoordinateList coordinateList2 = coordinateList;
            for (LineMergeDirectedEdge directedEdge : this.directedEdges) {
                if (directedEdge.getEdgeDirection()) {
                    forwardDirectedEdges++;
                } else {
                    reverseDirectedEdges++;
                }
                boolean add = coordinateList2.add(((LineMergeEdge) directedEdge.getEdge()).getLine().getCoordinates(), false, directedEdge.getEdgeDirection());
            }
            this.coordinates = coordinateList2.toCoordinateArray();
            if (reverseDirectedEdges > forwardDirectedEdges) {
                CoordinateArrays.reverse(this.coordinates);
            }
        }
        return this.coordinates;
    }

    public LineString toLineString() {
        return this.factory.createLineString(getCoordinates());
    }
}
