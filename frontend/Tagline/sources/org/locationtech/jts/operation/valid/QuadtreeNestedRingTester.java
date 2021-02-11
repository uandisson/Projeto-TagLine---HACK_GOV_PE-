package org.locationtech.jts.operation.valid;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.index.quadtree.Quadtree;
import org.locationtech.jts.util.Assert;

public class QuadtreeNestedRingTester {
    private GeometryGraph graph;
    private Coordinate nestedPt;
    private Quadtree quadtree;
    private List rings;
    private Envelope totalEnv;

    public QuadtreeNestedRingTester(GeometryGraph graph2) {
        List list;
        Envelope envelope;
        new ArrayList();
        this.rings = list;
        new Envelope();
        this.totalEnv = envelope;
        this.graph = graph2;
    }

    public Coordinate getNestedPoint() {
        return this.nestedPt;
    }

    public void add(LinearRing linearRing) {
        LinearRing ring = linearRing;
        boolean add = this.rings.add(ring);
        this.totalEnv.expandToInclude(ring.getEnvelopeInternal());
    }

    public boolean isNonNested() {
        buildQuadtree();
        for (int i = 0; i < this.rings.size(); i++) {
            LinearRing innerRing = (LinearRing) this.rings.get(i);
            Coordinate[] innerRingPts = innerRing.getCoordinates();
            List results = this.quadtree.query(innerRing.getEnvelopeInternal());
            for (int j = 0; j < results.size(); j++) {
                LinearRing searchRing = (LinearRing) results.get(j);
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

    private void buildQuadtree() {
        Quadtree quadtree2;
        new Quadtree();
        this.quadtree = quadtree2;
        for (int i = 0; i < this.rings.size(); i++) {
            LinearRing ring = (LinearRing) this.rings.get(i);
            this.quadtree.insert(ring.getEnvelopeInternal(), ring);
        }
    }
}
