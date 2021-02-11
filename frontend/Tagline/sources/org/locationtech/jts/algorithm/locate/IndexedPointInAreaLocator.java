package org.locationtech.jts.algorithm.locate;

import java.util.List;
import org.locationtech.jts.algorithm.RayCrossingCounter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.util.LinearComponentExtracter;
import org.locationtech.jts.index.ArrayListVisitor;
import org.locationtech.jts.index.ItemVisitor;
import org.locationtech.jts.index.intervalrtree.SortedPackedIntervalRTree;

public class IndexedPointInAreaLocator implements PointOnGeometryLocator {
    private final IntervalIndexedGeometry index;

    public IndexedPointInAreaLocator(Geometry geometry) {
        IntervalIndexedGeometry intervalIndexedGeometry;
        Throwable th;
        Geometry g = geometry;
        if (!(g instanceof Polygonal)) {
            Throwable th2 = th;
            new IllegalArgumentException("Argument must be Polygonal");
            throw th2;
        }
        new IntervalIndexedGeometry(g);
        this.index = intervalIndexedGeometry;
    }

    public int locate(Coordinate coordinate) {
        RayCrossingCounter rayCrossingCounter;
        ItemVisitor itemVisitor;
        Coordinate p = coordinate;
        new RayCrossingCounter(p);
        RayCrossingCounter rcc = rayCrossingCounter;
        new SegmentVisitor(rcc);
        ItemVisitor itemVisitor2 = itemVisitor;
        this.index.query(p.f413y, p.f413y, itemVisitor2);
        return rcc.getLocation();
    }

    private static class SegmentVisitor implements ItemVisitor {
        private RayCrossingCounter counter;

        public SegmentVisitor(RayCrossingCounter counter2) {
            this.counter = counter2;
        }

        public void visitItem(Object item) {
            LineSegment seg = (LineSegment) item;
            this.counter.countSegment(seg.getCoordinate(0), seg.getCoordinate(1));
        }
    }

    private static class IntervalIndexedGeometry {
        private final SortedPackedIntervalRTree index;

        public IntervalIndexedGeometry(Geometry geom) {
            SortedPackedIntervalRTree sortedPackedIntervalRTree;
            new SortedPackedIntervalRTree();
            this.index = sortedPackedIntervalRTree;
            init(geom);
        }

        private void init(Geometry geom) {
            for (LineString line : LinearComponentExtracter.getLines(geom)) {
                addLine(line.getCoordinates());
            }
        }

        private void addLine(Coordinate[] coordinateArr) {
            LineSegment lineSegment;
            Coordinate[] pts = coordinateArr;
            for (int i = 1; i < pts.length; i++) {
                new LineSegment(pts[i - 1], pts[i]);
                LineSegment seg = lineSegment;
                this.index.insert(Math.min(seg.f422p0.f413y, seg.f423p1.f413y), Math.max(seg.f422p0.f413y, seg.f423p1.f413y), seg);
            }
        }

        public List query(double min, double max) {
            ArrayListVisitor arrayListVisitor;
            new ArrayListVisitor();
            ArrayListVisitor visitor = arrayListVisitor;
            this.index.query(min, max, visitor);
            return visitor.getItems();
        }

        public void query(double min, double max, ItemVisitor visitor) {
            this.index.query(min, max, visitor);
        }
    }
}
