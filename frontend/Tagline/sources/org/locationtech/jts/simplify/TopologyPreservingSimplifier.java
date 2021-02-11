package org.locationtech.jts.simplify;

import java.util.HashMap;
import java.util.Map;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.util.GeometryTransformer;

public class TopologyPreservingSimplifier {
    private Geometry inputGeom;
    private TaggedLinesSimplifier lineSimplifier;
    /* access modifiers changed from: private */
    public Map linestringMap;

    public static Geometry simplify(Geometry geom, double distanceTolerance) {
        TopologyPreservingSimplifier topologyPreservingSimplifier;
        new TopologyPreservingSimplifier(geom);
        TopologyPreservingSimplifier tss = topologyPreservingSimplifier;
        tss.setDistanceTolerance(distanceTolerance);
        return tss.getResultGeometry();
    }

    public TopologyPreservingSimplifier(Geometry inputGeom2) {
        TaggedLinesSimplifier taggedLinesSimplifier;
        new TaggedLinesSimplifier();
        this.lineSimplifier = taggedLinesSimplifier;
        this.inputGeom = inputGeom2;
    }

    public void setDistanceTolerance(double d) {
        Throwable th;
        double distanceTolerance = d;
        if (distanceTolerance < 0.0d) {
            Throwable th2 = th;
            new IllegalArgumentException("Tolerance must be non-negative");
            throw th2;
        }
        this.lineSimplifier.setDistanceTolerance(distanceTolerance);
    }

    public Geometry getResultGeometry() {
        Map map;
        GeometryComponentFilter geometryComponentFilter;
        LineStringTransformer lineStringTransformer;
        if (this.inputGeom.isEmpty()) {
            return (Geometry) this.inputGeom.clone();
        }
        new HashMap();
        this.linestringMap = map;
        new LineStringMapBuilderFilter(this);
        this.inputGeom.apply(geometryComponentFilter);
        this.lineSimplifier.simplify(this.linestringMap.values());
        new LineStringTransformer(this);
        return lineStringTransformer.transform(this.inputGeom);
    }

    class LineStringTransformer extends GeometryTransformer {
        final /* synthetic */ TopologyPreservingSimplifier this$0;

        LineStringTransformer(TopologyPreservingSimplifier this$02) {
            this.this$0 = this$02;
        }

        /* access modifiers changed from: protected */
        public CoordinateSequence transformCoordinates(CoordinateSequence coordinateSequence, Geometry geometry) {
            CoordinateSequence coords = coordinateSequence;
            Geometry parent = geometry;
            if (coords.size() == 0) {
                return null;
            }
            if (!(parent instanceof LineString)) {
                return super.transformCoordinates(coords, parent);
            }
            return createCoordinateSequence(((TaggedLineString) this.this$0.linestringMap.get(parent)).getResultCoordinates());
        }
    }

    class LineStringMapBuilderFilter implements GeometryComponentFilter {
        final /* synthetic */ TopologyPreservingSimplifier this$0;

        LineStringMapBuilderFilter(TopologyPreservingSimplifier this$02) {
            this.this$0 = this$02;
        }

        public void filter(Geometry geometry) {
            Object obj;
            Geometry geom = geometry;
            if (geom instanceof LineString) {
                LineString line = (LineString) geom;
                if (!line.isEmpty()) {
                    new TaggedLineString(line, line.isClosed() ? 4 : 2);
                    Object put = this.this$0.linestringMap.put(line, obj);
                }
            }
        }
    }
}
