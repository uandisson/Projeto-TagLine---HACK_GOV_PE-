package org.locationtech.jts.operation.buffer;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.TopologyException;
import org.locationtech.jts.math.MathUtil;
import org.locationtech.jts.noding.Noder;
import org.locationtech.jts.noding.ScaledNoder;
import org.locationtech.jts.noding.snapround.MCIndexSnapRounder;

public class BufferOp {
    public static final int CAP_BUTT = 2;
    public static final int CAP_FLAT = 2;
    public static final int CAP_ROUND = 1;
    public static final int CAP_SQUARE = 3;
    private static int MAX_PRECISION_DIGITS = 12;
    private Geometry argGeom;
    private BufferParameters bufParams;
    private double distance;
    private Geometry resultGeometry = null;
    private RuntimeException saveException;

    private static double precisionScaleFactor(Geometry g, double d, int i) {
        double distance2 = d;
        int maxPrecisionDigits = i;
        Envelope env = g.getEnvelopeInternal();
        return Math.pow(10.0d, (double) (maxPrecisionDigits - ((int) ((Math.log(MathUtil.max(Math.abs(env.getMaxX()), Math.abs(env.getMaxY()), Math.abs(env.getMinX()), Math.abs(env.getMinY())) + (2.0d * (distance2 > 0.0d ? distance2 : 0.0d))) / Math.log(10.0d)) + 1.0d))));
    }

    public static Geometry bufferOp(Geometry g, double distance2) {
        BufferOp gBuf;
        new BufferOp(g);
        return gBuf.getResultGeometry(distance2);
    }

    public static Geometry bufferOp(Geometry g, double distance2, BufferParameters params) {
        BufferOp bufOp;
        new BufferOp(g, params);
        return bufOp.getResultGeometry(distance2);
    }

    public static Geometry bufferOp(Geometry g, double distance2, int quadrantSegments) {
        BufferOp bufferOp;
        new BufferOp(g);
        BufferOp bufOp = bufferOp;
        bufOp.setQuadrantSegments(quadrantSegments);
        return bufOp.getResultGeometry(distance2);
    }

    public static Geometry bufferOp(Geometry g, double distance2, int quadrantSegments, int endCapStyle) {
        BufferOp bufferOp;
        new BufferOp(g);
        BufferOp bufOp = bufferOp;
        bufOp.setQuadrantSegments(quadrantSegments);
        bufOp.setEndCapStyle(endCapStyle);
        return bufOp.getResultGeometry(distance2);
    }

    public BufferOp(Geometry g) {
        BufferParameters bufferParameters;
        new BufferParameters();
        this.bufParams = bufferParameters;
        this.argGeom = g;
    }

    public BufferOp(Geometry g, BufferParameters bufParams2) {
        BufferParameters bufferParameters;
        new BufferParameters();
        this.bufParams = bufferParameters;
        this.argGeom = g;
        this.bufParams = bufParams2;
    }

    public void setEndCapStyle(int endCapStyle) {
        this.bufParams.setEndCapStyle(endCapStyle);
    }

    public void setQuadrantSegments(int quadrantSegments) {
        this.bufParams.setQuadrantSegments(quadrantSegments);
    }

    public Geometry getResultGeometry(double distance2) {
        this.distance = distance2;
        computeGeometry();
        return this.resultGeometry;
    }

    private void computeGeometry() {
        bufferOriginalPrecision();
        if (this.resultGeometry == null) {
            PrecisionModel argPM = this.argGeom.getFactory().getPrecisionModel();
            if (argPM.getType() == PrecisionModel.FIXED) {
                bufferFixedPrecision(argPM);
            } else {
                bufferReducedPrecision();
            }
        }
    }

    private void bufferReducedPrecision() {
        int precDigits = MAX_PRECISION_DIGITS;
        while (precDigits >= 0) {
            try {
                bufferReducedPrecision(precDigits);
            } catch (TopologyException e) {
                this.saveException = e;
            }
            if (this.resultGeometry == null) {
                precDigits--;
            } else {
                return;
            }
        }
        throw this.saveException;
    }

    private void bufferOriginalPrecision() {
        BufferBuilder bufBuilder;
        try {
            new BufferBuilder(this.bufParams);
            this.resultGeometry = bufBuilder.buffer(this.argGeom, this.distance);
        } catch (RuntimeException e) {
            this.saveException = e;
        }
    }

    private void bufferReducedPrecision(int precisionDigits) {
        PrecisionModel fixedPM;
        new PrecisionModel(precisionScaleFactor(this.argGeom, this.distance, precisionDigits));
        bufferFixedPrecision(fixedPM);
    }

    private void bufferFixedPrecision(PrecisionModel precisionModel) {
        Noder noder;
        Noder noder2;
        PrecisionModel precisionModel2;
        BufferBuilder bufferBuilder;
        PrecisionModel fixedPM = precisionModel;
        new PrecisionModel(1.0d);
        new MCIndexSnapRounder(precisionModel2);
        new ScaledNoder(noder2, fixedPM.getScale());
        new BufferBuilder(this.bufParams);
        BufferBuilder bufBuilder = bufferBuilder;
        bufBuilder.setWorkingPrecisionModel(fixedPM);
        bufBuilder.setNoder(noder);
        this.resultGeometry = bufBuilder.buffer(this.argGeom, this.distance);
    }
}
