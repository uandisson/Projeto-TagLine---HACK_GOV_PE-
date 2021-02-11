package org.locationtech.jts.operation.buffer.validate;

import java.io.PrintStream;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

public class BufferResultValidator {
    private static final double MAX_ENV_DIFF_FRAC = 0.012d;
    private static boolean VERBOSE = false;
    private double distance;
    private Geometry errorIndicator = null;
    private Coordinate errorLocation = null;
    private String errorMsg = null;
    private Geometry input;
    private boolean isValid = true;
    private Geometry result;

    public static boolean isValid(Geometry g, double distance2, Geometry result2) {
        BufferResultValidator validator;
        new BufferResultValidator(g, distance2, result2);
        if (validator.isValid()) {
            return true;
        }
        return false;
    }

    public static String isValidMsg(Geometry g, double distance2, Geometry result2) {
        BufferResultValidator bufferResultValidator;
        new BufferResultValidator(g, distance2, result2);
        BufferResultValidator validator = bufferResultValidator;
        if (!validator.isValid()) {
            return validator.getErrorMessage();
        }
        return null;
    }

    public BufferResultValidator(Geometry input2, double distance2, Geometry result2) {
        this.input = input2;
        this.distance = distance2;
        this.result = result2;
    }

    public boolean isValid() {
        checkPolygonal();
        if (!this.isValid) {
            return this.isValid;
        }
        checkExpectedEmpty();
        if (!this.isValid) {
            return this.isValid;
        }
        checkEnvelope();
        if (!this.isValid) {
            return this.isValid;
        }
        checkArea();
        if (!this.isValid) {
            return this.isValid;
        }
        checkDistance();
        return this.isValid;
    }

    public String getErrorMessage() {
        return this.errorMsg;
    }

    public Coordinate getErrorLocation() {
        return this.errorLocation;
    }

    public Geometry getErrorIndicator() {
        return this.errorIndicator;
    }

    private void report(String str) {
        StringBuilder sb;
        String checkName = str;
        if (VERBOSE) {
            PrintStream printStream = System.out;
            new StringBuilder();
            printStream.println(sb.append("Check ").append(checkName).append(": ").append(this.isValid ? "passed" : "FAILED").toString());
        }
    }

    private void checkPolygonal() {
        if (!(this.result instanceof Polygon) && !(this.result instanceof MultiPolygon)) {
            this.isValid = false;
        }
        this.errorMsg = "Result is not polygonal";
        this.errorIndicator = this.result;
        report("Polygonal");
    }

    private void checkExpectedEmpty() {
        if (this.input.getDimension() < 2 && this.distance <= 0.0d) {
            if (!this.result.isEmpty()) {
                this.isValid = false;
                this.errorMsg = "Result is non-empty";
                this.errorIndicator = this.result;
            }
            report("ExpectedEmpty");
        }
    }

    private void checkEnvelope() {
        Envelope envelope;
        Envelope envelope2;
        if (this.distance >= 0.0d) {
            double padding = this.distance * MAX_ENV_DIFF_FRAC;
            if (padding == 0.0d) {
                padding = 0.001d;
            }
            new Envelope(this.input.getEnvelopeInternal());
            Envelope expectedEnv = envelope;
            expectedEnv.expandBy(this.distance);
            new Envelope(this.result.getEnvelopeInternal());
            Envelope bufEnv = envelope2;
            bufEnv.expandBy(padding);
            if (!bufEnv.contains(expectedEnv)) {
                this.isValid = false;
                this.errorMsg = "Buffer envelope is incorrect";
                this.errorIndicator = this.input.getFactory().toGeometry(bufEnv);
            }
            report("Envelope");
        }
    }

    private void checkArea() {
        double inputArea = this.input.getArea();
        double resultArea = this.result.getArea();
        if (this.distance > 0.0d && inputArea > resultArea) {
            this.isValid = false;
            this.errorMsg = "Area of positive buffer is smaller than input";
            this.errorIndicator = this.result;
        }
        if (this.distance < 0.0d && inputArea < resultArea) {
            this.isValid = false;
            this.errorMsg = "Area of negative buffer is larger than input";
            this.errorIndicator = this.result;
        }
        report("Area");
    }

    private void checkDistance() {
        BufferDistanceValidator bufferDistanceValidator;
        new BufferDistanceValidator(this.input, this.distance, this.result);
        BufferDistanceValidator distValid = bufferDistanceValidator;
        if (!distValid.isValid()) {
            this.isValid = false;
            this.errorMsg = distValid.getErrorMessage();
            this.errorLocation = distValid.getErrorLocation();
            this.errorIndicator = distValid.getErrorIndicator();
        }
        report("Distance");
    }
}
