package org.locationtech.jts.noding;

import java.util.Collection;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.TopologyException;
import org.locationtech.jts.p006io.WKTWriter;

public class FastNodingValidator {
    private boolean findAllIntersections = false;
    private boolean isValid = true;

    /* renamed from: li */
    private LineIntersector f460li;
    private InteriorIntersectionFinder segInt = null;
    private Collection segStrings;

    public static List computeIntersections(Collection segStrings2) {
        FastNodingValidator fastNodingValidator;
        new FastNodingValidator(segStrings2);
        FastNodingValidator nv = fastNodingValidator;
        nv.setFindAllIntersections(true);
        boolean isValid2 = nv.isValid();
        return nv.getIntersections();
    }

    public FastNodingValidator(Collection segStrings2) {
        LineIntersector lineIntersector;
        new RobustLineIntersector();
        this.f460li = lineIntersector;
        this.segStrings = segStrings2;
    }

    public void setFindAllIntersections(boolean findAllIntersections2) {
        boolean z = findAllIntersections2;
        this.findAllIntersections = z;
    }

    public List getIntersections() {
        return this.segInt.getIntersections();
    }

    public boolean isValid() {
        execute();
        return this.isValid;
    }

    public String getErrorMessage() {
        StringBuilder sb;
        if (this.isValid) {
            return "no intersections found";
        }
        Coordinate[] intSegs = this.segInt.getIntersectionSegments();
        new StringBuilder();
        return sb.append("found non-noded intersection between ").append(WKTWriter.toLineString(intSegs[0], intSegs[1])).append(" and ").append(WKTWriter.toLineString(intSegs[2], intSegs[3])).toString();
    }

    public void checkValid() {
        Throwable th;
        execute();
        if (!this.isValid) {
            Throwable th2 = th;
            new TopologyException(getErrorMessage(), this.segInt.getInteriorIntersection());
            throw th2;
        }
    }

    private void execute() {
        if (this.segInt == null) {
            checkInteriorIntersections();
        }
    }

    private void checkInteriorIntersections() {
        InteriorIntersectionFinder interiorIntersectionFinder;
        MCIndexNoder mCIndexNoder;
        this.isValid = true;
        new InteriorIntersectionFinder(this.f460li);
        this.segInt = interiorIntersectionFinder;
        this.segInt.setFindAllIntersections(this.findAllIntersections);
        new MCIndexNoder();
        MCIndexNoder noder = mCIndexNoder;
        noder.setSegmentIntersector(this.segInt);
        noder.computeNodes(this.segStrings);
        if (this.segInt.hasIntersection()) {
            this.isValid = false;
        }
    }
}
