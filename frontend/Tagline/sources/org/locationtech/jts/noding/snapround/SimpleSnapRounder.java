package org.locationtech.jts.noding.snapround;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.noding.InteriorIntersectionFinderAdder;
import org.locationtech.jts.noding.MCIndexNoder;
import org.locationtech.jts.noding.NodedSegmentString;
import org.locationtech.jts.noding.Noder;
import org.locationtech.jts.noding.NodingValidator;
import org.locationtech.jts.noding.SinglePassNoder;

public class SimpleSnapRounder implements Noder {

    /* renamed from: li */
    private LineIntersector f476li;
    private Collection nodedSegStrings;

    /* renamed from: pm */
    private final PrecisionModel f477pm;
    private final double scaleFactor;

    public SimpleSnapRounder(PrecisionModel precisionModel) {
        LineIntersector lineIntersector;
        PrecisionModel pm = precisionModel;
        this.f477pm = pm;
        new RobustLineIntersector();
        this.f476li = lineIntersector;
        this.f476li.setPrecisionModel(pm);
        this.scaleFactor = pm.getScale();
    }

    public Collection getNodedSubstrings() {
        return NodedSegmentString.getNodedSubstrings(this.nodedSegStrings);
    }

    public void computeNodes(Collection collection) {
        Collection inputSegmentStrings = collection;
        this.nodedSegStrings = inputSegmentStrings;
        snapRound(inputSegmentStrings, this.f476li);
    }

    private void checkCorrectness(Collection inputSegmentStrings) {
        NodingValidator nv;
        new NodingValidator(NodedSegmentString.getNodedSubstrings(inputSegmentStrings));
        try {
            nv.checkValid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void snapRound(Collection collection, LineIntersector li) {
        Collection segStrings = collection;
        computeSnaps(segStrings, (Collection) findInteriorIntersections(segStrings, li));
        computeVertexSnaps(segStrings);
    }

    private List findInteriorIntersections(Collection segStrings, LineIntersector li) {
        InteriorIntersectionFinderAdder interiorIntersectionFinderAdder;
        SinglePassNoder singlePassNoder;
        new InteriorIntersectionFinderAdder(li);
        InteriorIntersectionFinderAdder intFinderAdder = interiorIntersectionFinderAdder;
        new MCIndexNoder();
        SinglePassNoder noder = singlePassNoder;
        noder.setSegmentIntersector(intFinderAdder);
        noder.computeNodes(segStrings);
        return intFinderAdder.getInteriorIntersections();
    }

    private void computeSnaps(Collection segStrings, Collection collection) {
        Collection snapPts = collection;
        Iterator i0 = segStrings.iterator();
        while (i0.hasNext()) {
            computeSnaps((NodedSegmentString) i0.next(), snapPts);
        }
    }

    private void computeSnaps(NodedSegmentString nodedSegmentString, Collection snapPts) {
        HotPixel hotPixel;
        NodedSegmentString ss = nodedSegmentString;
        Iterator it = snapPts.iterator();
        while (it.hasNext()) {
            new HotPixel((Coordinate) it.next(), this.scaleFactor, this.f476li);
            HotPixel hotPixel2 = hotPixel;
            for (int i = 0; i < ss.size() - 1; i++) {
                boolean addSnappedNode = hotPixel2.addSnappedNode(ss, i);
            }
        }
    }

    public void computeVertexSnaps(Collection collection) {
        Collection<NodedSegmentString> edges = collection;
        for (NodedSegmentString edge0 : edges) {
            for (NodedSegmentString edge1 : edges) {
                computeVertexSnaps(edge0, edge1);
            }
        }
    }

    private void computeVertexSnaps(NodedSegmentString nodedSegmentString, NodedSegmentString nodedSegmentString2) {
        HotPixel hotPixel;
        NodedSegmentString e0 = nodedSegmentString;
        NodedSegmentString e1 = nodedSegmentString2;
        Coordinate[] pts0 = e0.getCoordinates();
        Coordinate[] pts1 = e1.getCoordinates();
        for (int i0 = 0; i0 < pts0.length - 1; i0++) {
            new HotPixel(pts0[i0], this.scaleFactor, this.f476li);
            HotPixel hotPixel2 = hotPixel;
            for (int i1 = 0; i1 < pts1.length - 1; i1++) {
                if (!(e0 == e1 && i0 == i1) && hotPixel2.addSnappedNode(e1, i1)) {
                    e0.addIntersection(pts0[i0], i0);
                }
            }
        }
    }
}
