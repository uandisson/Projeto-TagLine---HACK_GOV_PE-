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

public class MCIndexSnapRounder implements Noder {

    /* renamed from: li */
    private LineIntersector f474li;
    private Collection nodedSegStrings;
    private MCIndexNoder noder;

    /* renamed from: pm */
    private final PrecisionModel f475pm;
    private MCIndexPointSnapper pointSnapper;
    private final double scaleFactor;

    public MCIndexSnapRounder(PrecisionModel precisionModel) {
        LineIntersector lineIntersector;
        PrecisionModel pm = precisionModel;
        this.f475pm = pm;
        new RobustLineIntersector();
        this.f474li = lineIntersector;
        this.f474li.setPrecisionModel(pm);
        this.scaleFactor = pm.getScale();
    }

    public Collection getNodedSubstrings() {
        return NodedSegmentString.getNodedSubstrings(this.nodedSegStrings);
    }

    public void computeNodes(Collection collection) {
        MCIndexNoder mCIndexNoder;
        MCIndexPointSnapper mCIndexPointSnapper;
        Collection inputSegmentStrings = collection;
        this.nodedSegStrings = inputSegmentStrings;
        new MCIndexNoder();
        this.noder = mCIndexNoder;
        new MCIndexPointSnapper(this.noder.getIndex());
        this.pointSnapper = mCIndexPointSnapper;
        snapRound(inputSegmentStrings, this.f474li);
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
        computeIntersectionSnaps(findInteriorIntersections(segStrings, li));
        computeVertexSnaps(segStrings);
    }

    private List findInteriorIntersections(Collection segStrings, LineIntersector li) {
        InteriorIntersectionFinderAdder interiorIntersectionFinderAdder;
        new InteriorIntersectionFinderAdder(li);
        InteriorIntersectionFinderAdder intFinderAdder = interiorIntersectionFinderAdder;
        this.noder.setSegmentIntersector(intFinderAdder);
        this.noder.computeNodes(segStrings);
        return intFinderAdder.getInteriorIntersections();
    }

    private void computeIntersectionSnaps(Collection snapPts) {
        HotPixel hotPixel;
        Iterator it = snapPts.iterator();
        while (it.hasNext()) {
            new HotPixel((Coordinate) it.next(), this.scaleFactor, this.f474li);
            boolean snap = this.pointSnapper.snap(hotPixel);
        }
    }

    public void computeVertexSnaps(Collection edges) {
        Iterator i0 = edges.iterator();
        while (i0.hasNext()) {
            computeVertexSnaps((NodedSegmentString) i0.next());
        }
    }

    private void computeVertexSnaps(NodedSegmentString nodedSegmentString) {
        HotPixel hotPixel;
        NodedSegmentString e = nodedSegmentString;
        Coordinate[] pts0 = e.getCoordinates();
        for (int i = 0; i < pts0.length; i++) {
            new HotPixel(pts0[i], this.scaleFactor, this.f474li);
            if (this.pointSnapper.snap(hotPixel, e, i)) {
                e.addIntersection(pts0[i], i);
            }
        }
    }
}
