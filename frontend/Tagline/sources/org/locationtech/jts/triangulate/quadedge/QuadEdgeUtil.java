package org.locationtech.jts.triangulate.quadedge;

import java.util.ArrayList;
import java.util.List;

public class QuadEdgeUtil {
    public QuadEdgeUtil() {
    }

    public static List findEdgesIncidentOnOrigin(QuadEdge quadEdge) {
        List list;
        QuadEdge start = quadEdge;
        new ArrayList();
        List incEdge = list;
        QuadEdge qe = start;
        do {
            boolean add = incEdge.add(qe);
            qe = qe.oNext();
        } while (qe != start);
        return incEdge;
    }
}
