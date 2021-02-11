package org.locationtech.jts.noding.snapround;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.util.LinearComponentExtracter;
import org.locationtech.jts.noding.NodedSegmentString;
import org.locationtech.jts.noding.Noder;
import org.locationtech.jts.noding.NodingValidator;
import org.locationtech.jts.noding.SegmentString;

public class GeometryNoder {
    private GeometryFactory geomFact;
    private boolean isValidityChecked = false;

    /* renamed from: pm */
    private PrecisionModel f471pm;

    public GeometryNoder(PrecisionModel pm) {
        this.f471pm = pm;
    }

    public void setValidate(boolean isValidityChecked2) {
        boolean z = isValidityChecked2;
        this.isValidityChecked = z;
    }

    public List node(Collection collection) {
        Noder noder;
        NodingValidator nv;
        Collection geoms = collection;
        this.geomFact = ((Geometry) geoms.iterator().next()).getFactory();
        List segStrings = toSegmentStrings(extractLines(geoms));
        new MCIndexSnapRounder(this.f471pm);
        Noder sr = noder;
        sr.computeNodes(segStrings);
        Collection nodedLines = sr.getNodedSubstrings();
        if (this.isValidityChecked) {
            new NodingValidator(nodedLines);
            nv.checkValid();
        }
        return toLineStrings(nodedLines);
    }

    private List toLineStrings(Collection segStrings) {
        List list;
        new ArrayList();
        List lines = list;
        Iterator it = segStrings.iterator();
        while (it.hasNext()) {
            SegmentString ss = (SegmentString) it.next();
            if (ss.size() >= 2) {
                boolean add = lines.add(this.geomFact.createLineString(ss.getCoordinates()));
            }
        }
        return lines;
    }

    private List extractLines(Collection geoms) {
        List list;
        GeometryComponentFilter geometryComponentFilter;
        new ArrayList();
        List lines = list;
        new LinearComponentExtracter(lines);
        GeometryComponentFilter geometryComponentFilter2 = geometryComponentFilter;
        Iterator it = geoms.iterator();
        while (it.hasNext()) {
            ((Geometry) it.next()).apply(geometryComponentFilter2);
        }
        return lines;
    }

    private List toSegmentStrings(Collection lines) {
        List list;
        Object obj;
        new ArrayList();
        List segStrings = list;
        Iterator it = lines.iterator();
        while (it.hasNext()) {
            new NodedSegmentString(((LineString) it.next()).getCoordinates(), (Object) null);
            boolean add = segStrings.add(obj);
        }
        return segStrings;
    }
}
