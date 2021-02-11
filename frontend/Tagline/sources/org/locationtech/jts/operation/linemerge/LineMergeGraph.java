package org.locationtech.jts.operation.linemerge;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.planargraph.Edge;
import org.locationtech.jts.planargraph.Node;
import org.locationtech.jts.planargraph.PlanarGraph;

public class LineMergeGraph extends PlanarGraph {
    public LineMergeGraph() {
    }

    public void addEdge(LineString lineString) {
        DirectedEdge directedEdge0;
        DirectedEdge directedEdge1;
        Edge edge;
        LineString lineString2 = lineString;
        if (!lineString2.isEmpty()) {
            Coordinate[] coordinates = CoordinateArrays.removeRepeatedPoints(lineString2.getCoordinates());
            if (coordinates.length > 1) {
                Coordinate startCoordinate = coordinates[0];
                Coordinate endCoordinate = coordinates[coordinates.length - 1];
                Node startNode = getNode(startCoordinate);
                Node endNode = getNode(endCoordinate);
                new LineMergeDirectedEdge(startNode, endNode, coordinates[1], true);
                new LineMergeDirectedEdge(endNode, startNode, coordinates[coordinates.length - 2], false);
                new LineMergeEdge(lineString2);
                Edge edge2 = edge;
                edge2.setDirectedEdges(directedEdge0, directedEdge1);
                add(edge2);
            }
        }
    }

    private Node getNode(Coordinate coordinate) {
        Node node;
        Coordinate coordinate2 = coordinate;
        Node node2 = findNode(coordinate2);
        if (node2 == null) {
            new Node(coordinate2);
            node2 = node;
            add(node2);
        }
        return node2;
    }
}
