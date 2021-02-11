package org.locationtech.jts.precision;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateFilter;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFilter;
import org.locationtech.jts.geom.Geometry;

public class CommonBitsRemover {
    private CommonCoordinateFilter ccFilter;
    private Coordinate commonCoord;

    public CommonBitsRemover() {
        CommonCoordinateFilter commonCoordinateFilter;
        new CommonCoordinateFilter(this);
        this.ccFilter = commonCoordinateFilter;
    }

    public void add(Geometry geom) {
        geom.apply((CoordinateFilter) this.ccFilter);
        this.commonCoord = this.ccFilter.getCommonCoordinate();
    }

    public Coordinate getCommonCoordinate() {
        return this.commonCoord;
    }

    public Geometry removeCommonBits(Geometry geometry) {
        Coordinate coordinate;
        CoordinateSequenceFilter coordinateSequenceFilter;
        Geometry geom = geometry;
        if (this.commonCoord.f412x == 0.0d && this.commonCoord.f413y == 0.0d) {
            return geom;
        }
        new Coordinate(this.commonCoord);
        Coordinate invCoord = coordinate;
        invCoord.f412x = -invCoord.f412x;
        invCoord.f413y = -invCoord.f413y;
        new Translater(this, invCoord);
        geom.apply(coordinateSequenceFilter);
        geom.geometryChanged();
        return geom;
    }

    public void addCommonBits(Geometry geometry) {
        CoordinateSequenceFilter coordinateSequenceFilter;
        Geometry geom = geometry;
        new Translater(this, this.commonCoord);
        geom.apply(coordinateSequenceFilter);
        geom.geometryChanged();
    }

    class CommonCoordinateFilter implements CoordinateFilter {
        private CommonBits commonBitsX;
        private CommonBits commonBitsY;
        final /* synthetic */ CommonBitsRemover this$0;

        CommonCoordinateFilter(CommonBitsRemover this$02) {
            CommonBits commonBits;
            CommonBits commonBits2;
            this.this$0 = this$02;
            new CommonBits();
            this.commonBitsX = commonBits;
            new CommonBits();
            this.commonBitsY = commonBits2;
        }

        public void filter(Coordinate coordinate) {
            Coordinate coord = coordinate;
            this.commonBitsX.add(coord.f412x);
            this.commonBitsY.add(coord.f413y);
        }

        public Coordinate getCommonCoordinate() {
            Coordinate coordinate;
            new Coordinate(this.commonBitsX.getCommon(), this.commonBitsY.getCommon());
            return coordinate;
        }
    }

    class Translater implements CoordinateSequenceFilter {
        final /* synthetic */ CommonBitsRemover this$0;
        Coordinate trans = null;

        public Translater(CommonBitsRemover this$02, Coordinate trans2) {
            this.this$0 = this$02;
            this.trans = trans2;
        }

        public void filter(CoordinateSequence coordinateSequence, int i) {
            CoordinateSequence seq = coordinateSequence;
            int i2 = i;
            double xp = seq.getOrdinate(i2, 0) + this.trans.f412x;
            double yp = seq.getOrdinate(i2, 1) + this.trans.f413y;
            seq.setOrdinate(i2, 0, xp);
            seq.setOrdinate(i2, 1, yp);
        }

        public boolean isDone() {
            return false;
        }

        public boolean isGeometryChanged() {
            return true;
        }
    }
}
