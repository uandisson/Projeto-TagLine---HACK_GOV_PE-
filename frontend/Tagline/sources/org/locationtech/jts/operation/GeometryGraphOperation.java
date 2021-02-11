package org.locationtech.jts.operation;

import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geomgraph.GeometryGraph;

public class GeometryGraphOperation {
    protected GeometryGraph[] arg;

    /* renamed from: li */
    protected final LineIntersector f478li;
    protected PrecisionModel resultPrecisionModel;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public GeometryGraphOperation(Geometry g0, Geometry g1) {
        this(g0, g1, BoundaryNodeRule.OGC_SFS_BOUNDARY_RULE);
    }

    public GeometryGraphOperation(Geometry geometry, Geometry geometry2, BoundaryNodeRule boundaryNodeRule) {
        LineIntersector lineIntersector;
        GeometryGraph geometryGraph;
        GeometryGraph geometryGraph2;
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        BoundaryNodeRule boundaryNodeRule2 = boundaryNodeRule;
        new RobustLineIntersector();
        this.f478li = lineIntersector;
        if (g0.getPrecisionModel().compareTo(g1.getPrecisionModel()) >= 0) {
            setComputationPrecision(g0.getPrecisionModel());
        } else {
            setComputationPrecision(g1.getPrecisionModel());
        }
        this.arg = new GeometryGraph[2];
        new GeometryGraph(0, g0, boundaryNodeRule2);
        this.arg[0] = geometryGraph;
        new GeometryGraph(1, g1, boundaryNodeRule2);
        this.arg[1] = geometryGraph2;
    }

    public GeometryGraphOperation(Geometry geometry) {
        LineIntersector lineIntersector;
        GeometryGraph geometryGraph;
        Geometry g0 = geometry;
        new RobustLineIntersector();
        this.f478li = lineIntersector;
        setComputationPrecision(g0.getPrecisionModel());
        this.arg = new GeometryGraph[1];
        new GeometryGraph(0, g0);
        this.arg[0] = geometryGraph;
    }

    public Geometry getArgGeometry(int i) {
        return this.arg[i].getGeometry();
    }

    /* access modifiers changed from: protected */
    public void setComputationPrecision(PrecisionModel pm) {
        this.resultPrecisionModel = pm;
        this.f478li.setPrecisionModel(this.resultPrecisionModel);
    }
}
