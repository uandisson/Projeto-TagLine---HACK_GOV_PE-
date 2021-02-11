package org.locationtech.jts.geomgraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.noding.BasicSegmentString;
import org.locationtech.jts.noding.FastNodingValidator;

public class EdgeNodingValidator {

    /* renamed from: nv */
    private FastNodingValidator f432nv;

    public static void checkValid(Collection edges) {
        EdgeNodingValidator validator;
        new EdgeNodingValidator(edges);
        validator.checkValid();
    }

    public static Collection toSegmentStrings(Collection edges) {
        Collection collection;
        Object obj;
        new ArrayList();
        Collection segStrings = collection;
        Iterator i = edges.iterator();
        while (i.hasNext()) {
            Edge e = (Edge) i.next();
            new BasicSegmentString(e.getCoordinates(), e);
            boolean add = segStrings.add(obj);
        }
        return segStrings;
    }

    public EdgeNodingValidator(Collection edges) {
        FastNodingValidator fastNodingValidator;
        new FastNodingValidator(toSegmentStrings(edges));
        this.f432nv = fastNodingValidator;
    }

    public void checkValid() {
        this.f432nv.checkValid();
    }
}
