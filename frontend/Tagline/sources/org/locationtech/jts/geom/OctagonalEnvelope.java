package org.locationtech.jts.geom;

public class OctagonalEnvelope {
    private static double SQRT2 = Math.sqrt(2.0d);
    private double maxA;
    private double maxB;
    private double maxX;
    private double maxY;
    private double minA;
    private double minB;
    private double minX = Double.NaN;
    private double minY;

    public static Geometry octagonalEnvelope(Geometry geometry) {
        OctagonalEnvelope octagonalEnvelope;
        Geometry geom = geometry;
        new OctagonalEnvelope(geom);
        return octagonalEnvelope.toGeometry(geom.getFactory());
    }

    private static double computeA(double x, double y) {
        return x + y;
    }

    private static double computeB(double x, double y) {
        return x - y;
    }

    public OctagonalEnvelope() {
    }

    public OctagonalEnvelope(Coordinate p) {
        OctagonalEnvelope expandToInclude = expandToInclude(p);
    }

    public OctagonalEnvelope(Coordinate p0, Coordinate p1) {
        OctagonalEnvelope expandToInclude = expandToInclude(p0);
        OctagonalEnvelope expandToInclude2 = expandToInclude(p1);
    }

    public OctagonalEnvelope(Envelope env) {
        OctagonalEnvelope expandToInclude = expandToInclude(env);
    }

    public OctagonalEnvelope(OctagonalEnvelope oct) {
        OctagonalEnvelope expandToInclude = expandToInclude(oct);
    }

