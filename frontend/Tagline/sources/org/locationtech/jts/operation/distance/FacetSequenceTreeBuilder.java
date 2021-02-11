package org.locationtech.jts.operation.distance;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.index.strtree.STRtree;

public class FacetSequenceTreeBuilder {
    private static final int FACET_SEQUENCE_SIZE = 6;
    private static final int STR_TREE_NODE_CAPACITY = 4;

    public FacetSequenceTreeBuilder() {
    }

    public static STRtree build(Geometry g) {
        STRtree sTRtree;
        new STRtree(4);
        STRtree tree = sTRtree;
        for (FacetSequence section : computeFacetSequences(g)) {
            tree.insert(section.getEnvelope(), section);
        }
        tree.build();
        return tree;
    }

    private static List computeFacetSequences(Geometry g) {
        List list;
        GeometryComponentFilter geometryComponentFilter;
        new ArrayList();
        List sections = list;
        final List list2 = sections;
        new GeometryComponentFilter() {
            public void filter(Geometry geometry) {
                Geometry geom = geometry;
                if (geom instanceof LineString) {
                    FacetSequenceTreeBuilder.addFacetSequences(((LineString) geom).getCoordinateSequence(), list2);
                } else if (geom instanceof Point) {
                    FacetSequenceTreeBuilder.addFacetSequences(((Point) geom).getCoordinateSequence(), list2);
                }
            }
        };
        g.apply(geometryComponentFilter);
        return sections;
    }

    /* access modifiers changed from: private */
    public static void addFacetSequences(CoordinateSequence coordinateSequence, List list) {
        Object obj;
        CoordinateSequence pts = coordinateSequence;
        List sections = list;
        int size = pts.size();
        for (int i = 0; i <= size - 1; i += 6) {
            int end = i + 6 + 1;
            if (end >= size - 1) {
                end = size;
            }
            new FacetSequence(pts, i, end);
            boolean add = sections.add(obj);
        }
    }
}
