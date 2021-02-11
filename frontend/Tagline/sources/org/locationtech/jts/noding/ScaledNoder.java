package org.locationtech.jts.noding;

import java.util.Collection;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.util.CollectionUtil;

public class ScaledNoder implements Noder {
    private boolean isScaled;
    private Noder noder;
    private double offsetX;
    private double offsetY;
    private double scaleFactor;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ScaledNoder(Noder noder2, double scaleFactor2) {
        this(noder2, scaleFactor2, 0.0d, 0.0d);
    }

    public ScaledNoder(Noder noder2, double scaleFactor2, double d, double d2) {
        double d3 = d;
        double d4 = d2;
        this.isScaled = false;
        this.noder = noder2;
        this.scaleFactor = scaleFactor2;
        this.isScaled = !isIntegerPrecision();
    }

    public boolean isIntegerPrecision() {
        return this.scaleFactor == 1.0d;
    }

    public Collection getNodedSubstrings() {
        Collection splitSS = this.noder.getNodedSubstrings();
        if (this.isScaled) {
            rescale(splitSS);
        }
        return splitSS;
    }

    public void computeNodes(Collection collection) {
        Collection inputSegStrings = collection;
        Collection intSegStrings = inputSegStrings;
        if (this.isScaled) {
            intSegStrings = scale(inputSegStrings);
        }
        this.noder.computeNodes(intSegStrings);
    }

    private Collection scale(Collection segStrings) {
        CollectionUtil.Function function;
        new CollectionUtil.Function(this) {
            final /* synthetic */ ScaledNoder this$0;

            {
                this.this$0 = this$0;
            }

            public Object execute(Object obj) {
                Object obj2;
                SegmentString ss = (SegmentString) obj;
                new NodedSegmentString(this.this$0.scale(ss.getCoordinates()), ss.getData());
                return obj2;
            }
        };
        return CollectionUtil.transform(segStrings, function);
    }

    /* access modifiers changed from: private */
    public Coordinate[] scale(Coordinate[] coordinateArr) {
        Coordinate coordinate;
        Coordinate[] pts = coordinateArr;
        Coordinate[] roundPts = new Coordinate[pts.length];
        for (int i = 0; i < pts.length; i++) {
            new Coordinate((double) Math.round((pts[i].f412x - this.offsetX) * this.scaleFactor), (double) Math.round((pts[i].f413y - this.offsetY) * this.scaleFactor), pts[i].f414z);
            roundPts[i] = coordinate;
        }
        return CoordinateArrays.removeRepeatedPoints(roundPts);
    }

    private void rescale(Collection segStrings) {
        CollectionUtil.Function function;
        new CollectionUtil.Function(this) {
            final /* synthetic */ ScaledNoder this$0;

            {
                this.this$0 = this$0;
            }

            public Object execute(Object obj) {
                this.this$0.rescale(((SegmentString) obj).getCoordinates());
                return null;
            }
        };
        CollectionUtil.apply(segStrings, function);
    }

    /* access modifiers changed from: private */
    public void rescale(Coordinate[] coordinateArr) {
        Object obj;
        Object obj2;
        Coordinate[] pts = coordinateArr;
        if (pts.length == 2) {
            new Coordinate(pts[0]);
            Object obj3 = obj;
            new Coordinate(pts[1]);
            Object obj4 = obj2;
        }
        for (int i = 0; i < pts.length; i++) {
            pts[i].f412x = (pts[i].f412x / this.scaleFactor) + this.offsetX;
            pts[i].f413y = (pts[i].f413y / this.scaleFactor) + this.offsetY;
        }
        if (pts.length == 2 && pts[0].equals2D(pts[1])) {
            System.out.println(pts);
        }
    }
}