    public OctagonalEnvelope(Geometry geom) {
        expandToInclude(geom);
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public double getMinA() {
        return this.minA;
    }

    public double getMaxA() {
        return this.maxA;
    }

    public double getMinB() {
        return this.minB;
    }

    public double getMaxB() {
        return this.maxB;
    }

    public boolean isNull() {
        return Double.isNaN(this.minX);
    }

    public void setToNull() {
        this.minX = Double.NaN;
    }

    public void expandToInclude(Geometry g) {
        GeometryComponentFilter geometryComponentFilter;
        new BoundingOctagonComponentFilter(this, (C15431) null);
        g.apply(geometryComponentFilter);
    }

    public OctagonalEnvelope expandToInclude(CoordinateSequence coordinateSequence) {
        CoordinateSequence seq = coordinateSequence;
        for (int i = 0; i < seq.size(); i++) {
            OctagonalEnvelope expandToInclude = expandToInclude(seq.getX(i), seq.getY(i));
        }
        return this;
    }

    public OctagonalEnvelope expandToInclude(OctagonalEnvelope octagonalEnvelope) {
        OctagonalEnvelope oct = octagonalEnvelope;
        if (oct.isNull()) {
            return this;
        } else if (isNull()) {
            this.minX = oct.minX;
            this.maxX = oct.maxX;
            this.minY = oct.minY;
            this.maxY = oct.maxY;
            this.minA = oct.minA;
            this.maxA = oct.maxA;
            this.minB = oct.minB;
            this.maxB = oct.maxB;
            return this;
        } else {
            if (oct.minX < this.minX) {
                this.minX = oct.minX;
            }
            if (oct.maxX > this.maxX) {
                this.maxX = oct.maxX;
            }
            if (oct.minY < this.minY) {
                this.minY = oct.minY;
            }
            if (oct.maxY > this.maxY) {
                this.maxY = oct.maxY;
            }
            if (oct.minA < this.minA) {
                this.minA = oct.minA;
            }
            if (oct.maxA > this.maxA) {
                this.maxA = oct.maxA;
            }
            if (oct.minB < this.minB) {
                this.minB = oct.minB;
            }
            if (oct.maxB > this.maxB) {
                this.maxB = oct.maxB;
            }
            return this;
        }
    }

    public OctagonalEnvelope expandToInclude(Coordinate coordinate) {
        Coordinate p = coordinate;
        OctagonalEnvelope expandToInclude = expandToInclude(p.f412x, p.f413y);
        return this;
    }

    public OctagonalEnvelope expandToInclude(Envelope envelope) {
        Envelope env = envelope;
        OctagonalEnvelope expandToInclude = expandToInclude(env.getMinX(), env.getMinY());
        OctagonalEnvelope expandToInclude2 = expandToInclude(env.getMinX(), env.getMaxY());
        OctagonalEnvelope expandToInclude3 = expandToInclude(env.getMaxX(), env.getMinY());
        OctagonalEnvelope expandToInclude4 = expandToInclude(env.getMaxX(), env.getMaxY());
        return this;
    }

    public OctagonalEnvelope expandToInclude(double d, double d2) {
        double x = d;
        double y = d2;
        double A = computeA(x, y);
        double B = computeB(x, y);
        if (isNull()) {
            this.minX = x;
            this.maxX = x;
            this.minY = y;
            this.maxY = y;
            this.minA = A;
            this.maxA = A;
            this.minB = B;
            this.maxB = B;
        } else {
            if (x < this.minX) {
                this.minX = x;
            }
            if (x > this.maxX) {
                this.maxX = x;
            }
            if (y < this.minY) {
                this.minY = y;
            }
            if (y > this.maxY) {
                this.maxY = y;
            }
            if (A < this.minA) {
                this.minA = A;
            }
            if (A > this.maxA) {
                this.maxA = A;
            }
            if (B < this.minB) {
                this.minB = B;
            }
            if (B > this.maxB) {
                this.maxB = B;
            }
        }
        return this;
    }

    public void expandBy(double d) {
        double distance = d;
        if (!isNull()) {
            double diagonalDistance = SQRT2 * distance;
            this.minX -= distance;
            this.maxX += distance;
            this.minY -= distance;
            this.maxY += distance;
            this.minA -= diagonalDistance;
            this.maxA += diagonalDistance;
            this.minB -= diagonalDistance;
            this.maxB += diagonalDistance;
            if (!isValid()) {
                setToNull();
            }
        }
    }

    private boolean isValid() {
        if (isNull()) {
            return true;
        }
        return this.minX <= this.maxX && this.minY <= this.maxY && this.minA <= this.maxA && this.minB <= this.maxB;
    }

    public boolean intersects(OctagonalEnvelope octagonalEnvelope) {
        OctagonalEnvelope other = octagonalEnvelope;
        if (isNull() || other.isNull()) {
            return false;
        }
        if (this.minX > other.maxX) {
            return false;
        }
        if (this.maxX < other.minX) {
            return false;
        }
        if (this.minY > other.maxY) {
            return false;
        }
        if (this.maxY < other.minY) {
            return false;
        }
        if (this.minA > other.maxA) {
            return false;
        }
        if (this.maxA < other.minA) {
            return false;
        }
        if (this.minB > other.maxB) {
            return false;
        }
        if (this.maxB < other.minB) {
            return false;
        }
        return true;
    }

    public boolean intersects(Coordinate coordinate) {
        Coordinate p = coordinate;
        if (this.minX > p.f412x) {
            return false;
        }
        if (this.maxX < p.f412x) {
            return false;
        }
        if (this.minY > p.f413y) {
            return false;
        }
        if (this.maxY < p.f413y) {
            return false;
        }
        double A = computeA(p.f412x, p.f413y);
        double B = computeB(p.f412x, p.f413y);
        if (this.minA > A) {
            return false;
        }
        if (this.maxA < A) {
            return false;
        }
        if (this.minB > B) {
            return false;
        }
        if (this.maxB < B) {
            return false;
        }
        return true;
    }

    public boolean contains(OctagonalEnvelope octagonalEnvelope) {
        OctagonalEnvelope other = octagonalEnvelope;
        if (isNull() || other.isNull()) {
            return false;
        }
        return other.minX >= this.minX && other.maxX <= this.maxX && other.minY >= this.minY && other.maxY <= this.maxY && other.minA >= this.minA && other.maxA <= this.maxA && other.minB >= this.minB && other.maxB <= this.maxB;
    }

    public Geometry toGeometry(GeometryFactory geometryFactory) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        Coordinate coordinate6;
        Coordinate coordinate7;
        Coordinate coordinate8;
        CoordinateList coordinateList;
        GeometryFactory geomFactory = geometryFactory;
        if (isNull()) {
            return geomFactory.createPoint((CoordinateSequence) null);
        }
        new Coordinate(this.minX, this.minA - this.minX);
        Coordinate px00 = coordinate;
        new Coordinate(this.minX, this.minX - this.minB);
        Coordinate px01 = coordinate2;
        new Coordinate(this.maxX, this.maxX - this.maxB);
        Coordinate px10 = coordinate3;
        new Coordinate(this.maxX, this.maxA - this.maxX);
        Coordinate px11 = coordinate4;
        new Coordinate(this.minA - this.minY, this.minY);
        Coordinate py00 = coordinate5;
        new Coordinate(this.minY + this.maxB, this.minY);
        Coordinate py01 = coordinate6;
        new Coordinate(this.maxY + this.minB, this.maxY);
        Coordinate py10 = coordinate7;
        new Coordinate(this.maxA - this.maxY, this.maxY);
        Coordinate py11 = coordinate8;
        PrecisionModel pm = geomFactory.getPrecisionModel();
        pm.makePrecise(px00);
        pm.makePrecise(px01);
        pm.makePrecise(px10);
        pm.makePrecise(px11);
        pm.makePrecise(py00);
        pm.makePrecise(py01);
        pm.makePrecise(py10);
        pm.makePrecise(py11);
        new CoordinateList();
        CoordinateList coordList = coordinateList;
        coordList.add(px00, false);
        coordList.add(px01, false);
        coordList.add(py10, false);
        coordList.add(py11, false);
        coordList.add(px11, false);
        coordList.add(px10, false);
        coordList.add(py01, false);
        coordList.add(py00, false);
        if (coordList.size() == 1) {
            return geomFactory.createPoint(px00);
        }
        if (coordList.size() == 2) {
            return geomFactory.createLineString(coordList.toCoordinateArray());
        }
        coordList.add(px00, false);
        return geomFactory.createPolygon(geomFactory.createLinearRing(coordList.toCoordinateArray()), (LinearRing[]) null);
    }

    private class BoundingOctagonComponentFilter implements GeometryComponentFilter {
        final /* synthetic */ OctagonalEnvelope this$0;

        private BoundingOctagonComponentFilter(OctagonalEnvelope octagonalEnvelope) {
            this.this$0 = octagonalEnvelope;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ BoundingOctagonComponentFilter(OctagonalEnvelope x0, C15431 r7) {
            this(x0);
            C15431 r2 = r7;
        }

        public void filter(Geometry geometry) {
            Geometry geom = geometry;
            if (geom instanceof LineString) {
                OctagonalEnvelope expandToInclude = this.this$0.expandToInclude(((LineString) geom).getCoordinateSequence());
            } else if (geom instanceof Point) {
                OctagonalEnvelope expandToInclude2 = this.this$0.expandToInclude(((Point) geom).getCoordinateSequence());
            }
        }
    }
}
