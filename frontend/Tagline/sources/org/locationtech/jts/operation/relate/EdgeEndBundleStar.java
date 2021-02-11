package org.locationtech.jts.operation.relate;

import java.util.Iterator;
import org.locationtech.jts.geom.IntersectionMatrix;
import org.locationtech.jts.geomgraph.EdgeEnd;
import org.locationtech.jts.geomgraph.EdgeEndStar;

public class EdgeEndBundleStar extends EdgeEndStar {
    public EdgeEndBundleStar() {
    }

    public void insert(EdgeEnd edgeEnd) {
        Object obj;
        EdgeEnd e = edgeEnd;
        EdgeEndBundle eb = (EdgeEndBundle) this.edgeMap.get(e);
        if (eb == null) {
            new EdgeEndBundle(e);
            insertEdgeEnd(e, obj);
            return;
        }
        eb.insert(e);
    }

    /* access modifiers changed from: package-private */
    public void updateIM(IntersectionMatrix intersectionMatrix) {
        IntersectionMatrix im = intersectionMatrix;
        Iterator it = iterator();
        while (it.hasNext()) {
            ((EdgeEndBundle) it.next()).updateIM(im);
        }
    }
}
