package org.locationtech.jts.noding;

import java.util.Collection;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.TopologyException;

public class IteratedNoder implements Noder {
    public static final int MAX_ITER = 5;

    /* renamed from: li */
    private LineIntersector f465li;
    private int maxIter = 5;
    private Collection nodedSegStrings;

    /* renamed from: pm */
    private PrecisionModel f466pm;

    public IteratedNoder(PrecisionModel precisionModel) {
        LineIntersector lineIntersector;
        PrecisionModel pm = precisionModel;
        new RobustLineIntersector();
        this.f465li = lineIntersector;
        this.f466pm = pm;
        this.f465li.setPrecisionModel(pm);
    }

    public void setMaximumIterations(int maxIter2) {
        int i = maxIter2;
        this.maxIter = i;
    }

    public Collection getNodedSubstrings() {
        return this.nodedSegStrings;
    }

    public void computeNodes(Collection segStrings) throws TopologyException {
        Throwable th;
        StringBuilder sb;
        int[] numInteriorIntersections = new int[1];
        this.nodedSegStrings = segStrings;
        int nodingIterationCount = 0;
        int lastNodesCreated = -1;
        do {
            node(this.nodedSegStrings, numInteriorIntersections);
            nodingIterationCount++;
            int nodesCreated = numInteriorIntersections[0];
            if (lastNodesCreated <= 0 || nodesCreated < lastNodesCreated || nodingIterationCount <= this.maxIter) {
                lastNodesCreated = nodesCreated;
            } else {
                Throwable th2 = th;
                new StringBuilder();
                new TopologyException(sb.append("Iterated noding failed to converge after ").append(nodingIterationCount).append(" iterations").toString());
                throw th2;
            }
        } while (lastNodesCreated > 0);
    }

    private void node(Collection segStrings, int[] numInteriorIntersections) {
        IntersectionAdder intersectionAdder;
        MCIndexNoder mCIndexNoder;
        new IntersectionAdder(this.f465li);
        IntersectionAdder si = intersectionAdder;
        new MCIndexNoder();
        MCIndexNoder noder = mCIndexNoder;
        noder.setSegmentIntersector(si);
        noder.computeNodes(segStrings);
        this.nodedSegStrings = noder.getNodedSubstrings();
        numInteriorIntersections[0] = si.numInteriorIntersections;
    }
}
