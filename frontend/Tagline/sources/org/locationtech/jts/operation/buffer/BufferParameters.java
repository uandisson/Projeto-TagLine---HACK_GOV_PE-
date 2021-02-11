package org.locationtech.jts.operation.buffer;

public class BufferParameters {
    public static final int CAP_FLAT = 2;
    public static final int CAP_ROUND = 1;
    public static final int CAP_SQUARE = 3;
    public static final double DEFAULT_MITRE_LIMIT = 5.0d;
    public static final int DEFAULT_QUADRANT_SEGMENTS = 8;
    public static final double DEFAULT_SIMPLIFY_FACTOR = 0.01d;
    public static final int JOIN_BEVEL = 3;
    public static final int JOIN_MITRE = 2;
    public static final int JOIN_ROUND = 1;
    private int endCapStyle = 1;
    private boolean isSingleSided = false;
    private int joinStyle = 1;
    private double mitreLimit = 5.0d;
    private int quadrantSegments = 8;
    private double simplifyFactor = 0.01d;

    public BufferParameters() {
    }

    public BufferParameters(int quadrantSegments2) {
        setQuadrantSegments(quadrantSegments2);
    }

    public BufferParameters(int quadrantSegments2, int endCapStyle2) {
        setQuadrantSegments(quadrantSegments2);
        setEndCapStyle(endCapStyle2);
    }

    public BufferParameters(int quadrantSegments2, int endCapStyle2, int joinStyle2, double mitreLimit2) {
        setQuadrantSegments(quadrantSegments2);
        setEndCapStyle(endCapStyle2);
        setJoinStyle(joinStyle2);
        setMitreLimit(mitreLimit2);
    }

    public int getQuadrantSegments() {
        return this.quadrantSegments;
    }

    public void setQuadrantSegments(int i) {
        int quadSegs = i;
        this.quadrantSegments = quadSegs;
        if (this.quadrantSegments == 0) {
            this.joinStyle = 3;
        }
        if (this.quadrantSegments < 0) {
            this.joinStyle = 2;
            this.mitreLimit = (double) Math.abs(this.quadrantSegments);
        }
        if (quadSegs <= 0) {
            this.quadrantSegments = 1;
        }
        if (this.joinStyle != 1) {
            this.quadrantSegments = 8;
        }
    }

    public static double bufferDistanceError(int quadSegs) {
        return 1.0d - Math.cos((1.5707963267948966d / ((double) quadSegs)) / 2.0d);
    }

    public int getEndCapStyle() {
        return this.endCapStyle;
    }

    public void setEndCapStyle(int endCapStyle2) {
        int i = endCapStyle2;
        this.endCapStyle = i;
    }

    public int getJoinStyle() {
        return this.joinStyle;
    }

    public void setJoinStyle(int joinStyle2) {
        int i = joinStyle2;
        this.joinStyle = i;
    }

    public double getMitreLimit() {
        return this.mitreLimit;
    }

    public void setMitreLimit(double mitreLimit2) {
        double d = mitreLimit2;
        this.mitreLimit = d;
    }

    public void setSingleSided(boolean isSingleSided2) {
        boolean z = isSingleSided2;
        this.isSingleSided = z;
    }

    public boolean isSingleSided() {
        return this.isSingleSided;
    }

    public double getSimplifyFactor() {
        return this.simplifyFactor;
    }

    public void setSimplifyFactor(double d) {
        double simplifyFactor2 = d;
        this.simplifyFactor = simplifyFactor2 < 0.0d ? 0.0d : simplifyFactor2;
    }
}
