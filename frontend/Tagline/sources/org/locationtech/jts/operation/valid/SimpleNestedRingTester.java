package org.locationtech.jts.operation.valid;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.util.Assert;

public class SimpleNestedRingTester {
    private GeometryGraph graph;
    private Coordinate nestedPt;
    private List rings;

    public SimpleNestedRingTester(GeometryGraph graph2) {
        List list;
        new ArrayList();
        this.rings = list;
        this.graph = graph2;
    }

    public void add(LinearRing ring) {
        boolean add = this.rings.add(ring);
    }

    public Coordinate getNestedPoint() {
        return this.nestedPt;
    }

    public boolean isNonNested() {
        for (int i = 0; i < this.rings.size(); i++) {
            LinearRing innerRing = (LinearRing) this.rings.get(i);
            Coordinate[] innerRingPts = innerRing.getCoordinates();
            for (int j = 0; j < this.rings.size(); j++) {
                LinearRing searchRing = (LinearRing) this.rings.get(j);
                Coordinate[] searchRingPts = searchRing.getCoordinates();
                if (innerRing != searchRing && innerRing.getEnvelopeInternal().intersects(searchRing.getEnvelopeInternal())) {
                    Coordinate innerRingPt = IsValidOp.findPtNotNode(innerRingPts, searchRing, this.graph);
                    Assert.isTrue(innerRingPt != null, "Unable to find a ring point not a node of the search ring");
                    if (CGAlgorithms.isPointInRing(innerRingPt, searchRingPts)) {
                        this.nestedPt = innerRingPt;
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
