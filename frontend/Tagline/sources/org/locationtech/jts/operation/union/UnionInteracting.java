package org.locationtech.jts.operation.union;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.util.GeometryCombiner;

public class UnionInteracting {

    /* renamed from: g0 */
    private Geometry f500g0;

    /* renamed from: g1 */
    private Geometry f501g1;
    private GeometryFactory geomFactory;
    private boolean[] interacts0;
    private boolean[] interacts1;

    public static Geometry union(Geometry g0, Geometry g1) {
        UnionInteracting uue;
        new UnionInteracting(g0, g1);
        return uue.union();
    }

    public UnionInteracting(Geometry geometry, Geometry geometry2) {
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        this.f500g0 = g0;
        this.f501g1 = g1;
        this.geomFactory = g0.getFactory();
        this.interacts0 = new boolean[g0.getNumGeometries()];
        this.interacts1 = new boolean[g1.getNumGeometries()];
    }

    public Geometry union() {
        computeInteracting();
        Geometry int0 = extractElements(this.f500g0, this.interacts0, true);
        Geometry int1 = extractElements(this.f501g1, this.interacts1, true);
        if (int0.isEmpty() || int1.isEmpty()) {
            System.out.println("found empty!");
        }
        return GeometryCombiner.combine(int0.union(int1), extractElements(this.f500g0, this.interacts0, false), extractElements(this.f501g1, this.interacts1, false));
    }

    private Geometry bufferUnion(Geometry geometry, Geometry g1) {
        Geometry g0 = geometry;
        Geometry[] geometryArr = new Geometry[2];
        geometryArr[0] = g0;
        Geometry[] geometryArr2 = geometryArr;
        geometryArr2[1] = g1;
        return g0.getFactory().createGeometryCollection(geometryArr2).buffer(0.0d);
    }

    private void computeInteracting() {
        for (int i = 0; i < this.f500g0.getNumGeometries(); i++) {
            this.interacts0[i] = computeInteracting(this.f500g0.getGeometryN(i));
        }
    }

    private boolean computeInteracting(Geometry geometry) {
        Geometry elem0 = geometry;
        boolean interactsWithAny = false;
        for (int i = 0; i < this.f501g1.getNumGeometries(); i++) {
            boolean interacts = this.f501g1.getGeometryN(i).getEnvelopeInternal().intersects(elem0.getEnvelopeInternal());
            if (interacts) {
                this.interacts1[i] = true;
            }
            if (interacts) {
                interactsWithAny = true;
            }
        }
        return interactsWithAny;
    }

    private Geometry extractElements(Geometry geometry, boolean[] zArr, boolean z) {
        List list;
        Geometry geom = geometry;
        boolean[] interacts = zArr;
        boolean isInteracting = z;
        new ArrayList();
        List extractedGeoms = list;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            Geometry elem = geom.getGeometryN(i);
            if (interacts[i] == isInteracting) {
                boolean add = extractedGeoms.add(elem);
            }
        }
        return this.geomFactory.buildGeometry(extractedGeoms);
    }
}
