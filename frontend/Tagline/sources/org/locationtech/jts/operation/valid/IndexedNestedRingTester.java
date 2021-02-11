package org.locationtech.jts.operation.valid;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.index.SpatialIndex;
import org.locationtech.jts.index.strtree.STRtree;

public class IndexedNestedRingTester {
    private GeometryGraph graph;
    private SpatialIndex index;
    private Coordinate nestedPt;
    private List rings;
    private Envelope totalEnv;

    public IndexedNestedRingTester(GeometryGraph graph2) {
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
        buildIndex();
        for (int i = 0; i < this.rings.size(); i++) {
            LinearRing innerRing = (LinearRing) this.rings.get(i);
            Coordinate[] innerRingPts = innerRing.getCoordinates();
            List results = this.index.query(innerRing.getEnvelopeInternal());
            for (int j = 0; j < results.size(); j++) {
                LinearRing searchRing = (LinearRing) results.get(j);
                Coordinate[] searchRingPts = searchRing.getCoordinates();
                if (innerRing != searchRing && innerRing.getEnvelopeInternal().intersects(searchRing.getEnvelopeInternal())) {
                    Coordinate innerRingPt = IsValidOp.findPtNotNode(innerRingPts, searchRing, this.graph);
                    if (innerRingPt != null && CGAlgorithms.isPointInRing(innerRingPt, searchRingPts)) {
                        this.nestedPt = innerRingPt;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void buildIndex() {
        SpatialIndex spatialIndex;
        new STRtree();
        this.index = spatialIndex;
        for (int i = 0; i < this.rings.size(); i++) {
            LinearRing ring = (LinearRing) this.rings.get(i);
            this.index.insert(ring.getEnvelopeInternal(), ring);
        }
    }
}
